package com.github.privatech.minimessage.lexer

import com.github.privatech.minimessage.psi.MiniMessageTypes
import com.intellij.lexer.Lexer
import com.intellij.lexer.LexerPosition
import com.intellij.lexer.LexerPositionImpl
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.intellij.util.text.CharArrayUtil.fromSequenceWithoutCopying

class MiniMessageLexer() : Lexer() {
    private lateinit var myBuffer: CharSequence
    private var bufferArray: CharArray? = null
    private var bufferIndex = 0
    private var bufferEndOffset = 0
    private var tokenEndOffset = 0 // positioned after the last symbol of the current token
    private var tokenType: IElementType? = null
    private var tokenStartIndex = 0
    private var tokenEndIndex = 0
    private var state: Int = 0 // 0 - plain text, 1 - tag name, 2 - argument

    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        myBuffer = buffer
        bufferArray = fromSequenceWithoutCopying(buffer)
        bufferIndex = startOffset
        bufferEndOffset = endOffset
        tokenEndOffset = startOffset // reset token end offset to start of the buffer
    }

    override fun getState(): Int {
        return state
    }

    override fun getTokenType(): IElementType? {
        return tokenType
    }

    override fun getTokenStart(): Int {
        return tokenStartIndex
    }

    override fun getTokenEnd(): Int {
        return tokenEndIndex
    }

    fun getCurrentChar(): Char {
        return if (bufferArray != null && bufferIndex < bufferEndOffset) {
            bufferArray!![bufferIndex]
        } else {
            '\u0000' // return null character if out of bounds
        }
    }

    companion object {
        val TAG_NAME_EXITING_CHARS: Set<Char> = setOf('"', '\'', '>', ':')
        val PLAIN_TEXT_EXITING_CHARS: Set<Char> = setOf('\\', '§', '<', '>')
        val LEGACY_COLOR_CODES: Set<Char> = setOf('0'..'9', 'a'..'f', 'k'..'o', 'r'..'r')
            .flatMap { it.toList() }.toSet()
    }

    private fun setSingleCharTokenType(type: IElementType) {
        tokenType = type
        bufferIndex++
        tokenEndIndex = bufferIndex
    }

    override fun advance() {
        tokenStartIndex = bufferIndex
        when (state) {
            0 -> { // Plain text state
                when (getCurrentChar()) {
                    '\\' -> {
                        setSingleCharTokenType(MiniMessageTypes.ESCAPE)
                        return
                    }
                    '§' -> {
                        setSingleCharTokenType(MiniMessageTypes.SECTION)
                        state = 3
                        return
                    }
                    '<' -> {
                        setSingleCharTokenType(MiniMessageTypes.LT)
                        state = 1
                        return
                    }
                    '>' -> {
                        setSingleCharTokenType(MiniMessageTypes.GT)
                        return
                    }
                }
                if (getCurrentChar().isWhitespace()) {
                    while (bufferIndex < bufferEndOffset && getCurrentChar().isWhitespace()) {
                        bufferIndex++
                    }
                    tokenType = TokenType.WHITE_SPACE
                    tokenEndIndex = bufferIndex
                    return
                }
                while (bufferIndex < bufferEndOffset && getCurrentChar() !in PLAIN_TEXT_EXITING_CHARS) {
                    bufferIndex++
                }
                tokenType = MiniMessageTypes.PLAIN_TEXT
                tokenEndIndex = bufferIndex
            }
            1 -> { // Tag state
                while (bufferIndex < bufferEndOffset && getCurrentChar() !in TAG_NAME_EXITING_CHARS && !getCurrentChar().isWhitespace()) {
                    bufferIndex++
                }
                if (tokenStartIndex != bufferIndex) {
                    tokenType = MiniMessageTypes.TAG_NAME
                    tokenEndIndex = bufferIndex
                }
                state = if (getCurrentChar() == ':') 2 else 0
            }
            2 -> { // Argument state
                if (getCurrentChar() == '"' || getCurrentChar() == '\'') {
                    val quote = getCurrentChar()
                    var escaped = false
                    bufferIndex++
                    while (bufferIndex < bufferEndOffset && getCurrentChar() != quote && !escaped) {
                        bufferIndex++
                        escaped = getCurrentChar() == '\\'
                    }
                    if (bufferIndex < bufferEndOffset) {
                        bufferIndex++ // consume the closing quote
                    }
                    tokenType = MiniMessageTypes.STRING
                    tokenEndIndex = bufferIndex
                } else {
                    while (bufferIndex < bufferEndOffset && !getCurrentChar().isWhitespace()) {
                        bufferIndex++
                    }
                    tokenType = MiniMessageTypes.ARGUMENT
                    tokenEndIndex = bufferIndex
                }
                state = 1 // return to tag state after processing argument
            }
            3 -> { // Color code state
                if (getCurrentChar() in LEGACY_COLOR_CODES) {
                    setSingleCharTokenType(MiniMessageTypes.LEGACY_COLOR_CODE)
                } else {
                    state = 0
                }
            }
        }
    }

    override fun getCurrentPosition(): LexerPosition {
        return LexerPositionImpl(tokenStart, state)
    }

    override fun restore(position: LexerPosition) {
        start(bufferSequence, position.offset, bufferEnd, position.state)
    }

    override fun getBufferSequence(): CharSequence {
        return myBuffer
    }

    override fun getBufferEnd(): Int {
        return bufferEndOffset
    }

}
package com.github.privatech.minimessage

import com.github.privatech.minimessage.psi.MiniMessageTypes
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType

class MiniMessageSyntaxHighlighter : SyntaxHighlighterBase() {

    val TAG: TextAttributesKey = createTextAttributesKey("MINIMESSAGE_TAG", DefaultLanguageHighlighterColors.KEYWORD)
    val STRING: TextAttributesKey = createTextAttributesKey("MINIMESSAGE_STRING", DefaultLanguageHighlighterColors.STRING)
    val ARGUMENT: TextAttributesKey = createTextAttributesKey("MINIMESSAGE_ARGUMENT", DefaultLanguageHighlighterColors.MARKUP_ATTRIBUTE)
    val BAD_CHARACTER: TextAttributesKey = createTextAttributesKey("MINIMESSAGE_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)

    val BAD_CHAR_KEYS: Array<TextAttributesKey> = arrayOf(BAD_CHARACTER)
    val TAG_KEYS: Array<TextAttributesKey> = arrayOf(TAG)
    val STRING_KEYS: Array<TextAttributesKey> = arrayOf(STRING)
    val ARGUMENT_KEYS: Array<TextAttributesKey> = arrayOf(ARGUMENT)
    val EMPTY_KEYS: Array<TextAttributesKey> = emptyArray()

    override fun getHighlightingLexer(): Lexer {
        return MiniMessageLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType?): Array<out TextAttributesKey?> {
        return when (tokenType) {
            MiniMessageTypes.TAG_NAME, MiniMessageTypes.LT, MiniMessageTypes.GT, MiniMessageTypes.SLASH, MiniMessageTypes.COLON -> TAG_KEYS
            MiniMessageTypes.STRING -> STRING_KEYS
            MiniMessageTypes.ARGUMENT -> ARGUMENT_KEYS
            TokenType.BAD_CHARACTER -> BAD_CHAR_KEYS
            else -> EMPTY_KEYS
        }
    }
}
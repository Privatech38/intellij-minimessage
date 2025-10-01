package dev.privatech.plugin.minimessage

import dev.privatech.plugin.minimessage.psi.MiniMessageTypes
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.XmlHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType

class MiniMessageSyntaxHighlighter : SyntaxHighlighterBase() {

    companion object {
        @JvmField
        val PLAIN_TEXT: TextAttributesKey = createTextAttributesKey("MINIMESSAGE_PLAIN_TEXT", HighlighterColors.TEXT)
        @JvmField
        val LEGACY_FORMAT: TextAttributesKey = createTextAttributesKey("MINIMESSAGE_LEGACY_FORMAT",
            XmlHighlighterColors.HTML_ENTITY_REFERENCE)
        @JvmField
        val TAG: TextAttributesKey = createTextAttributesKey("MINIMESSAGE_TAG", XmlHighlighterColors.HTML_TAG)
        @JvmField
        val TAG_NAME: TextAttributesKey = createTextAttributesKey("MINIMESSAGE_TAG_NAME", XmlHighlighterColors.HTML_TAG_NAME)
        @JvmField
        val CUSTOM_TAG_NAME: TextAttributesKey = createTextAttributesKey("MINIMESSAGE_CUSTOM_TAG_NAME", XmlHighlighterColors.HTML_CUSTOM_TAG_NAME)
        @JvmField
        val STRING: TextAttributesKey =
            createTextAttributesKey("MINIMESSAGE_STRING", XmlHighlighterColors.HTML_ATTRIBUTE_VALUE)
        @JvmField
        val ARGUMENT: TextAttributesKey =
            createTextAttributesKey("MINIMESSAGE_ARGUMENT", XmlHighlighterColors.HTML_ATTRIBUTE_NAME)
        @JvmField
        val BAD_CHARACTER: TextAttributesKey =
            createTextAttributesKey("MINIMESSAGE_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)

        @JvmField
        val BAD_CHAR_KEYS: Array<TextAttributesKey> = arrayOf(BAD_CHARACTER)
        @JvmField
        val PLAIN_TEXT_KEYS: Array<TextAttributesKey> = arrayOf(PLAIN_TEXT)
        @JvmField
        val LEGACY_FORMAT_KEYS: Array<TextAttributesKey> = arrayOf(LEGACY_FORMAT)
        @JvmField
        val TAG_KEYS: Array<TextAttributesKey> = arrayOf(TAG)
        @JvmField
        val TAG_NAME_KEYS: Array<TextAttributesKey> = arrayOf(TAG, TAG_NAME)
        @JvmField
        val CUSTOM_TAG_NAME_KEYS: Array<TextAttributesKey> = arrayOf(TAG, CUSTOM_TAG_NAME)
        @JvmField
        val STRING_KEYS: Array<TextAttributesKey> = arrayOf(TAG, STRING)
        @JvmField
        val ARGUMENT_KEYS: Array<TextAttributesKey> = arrayOf(TAG, ARGUMENT)
        @JvmField
        val EMPTY_KEYS: Array<TextAttributesKey> = emptyArray()
    }

    override fun getHighlightingLexer(): Lexer {
        return MiniMessageLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType?): Array<out TextAttributesKey?> {
        return when (tokenType) {
            MiniMessageTypes.PLAIN_TEXT -> PLAIN_TEXT_KEYS
            MiniMessageTypes.LEGACY_FORMATTING_CODE -> LEGACY_FORMAT_KEYS
            MiniMessageTypes.COLON, MiniMessageTypes.SLASH, MiniMessageTypes.LT, MiniMessageTypes.GT -> TAG_KEYS
            MiniMessageTypes.TAG_NAME -> TAG_NAME_KEYS
            MiniMessageTypes.CUSTOM_TAG_NAME -> CUSTOM_TAG_NAME_KEYS
            MiniMessageTypes.STRING_TEXT, MiniMessageTypes.QUOTATION -> STRING_KEYS
            MiniMessageTypes.ARGUMENT -> ARGUMENT_KEYS
            TokenType.BAD_CHARACTER -> BAD_CHAR_KEYS
            else -> EMPTY_KEYS
        }
    }
}
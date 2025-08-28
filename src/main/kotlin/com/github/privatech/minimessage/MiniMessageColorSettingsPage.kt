package com.github.privatech.minimessage

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import com.intellij.openapi.util.NlsContexts
import org.jetbrains.annotations.NonNls
import javax.swing.Icon

class MiniMessageColorSettingsPage : ColorSettingsPage {

    val DESCRIPTORS: Array<AttributesDescriptor> = arrayOf(
        AttributesDescriptor("Plain text", MiniMessageSyntaxHighlighter.PLAIN_TEXT),
        AttributesDescriptor("Tag", MiniMessageSyntaxHighlighter.TAG),
        AttributesDescriptor("Tag//Name", MiniMessageSyntaxHighlighter.TAG_NAME),
        AttributesDescriptor("Tag//Custom name", MiniMessageSyntaxHighlighter.CUSTOM_TAG_NAME),
        AttributesDescriptor("Tag//String", MiniMessageSyntaxHighlighter.STRING),
        AttributesDescriptor("Tag//Argument", MiniMessageSyntaxHighlighter.ARGUMENT),
        AttributesDescriptor("Bad character", MiniMessageSyntaxHighlighter.BAD_CHARACTER),
        AttributesDescriptor("Legacy format", MiniMessageSyntaxHighlighter.LEGACY_FORMAT)
    )

    override fun getIcon(): Icon? {
        return null
    }

    override fun getHighlighter(): SyntaxHighlighter {
        return MiniMessageSyntaxHighlighter()
    }

    override fun getDemoText(): @NonNls String {
        return """
            <red>This is red</red>
            <hover:show_text:'Hello!'>Hover over me!</hover>
            §a§llegacy §rformatted text
        """.trimIndent()
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String?, TextAttributesKey?>? {
        return null
    }

    override fun getAttributeDescriptors(): Array<out AttributesDescriptor?> {
        return DESCRIPTORS
    }

    override fun getColorDescriptors(): Array<out ColorDescriptor?> {
        return ColorDescriptor.EMPTY_ARRAY
    }

    override fun getDisplayName(): @NlsContexts.ConfigurableName String {
        return "MiniMessage"
    }
}
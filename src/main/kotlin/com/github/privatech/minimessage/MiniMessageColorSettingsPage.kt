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
        AttributesDescriptor("Tag", MiniMessageSyntaxHighlighter.TAG),
        AttributesDescriptor("Tag//String", MiniMessageSyntaxHighlighter.STRING),
        AttributesDescriptor("Tag//Argument", MiniMessageSyntaxHighlighter.ARGUMENT),
        AttributesDescriptor("Bad Character", MiniMessageSyntaxHighlighter.BAD_CHARACTER)
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
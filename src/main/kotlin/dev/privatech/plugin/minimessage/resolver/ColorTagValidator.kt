package dev.privatech.plugin.minimessage.resolver

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import dev.privatech.plugin.minimessage.psi.MiniMessageTagArgument
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import java.util.*

class ColorTagValidator : TagValidator() {
    override fun validate(tagName: PsiElement, arguments: LinkedList<MiniMessageTagArgument>, holder: AnnotationHolder) {
        if (!isColorOrAbbreviation(tagName.text)) {
            return
        }
        val colorArg: MiniMessageTagArgument? = arguments.poll()
        if (colorArg == null) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Missing color argument").range(tagName).create()
            return
        }
    }

    override fun has(tagName: String): Boolean {
        return NamedTextColor.NAMES.value(tagName) != null
            || isColorOrAbbreviation(tagName)
            || COLOR_ALIASES.containsKey(tagName)
            || tagName.matches(Regex("#[0-9a-fA-F]{6}")) // Hex color code

    }

    private fun isColorOrAbbreviation(tagName: String): Boolean {
        return tagName == "color" || tagName == "colour" || tagName == "c"
    }

    companion object {
        private val COLOR_ALIASES: Map<String, TextColor> = mapOf(
            "dark_grey" to NamedTextColor.DARK_GRAY,
            "grey" to NamedTextColor.GRAY
        )

        @JvmField
        val INSTANCE: ColorTagValidator = ColorTagValidator()
    }
}
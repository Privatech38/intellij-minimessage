package dev.privatech.plugin.minimessage.tag.validator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor

class ColorTagValidator : TagValidator() {
    override fun validate(tagName: PsiElement, arguments: ArgumentQueue, holder: AnnotationHolder) {
        if (!isColorOrAbbreviation(tagName.text)) {
            return
        }
        val colorArg = arguments.popOr(tagName, "The 'color' tag requires a color argument") ?: return
        val trimmed = colorArg.trimmedArgument
        if (!isColor(trimmed)) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Unknown color: '$trimmed'").range(colorArg.normalizeTextRange()).create()
        }
    }

    override fun has(tagName: String): Boolean {
        return isColor(tagName)
            || isColorOrAbbreviation(tagName)

    }

    private fun isColorOrAbbreviation(tagName: String): Boolean {
        return tagName == "color" || tagName == "colour" || tagName == "c"
    }

    companion object {
        @JvmField
        val COLOR_ALIASES: Map<String, TextColor> = mapOf(
            "dark_grey" to NamedTextColor.DARK_GRAY,
            "grey" to NamedTextColor.GRAY
        )
    }
}
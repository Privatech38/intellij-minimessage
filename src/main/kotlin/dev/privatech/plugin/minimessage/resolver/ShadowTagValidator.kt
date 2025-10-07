package dev.privatech.plugin.minimessage.resolver

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import dev.privatech.plugin.minimessage.psi.MiniMessageTagArgument
import dev.privatech.plugin.minimessage.resolver.ColorTagValidator.Companion.COLOR_ALIASES
import net.kyori.adventure.text.format.NamedTextColor
import java.util.LinkedList

class ShadowTagValidator : TagValidator() {

    override fun validate(
        tagName: PsiElement,
        arguments: LinkedList<MiniMessageTagArgument>,
        holder: AnnotationHolder
    ) {
        if (tagName.text == "!shadow") {
            return
        }
        val colorArg = arguments.pollFirst()
        if (colorArg == null) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Missing color argument")
                .range(tagName).create()
            return
        }
        val trimmedColor = colorArg.trimmedArgument
        if (NamedTextColor.NAMES.value(trimmedColor) == null && !trimmedColor.matches(Regex("#([0-9a-fA-F]{6}|[0-9a-fA-F]{8})"))
            && !COLOR_ALIASES.containsKey(trimmedColor)
        ) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Unknown color: '$trimmedColor'").range(colorArg.normalizeTextRange()).create()
        }
        val opacityArg = arguments.pollFirst()
        if (opacityArg != null) {
            val opacityTrimmed = opacityArg.trimmedArgument
            val opacity = opacityTrimmed.toFloatOrNull()
            if (opacity == null || opacity < 0 || opacity > 1) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Opacity must be a floating point number between 0 and 1")
                    .range(opacityArg.normalizeTextRange()).create()
            } else if (trimmedColor.length == 9) {
                holder.newAnnotation(HighlightSeverity.WARNING, "Opacity argument is ignored when using 8-digit hex color")
                    .range(opacityArg.normalizeTextRange()).create()
            }
        }
    }

    override fun has(tagName: String): Boolean {
        return tagName == "shadow" || tagName == "!shadow"
    }

    companion object {
        val INSTANCE: ShadowTagValidator = ShadowTagValidator()
    }

}
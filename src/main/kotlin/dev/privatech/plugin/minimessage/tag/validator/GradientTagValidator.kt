package dev.privatech.plugin.minimessage.tag.validator

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement

class GradientTagValidator : TagValidator() {

    override fun validate(
        tagName: PsiElement,
        arguments: ArgumentQueue,
        holder: AnnotationHolder
    ) {
        while (!arguments.isEmpty()) {
            val arg = arguments.pop()
            val trimmedArg = arg.trimmedArgument
            if (arguments.isNotEmpty()) {
                if (!isColor(trimmedArg)) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "Invalid color argument: '$trimmedArg'")
                        .range(arg.normalizeTextRange())
                        .create()
                }
            } else {
                if (!isColor(trimmedArg)) {
                    val phase = trimmedArg.toFloatOrNull()
                    if (phase != null) {
                        if (phase < -1 || phase > 1) {
                            holder.newAnnotation(
                                HighlightSeverity.ERROR,
                                "Phase argument must be between -1 and 1: '$trimmedArg'"
                            )
                                .range(arg.normalizeTextRange())
                                .create()
                        }
                    } else {
                        holder.newAnnotation(HighlightSeverity.ERROR, "Invalid color or phase argument: '$trimmedArg'")
                            .range(arg.normalizeTextRange())
                            .create()
                    }
                }
            }
        }
    }

    override fun has(tagName: String): Boolean {
        return tagName == "gradient" || tagName == "transition"
    }

    override fun tags(): Set<String> = TAG_NAMES

    override fun tagLookupElements(): Iterable<LookupElementBuilder> {
        return TAG_LOOKUPS
    }

    companion object {
        private val TAG_NAMES = setOf("gradient", "transition")
        private val TAG_LOOKUPS = listOf(
            LookupElementBuilder.create("gradient").withTypeText("Gradient"),
            LookupElementBuilder.create("transition").withTypeText("Transition")
        ).map { it.withTailText("[:color]...[:phase]") }
    }

}
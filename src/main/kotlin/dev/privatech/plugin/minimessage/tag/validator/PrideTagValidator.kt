package dev.privatech.plugin.minimessage.tag.validator

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement

class PrideTagValidator : TagValidator() {
    override fun validate(
        tagName: PsiElement,
        arguments: ArgumentQueue,
        holder: AnnotationHolder
    ) {
        val flag = arguments.pollFirst()
        if (flag != null) {
            val trimmedFlag = flag.trimmedArgument
            if (trimmedFlag !in PRIDE_FLAGS) {
                val phase = trimmedFlag.toDoubleOrNull()
                if (phase == null) {
                    holder.newAnnotation(
                        HighlightSeverity.ERROR,
                        "Invalid pride flag or phase: '$trimmedFlag'"
                    )
                        .range(flag.normalizeTextRange())
                        .create()
                } else if (phase < -1.0 || phase > 1.0) {
                    holder.newAnnotation(
                        HighlightSeverity.ERROR,
                        "Pride phase must be between -1.0 and 1.0: '$trimmedFlag'"
                    )
                        .range(flag.normalizeTextRange())
                        .create()
                }
            }
        }
    }

    override fun has(tagName: String): Boolean {
        return tagName == "pride"
    }

    override fun tags(): Set<String> = TAG_NAMES
    override fun tagLookupElements(): Iterable<LookupElementBuilder> {
        return TAG_LOOKUPS
    }

    companion object {
        private val TAG_NAMES = setOf("pride")
        private val PRIDE_FLAGS = setOf(
            "pride", "progress", "trans", "bi", "pan", "nb", "lesbian", "ace", "agender",
            "demisexual", "genderqueer", "genderfluid", "intersex", "aro", "baker", "philly",
            "queer", "gay", "bigender", "demigender"
        )

        private val TAG_LOOKUPS = TAG_NAMES.map {
            LookupElementBuilder.create(it)
                .withTypeText("Pride")
                .withTailText("[:flag|phase]", true)
        }
    }
}
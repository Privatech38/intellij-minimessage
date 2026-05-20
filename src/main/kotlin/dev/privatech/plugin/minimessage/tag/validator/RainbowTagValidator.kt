package dev.privatech.plugin.minimessage.tag.validator

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement

class RainbowTagValidator : TagValidator() {

    override fun validate(
        tagName: PsiElement,
        arguments: ArgumentQueue,
        holder: AnnotationHolder
    ) {
        val arg = arguments.pollFirst() ?: return
        val trimmedArg = arg.trimmedArgument
        if (!Regex("!?[0-9]*").matches(trimmedArg)) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Invalid phase argument: '$trimmedArg'")
                .range(arg.normalizeTextRange())
                .create()
        }
    }

    override fun has(tagName: String): Boolean {
        return tagName == "rainbow"
    }

    override fun tags(): Set<String> = TAG_NAMES
    override fun tagLookupElements(): Iterable<LookupElementBuilder> {
        return TAG_LOOKUPS
    }

    companion object{
        private val TAG_NAMES = setOf("rainbow")
        private val TAG_LOOKUPS = TAG_NAMES.map {
            LookupElementBuilder.create(it)
                .withTypeText("Rainbow")
                .withTailText(":[!][phase]", true)
        }
    }

}
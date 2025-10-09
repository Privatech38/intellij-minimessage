package dev.privatech.plugin.minimessage.tag.validator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement

class SelectorTagValidator : TagValidator(true) {

    override fun validate(
        tagName: PsiElement,
        arguments: ArgumentQueue,
        holder: AnnotationHolder
    ) {
        val selector = arguments.popOr(tagName, "The Selector tag requires a selector argument") ?: return
        val trimmedSelector = selector.trimmedArgument
        if (!Regex("@[pnraes](\\[.*])?$").matches(trimmedSelector)) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Invalid selector: '$trimmedSelector'")
                .range(selector.normalizeTextRange())
                .create()
        }
        arguments.pollFirst()
    }

    override fun has(tagName: String): Boolean {
        return tagName == "selector" || tagName == "sel"
    }


}
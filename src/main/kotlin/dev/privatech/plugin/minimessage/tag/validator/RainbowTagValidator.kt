package dev.privatech.plugin.minimessage.tag.validator

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

}
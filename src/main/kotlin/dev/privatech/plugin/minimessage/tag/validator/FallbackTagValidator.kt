package dev.privatech.plugin.minimessage.tag.validator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement

class FallbackTagValidator : TagValidator(true) {

    override fun validate(
        tagName: PsiElement,
        arguments: ArgumentQueue,
        holder: AnnotationHolder
    ) {
        val key = arguments.popOr(tagName, "The Fallback tag requires a translation key argument") ?: return
        val trimmedKey = key.trimmedArgument
        if (!Regex("[a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)*").matches(trimmedKey)) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Invalid translation key: '$trimmedKey'")
                .range(key.normalizeTextRange())
                .create()
        }
        arguments.popOr(tagName, "The Fallback tag requires a fallback text argument") ?: return
        arguments.clear()
    }

    override fun has(tagName: String): Boolean {
        return tagName in TAG_NAMES
    }

    companion object {
        private val TAG_NAMES = setOf("lang_or", "tr_or", "translate_or")
    }
}
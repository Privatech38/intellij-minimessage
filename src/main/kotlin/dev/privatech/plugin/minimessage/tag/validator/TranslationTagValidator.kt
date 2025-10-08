package dev.privatech.plugin.minimessage.tag.validator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement

class TranslationTagValidator : TagValidator(true) {
    override fun validate(
        tagName: PsiElement,
        arguments: ArgumentQueue,
        holder: AnnotationHolder
    ) {
        val key = arguments.popOr(tagName, "The 'lang' tag requires a translation key argument") ?: return
        val trimmedKey = key.trimmedArgument
        if (!Regex("[a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)*").matches(trimmedKey)) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Invalid translation key: '$trimmedKey'")
                .range(key.normalizeTextRange())
                .create()
        }
        arguments.clear()
    }

    override fun has(tagName: String): Boolean {
        return tagName == "lang"
    }
}
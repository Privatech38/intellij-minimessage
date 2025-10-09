package dev.privatech.plugin.minimessage.tag.validator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement

class KeybindTagValidator : TagValidator(true) {
    override fun validate(
        tagName: PsiElement,
        arguments: ArgumentQueue,
        holder: AnnotationHolder
    ) {
        val arg = arguments.popOr(tagName, "The 'key' tag requires a keybind identifier argument") ?: return
        val trimmedKey = arg.trimmedArgument
        if (!Regex("^key\\.[a-zA-Z0-9]+$").matches(trimmedKey)) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Invalid keybind identifier: '$trimmedKey'")
                .range(arg.normalizeTextRange())
                .create()
        }
    }

    override fun has(tagName: String): Boolean {
        return tagName == "key"
    }
}
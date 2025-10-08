package dev.privatech.plugin.minimessage.resolver

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import dev.privatech.plugin.minimessage.psi.MiniMessageTagArgument
import java.util.LinkedList

class KeybindTagValidator : TagValidator(true) {
    override fun validate(
        tagName: PsiElement,
        arguments: LinkedList<MiniMessageTagArgument>,
        holder: AnnotationHolder
    ) {
        if (arguments.isEmpty()) {
            holder.newAnnotation(HighlightSeverity.ERROR, "The 'key' tag requires a keybind identifier argument")
                .range(tagName)
                .create()
            return
        }
        val arg = arguments.pop()
        val trimmedKey = arg.trimmedArgument
        if (!Regex("^key\\.[a-zA-Z]+$").matches(trimmedKey)) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Invalid keybind identifier: '$trimmedKey'")
                .range(arg.normalizeTextRange())
                .create()
        }
    }

    override fun has(tagName: String): Boolean {
        return tagName == "key"
    }
}
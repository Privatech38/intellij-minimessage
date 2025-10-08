package dev.privatech.plugin.minimessage.tag.validator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.psi.PsiElement
import dev.privatech.plugin.minimessage.psi.MiniMessageTagArgument
import java.util.LinkedList

class ArgumentQueue(val holder: AnnotationHolder, arguments: Collection<MiniMessageTagArgument>) : LinkedList<MiniMessageTagArgument>(arguments) {

    fun popOr(element: PsiElement, errorMessage: String): String? {
        if (this.isEmpty()) {
            holder.newAnnotation(com.intellij.lang.annotation.HighlightSeverity.ERROR, errorMessage)
                .range(if (element is MiniMessageTagArgument) element.normalizeTextRange() else element.textRange)
                .create()
            return null
        }
        return this.pop().trimmedArgument
    }

}
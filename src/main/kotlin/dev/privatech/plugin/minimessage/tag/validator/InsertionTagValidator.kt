package dev.privatech.plugin.minimessage.tag.validator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.psi.PsiElement

class InsertionTagValidator : TagValidator() {
    override fun validate(
        tagName: PsiElement,
        arguments: ArgumentQueue,
        holder: AnnotationHolder
    ) {
        arguments.popOr(tagName, "The Insertion tag requires a text argument")
    }

    override fun has(tagName: String): Boolean {
        return tagName == "insert"
    }
}
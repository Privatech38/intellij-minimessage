package dev.privatech.plugin.minimessage.tag.validator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.psi.PsiElement

class ScoreTagValidator : TagValidator(true) {
    override fun validate(
        tagName: PsiElement,
        arguments: ArgumentQueue,
        holder: AnnotationHolder
    ) {
        arguments.popOr(tagName, "The Score tag requires a score holder name argument") ?: return
        arguments.popOr(tagName, "The Score tag requires an objective name argument") ?: return
    }

    override fun has(tagName: String): Boolean {
        return tagName == "score"
    }
}
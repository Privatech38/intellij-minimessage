package com.github.privatech.minimessage.annotator

import com.github.privatech.minimessage.MiniMessageSyntaxHighlighter
import com.github.privatech.minimessage.psi.MiniMessageEmptyTag
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement

/**
 * This annotator colors LT and GT of tags.
 */
class MiniMessageSyntaxColorAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element is MiniMessageEmptyTag)
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(element).textAttributes(MiniMessageSyntaxHighlighter.PLAIN_TEXT).create()
    }
}
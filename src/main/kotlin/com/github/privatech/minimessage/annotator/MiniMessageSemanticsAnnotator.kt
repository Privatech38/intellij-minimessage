package com.github.privatech.minimessage.annotator

import com.github.privatech.minimessage.psi.MiniMessageTypes
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement

class MiniMessageSemanticsAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element.node.elementType == MiniMessageTypes.LEGACY_FORMATTING_CODE)
            holder.newAnnotation(HighlightSeverity.ERROR, "Legacy formatting codes are deprecated")
                .highlightType(ProblemHighlightType.ERROR)
                .range(element).create()

    }

}
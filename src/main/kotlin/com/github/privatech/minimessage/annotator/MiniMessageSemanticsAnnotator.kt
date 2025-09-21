package com.github.privatech.minimessage.annotator

import com.github.privatech.minimessage.psi.MiniMessageOpeningTag
import com.github.privatech.minimessage.psi.MiniMessageTypes
import com.github.privatech.minimessage.validation.ValidatorRegistry.Companion.getValidator
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement

class MiniMessageSemanticsAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element.node.elementType == MiniMessageTypes.LEGACY_FORMATTING_CODE)
            holder.newAnnotation(HighlightSeverity.ERROR, "Legacy formatting codes are deprecated " +
                    "and not allowed inside MiniMessage strings")
                .highlightType(ProblemHighlightType.LIKE_MARKED_FOR_REMOVAL)
                .range(element).create()
        // Annotate standard tags
        if (element is MiniMessageOpeningTag) {
            val tagName = element.tagName?.text ?: return
            val validator = getValidator(tagName) ?: return
            validator.annotate(element, holder)
        }
    }

}
package com.github.privatech.minimessage.validation

import com.github.privatech.minimessage.psi.MiniMessageOpeningTag
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity

abstract class TagValidator {

    abstract fun has(tagName: String): Boolean

    abstract fun annotate(element: MiniMessageOpeningTag, holder: AnnotationHolder)

    fun missingArgument(message: String, element: MiniMessageOpeningTag, holder: AnnotationHolder) {
        holder.newAnnotation(HighlightSeverity.ERROR, message)
            .range(element.tagName ?: element)
            .create()
    }

}
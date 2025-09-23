package com.github.privatech.minimessage.validation

import com.github.privatech.minimessage.psi.MiniMessageOpeningTag
import com.github.privatech.minimessage.validation.argument.Argument
import com.github.privatech.minimessage.validation.argument.SequentualArgumentValidator
import com.intellij.lang.annotation.AnnotationHolder
import kotlinx.collections.immutable.toPersistentSet

abstract class SequentalTagValidator(override val requiresArg: Boolean, override val arguments: Set<Argument>) : SequentualArgumentValidator {

    constructor(requiresArg: Boolean, vararg arguments: Argument) : this(requiresArg, arguments.toPersistentSet())

    abstract fun has(tagName: String): Boolean

    fun annotate(element: MiniMessageOpeningTag, holder: AnnotationHolder) {
        val argumentIterator = element.tagArgumentList.iterator()
        validateNextArgument(element, argumentIterator, holder)
    }

}
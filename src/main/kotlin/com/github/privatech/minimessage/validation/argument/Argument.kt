package com.github.privatech.minimessage.validation.argument

import com.github.privatech.minimessage.psi.MiniMessageTagArgument
import com.intellij.lang.annotation.AnnotationHolder

abstract class Argument(override val requiresArg: Boolean, val name: String, override val arguments: Set<Argument>) : SequentualArgumentValidator {

    abstract fun annotate(element: MiniMessageTagArgument, holder: AnnotationHolder)

    abstract fun matches(argument: String): Boolean

}
package com.github.privatech.minimessage.validation.argument

import com.github.privatech.minimessage.psi.MiniMessageTagArgument
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity

class LiteralArgument(name: String, requiredArg: Boolean, arguments: Set<Argument>) : Argument(requiredArg, name, arguments) {

    override val multiArgumentsErrorMessage: String? = "literal argument does not support multiple arguments"

    override fun annotate(
        element: MiniMessageTagArgument,
        holder: AnnotationHolder
    ) {
        holder.newAnnotation(HighlightSeverity.ERROR, "Invalid argument, expected $name")
            .range(element)
            .create()
    }

    override fun matches(argument: String): Boolean {
        return argument == name
    }


}
package com.github.privatech.minimessage.validation.argument

import com.github.privatech.minimessage.psi.MiniMessageOpeningTag
import com.github.privatech.minimessage.psi.MiniMessageTagArgument
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import java.util.stream.Collectors

interface SequentualArgumentValidator {

    val requiresArg: Boolean
    val arguments: Set<Argument>

    val multiArgumentsErrorMessage: String?

    fun validateNextArgument(currentElement: PsiElement, argumentIterator: Iterator<MiniMessageTagArgument>, holder: AnnotationHolder) {
        if (!argumentIterator.hasNext()) {
            if (requiresArg && arguments.isNotEmpty()) {
                if (arguments.size == 1) {
                    val arg = arguments.first()
                    missingArgument("Missing required ${arg.name} argument", currentElement, holder)
                } else {
                    missingArgument("Missing an argument, either ${arguments.stream().map { argument -> argument.name }
                        .collect(Collectors.joining(", "))}" +
                            " is required", currentElement, holder)
                }
            }
            return
        }
        if (arguments.isEmpty()) {
            argumentIterator.forEachRemaining { argument ->
                holder.newAnnotation(HighlightSeverity.WARNING, "Unused argument")
                    .range(argument)
                    .create()
            }
            return
        }
        val argument: MiniMessageTagArgument = argumentIterator.next()
        val argText = argument.toString()
        if (arguments.size == 1) {
            val arg = arguments.first()
            if (arg.matches(argText))
                arg.validateNextArgument(argument, argumentIterator, holder)
            else
                arg.annotate(argument, holder)
            return
        } else {
            val matchedArg = arguments.firstOrNull { arg -> arg.matches(argText) }
            if (matchedArg == null) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Unknown argument, expected either " +
                        "${arguments.stream().map { arg -> arg.name }
                    .collect(Collectors.joining(", "))}")
                    .range(argument)
                    .create()
                return
            } else {
                matchedArg.validateNextArgument(argument, argumentIterator, holder)
            }
        }
    }

    private fun missingArgument(message: String, element: PsiElement, holder: AnnotationHolder) {
        holder.newAnnotation(HighlightSeverity.ERROR, message)
            .range(if (element is MiniMessageOpeningTag) element.tagName ?: element else element)
            .create()
    }

}
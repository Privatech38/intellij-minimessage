package dev.privatech.plugin.minimessage.tag.validator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import java.util.UUID

class HeadTagValidator : TagValidator(true) {
    override fun validate(
        tagName: PsiElement,
        arguments: ArgumentQueue,
        holder: AnnotationHolder
    ) {
        if (arguments.isEmpty()) return
        val arg = arguments.pop()
        val trimmed = arg.trimmedArgument
        if (!Regex("[a-zA-Z0-9_]{3,16}").matches(trimmed) && !Regex("[a-zA-Z0-9-._/]+").matches(trimmed)) {
            try {
                UUID.fromString(trimmed)
            } catch (_: Exception) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Invalid name, UUID or path: '$trimmed'")
                    .range(arg.normalizeTextRange())
                    .create()
            }
        }
        if (arguments.isNotEmpty()) {
            val outerLayer = arguments.pop()
            val trimmedOuterLayer = outerLayer.trimmedArgument
            if (trimmedOuterLayer !in setOf("true", "on", "off", "false")) {
                holder.newAnnotation(HighlightSeverity.WARNING, "Can not parse outer layer value: '$trimmedOuterLayer'. " +
                        "State will not be set. Valid values are: true, on, false, off")
                    .range(outerLayer.normalizeTextRange())
                    .create()
            }
        }
    }

    override fun has(tagName: String): Boolean {
        return tagName == "head"
    }


}
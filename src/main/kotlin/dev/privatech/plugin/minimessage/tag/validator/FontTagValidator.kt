package dev.privatech.plugin.minimessage.tag.validator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement

class FontTagValidator : TagValidator() {
    override fun validate(
        tagName: PsiElement,
        arguments: ArgumentQueue,
        holder: AnnotationHolder
    ) {
        val valueOrNamespace = arguments.popOr(tagName, "The Font tag requires a resource location argument") ?: return
        val trimmed = valueOrNamespace.trimmedArgument
        if (arguments.isNotEmpty()) {
            val path = arguments.pop()
            val trimmedPath = path.trimmedArgument
            if (!Regex("[a-z0-9-._]+").matches(trimmed)) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Invalid font namespace: '$trimmed'")
                    .range(valueOrNamespace.normalizeTextRange())
                    .create()
            }
            if (!Regex("[a-z0-9-._/]+").matches(trimmedPath)) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Invalid font path: '$trimmedPath'")
                    .range(path.normalizeTextRange())
                    .create()
            }
        } else {
            if (!RESOURCE_LOCATION_REGEX.matches(trimmed)) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Invalid font resource location: '$trimmed'")
                    .range(valueOrNamespace.normalizeTextRange())
                    .create()
            }
        }
    }

    override fun has(tagName: String): Boolean {
        return tagName == "font"
    }
}
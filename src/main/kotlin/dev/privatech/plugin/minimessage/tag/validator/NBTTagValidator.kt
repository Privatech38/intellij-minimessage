package dev.privatech.plugin.minimessage.tag.validator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement

class NBTTagValidator : TagValidator(true) {
    override fun validate(
        tagName: PsiElement,
        arguments: ArgumentQueue,
        holder: AnnotationHolder
    ) {
        val type = arguments.popOr(tagName, "The NBT tag requires a type argument (block, entity, or storage)") ?: return
        val trimmedType = type.trimmedArgument
        when (trimmedType) {
            "block" -> {
                val location = arguments.popOr(type, "The NBT block type requires a block position argument") ?: return
                val trimmedLocation = location.trimmedArgument
                if (!Regex("^\\^(-?\\d+(\\.\\d+)?) \\^(-?\\d+(\\.\\d+)?) \\^(-?\\d+(\\.\\d+)?)$").matches(trimmedLocation)) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "Invalid block position: '$trimmedLocation'")
                        .range(location.normalizeTextRange())
                        .create()
                }
            }
            "entity" -> {
                val selector = arguments.popOr(type, "The NBT entity type requires a selector argument") ?: return
                val trimmedSelector = selector.trimmedArgument
                if (!SELECTOR_REGEX.matches(trimmedSelector)) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "Invalid selector: '$trimmedSelector'")
                        .range(selector.normalizeTextRange())
                        .create()
                }
            }
            "storage" -> {
                val resourceLocation = arguments.popOr(type, "The NBT storage type requires a resource location argument") ?: return
                val trimmedResourceLocation = resourceLocation.trimmedArgument
                if (!RESOURCE_LOCATION_REGEX.matches(trimmedResourceLocation)) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "Invalid resource location: '$trimmedResourceLocation'")
                        .range(resourceLocation.normalizeTextRange())
                        .create()
                }
            }
            else -> {
                holder.newAnnotation(
                    HighlightSeverity.ERROR,
                    "Invalid NBT type: '$trimmedType'"
                )
                    .range(type.normalizeTextRange())
                    .create()
            }
        }
        arguments.popOr(type, "The NBT tag requires a path argument") ?: return
        val separatorOrInterpret = arguments.pollFirst()
        if (separatorOrInterpret != null) {
            val trimmed = separatorOrInterpret.trimmedArgument
            if (trimmed == "interpret") {
                return
            } else {
                val interpret = arguments.pollFirst()
                val trimmedArgument = interpret.trimmedArgument
                if (interpret != null && trimmedArgument != "interpret") {
                    holder.newAnnotation(
                        HighlightSeverity.WARNING,
                        "The data value will not be interpreted: found unexpected argument '$trimmedArgument'"
                    )
                        .range(interpret.normalizeTextRange())
                        .create()
                }
            }
        }
    }

    override fun has(tagName: String): Boolean {
        return tagName == "nbt" || tagName == "data"
    }
}
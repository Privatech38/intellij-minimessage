package dev.privatech.plugin.minimessage.tag.validator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import java.util.UUID

class HoverTagValidator : TagValidator() {
    
    enum class HoverAction {
        SHOW_TEXT,
        SHOW_ITEM,
        SHOW_ENTITY;
    }
    
    override fun validate(
        tagName: PsiElement,
        arguments: ArgumentQueue,
        holder: AnnotationHolder
    ) {
        val actionArg = arguments.popOr(tagName, "The 'hover' tag requires an action argument") ?: return
        val actionTrimmed = actionArg.trimmedArgument
        val action = try {
            HoverAction.valueOf(actionTrimmed.uppercase())
        } catch (_: IllegalArgumentException) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Invalid hover action: '$actionTrimmed'")
                .range(actionArg.normalizeTextRange())
                .create()
            return
        }
        when (action) {
            HoverAction.SHOW_TEXT -> {
                arguments.popOr(actionArg, "The 'show_text' action requires a MiniMessage content argument") ?: return
            }
            HoverAction.SHOW_ITEM -> {
                val keyArg = arguments.popOr(actionArg, "The 'show_item' action requires an item resource location argument") ?: return
                val keyTrimmed = keyArg.trimmedArgument
                if (!RESOURCE_LOCATION_REGEX.matches(keyTrimmed)) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "Invalid item resource location: '$keyTrimmed'")
                        .range(keyArg.normalizeTextRange())
                        .create()
                }
                if (arguments.isEmpty()) {
                    return
                }
                val countArg = arguments.pop()
                val countTrimmed = countArg.trimmedArgument
                val count = countTrimmed.toIntOrNull()
                if (count == null || count < 1) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "Item count must be a positive integer, found: '${countTrimmed}'")
                        .range(countArg.normalizeTextRange())
                        .create()
                }
                // Further validation for NBT data can be added here if needed
                arguments.pollFirst()
            }
            HoverAction.SHOW_ENTITY -> {
                val key = arguments.popOr(tagName, "The 'show_entity' action requires an entity resource location argument") ?: return
                val uuid = arguments.popOr(tagName, "The 'show_entity' action requires an entity UUID argument") ?: return
                val keyTrimmed = key.trimmedArgument
                if (!RESOURCE_LOCATION_REGEX.matches(keyTrimmed)) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "Invalid entity resource location: '$keyTrimmed'")
                        .range(key.normalizeTextRange())
                        .create()
                }
                val uuidTrimmed = uuid.trimmedArgument
                try {
                    UUID.fromString(uuidTrimmed)
                } catch (_: Exception) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "Invalid UUID argument: '$uuidTrimmed'")
                        .range(uuid.normalizeTextRange())
                        .create()
                }
                arguments.pollFirst()
            }
        }
        
    }

    override fun has(tagName: String): Boolean {
        return tagName == "hover"
    }
}
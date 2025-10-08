package dev.privatech.plugin.minimessage.resolver

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import dev.privatech.plugin.minimessage.psi.MiniMessageTagArgument
import java.util.LinkedList
import java.util.UUID

class HoverTagValidator : TagValidator() {
    
    enum class HoverAction {
        SHOW_TEXT,
        SHOW_ITEM,
        SHOW_ENTITY;
    }
    
    override fun validate(
        tagName: PsiElement,
        arguments: LinkedList<MiniMessageTagArgument>,
        holder: AnnotationHolder
    ) {
        if (arguments.isEmpty()) {
            holder.newAnnotation(HighlightSeverity.ERROR, "The 'hover' tag requires a hover action argument")
                .range(tagName)
                .create()
            return
        }
        val actionArg = arguments.pop()
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
                if (arguments.isEmpty()) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "The 'show_text' action requires a MiniMessage content argument")
                        .range(actionArg.normalizeTextRange())
                        .create()
                } else {
                    arguments.pop() // Further validation can be added here if needed
                }
            }
            HoverAction.SHOW_ITEM -> {
                if (arguments.isEmpty()) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "The 'show_item' action requires an item resource location argument")
                        .range(actionArg.normalizeTextRange())
                        .create()
                } else {
                    val keyArg = arguments.pop()
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
            }
            HoverAction.SHOW_ENTITY -> {
                if (arguments.size < 2) {
                    if (arguments.isEmpty()) {
                        holder.newAnnotation(HighlightSeverity.ERROR, "The 'show_entity' action requires an entity resource location argument")
                            .range(tagName)
                            .create()
                    } else {
                        val keyArg = arguments.pop()
                        val keyTrimmed = keyArg.trimmedArgument
                        if (!RESOURCE_LOCATION_REGEX.matches(keyTrimmed)) {
                            holder.newAnnotation(HighlightSeverity.ERROR, "Invalid entity resource location: '$keyTrimmed'")
                                .range(keyArg.normalizeTextRange())
                                .create()
                        }
                    }
                    holder.newAnnotation(HighlightSeverity.ERROR, "The 'show_entity' action requires an entity UUID argument")
                        .range(tagName)
                        .create()
                } else {
                    val uuidArg = arguments.pop()
                    val uuidTrimmed = uuidArg.trimmedArgument
                    try {
                        UUID.fromString(uuidTrimmed)
                    } catch (_: Exception) {
                        holder.newAnnotation(HighlightSeverity.ERROR, "Invalid UUID argument: '$uuidTrimmed'")
                            .range(uuidArg.normalizeTextRange())
                            .create()
                    }
                    // Further validation for entity name can be added here if needed
                    arguments.pollFirst()
                }
            }
        }
        
    }

    override fun has(tagName: String): Boolean {
        return tagName == "hover"
    }
}
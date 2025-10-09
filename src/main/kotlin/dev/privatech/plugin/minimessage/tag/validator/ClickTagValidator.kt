package dev.privatech.plugin.minimessage.tag.validator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import com.intellij.util.io.URLUtil
import net.kyori.adventure.text.event.ClickEvent
import java.nio.file.InvalidPathException
import java.nio.file.Paths

class ClickTagValidator : TagValidator() {
    override fun validate(
        tagName: PsiElement,
        arguments: ArgumentQueue,
        holder: AnnotationHolder
    ) {
        val actionArg = arguments.popOr(tagName, "The 'click' tag requires an action argument") ?: return
        val action = actionArg.trimmedArgument
        if (action.uppercase() !in ClickEvent.Action.entries.map(ClickEvent.Action::name)) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Unknown click action: '$action'")
                .range(actionArg.normalizeTextRange()).create()
            return
        }
        val valueArg = arguments.popOr(actionArg, "Missing click value argument") ?: return
        val value = valueArg.trimmedArgument
        when (ClickEvent.Action.valueOf(action.uppercase())) {
            ClickEvent.Action.OPEN_URL -> {
                if (!URLUtil.URL_PATTERN.matcher(value).matches()) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "Invalid URL: '$value'")
                        .range(valueArg.normalizeTextRange()).create()
                }
            }
            ClickEvent.Action.OPEN_FILE -> {
                try {
                    Paths.get(value)
                } catch (_: InvalidPathException) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "Invalid file path: '$value'")
                        .range(valueArg.normalizeTextRange()).create()
                }
            }
            ClickEvent.Action.RUN_COMMAND -> {}
            ClickEvent.Action.SUGGEST_COMMAND -> {}
            ClickEvent.Action.CHANGE_PAGE -> {
                val page = value.toIntOrNull()
                if (page == null || page < 0) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "Page must be a positive integer")
                        .range(valueArg.normalizeTextRange()).create()
                }
            }
            ClickEvent.Action.COPY_TO_CLIPBOARD -> {}
            ClickEvent.Action.SHOW_DIALOG -> {
                if (!value.startsWith('{') && !RESOURCE_LOCATION_REGEX.matches(value)) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "Invalid resource location: '$value'")
                        .range(valueArg.normalizeTextRange()).create()
                }
            }
            ClickEvent.Action.CUSTOM -> {
                if (!RESOURCE_LOCATION_REGEX.matches(value)) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "Invalid resource location: '$value'")
                        .range(valueArg.normalizeTextRange()).create()
                }
            }
        }
    }

    override fun has(tagName: String): Boolean {
        return tagName == "click"
    }
}
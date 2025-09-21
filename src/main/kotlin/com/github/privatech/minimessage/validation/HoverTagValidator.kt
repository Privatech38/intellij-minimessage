package com.github.privatech.minimessage.validation

import com.github.privatech.minimessage.psi.MiniMessageOpeningTag
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import net.kyori.adventure.text.event.HoverEvent

class HoverTagValidator : TagValidator() {

    override fun has(tagName: String): Boolean {
        return tagName == "hover"
    }

    override fun annotate(
        element: MiniMessageOpeningTag,
        holder: AnnotationHolder
    ) {
        val argLength = element.tagArgumentList.size
        if (argLength < 1) {
            missingArgument("Hover event requires an action as its first argument", element, holder)
            return
        }
        val action = HoverEvent.Action.NAMES.value(element.tagArgumentList[0].toString())
        if (action == null) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Invalid hover action")
                .range(element.tagArgumentList[0].textRange)
                .create()
            return
        }
    }

    companion object {
        val INSTANCE = HoverTagValidator()
    }

}
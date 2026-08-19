package dev.privatech.plugin.minimessage.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import dev.privatech.plugin.minimessage.MiniMessageUtil.Companion.findReachableOpeningTag
import dev.privatech.plugin.minimessage.psi.MiniMessageClosingTag
import dev.privatech.plugin.minimessage.psi.MiniMessageOpeningTag
import dev.privatech.plugin.minimessage.psi.MiniMessageTypes
import dev.privatech.plugin.minimessage.tag.validator.ArgumentQueue
import dev.privatech.plugin.minimessage.tag.validator.TagValidator

class MiniMessageSemanticsAnnotator : Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        when (element) {
            is MiniMessageOpeningTag -> {
                val tagName = element.tagName ?: return
                val validator: TagValidator = TagValidator.STANDARD_VALIDATORS.firstOrNull { validator -> validator.has(tagName.text) } ?: return
                val arguments = ArgumentQueue(holder, element.tagArgumentList)
                validator.validate(tagName, arguments, holder)
                if (arguments.isNotEmpty()) {
                    for (argument in arguments) {
                        holder.newAnnotation(HighlightSeverity.WARNING, "Unused tag argument").range(argument.normalizeTextRange()).create()
                    }
                }
                if (!validator.autoCloseable && element.lastChild.prevSibling.node.elementType == MiniMessageTypes.SLASH) {
                    holder.newAnnotation(HighlightSeverity.WARNING, "Tag has no effect, because it is self-closing")
                        .range(element.lastChild.prevSibling).create()
                }
            }
            is MiniMessageClosingTag -> {
                val tagName = element.tagName ?: return
                val validator: TagValidator = TagValidator.STANDARD_VALIDATORS.firstOrNull { validator -> validator.has(tagName.text) } ?: return
                if (validator.autoCloseable) {
                    holder.newAnnotation(HighlightSeverity.WARNING, "Tag has no effect, because it is self-closing")
                        .range(element).create()
                    return
                }
                if (findReachableOpeningTag(element.containingFile, element.textOffset) == null) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "No matching opening tag found").range(element).create()
                }
            }
        }
    }

}
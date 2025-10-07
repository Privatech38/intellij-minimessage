package dev.privatech.plugin.minimessage.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import dev.privatech.plugin.minimessage.psi.MiniMessageOpeningTag
import dev.privatech.plugin.minimessage.psi.MiniMessageTypes
import dev.privatech.plugin.minimessage.resolver.TagValidator
import java.util.*

class MiniMessageSemanticsAnnotator : Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element !is MiniMessageOpeningTag) {
            return
        }
        val tagName = element.tagName ?: return
        val validator: TagValidator = TagValidator.STANDARD_VALIDATORS.firstOrNull { validator -> validator.has(tagName.text) } ?: return
        val arguments = LinkedList(element.tagArgumentList)
        validator.validate(tagName, arguments, holder)
        if (arguments.isNotEmpty()) {
            for (argument in arguments) {
                holder.newAnnotation(HighlightSeverity.WARNING, "Unused tag argument").range(argument).create()
            }
        }
        if (!validator.autoCloseable && element.lastChild.prevSibling.node.elementType == MiniMessageTypes.SLASH) {
            holder.newAnnotation(HighlightSeverity.WARNING, "Tag has no effect, because it is self-closing")
                .range(element.lastChild.prevSibling).create()
        }
    }

}
package dev.privatech.plugin.minimessage.resolver

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import dev.privatech.plugin.minimessage.psi.MiniMessageTagArgument
import java.util.LinkedList

class DecorationTagValidator : TagValidator() {
    override fun validate(
        tagName: PsiElement,
        arguments: LinkedList<MiniMessageTagArgument>,
        holder: AnnotationHolder
    ) {
        if (tagName.text.startsWith('!')) {
            return
        }
        if (arguments.isNotEmpty()) {
            val argument = arguments.pop()
            if (argument.trimmedArgument != "false") {
                holder.newAnnotation(HighlightSeverity.ERROR, "Argument can only be 'false'")
                    .range(argument.normalizeTextRange()).create()
            }
        }
    }

    override fun has(tagName: String): Boolean {
        return tagName.matches(Regex("!?(bold|b|italic|i|em|underlined|u|strikethrough|st|obfuscated|obf|reset)"))
    }
}
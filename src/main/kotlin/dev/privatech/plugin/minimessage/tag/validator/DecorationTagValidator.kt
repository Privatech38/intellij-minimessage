package dev.privatech.plugin.minimessage.tag.validator

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement

class DecorationTagValidator : TagValidator() {
    override fun validate(
        tagName: PsiElement,
        arguments: ArgumentQueue,
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
        return tagName in TAG_NAMES
    }

    override fun tags(): Set<String> = TAG_NAMES

    override fun tagLookupElements(): Iterable<LookupElementBuilder> {
        return TAG_LOOKUPS
    }

    companion object {
        private val BASE_TAG_NAMES = setOf(
            "bold", "b", "italic", "i", "em",
            "underlined", "u", "strikethrough", "st",
            "obfuscated", "obf", "reset"
        )

        private val INVERTED_TAG_NAMES = BASE_TAG_NAMES.map { "!$it" }

        private val TAG_NAMES: Set<String> = BASE_TAG_NAMES + INVERTED_TAG_NAMES

        private val TAG_LOOKUPS = BASE_TAG_NAMES.map {
            LookupElementBuilder.create(it)
                .withTailText("[:false]", true)
                .withTypeText("Decoration")
        } + INVERTED_TAG_NAMES.map {
            LookupElementBuilder.create(it)
                .withTypeText("Decoration")
        }
    }
}
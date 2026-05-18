package dev.privatech.plugin.minimessage.tag.validator

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.psi.PsiElement

class InsertionTagValidator : TagValidator() {
    override fun validate(
        tagName: PsiElement,
        arguments: ArgumentQueue,
        holder: AnnotationHolder
    ) {
        arguments.popOr(tagName, "The Insertion tag requires a text argument")
    }

    override fun has(tagName: String): Boolean {
        return tagName == "insert"
    }

    override fun tags(): Set<String> = TAG_NAMES

    override fun tagLookupElements(): Iterable<LookupElementBuilder> {
        return TAG_LOOKUPS
    }

    companion object {
        private val TAG_NAMES = setOf("insert")
        private val TAG_LOOKUPS =
            TAG_NAMES.map { LookupElementBuilder.create(it).withTypeText("Insertion").withTailText(":_text_") }
    }
}
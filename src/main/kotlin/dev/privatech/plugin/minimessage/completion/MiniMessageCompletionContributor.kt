package dev.privatech.plugin.minimessage.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import dev.privatech.plugin.minimessage.tag.validator.TagValidator

class MiniMessageCompletionContributor : CompletionContributor() {

    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(),
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet
                ) {
                    result.addAllElements(LOOKUP_ELEMENTS)
                }
            }
        )
    }

}

val INSERT_HANDLER = InsertHandler<LookupElement> { insertionContext, _ ->
    val document = insertionContext.document
    val tailOffset = insertionContext.tailOffset

    // Avoid duplicating the closing angle bracket when one already exists.
    if (tailOffset < document.textLength && document.charsSequence[tailOffset] == '>') {
        insertionContext.editor.caretModel.moveToOffset(tailOffset + 1)
        return@InsertHandler
    }

    document.insertString(tailOffset, ">")
    insertionContext.editor.caretModel.moveToOffset(tailOffset + 1)
}

val LOOKUP_ELEMENTS = TagValidator.STANDARD_VALIDATORS.flatMap(TagValidator::tags)
    .map {
        LookupElementBuilder.create(it)
            .withIcon(AllIcons.Nodes.Tag)
            .withInsertHandler(INSERT_HANDLER)
    }

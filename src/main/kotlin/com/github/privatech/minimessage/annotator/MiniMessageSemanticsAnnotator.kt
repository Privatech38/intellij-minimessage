package com.github.privatech.minimessage.annotator

import com.github.privatech.minimessage.psi.MiniMessageOpeningTag
import com.github.privatech.minimessage.psi.MiniMessageTypes
import com.github.privatech.minimessage.validation.argument.ArgumentQueueImpl
import com.github.privatech.minimessage.validation.argument.ContextImpl
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import net.kyori.adventure.text.minimessage.Context
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.ParsingException
import net.kyori.adventure.text.minimessage.internal.parser.Token
import net.kyori.adventure.text.minimessage.internal.parser.TokenParser
import net.kyori.adventure.text.minimessage.internal.parser.TokenParser.TagProvider
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import java.util.function.Predicate

class MiniMessageSemanticsAnnotator : Annotator {

    val MINIMESSAGE_INSTANCE: MiniMessage = MiniMessage.builder().tags(TagResolver.standard()).build()

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        val context: Context = ContextImpl(
            false, false, null, element.containingFile.text,
            MINIMESSAGE_INSTANCE, null, TagResolver.standard(), null, null
        )
        if (element is MiniMessageOpeningTag) {
            val resolver: TagResolver = TagResolver.standard()
            val tagNameChecker = Predicate { name: String? ->
                val sanitized = TagProvider.sanitizePlaceholderName(name!!)
                resolver.has(sanitized)
            }
            val tagProvider: TokenParser.TagProvider = TagProvider { name: String, args: MutableList<out Tag.Argument>, token: Token? ->
                try {
                    return@TagProvider resolver.resolve(
                        name,
                        ArgumentQueueImpl(context, args),
                        context
                    )
                } catch (e: ParsingException) {
                    holder.newAnnotation(HighlightSeverity.ERROR, e.detailMessage() ?: "Parsing exception ${e.message}")
                        .range(if (e.startIndex() == ParsingException.LOCATION_UNKNOWN) element.tagName?.textRange ?:
                        element.textRange else element.textRange.cutOut(TextRange(e.startIndex(), (e.endIndex()) + 1)))
                        .create()
                    return@TagProvider null
                }
            };
            try {
                TokenParser.parse(tagProvider, tagNameChecker, element.text, element.text, false)
            } catch (e: ParsingException) {
                holder.newAnnotation(HighlightSeverity.ERROR, e.detailMessage() ?: "Parsing exception ${e.message}")
                    .range(if (e.startIndex() == ParsingException.LOCATION_UNKNOWN) element.tagName?.textRange ?:
                    element.textRange else TextRange(e.startIndex(), (e.endIndex()) + 1))
                    .create()
            }
        }
    }

}
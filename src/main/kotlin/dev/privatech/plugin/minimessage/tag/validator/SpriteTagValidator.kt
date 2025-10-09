package dev.privatech.plugin.minimessage.tag.validator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement

class SpriteTagValidator : TagValidator(true) {
    override fun validate(
        tagName: PsiElement,
        arguments: ArgumentQueue,
        holder: AnnotationHolder
    ) {
        val atlasOrSprite = arguments.popOr(tagName, "The Sprite tag requires an atlas or sprite argument") ?: return
        val trimmed = atlasOrSprite.trimmedArgument
        if (arguments.isEmpty()) {
            if (!Regex("[a-zA-Z0-9-._/]+").matches(trimmed)) {
                holder.newAnnotation(
                    HighlightSeverity.ERROR,
                    "Invalid name for sprite: '$trimmed'"
                )
                    .range(atlasOrSprite.normalizeTextRange())
                    .create()
            }
        } else {
            val sprite = arguments.pop()
            val spriteTrimmed = sprite.trimmedArgument
            if (!RESOURCE_LOCATION_REGEX.matches(trimmed)) {
                holder.newAnnotation(
                    HighlightSeverity.ERROR,
                    "Invalid resource location for atlas: '$trimmed'"
                )
                    .range(atlasOrSprite.normalizeTextRange())
                    .create()
            }
            if (!Regex("[a-zA-Z0-9-._/]+").matches(spriteTrimmed)) {
                holder.newAnnotation(
                    HighlightSeverity.ERROR,
                    "Invalid name for sprite: '$spriteTrimmed'"
                )
                    .range(sprite.normalizeTextRange())
                    .create()
            }
        }
    }

    override fun has(tagName: String): Boolean {
        return tagName == "sprite"
    }
}
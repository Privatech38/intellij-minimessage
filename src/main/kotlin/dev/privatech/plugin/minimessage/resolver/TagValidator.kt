package dev.privatech.plugin.minimessage.resolver

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.psi.PsiElement
import dev.privatech.plugin.minimessage.psi.MiniMessageTagArgument
import java.util.*

abstract class TagValidator(val autoCloseable: Boolean = false) {

    abstract fun validate(tagName: PsiElement, arguments: LinkedList<MiniMessageTagArgument>, holder: AnnotationHolder)

    abstract fun has(tagName: String): Boolean

    companion object {
        @JvmField
        val STANDARD_VALIDATORS: Set<TagValidator> = setOf(
            ColorTagValidator(),
            ShadowTagValidator(),
            DecorationTagValidator(),
        )
    }

}
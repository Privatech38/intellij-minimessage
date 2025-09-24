package com.github.privatech.minimessage.psi.impl

import com.github.privatech.minimessage.psi.MiniMessageTagArgument
import com.intellij.openapi.util.TextRange

class MiniMessagePsiImplUtil {

    companion object {
        @JvmStatic
        fun toString(tagArgument: MiniMessageTagArgument): String? {
            return tagArgument.argument?.text ?: tagArgument.string?.text?.trim('"', '\'')
        }

        @JvmStatic
        fun getTextRange(tagArgument: MiniMessageTagArgument): TextRange =
            tagArgument.argument?.textRange ?: tagArgument.string?.textRange ?: tagArgument.textRange
    }

}
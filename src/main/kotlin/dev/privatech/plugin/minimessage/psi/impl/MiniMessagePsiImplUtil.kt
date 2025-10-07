package dev.privatech.plugin.minimessage.psi.impl

import com.intellij.openapi.util.TextRange
import dev.privatech.plugin.minimessage.psi.MiniMessageTagArgument

class MiniMessagePsiImplUtil {

    companion object {

        /**
         * Transforms an argument to a raw string
         */
        @JvmStatic
        fun toString(tagArgument: MiniMessageTagArgument): String? {
            return tagArgument.text.trim('"', '\'')
        }

        @JvmStatic
        fun normalizeTextRange(tagArgument: MiniMessageTagArgument): TextRange = if (tagArgument.textRange.length == 1)
            tagArgument.textRange
         else
            TextRange(tagArgument.textRange.startOffset + 1, tagArgument.textRange.endOffset)
    }

}
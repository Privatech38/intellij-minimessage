package dev.privatech.plugin.minimessage.psi.impl

import dev.privatech.plugin.minimessage.psi.MiniMessageTagArgument
import com.intellij.openapi.util.TextRange

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
        fun getTextRange(tagArgument: MiniMessageTagArgument): TextRange =
            if (tagArgument.text.startsWith('"') || tagArgument.text.startsWith('\''))
                TextRange(1, tagArgument.text.length - 2)
            else
                tagArgument.textRange
    }

}
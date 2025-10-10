package dev.privatech.plugin.minimessage.psi.impl

import com.intellij.openapi.util.TextRange
import dev.privatech.plugin.minimessage.psi.MiniMessageTagArgument
import dev.privatech.plugin.minimessage.psi.MiniMessageTypes

class MiniMessagePsiImplUtil {

    companion object {

        /**
         * Transforms an argument to a raw string
         */
        @JvmStatic
        fun getTrimmedArgument(tagArgument: MiniMessageTagArgument): String {
            return tagArgument.textList.joinToString("") { textElement ->
                if (textElement.escapedChar != null)
                    if (textElement.escapedChar!!.text[1] == 'n')
                        "\n"
                    else
                        textElement.escapedChar!!.text[1].toString()
                else
                    textElement.text
            }
        }

        @JvmStatic
        fun normalizeTextRange(tagArgument: MiniMessageTagArgument): TextRange =
            if (tagArgument.textRange.length == 1)
                tagArgument.textRange
             else
                TextRange(tagArgument.textRange.startOffset + 1, tagArgument.textRange.endOffset)
    }

}
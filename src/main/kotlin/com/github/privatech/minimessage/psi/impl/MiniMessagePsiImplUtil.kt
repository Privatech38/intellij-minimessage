package com.github.privatech.minimessage.psi.impl

import com.github.privatech.minimessage.psi.MiniMessageTagArgument
import com.github.privatech.minimessage.validation.argument.Argument

class MiniMessagePsiImplUtil {

    companion object {
        @JvmStatic
        fun toString(tagArgument: MiniMessageTagArgument): String? {
            return tagArgument.argument?.text ?: tagArgument.string?.text?.trim('"', '\'');
        }

        @JvmStatic
        fun getTextRange(tagArgument: MiniMessageTagArgument) =
            tagArgument.argument?.textRange ?: tagArgument.string?.textRange ?: tagArgument.textRange

        @JvmStatic
        fun toAdventureArgument(tagArgument: MiniMessageTagArgument): Argument {
            return Argument(toString(tagArgument) ?: "")
        }
    }

}
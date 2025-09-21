package com.github.privatech.minimessage.psi.impl

import com.github.privatech.minimessage.psi.MiniMessageTagArgument

class MiniMessagePsiImplUtil {

    companion object {
        @JvmStatic
        fun toString(tagArgument: MiniMessageTagArgument): String? {
            return tagArgument.argument?.text ?: tagArgument.string?.text?.trim('"', '\'');
        }
    }

}
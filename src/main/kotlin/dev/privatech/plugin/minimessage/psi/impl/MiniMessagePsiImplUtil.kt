package dev.privatech.plugin.minimessage.psi.impl

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
    }

}
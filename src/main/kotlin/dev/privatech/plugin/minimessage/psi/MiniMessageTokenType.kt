package dev.privatech.plugin.minimessage.psi

import dev.privatech.plugin.minimessage.MiniMessageLanguage
import com.intellij.psi.tree.IElementType

class MiniMessageTokenType : IElementType {

    constructor(debugName: String) : super(debugName, MiniMessageLanguage.INSTANCE)

    override fun toString(): String {
        return "MiniMessageTokenType.${super.toString()}"
    }

}
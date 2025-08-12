package com.github.privatech.minimessage.psi

import com.github.privatech.minimessage.MiniMessageLanguage
import com.intellij.psi.tree.IElementType

class MiniMessageTokenType : IElementType {

    constructor(debugName: String) : super(debugName, MiniMessageLanguage.INSTANCE)

    override fun toString(): String {
        return "MiniMessageTokenType.${super.toString()}"
    }

}
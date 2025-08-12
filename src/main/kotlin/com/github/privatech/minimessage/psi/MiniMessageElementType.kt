package com.github.privatech.minimessage.psi

import com.github.privatech.minimessage.MiniMessageLanguage
import com.intellij.psi.tree.IElementType

class MiniMessageElementType : IElementType {

    constructor(debugName: String) : super(debugName, MiniMessageLanguage.INSTANCE)

    override fun toString(): String {
        return "MiniMessageElementType.${super.toString()}"
    }

}
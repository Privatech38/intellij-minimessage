package com.github.privatech.minimessage.psi

import com.github.privatech.minimessage.MiniMessageFileType
import com.github.privatech.minimessage.MiniMessageLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.psi.FileViewProvider

class MiniMessageFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, MiniMessageLanguage.INSTANCE) {

    override fun getFileType(): MiniMessageFileType {
        return MiniMessageFileType.INSTANCE
    }

    override fun toString(): String {
        return "MiniMessage File"
    }
}
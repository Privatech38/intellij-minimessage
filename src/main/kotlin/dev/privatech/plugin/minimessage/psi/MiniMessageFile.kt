package dev.privatech.plugin.minimessage.psi

import dev.privatech.plugin.minimessage.MiniMessageFileType
import dev.privatech.plugin.minimessage.MiniMessageLanguage
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
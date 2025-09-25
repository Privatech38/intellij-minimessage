package dev.privatech.plugin.minimessage

import com.intellij.icons.AllIcons
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsContexts
import com.intellij.openapi.util.NlsSafe
import org.jetbrains.annotations.NonNls
import javax.swing.Icon

class MiniMessageFileType() : LanguageFileType(MiniMessageLanguage.INSTANCE) {

    companion object {
        @JvmField
        val INSTANCE: MiniMessageFileType = MiniMessageFileType()
    }

    override fun getName(): @NonNls String {
        return "MiniMessage"
    }

    override fun getDescription(): @NlsContexts.Label String {
        return "MiniMessage file type for Minecraft chat formatting"
    }

    override fun getDefaultExtension(): @NlsSafe String {
        return "minimessage"
    }

    override fun getIcon(): Icon? {
        return AllIcons.FileTypes.Xhtml
    }
}
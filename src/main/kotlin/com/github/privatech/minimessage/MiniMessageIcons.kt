package com.github.privatech.minimessage

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

class MiniMessageIcons {

    companion object {
        @JvmStatic
        val FILE: Icon by lazy {
            IconLoader.getIcon("/icons/logo-notext.png", MiniMessageIcons::class.java)
        }
    }

}
package com.github.privatech.minimessage

import com.intellij.lang.Language

class MiniMessageLanguage private constructor() : Language("MiniMessage") {

    companion object {
        @JvmField
        val INSTANCE: MiniMessageLanguage = MiniMessageLanguage()
    }

}
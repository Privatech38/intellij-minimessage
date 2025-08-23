package com.github.privatech.minimessage

import com.github.privatech.minimessage.language.MiniMessageLexer
import com.intellij.lexer.FlexAdapter

class MiniMessageLexerAdapter() : FlexAdapter(MiniMessageLexer(null)) {
}
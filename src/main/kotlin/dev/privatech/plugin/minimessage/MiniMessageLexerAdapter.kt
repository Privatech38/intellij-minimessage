package dev.privatech.plugin.minimessage

import com.intellij.lexer.FlexAdapter

class MiniMessageLexerAdapter() : FlexAdapter(MiniMessageLexer(null))
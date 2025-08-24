package com.github.privatech.minimessage

import com.intellij.lexer.FlexAdapter

class MiniMessageLexerAdapter() : FlexAdapter(MiniMessageLexer(null))
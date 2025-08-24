package com.github.privatech.minimessage

import com.github.privatech.minimessage.parser.MiniMessageParser
import com.github.privatech.minimessage.psi.MiniMessageFile
import com.github.privatech.minimessage.psi.MiniMessageTypes
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

class MiniMessageParserDefinition() : ParserDefinition {

    companion object {
        @JvmField
        val FILE: IFileElementType = IFileElementType(MiniMessageLanguage.INSTANCE)
    }

    override fun createLexer(p0: Project?): Lexer {
        return MiniMessageLexerAdapter()
    }

    override fun createParser(p0: Project?): PsiParser {
        return MiniMessageParser()
    }

    override fun getFileNodeType(): IFileElementType {
        return FILE
    }

    override fun getCommentTokens(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun createElement(node: ASTNode?): PsiElement {
        return MiniMessageTypes.Factory.createElement(node)
    }

    override fun createFile(p0: FileViewProvider): PsiFile {
        return MiniMessageFile(p0)
    }
}
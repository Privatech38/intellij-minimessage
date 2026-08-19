package dev.privatech.plugin.minimessage.completion

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.ScrollType
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import dev.privatech.plugin.minimessage.MiniMessageLanguage
import dev.privatech.plugin.minimessage.MiniMessageUtil.Companion.findReachableOpeningTag
import dev.privatech.plugin.minimessage.psi.MiniMessageClosingTag
import dev.privatech.plugin.minimessage.psi.MiniMessageOpeningTag
import dev.privatech.plugin.minimessage.psi.MiniMessageTypes
import dev.privatech.plugin.minimessage.tag.validator.TagValidator

class MiniMessageSlashTypedHandler : TypedHandlerDelegate() {

    override fun beforeCharTyped(c: Char, project: Project, editor: Editor, file: PsiFile, fileType: FileType): Result {
        if (c != '/' || file.language !is MiniMessageLanguage) {
            return Result.CONTINUE
        }

        val document = editor.document
        val offset = editor.caretModel.offset
        if (offset <= 0 || offset > document.textLength || document.charsSequence[offset - 1] != '<') {
            return Result.CONTINUE
        }

        PsiDocumentManager.getInstance(project).commitDocument(document)
        val psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document) ?: return Result.CONTINUE

        // Check if opening tag
        val element = psiFile.findElementAt(offset)?.parent
        if (element is MiniMessageOpeningTag) {
            if (element.lastChild?.prevSibling?.node?.elementType == MiniMessageTypes.SLASH) {
                return Result.CONTINUE
            }
            val tagName = element.tagName?.text ?: element.customTagName?.text
            if (tagName != null && TagValidator.isAutoCloseable(tagName)) {
                editor.caretModel.moveToOffset(element.lastChild.textOffset)
                editor.scrollingModel.scrollToCaret(ScrollType.RELATIVE)
            }
            return Result.CONTINUE
        }

        val openingTag = findReachableOpeningTag(psiFile, offset) ?: return Result.CONTINUE
        val tagName = openingTag.tagName?.text ?: openingTag.customTagName?.text ?: return Result.CONTINUE

        document.insertString(offset, "/$tagName>")
        editor.caretModel.moveToOffset(offset + tagName.length + 2)
        return Result.STOP
    }
}

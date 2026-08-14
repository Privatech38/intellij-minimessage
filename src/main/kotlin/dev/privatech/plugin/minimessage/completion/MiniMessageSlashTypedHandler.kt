package dev.privatech.plugin.minimessage.completion

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.ScrollType
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import dev.privatech.plugin.minimessage.MiniMessageLanguage
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
            val tagName = element.tagName?.text
            if (tagName != null && TagValidator.isAutoCloseable(tagName)) {
                editor.caretModel.moveToOffset(element.lastChild.textOffset)
                editor.scrollingModel.scrollToCaret(ScrollType.RELATIVE)
            }
            return Result.CONTINUE
        }

        val tagName = findReachableOpeningTagName(psiFile, offset) ?: return Result.CONTINUE

        document.insertString(offset, "/$tagName>")
        editor.caretModel.moveToOffset(offset + tagName.length + 2)
        return Result.STOP
    }

    private fun findReachableOpeningTagName(file: PsiFile, offset: Int): String? {
        val element = file.findElementAt(offset)

        val closedTags: MutableList<String> = mutableListOf()
        for (child in file.children.takeWhile { it != element }.reversed()) {
            when (child) {
                is MiniMessageOpeningTag -> {
                    val tagName = child.tagName?.text ?: child.customTagName?.text ?: continue
                    if (closedTags.isNotEmpty()) {
                        val tagNameIndex = closedTags.lastIndexOf(tagName)
                        if (tagNameIndex >= 0) {
                            closedTags.subList(tagNameIndex, closedTags.size).clear()
                        }
                        continue
                    }
                    if (isSelfClosing(child) || !isClosableTag(tagName)) {
                        continue
                    }
                    return tagName
                }

                is MiniMessageClosingTag -> {
                    val tagName = child.tagName?.text ?: child.customTagName?.text ?: continue
                    closedTags.add(tagName)
                }
            }
        }

        return null
    }

    private fun isSelfClosing(tag: MiniMessageOpeningTag): Boolean {
        return tag.lastChild?.prevSibling?.node?.elementType == MiniMessageTypes.SLASH
    }

    private fun isClosableTag(tagName: String): Boolean {
        return TagValidator.STANDARD_VALIDATORS.any { validator ->
            validator.has(tagName) && !validator.autoCloseable
        }
    }
}

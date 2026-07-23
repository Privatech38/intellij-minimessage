package dev.privatech.plugin.minimessage.completion

import com.intellij.codeInsight.AutoPopupController
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import dev.privatech.plugin.minimessage.MiniMessageLanguage

class MiniMessageCompletionTypedHandler : TypedHandlerDelegate() {

    override fun checkAutoPopup(charTyped: Char, project: Project, editor: Editor, file: PsiFile): Result {
        if (charTyped != '<') {
            return Result.CONTINUE
        }

        if (file.language !is MiniMessageLanguage) {
            return Result.CONTINUE
        }

        AutoPopupController.getInstance(project).scheduleAutoPopup(editor)
        return Result.CONTINUE
    }
}




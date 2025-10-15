package dev.privatech.plugin.minimessage.annotator

import com.intellij.lang.injection.general.Injection
import com.intellij.lang.injection.general.LanguageInjectionContributor
import com.intellij.lang.injection.general.SimpleInjection
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiExpressionList
import com.intellij.psi.PsiLiteralExpression
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.PsiType
import dev.privatech.plugin.minimessage.MiniMessageLanguage
import net.kyori.adventure.text.minimessage.MiniMessage

class JavaLanguageInjectionContributor : LanguageInjectionContributor {
    override fun getInjection(element: PsiElement): Injection? {
        if (element !is PsiLiteralExpression ||
            element.parent !is PsiExpressionList ||
            element.parent.parent !is PsiMethodCallExpression) return null
        val methodReference: PsiMethodCallExpression = element.parent.parent as PsiMethodCallExpression;
        val method: PsiMethod = methodReference.resolveMethod() ?: return null
        // Check if method is deserialize and is called on MiniMessage
        if (!method.name.startsWith("deserialize")) return null
        val qualifierExpression: PsiType = methodReference.methodExpression.qualifierExpression?.type ?: return null
        if (qualifierExpression.canonicalText != MiniMessage::class.java.name) return null
        return SimpleInjection(MiniMessageLanguage.INSTANCE, "", "", null)
    }
}
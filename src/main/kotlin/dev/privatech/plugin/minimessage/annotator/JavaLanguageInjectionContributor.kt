package dev.privatech.plugin.minimessage.annotator

import com.intellij.lang.injection.general.Injection
import com.intellij.lang.injection.general.LanguageInjectionContributor
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethodCallExpression

class JavaLanguageInjectionContributor : LanguageInjectionContributor {
    override fun getInjection(element: PsiElement): Injection? {
        if (element !is PsiMethodCallExpression) return null
        element.methodExpression.qualifierExpression?.let {
            if (it.type)
        }
        return null
    }
}
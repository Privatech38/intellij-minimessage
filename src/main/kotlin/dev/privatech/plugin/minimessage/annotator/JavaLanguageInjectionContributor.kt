package dev.privatech.plugin.minimessage.annotator

import com.intellij.lang.injection.general.Injection
import com.intellij.lang.injection.general.LanguageInjectionContributor
import com.intellij.lang.injection.general.SimpleInjection
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiReferenceExpression
import com.intellij.psi.PsiType
import dev.privatech.plugin.minimessage.MiniMessageLanguage
import net.kyori.adventure.text.minimessage.MiniMessage

class JavaLanguageInjectionContributor : LanguageInjectionContributor {
    override fun getInjection(element: PsiElement): Injection? {
        if (element !is PsiReferenceExpression) return null
        val method = element.reference?.resolve() ?: return null
        if (method !is PsiMethod) return null
        // Check if method is deserialize and is called on MiniMessage
        if (!method.name.startsWith("deserialize")) return null
        val qualifierExpression: PsiType = element.qualifierExpression?.type ?: return null
        if (qualifierExpression.canonicalText != MiniMessage::class.java.name) return null
        return SimpleInjection(MiniMessageLanguage.INSTANCE, "", "", null)
    }
}
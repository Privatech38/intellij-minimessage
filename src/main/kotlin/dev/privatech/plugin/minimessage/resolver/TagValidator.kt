package dev.privatech.plugin.minimessage.resolver

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.psi.PsiElement
import dev.privatech.plugin.minimessage.psi.MiniMessageTagArgument
import dev.privatech.plugin.minimessage.resolver.ColorTagValidator.Companion.COLOR_ALIASES
import net.kyori.adventure.text.format.NamedTextColor
import java.util.*

abstract class TagValidator(val autoCloseable: Boolean = false) {

    abstract fun validate(tagName: PsiElement, arguments: LinkedList<MiniMessageTagArgument>, holder: AnnotationHolder)

    abstract fun has(tagName: String): Boolean

    companion object {
        @JvmField
        val STANDARD_VALIDATORS: Set<TagValidator> = setOf(
            ColorTagValidator(),
            ShadowTagValidator(),
            DecorationTagValidator(),
            ClickTagValidator(),
        )

        /**
         * Regex pattern to match valid resource locations (also known as namespaced IDs, namespaced identifiers, resource identifiers, or namespaced strings).
         */
        @JvmField
        val RESOURCE_LOCATION_REGEX = Regex("^([a-z0-9-._]+:[a-z0-9-._/]+|[a-z0-9-._/]+)$")

        /**
         * Checks if the given value is a valid color name, alias, or hex code.
         * @param value The color value to check.
         * @return True if the value is a valid color, false otherwise.
         */
        @JvmStatic
        fun isColor(value: String): Boolean {
            return NamedTextColor.NAMES.value(value) != null
                    || COLOR_ALIASES.containsKey(value)
                    || value.matches(Regex("#[0-9a-fA-F]{6}"))
        }
    }

}
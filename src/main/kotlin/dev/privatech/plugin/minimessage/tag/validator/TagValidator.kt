package dev.privatech.plugin.minimessage.tag.validator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.psi.PsiElement
import net.kyori.adventure.text.format.NamedTextColor

abstract class TagValidator(val autoCloseable: Boolean = false) {

    abstract fun validate(tagName: PsiElement, arguments: ArgumentQueue, holder: AnnotationHolder)

    abstract fun has(tagName: String): Boolean

    companion object {
        @JvmField
        val STANDARD_VALIDATORS: Set<TagValidator> = setOf(
            ColorTagValidator(),
            ShadowTagValidator(),
            DecorationTagValidator(),
            ClickTagValidator(),
            HoverTagValidator(),
            KeybindTagValidator(),
            TranslatableTagValidator(),
            InsertionTagValidator(),
            RainbowTagValidator()
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
                    || ColorTagValidator.Companion.COLOR_ALIASES.containsKey(value)
                    || value.matches(Regex("#[0-9a-fA-F]{6}"))
        }
    }

}
package com.github.privatech.minimessage.validation.standard

import com.github.privatech.minimessage.validation.SequentalTagValidator
import com.github.privatech.minimessage.validation.argument.LiteralArgument

class HoverTagValidator : SequentalTagValidator(
    true,
    setOf(
        LiteralArgument("show_text", false, emptySet()),
        LiteralArgument("show_item", false, emptySet()),
        LiteralArgument("show_entity", false, emptySet())
    )
) {

    override val multiArgumentsErrorMessage: String? = "Hover event requires an action as its first argument"

    override fun has(tagName: String): Boolean {
        return tagName == "hover"
    }

}
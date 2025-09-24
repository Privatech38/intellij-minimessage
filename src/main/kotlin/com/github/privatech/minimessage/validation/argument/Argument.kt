package com.github.privatech.minimessage.validation.argument

import net.kyori.adventure.text.minimessage.tag.Tag

class Argument(val plainValue: String) : Tag.Argument {

    override fun value(): String {
        return plainValue
    }

}
package com.github.privatech.minimessage.validation

interface ValidatorRegistry {

    companion object {
        @JvmField
        val VALIDATORS = mutableSetOf<TagValidator>()

        @JvmStatic
        fun register(validator: TagValidator) {
            VALIDATORS.add(validator)
        }

        @JvmStatic
        fun getValidator(tagName: String): TagValidator? {
            return VALIDATORS.firstOrNull { it.has(tagName) }
        }

        init {
            VALIDATORS.add(HoverTagValidator())
        }
    }

}
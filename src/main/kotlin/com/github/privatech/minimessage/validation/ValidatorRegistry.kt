package com.github.privatech.minimessage.validation

import com.github.privatech.minimessage.validation.standard.HoverTagValidator

interface ValidatorRegistry {

    companion object {
        @JvmField
        val VALIDATORS = mutableSetOf<SequentalTagValidator>()

        @JvmStatic
        fun register(validator: SequentalTagValidator) {
            VALIDATORS.add(validator)
        }

        @JvmStatic
        fun getValidator(tagName: String): SequentalTagValidator? {
            return VALIDATORS.firstOrNull { it.has(tagName) }
        }

        init {
            VALIDATORS.add(HoverTagValidator())
        }
    }

}
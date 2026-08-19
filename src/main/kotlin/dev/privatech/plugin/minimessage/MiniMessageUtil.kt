package dev.privatech.plugin.minimessage

import com.intellij.psi.PsiFile
import dev.privatech.plugin.minimessage.psi.MiniMessageClosingTag
import dev.privatech.plugin.minimessage.psi.MiniMessageOpeningTag
import dev.privatech.plugin.minimessage.psi.MiniMessageTypes
import dev.privatech.plugin.minimessage.tag.validator.TagValidator

/**
 * Provides utility functions for working with MiniMessage elements
 */
interface MiniMessageUtil {

    companion object {
        /**
         * Finds the nearest opening tag element
         */
        @JvmStatic
        fun findReachableOpeningTag(file: PsiFile, offset: Int): MiniMessageOpeningTag? {
            val element = file.findElementAt(offset)

            val closedTags: MutableList<String> = mutableListOf()
            for (child in file.children.takeWhile { it != element }.reversed()) {
                when (child) {
                    is MiniMessageOpeningTag -> {
                        val tagName = child.tagName?.text ?: child.customTagName?.text ?: continue
                        if (closedTags.isNotEmpty()) {
                            val tagNameIndex = closedTags.lastIndexOf(tagName)
                            if (tagNameIndex >= 0) {
                                closedTags.subList(tagNameIndex, closedTags.size).clear()
                            }
                            continue
                        }
                        if (isSelfClosing(child) || !isClosableTag(tagName)) {
                            continue
                        }
                        return child
                    }

                    is MiniMessageClosingTag -> {
                        val tagName = child.tagName?.text ?: child.customTagName?.text ?: continue
                        closedTags.add(tagName)
                    }
                }
            }

            return null
        }

        private fun isSelfClosing(tag: MiniMessageOpeningTag): Boolean {
            return tag.lastChild?.prevSibling?.node?.elementType == MiniMessageTypes.SLASH
        }

        private fun isClosableTag(tagName: String): Boolean {
            return TagValidator.STANDARD_VALIDATORS.any { validator ->
                validator.has(tagName) && !validator.autoCloseable
            }
        }
    }

}
/*
* This file is part of adventure, licensed under the MIT License.
*
* Copyright (c) 2017-2025 KyoriPowered
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/

package com.github.privatech.minimessage.validation.argument

import net.kyori.adventure.pointer.Pointered
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.Context
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.ParsingException
import net.kyori.adventure.text.minimessage.internal.parser.ParsingExceptionImpl
import net.kyori.adventure.text.minimessage.internal.parser.Token
import net.kyori.adventure.text.minimessage.internal.parser.node.TagPart
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import java.util.*
import java.util.function.Consumer
import java.util.function.UnaryOperator

/**
 * Carries needed context for minimessage around, ranging from debug info to the
 * configured minimessage instance.
 *
 * @since 4.10.0
 */
internal class ContextImpl(
    private val strict: Boolean,
    private val emitVirtuals: Boolean,
    private val debugOutput: Consumer<String?>?,
    private var message: String,
    private val miniMessage: MiniMessage,
    private val target: Pointered?,
    extraTags: TagResolver?,
    preProcessor: UnaryOperator<String?>?,
    postProcessor: UnaryOperator<Component?>?
) : Context {
    private val tagResolver: TagResolver
    private val preProcessor: UnaryOperator<String?>
    private val postProcessor: UnaryOperator<Component?>

    init {
        this.tagResolver = if (extraTags == null) TagResolver.empty() else extraTags
        this.preProcessor = if (preProcessor == null) UnaryOperator.identity<String?>() else preProcessor
        this.postProcessor = if (postProcessor == null) UnaryOperator.identity<Component?>() else postProcessor
    }

    fun strict(): Boolean {
        return this.strict
    }

    override fun emitVirtuals(): Boolean {
        return this.emitVirtuals
    }

    fun debugOutput(): Consumer<String?>? {
        return this.debugOutput
    }

    fun message(): String {
        return this.message
    }

    fun message(message: String) {
        this.message = message
    }

    fun extraTags(): TagResolver {
        return this.tagResolver
    }

    fun postProcessor(): UnaryOperator<Component?> {
        return this.postProcessor
    }

    fun preProcessor(): UnaryOperator<String?> {
        return this.preProcessor
    }

    override fun target(): Pointered? {
        return this.target
    }

    override fun targetOrThrow(): Pointered {
        if (this.target == null) {
            throw this.newException("A target is required for this deserialization attempt")
        } else {
            return this.target
        }
    }

    override fun <T : Pointered> targetAsType(targetClass: Class<T>): T {
        if (Objects.requireNonNull<Class<T>>(targetClass, "targetClass").isInstance(this.target)) {
            return targetClass.cast(this.target)
        } else {
            throw this.newException("A target with type " + targetClass.getSimpleName() + " is required for this deserialization attempt")
        }
    }

    override fun deserialize(message: String): Component {
        return this.deserializeWithOptionalTarget(Objects.requireNonNull<String?>(message, "message"), this.tagResolver)
    }

    override fun deserialize(message: String, resolver: TagResolver): Component {
        Objects.requireNonNull<String?>(message, "message")
        val combinedResolver = TagResolver.builder().resolver(this.tagResolver).resolver(resolver).build()
        return this.deserializeWithOptionalTarget(message, combinedResolver)
    }

    override fun deserialize(message: String, vararg resolvers: TagResolver): Component {
        Objects.requireNonNull<String?>(message, "message")
        val combinedResolver = TagResolver.builder().resolver(this.tagResolver).resolvers(*resolvers).build()
        return this.deserializeWithOptionalTarget(message, combinedResolver)
    }

    override fun newException(message: String): ParsingException {
        return ParsingExceptionImpl(message, this.message, null, false, *EMPTY_TOKEN_ARRAY)
    }

    override fun newException(message: String, tags: ArgumentQueue): ParsingException {
        val tokens = tagsToTokens((tags as ArgumentQueueImpl<*>).args)
        return ParsingExceptionImpl(
            message,
            this.message,
            null,
            false,
            *tokens
        )
    }

    override fun newException(message: String, cause: Throwable?, tags: ArgumentQueue): ParsingException {
        val tokens = tagsToTokens((tags as ArgumentQueueImpl<*>).args)
        return ParsingExceptionImpl(
            message,
            this.message,
            cause,
            false,
            *tokens)
    }

    private fun deserializeWithOptionalTarget(message: String, tagResolver: TagResolver): Component {
        if (this.target != null) {
            return this.miniMessage.deserialize(message, this.target, tagResolver)
        } else {
            return this.miniMessage.deserialize(message, tagResolver)
        }
    }

    companion object {
        private val EMPTY_TOKEN_ARRAY = emptyArray<Token>()

        private fun tagsToTokens(tags: MutableList<out Tag.Argument>): Array<Token> {
            val tokens = arrayOfNulls<Token>(tags.size)
            var i = 0
            val length = tokens.size
            while (i < length) {
                tokens[i] = (tags.get(i) as TagPart).token()
                i++
            }
            return tokens as Array<Token>
        }
    }
}
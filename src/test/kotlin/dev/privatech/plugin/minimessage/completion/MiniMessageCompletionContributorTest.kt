package dev.privatech.plugin.minimessage.completion

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import dev.privatech.plugin.minimessage.MiniMessageFileType
import dev.privatech.plugin.minimessage.tag.validator.TagValidator

class MiniMessageCompletionContributorTest : BasePlatformTestCase() {
    override fun getTestDataPath(): String {
        return "src/test/testData/completion"
    }

    fun testEndOfText() {
        myFixture.configureByText(MiniMessageFileType.INSTANCE, "Hello, <<caret>")
        myFixture.completeBasic()
        val lookupElementStrings = myFixture.lookupElementStrings as List<String>
        assertNotNull(lookupElementStrings)
        assertSameElements(lookupElementStrings, ALL_TAGS)
    }

    fun testMiddleOfText() {
        myFixture.configureByText(MiniMessageFileType.INSTANCE, "Hello, <<caret> World!")
        myFixture.completeBasic()
        val lookupElementStrings = myFixture.lookupElementStrings as List<String>
        assertNotNull(lookupElementStrings)
        assertSameElements(lookupElementStrings, ALL_TAGS)
    }

    companion object {
        val ALL_TAGS = TagValidator.STANDARD_VALIDATORS.flatMap { it.tags() }
    }
}
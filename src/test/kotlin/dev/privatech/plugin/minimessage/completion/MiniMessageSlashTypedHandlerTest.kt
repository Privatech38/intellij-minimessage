package dev.privatech.plugin.minimessage.completion

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import dev.privatech.plugin.minimessage.MiniMessageFileType

class MiniMessageSlashTypedHandlerTest : BasePlatformTestCase() {

    fun testAutoClose() {
        myFixture.configureByText(MiniMessageFileType.INSTANCE, "<red>Hello world<<caret>")

        myFixture.type("/")

        myFixture.checkResult("<red>Hello world</red>")
    }

    fun testAutoCloseNoReachableTag() {
        myFixture.configureByText(MiniMessageFileType.INSTANCE, "<red>Hello,<blue>World!</red> <<caret>")

        myFixture.type("/")

        myFixture.checkResult("<red>Hello,<blue>World!</red> </")
    }

    fun testAutoCloseWithDoubleEnclosedTags() {
        myFixture.configureByText(MiniMessageFileType.INSTANCE, "<red>Hello<green>,<blue>World</green>!</red> <<caret>")

        myFixture.type("/")

        myFixture.checkResult("<red>Hello<green>,<blue>World</green>!</red> </")
    }

    fun testAutoCloseAutoClosedTag() {
        myFixture.configureByText(MiniMessageFileType.INSTANCE, "<key:key.jump/> <<caret>")

        myFixture.type("/")

        myFixture.checkResult("<key:key.jump/> </")
    }

    fun testSkipClosingIfInsideOpeningTag() {
        myFixture.configureByText(MiniMessageFileType.INSTANCE, "<red>Hello</red> <<caret>red>")

        myFixture.type("/")

        myFixture.checkResult("<red>Hello</red> </red>")
    }

    // Autocloseable tests
    fun testAutoCloseable() {
        myFixture.configureByText(MiniMessageFileType.INSTANCE, "<<caret>head>")

        myFixture.type("/")

        myFixture.checkResult("<head/>")
    }

    fun testNoAutoFillAutoCloseable() {
        myFixture.configureByText(MiniMessageFileType.INSTANCE, "<head> <<caret>")

        myFixture.type("/")

        myFixture.checkResult("<head> </")
    }

    fun testAlreadyClosedAutoCloseable() {
        myFixture.configureByText(MiniMessageFileType.INSTANCE, "<<caret>head/>")

        myFixture.type("/")

        myFixture.checkResult("</head/>")
    }
}

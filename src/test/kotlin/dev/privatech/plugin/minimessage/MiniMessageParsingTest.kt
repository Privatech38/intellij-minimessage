package dev.privatech.plugin.minimessage

import com.intellij.testFramework.ParsingTestCase

class MiniMessageParsingTest : ParsingTestCase("", "minimessage", MiniMessageParserDefinition()) {

    fun testParsingData() {
        doTest(true)
    }

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    override fun includeRanges(): Boolean {
        return true
    }

}
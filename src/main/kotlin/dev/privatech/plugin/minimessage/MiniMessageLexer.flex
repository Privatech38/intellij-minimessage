package dev.privatech.plugin.minimessage;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static dev.privatech.plugin.minimessage.psi.MiniMessageTypes.*;

%%

%{
  public MiniMessageLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class MiniMessageLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WhiteSpace=\s+

PlainText=[^<§\\]*|§[^0-9a-fk-or]
PlaintLT=<+(\s+|[^/#?!a-z0-9_\-<]|>)
MultipleLT=<+
LegacyFormattingCode=§[0-9a-fk-or]
// Known tag names from MiniMessage
ColorTag=black|dark_blue|dark_green|dark_aqua|dark_red|dark_purple|gold|gray|grey|dark_gray|dark_grey|blue|green|aqua|red|light_purple|yellow|white|color|colour|c|shadow|#[0-9a-fA-F]{6}
DecorationTag=bold|b|italic|i|em|underlined|u|strikethrough|st|obfuscated|obf|reset
InteractiveTag=click|hover|key
TranslatableTag=lang|tr|translate|lang_or|tr_or|translate_or
InsertTag=insertion
GradientTag=gradient|rainbow|transition
MiscTag=font|newline|br|selector|sel|score|nbt|data|pride

CustomTagName=[!?#]?[a-z0-9_-]+
Argument=[^\"/:>\s][^/:>\s]+

EscapedChar=\\.

%state TAG, ARGUMENT_STATE, STRING_DOUBLE, STRING_SINGLE

%%
<YYINITIAL> {
    {WhiteSpace}          { return WHITE_SPACE; }
    "<"                   { yybegin(TAG); return LT; }
    {MultipleLT}          { yypushback(1); return PLAIN_TEXT; }
    \\[<\\n]              { return ESCAPED_CHAR; }
    {LegacyFormattingCode} { return LEGACY_FORMATTING_CODE; }
    {PlainText} | {PlaintLT} | \\[^<\\n]?
                          { return PLAIN_TEXT; }
}

<TAG> {
    {WhiteSpace}          { return WHITE_SPACE; }
    {ColorTag} | {DecorationTag} | {InteractiveTag} | {TranslatableTag} | {InsertTag} | {GradientTag} | {MiscTag}
                          { return TAG_NAME; }
    {CustomTagName}       { return CUSTOM_TAG_NAME; }
    "/"                   { return SLASH; }
    ":"                   { yybegin(ARGUMENT_STATE); return COLON; }
    ">"                   { yybegin(YYINITIAL); return GT; }
    "<"                   { return LT; }
}

<ARGUMENT_STATE> {
    \'                    { yybegin(STRING_SINGLE); return QUOTATION; }
    \"                    { yybegin(STRING_DOUBLE); return QUOTATION; }
    {Argument}            { yybegin(TAG); return ARGUMENT; }
}

<STRING_DOUBLE> {
    \"                    { yybegin(TAG); return QUOTATION; }
    \\[\"n\\]             { return ESCAPED_CHAR; }
    [^\"\\]+|\\[^\"n\\]?  { return STRING_TEXT; }
}

<STRING_SINGLE> {
    \'                    { yybegin(TAG); return QUOTATION; }
    \\[\"n\\]             { return ESCAPED_CHAR; }
    [^'\\]+|\\[^'n\\]?    { return STRING_TEXT; }
}

[^] { return BAD_CHARACTER; }

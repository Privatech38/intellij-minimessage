package com.github.privatech.minimessage;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.github.privatech.minimessage.psi.MiniMessageTypes.*;

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

PlainText=[^<§]* | §[^0-9a-fk-or]
LegacyFormattingCode=§[0-9a-fk-or]
TagName=[!?#]?[a-z0-9_-]*
Argument=[^\'\":>]+

%state TAG, ARGUMENT_STATE, STRING_DOUBLE, STRING_SINGLE

%%
<YYINITIAL> {
    {WhiteSpace}          { return WHITE_SPACE; }
    "<"                   { yybegin(TAG); return LT; }
    "\\"                  { return ESCAPE; }
    {LegacyFormattingCode} { return LEGACY_FORMATTING_CODE; }
    {PlainText}           { return PLAIN_TEXT; }
}

<TAG> {
    {WhiteSpace}          { return WHITE_SPACE; }
    {TagName}             { return TAG_NAME; }
    "/"                   { return SLASH; }
    ":"                   { yybegin(ARGUMENT_STATE); return COLON; }
    ">"                   { yybegin(YYINITIAL); return GT; }
}

<ARGUMENT_STATE> {
    \'                    { yybegin(STRING_SINGLE); }
    \"                    { yybegin(STRING_DOUBLE); }
    {Argument}            { yybegin(TAG); return ARGUMENT; }
}

<STRING_DOUBLE> {
    \"                    { yybegin(TAG); return STRING; }
    \\\"                  { /* Escaped quote, ignore */ }
    [^\"\n\r]+            { /* Consume string content */ }
}

<STRING_SINGLE> {
    \'                    { yybegin(TAG); return STRING; }
    \\\'                  { /* Escaped quote, ignore */ }
    [^'\n\r]+             { /* Consume string content */ }
}

[^] { return BAD_CHARACTER; }

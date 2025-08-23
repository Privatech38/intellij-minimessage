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

PlainText=[^<]*
TagName=[!?#]?[a-z0-9_-]*

%state TAG, ARGUMENT, STRING_DOUBLE, STRING_SINGLE

%%
<YYINITIAL> {
    {WhiteSpace}           { return WHITE_SPACE; }
    "<"                     { yybegin(TAG); return LT; }
    "\\"                    { return ESCAPE; }
    "§"                     { return SECTION; }
    {PlainText}             { return PLAIN_TEXT; }
}

<TAG> {
    {WhiteSpace}         { return WHITE_SPACE; }
    {TagName}             { return TAG_NAME; }

    "/"                   { return SLASH; }
    ":"                   { yybegin(ARGUMENT); return COLON; }
    ">"                   { yybegin(YYINITIAL); return GT; }
}

<ARGUMENT> {
    \'                    { yybegin(STRING_SINGLE); return STRING; }
    \"                    { yybegin(STRING_DOUBLE); return STRING; }
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

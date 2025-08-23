package com.github.privatech.minimessage.language;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.github.privatech.minimessage.psi.MiniMessageTypes.*;

%%

%{
  public _MiniMessageLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _MiniMessageLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

TagName=[]

%state TAG, ARGUMENT, STRING

%%
<YYINITIAL> {
  {WHITE_SPACE}           { return WHITE_SPACE; }

  ":"                     { return COLON; }
  "<"                     { yybegin(TAG); return LT; }
  ">"                     { return GT; }
  "/"                     { return SLASH; }
  "\\"                    { return ESCAPE; }
  "§"                     { return SECTION; }
  "PLAIN_TEXT"            { return PLAIN_TEXT; }
  "TAG_NAME"              { return TAG_NAME; }
  "STRING"                { return STRING; }
  "ARGUMENT"              { return ARGUMENT; }
  "LEGACY_COLOR_CODE"     { return LEGACY_COLOR_CODE; }


}

<TAG> {
    {WHITE_SPACE}         { return WHITE_SPACE; }
    {TagName}             { return TAG_NAME; }

}

[^] { return BAD_CHARACTER; }

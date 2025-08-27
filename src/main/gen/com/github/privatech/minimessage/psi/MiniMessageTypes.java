// This is a generated file. Not intended for manual editing.
package com.github.privatech.minimessage.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.github.privatech.minimessage.psi.impl.*;

public interface MiniMessageTypes {

  IElementType AUTO_CLOSED_TAG = new MiniMessageElementType("AUTO_CLOSED_TAG");
  IElementType CONTENT = new MiniMessageElementType("CONTENT");
  IElementType EMPTY_TAG = new MiniMessageElementType("EMPTY_TAG");
  IElementType LEGACY_FORMATTING_CODE = new MiniMessageElementType("LEGACY_FORMATTING_CODE");
  IElementType TAG = new MiniMessageElementType("TAG");
  IElementType TAG_ARGUMENT = new MiniMessageElementType("TAG_ARGUMENT");
  IElementType TAG_CLOSING = new MiniMessageElementType("TAG_CLOSING");
  IElementType TAG_OPENING = new MiniMessageElementType("TAG_OPENING");

  IElementType ARGUMENT = new MiniMessageTokenType("ARGUMENT");
  IElementType COLON = new MiniMessageTokenType(":");
  IElementType ESCAPE = new MiniMessageTokenType("\\");
  IElementType GT = new MiniMessageTokenType(">");
  IElementType LEGACY_COLOR_CODE = new MiniMessageTokenType("LEGACY_COLOR_CODE");
  IElementType LT = new MiniMessageTokenType("<");
  IElementType PLAIN_TEXT = new MiniMessageTokenType("PLAIN_TEXT");
  IElementType SECTION = new MiniMessageTokenType("§");
  IElementType SLASH = new MiniMessageTokenType("/");
  IElementType STRING = new MiniMessageTokenType("STRING");
  IElementType TAG_NAME = new MiniMessageTokenType("TAG_NAME");
  IElementType WHITE_SPACE = new MiniMessageTokenType("WHITE_SPACE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == AUTO_CLOSED_TAG) {
        return new MiniMessageAutoClosedTagImpl(node);
      }
      else if (type == CONTENT) {
        return new MiniMessageContentImpl(node);
      }
      else if (type == EMPTY_TAG) {
        return new MiniMessageEmptyTagImpl(node);
      }
      else if (type == LEGACY_FORMATTING_CODE) {
        return new MiniMessageLegacyFormattingCodeImpl(node);
      }
      else if (type == TAG) {
        return new MiniMessageTagImpl(node);
      }
      else if (type == TAG_ARGUMENT) {
        return new MiniMessageTagArgumentImpl(node);
      }
      else if (type == TAG_CLOSING) {
        return new MiniMessageTagClosingImpl(node);
      }
      else if (type == TAG_OPENING) {
        return new MiniMessageTagOpeningImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}

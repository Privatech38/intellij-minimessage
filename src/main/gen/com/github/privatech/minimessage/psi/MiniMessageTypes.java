// This is a generated file. Not intended for manual editing.
package com.github.privatech.minimessage.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.github.privatech.minimessage.psi.impl.*;

public interface MiniMessageTypes {

  IElementType ARGUMENT = new MiniMessageElementType("ARGUMENT");
  IElementType CLOSING_TAG = new MiniMessageElementType("CLOSING_TAG");
  IElementType OPENING_TAG = new MiniMessageElementType("OPENING_TAG");
  IElementType SELF_CLOSING_TAG = new MiniMessageElementType("SELF_CLOSING_TAG");

  IElementType ARGUMENT = new MiniMessageTokenType("ARGUMENT");
  IElementType COLON = new MiniMessageTokenType(":");
  IElementType CUSTOM_TAG_NAME = new MiniMessageTokenType("CUSTOM_TAG_NAME");
  IElementType ESCAPE = new MiniMessageTokenType("\\");
  IElementType GT = new MiniMessageTokenType(">");
  IElementType LEGACY_FORMATTING_CODE = new MiniMessageTokenType("LEGACY_FORMATTING_CODE");
  IElementType LT = new MiniMessageTokenType("<");
  IElementType PLAIN_TEXT = new MiniMessageTokenType("PLAIN_TEXT");
  IElementType SLASH = new MiniMessageTokenType("/");
  IElementType STRING = new MiniMessageTokenType("STRING");
  IElementType TAG_NAME = new MiniMessageTokenType("TAG_NAME");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ARGUMENT) {
        return new MiniMessageArgumentImpl(node);
      }
      else if (type == CLOSING_TAG) {
        return new MiniMessageClosingTagImpl(node);
      }
      else if (type == OPENING_TAG) {
        return new MiniMessageOpeningTagImpl(node);
      }
      else if (type == SELF_CLOSING_TAG) {
        return new MiniMessageSelfClosingTagImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}

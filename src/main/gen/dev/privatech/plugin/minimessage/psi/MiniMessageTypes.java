// This is a generated file. Not intended for manual editing.
package dev.privatech.plugin.minimessage.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import dev.privatech.plugin.minimessage.psi.impl.*;

public interface MiniMessageTypes {

  IElementType CLOSING_TAG = new MiniMessageElementType("CLOSING_TAG");
  IElementType OPENING_TAG = new MiniMessageElementType("OPENING_TAG");
  IElementType TAG_ARGUMENT = new MiniMessageElementType("TAG_ARGUMENT");
  IElementType TEXT = new MiniMessageElementType("TEXT");

  IElementType ARGUMENT = new MiniMessageTokenType("ARGUMENT");
  IElementType COLON = new MiniMessageTokenType(":");
  IElementType CUSTOM_TAG_NAME = new MiniMessageTokenType("CUSTOM_TAG_NAME");
  IElementType ESCAPED_CHAR = new MiniMessageTokenType("ESCAPED_CHAR");
  IElementType GT = new MiniMessageTokenType(">");
  IElementType LEGACY_FORMATTING_CODE = new MiniMessageTokenType("LEGACY_FORMATTING_CODE");
  IElementType LT = new MiniMessageTokenType("<");
  IElementType PLAIN_TEXT = new MiniMessageTokenType("PLAIN_TEXT");
  IElementType QUOTATION = new MiniMessageTokenType("QUOTATION");
  IElementType SLASH = new MiniMessageTokenType("/");
  IElementType STRING_TEXT = new MiniMessageTokenType("STRING_TEXT");
  IElementType TAG_NAME = new MiniMessageTokenType("TAG_NAME");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == CLOSING_TAG) {
        return new MiniMessageClosingTagImpl(node);
      }
      else if (type == OPENING_TAG) {
        return new MiniMessageOpeningTagImpl(node);
      }
      else if (type == TAG_ARGUMENT) {
        return new MiniMessageTagArgumentImpl(node);
      }
      else if (type == TEXT) {
        return new MiniMessageTextImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}

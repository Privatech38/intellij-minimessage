// This is a generated file. Not intended for manual editing.
package com.github.privatech.minimessage.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.github.privatech.minimessage.psi.MiniMessageTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class MiniMessageParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return root(b, l + 1);
  }

  /* ********************************************************** */
  // STRING | ARGUMENT
  static boolean argument_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "argument_type")) return false;
    if (!nextTokenIs(b, "", ARGUMENT, STRING)) return false;
    boolean r;
    r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, ARGUMENT);
    return r;
  }

  /* ********************************************************** */
  // LT SLASH tag_name GT
  public static boolean closing_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "closing_tag")) return false;
    if (!nextTokenIs(b, LT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CLOSING_TAG, null);
    r = consumeTokens(b, 2, LT, SLASH);
    p = r; // pin = 2
    r = r && report_error_(b, tag_name(b, l + 1));
    r = p && consumeToken(b, GT) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // (tag | LEGACY_FORMATTING_CODE | PLAIN_TEXT) *
  static boolean content(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "content")) return false;
    while (true) {
      int c = current_position_(b);
      if (!content_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "content", c)) break;
    }
    return true;
  }

  // tag | LEGACY_FORMATTING_CODE | PLAIN_TEXT
  private static boolean content_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "content_0")) return false;
    boolean r;
    r = tag(b, l + 1);
    if (!r) r = consumeToken(b, LEGACY_FORMATTING_CODE);
    if (!r) r = consumeToken(b, PLAIN_TEXT);
    return r;
  }

  /* ********************************************************** */
  // LT tag_name tag_argument * GT
  public static boolean opening_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "opening_tag")) return false;
    if (!nextTokenIs(b, LT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LT);
    r = r && tag_name(b, l + 1);
    r = r && opening_tag_2(b, l + 1);
    r = r && consumeToken(b, GT);
    exit_section_(b, m, OPENING_TAG, r);
    return r;
  }

  // tag_argument *
  private static boolean opening_tag_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "opening_tag_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!tag_argument(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "opening_tag_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // content
  static boolean root(PsiBuilder b, int l) {
    return content(b, l + 1);
  }

  /* ********************************************************** */
  // LT tag_name tag_argument * SLASH GT
  public static boolean self_closing_tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "self_closing_tag")) return false;
    if (!nextTokenIs(b, LT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LT);
    r = r && tag_name(b, l + 1);
    r = r && self_closing_tag_2(b, l + 1);
    r = r && consumeTokens(b, 0, SLASH, GT);
    exit_section_(b, m, SELF_CLOSING_TAG, r);
    return r;
  }

  // tag_argument *
  private static boolean self_closing_tag_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "self_closing_tag_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!tag_argument(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "self_closing_tag_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // self_closing_tag | opening_tag | closing_tag
  static boolean tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag")) return false;
    if (!nextTokenIs(b, LT)) return false;
    boolean r;
    r = self_closing_tag(b, l + 1);
    if (!r) r = opening_tag(b, l + 1);
    if (!r) r = closing_tag(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // COLON argument_type
  public static boolean tag_argument(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_argument")) return false;
    if (!nextTokenIs(b, COLON)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_ARGUMENT, null);
    r = consumeToken(b, COLON);
    p = r; // pin = 1
    r = r && argument_type(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // TAG_NAME | CUSTOM_TAG_NAME
  static boolean tag_name(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_name")) return false;
    if (!nextTokenIs(b, "", CUSTOM_TAG_NAME, TAG_NAME)) return false;
    boolean r;
    r = consumeToken(b, TAG_NAME);
    if (!r) r = consumeToken(b, CUSTOM_TAG_NAME);
    return r;
  }

}

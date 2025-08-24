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
  public static boolean argumentType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "argumentType")) return false;
    if (!nextTokenIs(b, "<argument type>", ARGUMENT, STRING)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ARGUMENT_TYPE, "<argument type>");
    r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, ARGUMENT);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // LT+ TAG_NAME tagArgument? SLASH GT
  public static boolean autoClosedTag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "autoClosedTag")) return false;
    if (!nextTokenIs(b, LT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = autoClosedTag_0(b, l + 1);
    r = r && consumeToken(b, TAG_NAME);
    r = r && autoClosedTag_2(b, l + 1);
    r = r && consumeTokens(b, 0, SLASH, GT);
    exit_section_(b, m, AUTO_CLOSED_TAG, r);
    return r;
  }

  // LT+
  private static boolean autoClosedTag_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "autoClosedTag_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LT);
    while (r) {
      int c = current_position_(b);
      if (!consumeToken(b, LT)) break;
      if (!empty_element_parsed_guard_(b, "autoClosedTag_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // tagArgument?
  private static boolean autoClosedTag_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "autoClosedTag_2")) return false;
    tagArgument(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // (possibleTag | legacyFormattingCode | tag | PLAIN_TEXT) *
  public static boolean content(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "content")) return false;
    Marker m = enter_section_(b, l, _NONE_, CONTENT, "<content>");
    while (true) {
      int c = current_position_(b);
      if (!content_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "content", c)) break;
    }
    exit_section_(b, l, m, true, false, null);
    return true;
  }

  // possibleTag | legacyFormattingCode | tag | PLAIN_TEXT
  private static boolean content_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "content_0")) return false;
    boolean r;
    r = possibleTag(b, l + 1);
    if (!r) r = legacyFormattingCode(b, l + 1);
    if (!r) r = tag(b, l + 1);
    if (!r) r = consumeToken(b, PLAIN_TEXT);
    return r;
  }

  /* ********************************************************** */
  // LT+ GT
  public static boolean emptyTag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emptyTag")) return false;
    if (!nextTokenIs(b, LT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = emptyTag_0(b, l + 1);
    r = r && consumeToken(b, GT);
    exit_section_(b, m, EMPTY_TAG, r);
    return r;
  }

  // LT+
  private static boolean emptyTag_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emptyTag_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LT);
    while (r) {
      int c = current_position_(b);
      if (!consumeToken(b, LT)) break;
      if (!empty_element_parsed_guard_(b, "emptyTag_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !ESCAPE SECTION LEGACY_COLOR_CODE
  public static boolean legacyFormattingCode(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "legacyFormattingCode")) return false;
    if (!nextTokenIs(b, SECTION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = legacyFormattingCode_0(b, l + 1);
    r = r && consumeTokens(b, 0, SECTION, LEGACY_COLOR_CODE);
    exit_section_(b, m, LEGACY_FORMATTING_CODE, r);
    return r;
  }

  // !ESCAPE
  private static boolean legacyFormattingCode_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "legacyFormattingCode_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, ESCAPE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ESCAPE LT? | emptyTag | LT+ WHITE_SPACE | tag
  public static boolean possibleTag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "possibleTag")) return false;
    if (!nextTokenIs(b, "<possible tag>", ESCAPE, LT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, POSSIBLE_TAG, "<possible tag>");
    r = possibleTag_0(b, l + 1);
    if (!r) r = emptyTag(b, l + 1);
    if (!r) r = possibleTag_2(b, l + 1);
    if (!r) r = tag(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ESCAPE LT?
  private static boolean possibleTag_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "possibleTag_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ESCAPE);
    r = r && possibleTag_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // LT?
  private static boolean possibleTag_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "possibleTag_0_1")) return false;
    consumeToken(b, LT);
    return true;
  }

  // LT+ WHITE_SPACE
  private static boolean possibleTag_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "possibleTag_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = possibleTag_2_0(b, l + 1);
    r = r && consumeToken(b, WHITE_SPACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // LT+
  private static boolean possibleTag_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "possibleTag_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LT);
    while (r) {
      int c = current_position_(b);
      if (!consumeToken(b, LT)) break;
      if (!empty_element_parsed_guard_(b, "possibleTag_2_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // content
  static boolean root(PsiBuilder b, int l) {
    return content(b, l + 1);
  }

  /* ********************************************************** */
  // autoClosedTag | tagOpening content tagClosing?
  public static boolean tag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag")) return false;
    if (!nextTokenIs(b, LT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = autoClosedTag(b, l + 1);
    if (!r) r = tag_1(b, l + 1);
    exit_section_(b, m, TAG, r);
    return r;
  }

  // tagOpening content tagClosing?
  private static boolean tag_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = tagOpening(b, l + 1);
    r = r && content(b, l + 1);
    r = r && tag_1_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // tagClosing?
  private static boolean tag_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_1_2")) return false;
    tagClosing(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // COLON argumentType tagArgument?
  public static boolean tagArgument(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tagArgument")) return false;
    if (!nextTokenIs(b, COLON)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_ARGUMENT, null);
    r = consumeToken(b, COLON);
    p = r; // pin = 1
    r = r && report_error_(b, argumentType(b, l + 1));
    r = p && tagArgument_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // tagArgument?
  private static boolean tagArgument_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tagArgument_2")) return false;
    tagArgument(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // LT+ SLASH TAG_NAME GT
  public static boolean tagClosing(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tagClosing")) return false;
    if (!nextTokenIs(b, LT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_CLOSING, null);
    r = tagClosing_0(b, l + 1);
    r = r && consumeTokens(b, 1, SLASH, TAG_NAME, GT);
    p = r; // pin = 2
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // LT+
  private static boolean tagClosing_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tagClosing_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LT);
    while (r) {
      int c = current_position_(b);
      if (!consumeToken(b, LT)) break;
      if (!empty_element_parsed_guard_(b, "tagClosing_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // LT+ TAG_NAME tagArgument? GT
  public static boolean tagOpening(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tagOpening")) return false;
    if (!nextTokenIs(b, LT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = tagOpening_0(b, l + 1);
    r = r && consumeToken(b, TAG_NAME);
    r = r && tagOpening_2(b, l + 1);
    r = r && consumeToken(b, GT);
    exit_section_(b, m, TAG_OPENING, r);
    return r;
  }

  // LT+
  private static boolean tagOpening_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tagOpening_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LT);
    while (r) {
      int c = current_position_(b);
      if (!consumeToken(b, LT)) break;
      if (!empty_element_parsed_guard_(b, "tagOpening_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // tagArgument?
  private static boolean tagOpening_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tagOpening_2")) return false;
    tagArgument(b, l + 1);
    return true;
  }

}

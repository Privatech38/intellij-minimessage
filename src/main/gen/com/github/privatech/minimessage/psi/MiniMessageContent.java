// This is a generated file. Not intended for manual editing.
package com.github.privatech.minimessage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface MiniMessageContent extends PsiElement {

  @NotNull
  List<MiniMessageLegacyFormattingCode> getLegacyFormattingCodeList();

  @NotNull
  List<MiniMessagePossibleTag> getPossibleTagList();

  @NotNull
  List<MiniMessageTag> getTagList();

}

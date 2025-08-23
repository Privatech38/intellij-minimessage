// This is a generated file. Not intended for manual editing.
package com.github.privatech.minimessage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface MiniMessageTag extends PsiElement {

  @Nullable
  MiniMessageAutoClosedTag getAutoClosedTag();

  @Nullable
  MiniMessageContent getContent();

  @Nullable
  MiniMessageTagClosing getTagClosing();

  @Nullable
  MiniMessageTagOpening getTagOpening();

}

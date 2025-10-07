// This is a generated file. Not intended for manual editing.
package dev.privatech.plugin.minimessage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.openapi.util.TextRange;

public interface MiniMessageTagArgument extends PsiElement {

  @NotNull
  List<MiniMessageText> getTextList();

  @Nullable
  PsiElement getArgument();

  @NotNull String getTrimmedArgument();

  @NotNull TextRange normalizeTextRange();

}

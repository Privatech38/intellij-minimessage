// This is a generated file. Not intended for manual editing.
package dev.privatech.plugin.minimessage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface MiniMessageOpeningTag extends PsiElement {

  @NotNull
  List<MiniMessageTagArgument> getTagArgumentList();

  @Nullable
  PsiElement getCustomTagName();

  @Nullable
  PsiElement getTagName();

}

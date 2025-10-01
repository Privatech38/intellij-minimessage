// This is a generated file. Not intended for manual editing.
package dev.privatech.plugin.minimessage.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static dev.privatech.plugin.minimessage.psi.MiniMessageTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import dev.privatech.plugin.minimessage.psi.*;

public class MiniMessageTextImpl extends ASTWrapperPsiElement implements MiniMessageText {

  public MiniMessageTextImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MiniMessageVisitor visitor) {
    visitor.visitText(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MiniMessageVisitor) accept((MiniMessageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PsiElement getEscapedChar() {
    return findChildByType(ESCAPED_CHAR);
  }

  @Override
  @Nullable
  public PsiElement getPlainText() {
    return findChildByType(PLAIN_TEXT);
  }

  @Override
  @Nullable
  public PsiElement getStringText() {
    return findChildByType(STRING_TEXT);
  }

}

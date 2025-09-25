// This is a generated file. Not intended for manual editing.
package dev.privatech.plugin.minimessage.psi.impl;

import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;

import static dev.privatech.plugin.minimessage.psi.MiniMessageTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import dev.privatech.plugin.minimessage.psi.*;
import com.intellij.openapi.util.TextRange;

public class MiniMessageTagArgumentImpl extends ASTWrapperPsiElement implements MiniMessageTagArgument {

  public MiniMessageTagArgumentImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MiniMessageVisitor visitor) {
    visitor.visitTagArgument(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MiniMessageVisitor) accept((MiniMessageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PsiElement getArgument() {
    return findChildByType(ARGUMENT);
  }

  @Override
  @Nullable
  public PsiElement getString() {
    return findChildByType(STRING);
  }

  @Override
  public @Nullable String toString() {
    return MiniMessagePsiImplUtil.toString(this);
  }

  @Override
  public TextRange getTextRange() {
    return MiniMessagePsiImplUtil.getTextRange(this);
  }

}

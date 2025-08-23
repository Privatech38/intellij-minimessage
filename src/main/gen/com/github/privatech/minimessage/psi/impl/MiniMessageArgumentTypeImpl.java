// This is a generated file. Not intended for manual editing.
package com.github.privatech.minimessage.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.privatech.minimessage.psi.MiniMessageTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.github.privatech.minimessage.psi.*;

public class MiniMessageArgumentTypeImpl extends ASTWrapperPsiElement implements MiniMessageArgumentType {

  public MiniMessageArgumentTypeImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MiniMessageVisitor visitor) {
    visitor.visitArgumentType(this);
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

}

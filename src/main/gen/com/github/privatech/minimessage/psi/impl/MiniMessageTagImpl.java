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

public class MiniMessageTagImpl extends ASTWrapperPsiElement implements MiniMessageTag {

  public MiniMessageTagImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MiniMessageVisitor visitor) {
    visitor.visitTag(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MiniMessageVisitor) accept((MiniMessageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public MiniMessageAutoClosedTag getAutoClosedTag() {
    return findChildByClass(MiniMessageAutoClosedTag.class);
  }

  @Override
  @Nullable
  public MiniMessageContent getContent() {
    return findChildByClass(MiniMessageContent.class);
  }

  @Override
  @Nullable
  public MiniMessageTagClosing getTagClosing() {
    return findChildByClass(MiniMessageTagClosing.class);
  }

  @Override
  @Nullable
  public MiniMessageTagOpening getTagOpening() {
    return findChildByClass(MiniMessageTagOpening.class);
  }

}

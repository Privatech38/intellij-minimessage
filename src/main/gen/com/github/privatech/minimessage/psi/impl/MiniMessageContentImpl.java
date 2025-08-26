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

public class MiniMessageContentImpl extends ASTWrapperPsiElement implements MiniMessageContent {

  public MiniMessageContentImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MiniMessageVisitor visitor) {
    visitor.visitContent(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MiniMessageVisitor) accept((MiniMessageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<MiniMessageEmptyTag> getEmptyTagList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MiniMessageEmptyTag.class);
  }

  @Override
  @NotNull
  public List<MiniMessageLegacyFormattingCode> getLegacyFormattingCodeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MiniMessageLegacyFormattingCode.class);
  }

  @Override
  @NotNull
  public List<MiniMessageTag> getTagList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MiniMessageTag.class);
  }

}

// This is a generated file. Not intended for manual editing.
package dev.privatech.plugin.minimessage.psi.impl;

import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;

import static dev.privatech.plugin.minimessage.psi.MiniMessageTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import dev.privatech.plugin.minimessage.psi.*;

public class MiniMessageClosingTagImpl extends ASTWrapperPsiElement implements MiniMessageClosingTag {

  public MiniMessageClosingTagImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MiniMessageVisitor visitor) {
    visitor.visitClosingTag(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MiniMessageVisitor) accept((MiniMessageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PsiElement getCustomTagName() {
    return findChildByType(CUSTOM_TAG_NAME);
  }

  @Override
  @Nullable
  public PsiElement getTagName() {
    return findChildByType(TAG_NAME);
  }

}

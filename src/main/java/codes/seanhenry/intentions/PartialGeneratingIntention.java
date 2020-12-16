package codes.seanhenry.intentions;

import org.jetbrains.annotations.NotNull;

public class PartialGeneratingIntention extends BaseGeneratingIntention {
  @Override
  protected String getMockType() {
    return "partial";
  }

  @NotNull
  @Override
  public String getText() {
    return "Generate partial spy";
  }
}

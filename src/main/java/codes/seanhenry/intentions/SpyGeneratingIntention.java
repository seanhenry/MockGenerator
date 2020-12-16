package codes.seanhenry.intentions;

import org.jetbrains.annotations.NotNull;

public class SpyGeneratingIntention extends BaseGeneratingIntention {
  @Override
  protected String getMockType() {
    return "spy";
  }

  @NotNull
  @Override
  public String getText() {
    return "Generate spy";
  }
}

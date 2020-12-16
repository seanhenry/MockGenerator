package codes.seanhenry.intentions;

import org.jetbrains.annotations.NotNull;

public class StubGeneratingIntention extends BaseGeneratingIntention {
  @Override
  protected String getMockType() {
    return "stub";
  }

  @NotNull
  @Override
  public String getText() {
    return "Generate stub";
  }
}

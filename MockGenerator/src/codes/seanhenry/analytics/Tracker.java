package codes.seanhenry.analytics;

public interface Tracker {
  void track(String category, String action, String value);
}

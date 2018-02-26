package codes.seanhenry.analytics;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleAnalyticsTracker implements Tracker {

  private final String tid = "UA-114749580-2";
//  private final String tid = "UA-114749580-1"; // Debug

  public void track(String action) {
    try {
      HttpClient httpclient = HttpClients.createDefault();
      HttpPost httppost = new HttpPost("https://www.google-analytics.com/collect");
      List<NameValuePair> params = new ArrayList<>(6);
      params.add(new BasicNameValuePair("v", "1"));
      params.add(new BasicNameValuePair("tid", tid));
      params.add(new BasicNameValuePair("cid", "AppCode"));
      params.add(new BasicNameValuePair("t", "event"));
      params.add(new BasicNameValuePair("ec", "mock"));
      params.add(new BasicNameValuePair("ea", action));
      httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
      httpclient.execute(httppost);
    } catch (IOException ignored) {}
  }
}

package utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlUtil {
	/**
	 * @param i
	 * @return
	 */
	public static String rmTag(String html, String tag, int i){
		if (html == null) {
			return "";
		}
		Document doc = Jsoup.parse(html);
		if (doc.select(tag).size() >= i) {
			doc.select(tag).get(i-1).remove();
		}
		return doc.select("body").html();
	}
	
	/**
	   *
	   * @param sUrl String
	   * @param sParentUrl String
	   * @return String
	   * @throws MalformedURLException
	   */
		public static String getAbsoluteURL(String sUrl, String sParentUrl){
			if (sUrl.startsWith("http://") || sUrl.startsWith("https://")) {
				return sUrl;
			}
			URL url = null;
			try {
				URL parentURL = new URL(sParentUrl);
				url = new URL(parentURL, sUrl);;
			} catch (MalformedURLException e) {
				return null;
			}
			return url.toString();
		}
}

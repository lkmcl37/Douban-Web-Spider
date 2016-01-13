package fetcher;

import java.util.Date;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;


public class DefaultFetcher implements Fetcher{
	private static final int TIMEOUT = 60*1000;
	private final Object mutex = new Object();
	private long lastFetchTime = 0;
	private static final int INTERVAL = 1000;
	@Override
	public Page fetch(Request request) {
		// TODO Auto-generated method stub
		Page page = new Page();
		Map<String, String> headers = request.getHeaders();
		Map<String, String> datas = request.getDatas();
		Connection con = Jsoup.connect(request.getUrl()).followRedirects(true).ignoreContentType(true).timeout(TIMEOUT);
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			con.header(entry.getKey(), entry.getValue());
		}
		try {
			synchronized (mutex) {
				long now = (new Date()).getTime();
				if (now - lastFetchTime < INTERVAL) 
					Thread.sleep(INTERVAL - (now - lastFetchTime));
				lastFetchTime = (new Date()).getTime();
			}
			
			Connection.Response response;
			if (datas != null && datas.size() > 0) {
				response = con.method(Method.POST).data(datas).execute();
			}else {
				response = con.method(Method.GET).execute();
			}
			page.setCode(response.statusCode());
			page.setContent(new String(response.bodyAsBytes(), request.getEncode()));
			page.setCharset(response.charset());
			page.setContentType(response.contentType());
			page.setUrl(request.getUrl());
//			page.setEncoding(response.);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return page;
	}

}

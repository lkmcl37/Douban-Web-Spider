package fetcher;

import java.util.HashMap;
import java.util.Map;

import utils.StrUtil;

public class Request {
	private String url;
	private String encode = "utf-8";
	private Map<String, String> datas;
	private Map<String, String> headers = new HashMap<String, String>();
//	private int code;
	public Request(String url,String encode){
		this.url = url;
		if (!StrUtil.isBlank(encode)) {
			this.encode = encode;
		}
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
	}
	public void addHeader(String key,String value) {
		this.headers.put(key, value);
	}
	public void addHeaders(Map<String, String> headers) {
		if (headers == null) {
			throw new IllegalArgumentException("header is null");
		}
		this.headers.putAll(headers);
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
//	public String getRedirect() {
//		return redirect;
//	}
//	public void setRedirect(String redirect) {
//		this.redirect = redirect;
//	}
	public Map<String, String> getHeaders() {
		return headers;
	}
//	public int getCode() {
//		return code;
//	}
//	public void setCode(int code) {
//		this.code = code;
//	}
	public Map<String, String> getDatas() {
		return datas;
	}
	public void setDatas(Map<String, String> datas) {
		this.datas = datas;
	}
	public String getEncode() {
		return encode;
	}
	public void setEncode(String encode) {
		this.encode = encode;
	}
}

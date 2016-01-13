package parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import config.Model;
import fetcher.Page;

public class DoubanParser implements Parser{

	@Override
	public List<Map<String, String>> parse(Page page,Model model) {
		// TODO Auto-generated method stub
		List<Map<String, String>> datas = new ArrayList<Map<String,String>>();
		Document doc = Jsoup.parse(page.getContent());
		Map<String, String> data = new HashMap<String, String>();
		Elements elements = doc.select("div#link-report > div");
		for (Element element : elements) {
			data.put("content", element.text());
			data.put("origin_url", page.getUrl());
			datas.add(data);
		}
		return datas;
	}

}

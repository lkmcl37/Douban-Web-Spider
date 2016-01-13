package parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parser.Parser;

import utils.StrUtil;
import config.Field;
import config.Model;
import fetcher.Page;

public class JsoupParser implements Parser {

	@Override
	public List<Map<String, String>> parse(Page page, Model model) {
		// TODO Auto-generated method stub
		List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
		Document doc = Jsoup.parse(page.getContent());
		Map<String, String> data = new HashMap<String, String>();
		
		for (Field field : model.fields) {
			String key = field.name;
			Elements e = doc.select(field.value);
			if (e == null || e.size() == 0) {
				continue;
			}
			Element element = e.get(0);
			String text = null;
			if (field.html) {
				text = element.html();
			} else {
				text = element.text();
			}
			if (StrUtil.isBlank(text)) {
				continue;
			}
			if (StrUtil.isBlank(data.get(key))) {
				data.put(key, text);
			}
		}
		data.put("origin_url", page.getUrl());
		datas.add(data);
		return datas;
	}

}

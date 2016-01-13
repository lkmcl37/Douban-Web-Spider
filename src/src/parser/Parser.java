package parser;

import java.util.List;
import java.util.Map;

import config.Model;
import fetcher.Page;

public interface Parser {
	public List<Map<String, String>> parse(Page page, Model model);
}

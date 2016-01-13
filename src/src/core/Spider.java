package core;

import java.util.List;
import java.util.Map;

import listener.DbSpiderListener;
import listener.SpiderContext;
import listener.SpiderListener;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import parser.JsoupParser;
import parser.Parser;
import utils.ConfigKit;
import utils.HtmlUtil;
import utils.StrUtil;

import com.jfinal.plugin.activerecord.DbKit;

import config.Dir;
import config.Dirs;
import config.Model;
import fetcher.Fetcher;
import fetcher.Page;
import fetcher.Request;


public class Spider implements Runnable {
	private static final Logger LOG = Logger.getLogger(Spider.class);
	private Fetcher fetcher;
	private Parser parser;
	private Model model;
	private Dirs dirs;
	private SpiderListener listener;


	public Spider(Dirs dirs, SpiderListener listener) {
		this.dirs = dirs;
		this.listener = listener;
		this.parser = new JsoupParser();
		this.model = dirs.model;
		try {
			
			String fc = (StrUtil.isBlank(dirs.fetcher)?"fetcher.DefaultFetcher":dirs.fetcher);
			this.fetcher = (Fetcher)Class.forName(fc).newInstance();
			
			if (this.listener instanceof DbSpiderListener) {
				ConfigKit.initDS(DbKit.MAIN_CONFIG_NAME);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {
		long start = System.currentTimeMillis();
		try {
			for (int i = 0; i < dirs.dirs.size(); i++) {
				Dir dir = dirs.dirs.get(i);
				LOG.info("Begin Spide Dir>>>>>>>>>>>name:" + dir.name + ",url:" + dir.url);
				try {
					spide(dir);
				} catch (Exception e) {
					// TODO: handle exception
					LOG.error("Error Spide Dir:" + dir.url,e);
				}
			}
			LOG.info("directory fetched,elapsed["
					+ (System.currentTimeMillis() - start) / 1000 + "s]");
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**

	 * @param website
	 * @param node
	 * @throws Exception
	 */
	protected void spide(Dir dir) throws Exception {
		
		int page = 1;
		String currDirUrl;		
		do {
			currDirUrl = getNextUrl(dir, page);
			if (currDirUrl == null) {
				break;
			}
			if (page > 1 && dirs.next == 0) {
				break;
			}
			if (dir.maxPage > 0 && dir.maxPage <= page) {
				break;
			}
			page++;
			Request request = new Request(currDirUrl,dirs.encode);
			request.addHeader("Referer", dir.url);
			Page listPage = fetcher.fetch(request);
			if (listPage.getCode() != 200) {
				LOG.warn("Page doesn't exist StatusCode:" + listPage.getCode() + ",url:" + currDirUrl);
				break;
			}
			String text = listPage.getContent();
			LOG.info("Get List Url:" + currDirUrl + ", size:" + text.length());
			if (!StrUtil.isBlank(dirs.forbidden) && text.indexOf(dirs.forbidden) != -1) {
				LOG.error("web forbidden,return:" + text);
				return;
			}
			if (!dirs.hasDetail) {
				List<Map<String, String>> datas = parser.parse(listPage,model);
				if (datas.size() == 0) {
					LOG.info("Sorry, we cannot find data relevant to"+dir.name+"..now exit");
					return;
				}
				listener.onParse(new SpiderContext(dirs, Thread.currentThread()), datas);

				continue;
			}		
			Elements elements = Jsoup.parse(text).select(dir.xpath);
			if (elements == null || elements.isEmpty()) {
				LOG.info("end list page,content:\n" + listPage.getContent());
				break;
			}		
			for (Element ele : elements) {
				String origin_url = ele.attr("href");
				try {
					origin_url = HtmlUtil.getAbsoluteURL(origin_url,
							dir.url);
					LOG.info("begin analyse detail page:" + origin_url);
					if (listener.checkUrl(new SpiderContext(dirs, Thread.currentThread()),origin_url)) {
						LOG.warn("url already fetch!!" + origin_url);
						continue;
					}
					Page result = fetcher.fetch(new Request(origin_url,dirs.encode));
					if (result.getCode() != 200) {
						LOG.warn("Page doesn't exist StatusCode:" + result.getCode() + ",url:" + origin_url);
						continue;
					}
					if (!StrUtil.isBlank(dirs.forbidden) && text.indexOf(dirs.forbidden) != -1) {
						LOG.error("web forbidden,return:" + text);
						return;
					}
					List<Map<String, String>> datas = parser.parse(result,model);
					listener.onParse(new SpiderContext(dirs, Thread.currentThread()), datas);
				} catch (Exception ex) {
					StringBuilder message = new StringBuilder();
					message.append(" Error in analyse url:").append(origin_url);
					LOG.error(message.toString(), ex);
				}

			}

		} while (true);
	}
	/**
	 * @param dir
	 * @param page
	 * @return
	 */
	private String getNextUrl(Dir dir, int page) {
		// TODO Auto-generated method stub
		if (page == 1) {
			return dir.url;
		}
		if (dir.next == null) {
			return null;
		}
		if (StrUtil.isBlank(dir.next.value)) {
			return null;
		}
		String start = String.valueOf(dir.next.start + (page - 2));
		return String.format(dir.next.value, start);
	}

	public Parser getParser() {
		return parser;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}
}

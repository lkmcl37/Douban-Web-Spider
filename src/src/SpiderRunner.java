import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import listener.SpiderListener;

import org.apache.log4j.Logger;

import parser.Parser;
import utils.ConfigKit;
import utils.StrUtil;
import utils.ThreadUtil;
import config.Dir;
import config.Dirs;
import config.SpiderConfigReader;
import core.Spider;
import fetcher.DirFetcher;

public class SpiderRunner {
	
	private static final Logger LOG = Logger.getLogger(SpiderRunner.class);

	private ThreadPoolExecutor executor = null;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		SpiderRunner runner = new SpiderRunner();
		runner.doSpider();
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void doSpider() throws Exception {
		
		List<Dirs> dirs = SpiderConfigReader.getDirs();
		if (dirs == null || dirs.size() == 0) {
			System.err.println("Cannot find any crawling task, exit...");
			return;
		}
		int num = ConfigKit.getInt("spider.thread");
		if (num > 0) {
			executor = ThreadUtil.getThreadPool(num, num);
		}

		String listener = SpiderConfigReader.getSpiderConfig().listener;
		for (final Dirs _dirs : dirs) {
			if (!StrUtil.isBlank(_dirs.listener)) {
				listener = _dirs.listener;
			}

			if (_dirs.dirs == null) {
				_dirs.dirs = new ArrayList<Dir>();
			}

			if (!StrUtil.isBlank(_dirs.dirFetcher)) {
				DirFetcher fetcher = (DirFetcher) Class.forName(
						_dirs.dirFetcher).newInstance();
				List<Dir> fDirs = fetcher.fetcherDir();
				if (fDirs != null && fDirs.size() > 0) {
					_dirs.dirs.addAll(fDirs);
				}
			}
			Collections.sort(_dirs.dirs);

			final SpiderListener spiderListener = (SpiderListener) Class
					.forName(listener).newInstance();
			LOG.info("Begin Spide ,listener=" + listener + ",directory size:"
					+ _dirs.dirs.size());
			Spider spider = new Spider(_dirs, spiderListener);
			if (!StrUtil.isBlank(_dirs.parser)) {
				final Parser parser = (Parser) Class.forName(_dirs.parser)
						.newInstance();
				spider.setParser(parser);
			}
			if (num == 0) {
				spider.run();
			} else {
				executor.submit(spider);
			}
		}

	}

}

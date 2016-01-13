package utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtil {
	private static final ThreadPoolExecutor.CallerRunsPolicy HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();
	private static final TimeUnit UNIT = TimeUnit.SECONDS;
	private static final BlockingQueue<Runnable> WORKQUEUE = new ArrayBlockingQueue<Runnable>(5);
	/**
	 * @param corePoolSize
	 * @param maximumPoolSize
	 * @return
	 */
	public static ThreadPoolExecutor getThreadPool(int corePoolSize, int maximumPoolSize) {
		return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 10L, UNIT, WORKQUEUE, HANDLER);
	}
}

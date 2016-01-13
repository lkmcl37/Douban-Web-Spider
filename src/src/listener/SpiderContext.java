package listener;

import config.Dirs;

public class SpiderContext {
	private Dirs dirs;
	private Thread thread;
	public SpiderContext(Dirs dirs, Thread thread) {
		this.dirs = dirs;
		this.thread = thread;
	}
	public Dirs getDirs() {
		return dirs;
	}
	public void setDirs(Dirs dirs) {
		this.dirs = dirs;
	}
	public Thread getThread() {
		return thread;
	}
	public void setThread(Thread thread) {
		this.thread = thread;
	}
	
}

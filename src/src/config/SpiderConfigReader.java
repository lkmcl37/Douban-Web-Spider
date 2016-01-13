package config;

import java.io.File;
import java.util.List;

import utils.JaxbKit;

public class SpiderConfigReader {
	private final static String CONFIG_XML = "SpiderConfig.xml";
	private static SpiderConfig spiderConfig = null;
	private static long start = System.currentTimeMillis();
	
	public static void main(String[] args) {
		SpiderConfig config = SpiderConfigReader.getSpiderConfig();
		System.out.println(config);
	}
	private SpiderConfigReader(){}
	/**
	 * get WebCofig, reload every two hours
	 * @return
	 */
	public static SpiderConfig getSpiderConfig(){
		if(spiderConfig == null || (System.currentTimeMillis() - start) > 60*60*2){
			String baseDir = SpiderConfigReader.class.getClassLoader().getResource("").getFile();
			spiderConfig = JaxbKit.unmarshal(new File(baseDir,CONFIG_XML), SpiderConfig.class);
			start = System.currentTimeMillis();
		}
		return spiderConfig;
	}

	public static List<Dirs> getDirs() {
		SpiderConfig config = SpiderConfigReader.getSpiderConfig();
		return config.dirs;
	}

}

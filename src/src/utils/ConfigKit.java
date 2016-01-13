package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.IDataSourceProvider;
import com.jfinal.plugin.c3p0.C3p0Plugin;


public class ConfigKit {
	private static final Logger LOG = Logger.getLogger(ConfigKit.class);
	private static Set<String> dsSet = new HashSet<String>();
	private static Properties properties = new Properties();
	static {
		try {
			properties.load(new FileInputStream(new File(PathKit.getRootClassPath(), "webspider.properties")));
			LOG.info("load webspider.properties success....");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private ConfigKit() {
	}

	/**
	 * @throws ParseException
	 */
	public synchronized static void initDS(String dsName) {
		try {
			if (!dsSet.contains(dsName)) {
				IDataSourceProvider ds = initDs();
				ActiveRecordPlugin arp = new ActiveRecordPlugin(dsName,ds);
				arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));//
				arp.setShowSql(true);
				arp.start();
				dsSet.add(dsName);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	/**
	 * @param dataSource
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private static IDataSourceProvider initDs() throws FileNotFoundException, IOException {
		C3p0Plugin c3p0Plugin = new C3p0Plugin(properties.getProperty("db.jdbcUrl"),
				properties.getProperty("db.user"), properties.getProperty("db.password"));
		c3p0Plugin.start();
		return c3p0Plugin;
	}
	/**
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static int getInt(String key) throws FileNotFoundException, IOException {
		return Integer.parseInt(properties.getProperty(key));
	}
	/**
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String getStr(String key) throws FileNotFoundException, IOException {
		return properties.getProperty(key);
	}

}

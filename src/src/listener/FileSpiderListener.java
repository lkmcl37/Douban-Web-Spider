package listener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.jfinal.kit.JsonKit;


public class FileSpiderListener implements SpiderListener {
	private static Set<String> urls = new HashSet<String>();
	@Override
	public void onParse(SpiderContext context, List<Map<String, String>> models) {
		// TODO Auto-generated method stub
		String name = context.getDirs().name;
		Writer writer = null;
		try {
			writer = new FileWriter(new File(name + ".json"), true);
			for (Map<String, String> model : models) {
				IOUtils.write(JsonKit.toJson(model) + "\r\n", writer);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Failed", e);
		}finally{
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public boolean checkUrl(SpiderContext context, String url) {
		// TODO Auto-generated method stub
		if (urls.contains(url)) {
			return true;
		}
		urls.add(url);
		return false;
	}
}

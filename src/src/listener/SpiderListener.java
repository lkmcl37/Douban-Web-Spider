package listener;

import java.util.List;
import java.util.Map;

public interface SpiderListener {

	void onParse(SpiderContext context, List<Map<String, String>> models);
	boolean checkUrl(SpiderContext context,String url);
	
}

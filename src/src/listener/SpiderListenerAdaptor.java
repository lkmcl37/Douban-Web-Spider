package listener;

import java.util.List;
import java.util.Map;

import com.jfinal.kit.JsonKit;

public class SpiderListenerAdaptor implements SpiderListener{

	@Override
	public void onParse(SpiderContext context, List<Map<String, String>> models) {
		// TODO Auto-generated method stub
		System.err.println(">>>result:" + JsonKit.toJson(models));
	}

	@Override
	public boolean checkUrl(SpiderContext context,String url) {
		// TODO Auto-generated method stub
		return false;
	}
}

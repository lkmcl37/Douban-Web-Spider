package listener;

import java.util.List;
import java.util.Map;

import utils.StrUtil;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.Record;
/**
 * A listener based on DP
 */
public class DbSpiderListener implements SpiderListener{
	@Override
	public void onParse(SpiderContext context, List<Map<String, String>> models) {
		String table = context.getDirs().model.table;
		if (StrUtil.isBlank(table)) {
			throw new IllegalArgumentException("table is null, please set table in model");
		}
		for (Map<String, String> map : models) {
			Record data = new Record();
			for (Map.Entry<String,String> entry : map.entrySet()) {
				data.set(entry.getKey(), entry.getValue());
			}
			Db.use(DbKit.MAIN_CONFIG_NAME).save(table, data);
		}
	}

	@Override
	public boolean checkUrl(SpiderContext context,String url) {
		// TODO Auto-generated method stub
		String table = context.getDirs().model.table;
		if (StrUtil.isBlank(table)) {
			throw new IllegalArgumentException("table is null, please set table in model");
		}
		String sql = "select * from "+ table +" where origin_url=?";
		Record record = Db.use(DbKit.MAIN_CONFIG_NAME).findFirst(sql,url);
		if (record != null) {
			return true;
		}
		return false;
	}
}

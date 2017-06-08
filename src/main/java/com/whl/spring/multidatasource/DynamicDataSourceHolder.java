package com.whl.spring.multidatasource;

/**
 * @Author: Whling
 * @Date: Created in 20:50 2017/6/7
 * @Modified By:
 * @Description:
 */
public class DynamicDataSourceHolder {
	// 写库对应的数据源key,只是一个标识
	public static final String MASTER = "master";

	public static final String SLAVE = "slave";

	public static final ThreadLocal<String> holder = new ThreadLocal<String>();

	/**
	 * 获取数据源的key
	 */
	public static String getDataSourceKey() {
		return holder.get();
	}

	/**
	 * 设置数据源的key
	 */
	public static void putDataSourceKey(String key) {
		holder.set(key);
	}

	/**
	 * 标记为MASTER
	 */
	public static void markMaster() {
		putDataSourceKey(MASTER);
	}

	/**
	 * 标记为SLAVER
	 */
	public static void markSlave() {
		putDataSourceKey(SLAVE);
	}

	public static boolean isMaster() {
		String dataSourceKey = getDataSourceKey();
		if (MASTER.equals(dataSourceKey)) {
			return true;
		}
		return false;
	}
}

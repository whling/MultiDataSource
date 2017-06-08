package com.whl.spring.multidatasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Whling
 * @Date: Created in 20:47 2017/6/7
 * @Modified By:
 * @Description: 自定义数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	// @Override
	// protected Object determineCurrentLookupKey() {
	// String dataSourceKey = DynamicDataSourceHolder.getDataSourceKey();
	// System.out.println("现在走的是"+dataSourceKey);
	// return dataSourceKey;
	// }

	private static final Logger LOGGER = LoggerFactory.getLogger(DynamicDataSource.class);

	private Integer slaveCount;

	// 轮询计数,初始为-1,AtomicInteger是线程安全的
	private AtomicInteger counter = new AtomicInteger(-1);

	// 记录读库的key
	private List<Object> slaveDataSources = new ArrayList<Object>(0);

	@Override
	protected Object determineCurrentLookupKey() {
		// 使用DynamicDataSourceHolder保证线程安全，并且得到当前线程中的数据源key
		// 获取key之后，父类会从一个map中获取DataSource，这个map的key就是这里返回的key
		if (DynamicDataSourceHolder.isMaster()) {
			Object key = DynamicDataSourceHolder.getDataSourceKey();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("当前DataSource的key为: " + key);
			}
			return key;
		}
		Object key = getSlaveKey();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("当前DataSource的key为: " + key);
		}
		return key;

	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		// 由于父类的resolvedDataSources属性是私有的子类获取不到，需要使用反射获取
		Field field = ReflectionUtils.findField(AbstractRoutingDataSource.class, "resolvedDataSources");
		field.setAccessible(true); // 设置可访问

		try {
			Map<Object, DataSource> resolvedDataSources = (Map<Object, DataSource>) field.get(this);
			// 读库的数据量等于数据源总数减去写库的数量
			this.slaveCount = resolvedDataSources.size() - 1;
			for (Map.Entry<Object, DataSource> entry : resolvedDataSources.entrySet()) {
				if (DynamicDataSourceHolder.MASTER.equals(entry.getKey())) {
					continue;
				}
				slaveDataSources.add(entry.getKey());
			}
		} catch (Exception e) {
			LOGGER.error("afterPropertiesSet error! ", e);
		}
	}

	/**
	 * 轮询算法实现
	 *
	 * @return
	 */
	public Object getSlaveKey() {
		// 得到的下标为：0、1、2、3……
		Integer index = counter.incrementAndGet() % slaveCount;
		if (counter.get() > 9999) { // 以免超出Integer范围
			counter.set(-1); // 还原
		}
		return slaveDataSources.get(index);
	}

}

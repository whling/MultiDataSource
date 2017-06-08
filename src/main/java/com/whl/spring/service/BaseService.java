package com.whl.spring.service;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author: Whling
 * @Date: Created in 17:55 2017/1/13
 * @Modified By:
 * @Description: 1、queryById 2、queryAll 3、queryOne 4、queryListByWhere
 *               5、queryPageListByWhere 6、save 7、update 8、deleteById
 *               9、deleteByIds 10、deleteByWhere
 */
public interface BaseService<T> {
	/**
	 * 通过主键查询
	 * 
	 * @param key
	 * @return
	 */
	public T getByPrimaryKey(Object key);

	/**
	 * 分页查询所有
	 * 
	 * @param pageNum
	 *            第几页
	 * @param pageSize
	 *            一页大小
	 * @return
	 */
	public PageInfo<T> getAll(int pageNum, int pageSize);

	/**
	 * 通过非主键字段查询，如果有多条则抛异常
	 * 
	 * @param t
	 * @return
	 */
	public T getOne(T t);

	/**
	 * 通过条件查询列表
	 * 
	 * @param t
	 * @return
	 */
	public List<T> getListByWhere(T t);

	/**
	 * 通过条件查询列表，分页
	 * 
	 * @param t
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<T> getPageListByWhere(T t, int pageNum, int pageSize);

	/**
	 * 通过example来查询，不分页
	 * 
	 * @param object
	 * @return
	 */
	public List<T> getListByExample(Object object);

	/**
	 * 通过example分页查询
	 * 
	 * @param object
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<T> getPageListByExample(Object object, int pageNum, int pageSize);

	/**
	 * 全部字段保存
	 * 
	 * @param t
	 * @return
	 */
	public Integer save(T t);

	/**
	 * 选择保存
	 * 
	 * @param t
	 * @return
	 */
	public Integer saveSelective(T t);

	/**
	 * 全部字段更新
	 * 
	 * @param t
	 * @return
	 */
	public Integer update(T t);

	/**
	 * 选择更新
	 * 
	 * @param t
	 * @return
	 */
	public Integer updateSelective(T t);

	/**
	 * 通过主键删除
	 * 
	 * @param key
	 * @return
	 */
	public Integer deleteByPrimaryKey(Object key);

	/**
	 * 批量删除主键
	 * 
	 * @param clazz
	 *            删除的类型
	 * @param property
	 *            哪个属性
	 * @param values
	 *            哪些值
	 * @return
	 */
	public Integer deleteByPrimaryKeys(Class clazz, String property, List<Object> values);

	/**
	 * 通过条件删除
	 * 
	 * @param t
	 * @return
	 */
	public Integer deleteByWhere(T t);
}

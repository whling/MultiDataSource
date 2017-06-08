package com.whl.spring.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whl.spring.service.BaseService;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

/**
 * @Author: Whling
 * @Date: Created in 17:55 2017/1/13
 * @Modified By:
 * @Description:
 */
public class BaseServiceImpl<T> implements BaseService<T> {
    @Autowired
    private Mapper<T> mapper;

    @Override
    public T getByPrimaryKey(Object object){
        return mapper.selectByPrimaryKey(object);
    }
    @Override
    public PageInfo<T> getAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<T> tList = mapper.selectAll();
        return new PageInfo<T>(tList);
    }

    @Override
    public T getOne(T t) {
        return mapper.selectOne(t);
    }

    @Override
    public List<T> getListByWhere(T t) {
        return mapper.select(t);
    }

    @Override
    public PageInfo<T> getPageListByWhere(T t, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<T> tList = mapper.select(t);
        return new PageInfo<T>(tList);
    }

    @Override
    public List<T> getListByExample(Object object) {
        return mapper.selectByExample(object);
    }

    @Override
    public PageInfo<T> getPageListByExample(Object object, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<T> tList = mapper.selectByExample(object);
        return new PageInfo<T>(tList);
    }

    @Override
    public Integer save(T t) {
        return mapper.insert(t);
    }

    @Override
    public Integer saveSelective(T t) {
        return mapper.insertSelective(t);
    }

    @Override
    public Integer update(T t) {
        return mapper.updateByPrimaryKey(t);
    }

    @Override
    public Integer updateSelective(T t) {
        return mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public Integer deleteByPrimaryKey(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    public Integer deleteByPrimaryKeys(Class clazz,String property, List<Object> values) {
        Example example=new Example(clazz);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn(property,values);
        return mapper.deleteByExample(example);
    }

    @Override
    public Integer deleteByWhere(T t) {
        return mapper.delete(t);
    }
}

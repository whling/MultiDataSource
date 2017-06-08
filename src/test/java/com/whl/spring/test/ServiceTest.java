package com.whl.spring.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageInfo;
import com.whl.spring.pojo.User;
import com.whl.spring.service.UserService;

/**
 * @Author: Whling
 * @Date: Created in 10:55 2017/1/13
 * @Modified By:
 * @Description:
 */
public class ServiceTest {
	private ApplicationContext applicationContext = null;
	private UserService userService = null;

	@Before
	public void init() {
		applicationContext = new ClassPathXmlApplicationContext(
				new String[] { "classpath:spring/applicationContext-*.xml" });
		userService = applicationContext.getBean(UserService.class);
	}

	@Test
	public void testSelect() {
		User user = userService.getByPrimaryKey(1);
		System.out.println(user.getUsername());
	}

	/**
	 * 多数据源动态切换
	 */
	@Test
	public void testSelectAll() {
		PageInfo<User> pageInfo = userService.getAll(0, 20);
		List<User> list = pageInfo.getList();
		for (User user : list) {
			System.out.println(user.getUsername());
		}
		User u = new User();
		u.setUsername("库里");
		u.setAddress("美国");
		u.setPassword("12345");
		userService.save(u);

	}

	@Test
	public void testSelectByWhere() {
		// PageInfo<User> pageInfo = userService.getAll(1, 10);
		// List<User> userList = pageInfo.getList();
		// System.out.println(userList.size());
		List<User> userList = userService.getListByWhere(null);
		System.out.println(userList.size());
	}

	@Test
	public void testDelete() {
		List<Object> values = new ArrayList();
		values.add(20);
		values.add(21);
		values.add(22);
		Integer id = userService.deleteByPrimaryKeys(User.class, "id", values);
		System.out.println(id);
	}

	@Test
	public void testInsert() {
		User user = new User();
		user.setUsername("cb");
		user.setPassword("123");
		user.setBirthday(new Date());
		userService.saveSelective(user);
	}

	@Test
	public void testUpdate() {
		User user = userService.getByPrimaryKey(1);
		user.setDescrible("student");
		user.setBirthday(new Date());
		userService.updateSelective(user);
	}
}

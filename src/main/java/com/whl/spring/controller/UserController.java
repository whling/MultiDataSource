package com.whl.spring.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whl.spring.pojo.User;
import com.whl.spring.service.UserService;

@Controller
public class UserController {
	// 注入service服务对象
	@Autowired
	private UserService userService;

	@RequestMapping("/getuser/{id}")
	@ResponseBody
	public User getUserById(@PathVariable Integer id) {
		User user = userService.getByPrimaryKey(id);
		return user;
	}

	@RequestMapping("hello")
	@ResponseBody
	public String hello() {
		// int i = 1 / 0;
		return "hello world";
	}

	/**
	 * 访问jsp页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/helloJsp")
	public String helloJsp(Model model) {
		System.out.println("HelloController.helloJsp().hello=" + "whling");
		model.addAttribute("hello", "whling");
		return "hello";
	}

	@RequestMapping(value = "user")
	@ResponseBody
	public User getUser() {
		User user = new User();
		user.setId(1);
		user.setUsername("周杰伦");
		user.setScore(100f);
		user.setAddress("中国台湾");
		user.setBirthday(new Date());
		return user;
	}

	@RequestMapping(value = "maps")
	@ResponseBody
	public Map<String, Object> getMap() {
		Map<String, Object> map = new ConcurrentHashMap<>();
		map.put("username", "孙燕姿");
		map.put("address", "新加坡");
		return map;

	}

	@RequestMapping(value = "list")
	@ResponseBody
	public List<User> getList() {
		ArrayList<User> list = new ArrayList<>();
		User user = new User();
		user.setId(1);
		user.setUsername("周杰伦");
		user.setScore(232F);
		user.setAddress("中国台湾");
		user.setBirthday(new Date());
		list.add(user);
		return list;

	}

	/**
	 * ssm框架整合test
	 *//*
		 * @RequestMapping("ssm")
		 * 
		 * @ResponseBody public List<User> getUserList() { List<User> userList =
		 * userService.getUserList(); return userList; }
		 */

}

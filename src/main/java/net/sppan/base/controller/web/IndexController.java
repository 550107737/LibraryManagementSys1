package net.sppan.base.controller.web;

import java.util.List;

import net.sppan.base.controller.BaseController;

import net.sppan.base.entity.UserModel;
import net.sppan.base.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value={"/","/index"})
	public String index(){
		List<UserModel> users = userService.findAll();
		logger.debug(users.toString());
		return "index";
	}
}

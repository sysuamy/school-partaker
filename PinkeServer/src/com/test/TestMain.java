package com.test;


public class TestMain {
	/**
	 * 
	 * @param args
	
	public static void main(String[] args) {
		MockHttpServletRequest request = new MockHttpServletRequest("post",
		"/my/user");
		request.addParameter("username","111");
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		MyController controller = new MyController();
		UserInterface userIFCProxy = SpringUtil.getBean("userIFCProxy",
		UserInterface.class);
		//注入接口代理
		controller.setUserIFCProxy(userIFCProxy);
		
		/**
		 * 添加用户
		UserBean userBean = new UserBean();
		userBean.setSuser("xxxx2");
		userBean.setSname("yyyy2");
		userBean.setSpwd("123456");

		DeptBean addDeptBean = new DeptBean();
		addDeptBean.setDeptId(8);
		addDeptBean.setDeptName("部门8");
		userBean.setDeptBean(addDeptBean);
		//将要保存的UserBean注入到MyController中
		controller.setUserBean(userBean);
		ModelAndView modelAndView = controller.saveAddUser(request, response);
		ModelMap modelMap = modelAndView.getModelMap();
		MessageBean messageBean = (MessageBean)modelMap.get("messageBean");
		System.out.println("返回结果布尔值:" + messageBean.isMessageFlag() + "返回信息:" + messageBean.getReturnMessage());
		 */		
		
		/**
		 * 得到用户的信息
		ModelAndView modelAndView = controller.getUser(request, response);
		System.out.println(modelAndView.getViewName());
		ModelMap modelMap = modelAndView.getModelMap();
		List<UserBean> userList = (List<UserBean>)modelMap.get("userList");
		for (UserBean bean : userList) {
			DeptBean deptBean = bean.getDeptBean();
			String str = "用户名：" + bean.getSuser() + "   姓名：" + bean.getSname()
					+ "   密码:" + bean.getSpwd() + "  部门ID：" + deptBean.getDeptId() + "  部门名称:" + deptBean.getDeptName();
			System.out.println(str);
		}
		*/
		
		/**
		 * 添加学生成绩 
		ScoreBeanId scoreBeanId = new ScoreBeanId(3,"语文");
		ScoreBean scoreBean = new ScoreBean(scoreBeanId,"振涛",89.0f);
		//将要保存的ScoreBean注入MyController
		controller.setScoreBean(scoreBean);
		ModelAndView modelAndView = controller.saveAddScore(request, response);
		System.out.println(modelAndView.getViewName());
		ModelMap modelMap = modelAndView.getModelMap();
		MessageBean messageBean = (MessageBean)modelMap.get("messageBean");
		System.out.println("返回结果布尔值:" + messageBean.isMessageFlag() + "返回信息:" + messageBean.getReturnMessage());
		 */				
		
		/**
		 * 读取学生成绩
		ModelAndView modelAndView = controller.getScore(request,response);
		System.out.println(modelAndView.getViewName());
		ModelMap modelMap = modelAndView.getModelMap();
		List<ScoreBean> scoreList = (List<ScoreBean>)modelMap.get("scoreList");
		for (ScoreBean bean : scoreList) {
			ScoreBeanId beanId = bean.getId();
			String str = "学生编号：" + beanId.getSno() + "   科目：" + beanId.getSubject()
					+ "   姓名:" + bean.getSname() + "  分数：" + bean.getScore();
			System.out.println(str);
		}

	}
*/
}

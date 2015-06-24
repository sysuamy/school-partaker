package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bean.ShareBean;
import com.bean.UserBean;
import com.ifc.ShareInterface;
import com.ifc.UserInterface;
import com.util.JsonUtil;

@Controller
@RequestMapping("/share")
public class ShareController {
	@Autowired
	private ShareInterface shareIFCProxy;
	
	@Autowired
	private UserInterface userIFCProxy;
	
	PrintWriter out;

	@RequestMapping("/getList")
	public void getList(HttpServletRequest request,HttpServletResponse response){
		String myActive = request.getParameter("myActive");
		String activeWeek = request.getParameter("active_week");
		String regionId = request.getParameter("regionId");
		String start = request.getParameter("currentPage");
		String limit = request.getParameter("pageSize");
		String suser = request.getParameter("suser");
		String category = request.getParameter("category");
		
		Map<String,Object> params = new LinkedHashMap<String, Object>();
		params.put("myactive",myActive);
		params.put("activeweek",activeWeek);
		params.put("regionid",regionId);
		params.put("start",start);
		params.put("limit",limit);
		params.put("suser",suser);
		params.put("category",category);
		List<ShareBean> shareList = new ArrayList<ShareBean>();
		shareList = shareIFCProxy.getList(params);
		for(ShareBean shareBean : shareList){
			List<UserBean> userSet = shareBean.getUserSet();
			for(UserBean user : userSet){
				//如果不设置为null列表会无限嵌套的。。。
		 		user.setShareSet(null);
	      	}
			
			UserBean launcherInfo= userIFCProxy.getUserById(shareBean.getMLaunch());
			launcherInfo.setShareSet(null);
			shareBean.setLauncherInfo(launcherInfo);
		}
		Map<String,Object> returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("returnCode",0);
		returnMap.put("shareList",shareList);
		String json = JsonUtil.jsonUtil(returnMap);
		System.out.println(json);
		response.setContentType("application/json ; charset=utf-8");
		Cookie cookie = new Cookie("JSESSIONID",request.getSession().getId());
		response.addCookie(cookie);
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/join")
	public void join(HttpServletRequest request,HttpServletResponse response){
		String suser = request.getParameter("suser");
		String activeIdStr = request.getParameter("activeid");
		int activeId = 0;
		if(activeIdStr!=null){
			activeId = Integer.parseInt(activeIdStr);
		}
		UserBean userBean = userIFCProxy.getUserById(suser);
		ShareBean shareBean = shareIFCProxy.getShareById(activeId);
		boolean addFlag = false;
		boolean joinFlag = false;
		boolean existFlag = true;
		if(shareBean != null && (shareBean.getMJoinNum() < shareBean.getMNeedNum() || shareBean.getMNeedNum() == 0)){
			joinFlag = true;
		}
		if(userBean!=null && joinFlag){
			if(shareBean != null){
				List<UserBean> userSet = shareBean.getUserSet();
				for(UserBean user : userSet){
					String suserTemp = user.getSuser();
					if(suser!=null && suser.equals(suserTemp)){
						existFlag = false;
					}
				}
				if(existFlag){
					userSet.add(userBean);
					shareBean.setMJoinNum(shareBean.getMJoinNum()+1);
					addFlag = shareIFCProxy.update(shareBean);
				}
			}
		}
		
		Map<String,Object> returnMap = new LinkedHashMap<String, Object>();
		if(addFlag){
			returnMap.put("returnCode",0);
			returnMap.put("returnMsg","恭喜你,加入成功");
		}else{
			returnMap.put("returnCode",1);
			if(!joinFlag){
				returnMap.put("returnMsg","对不起,所需人数已满");
			}else if(!existFlag){
				returnMap.put("returnMsg","您已经参加了该活动");
			}else {
				returnMap.put("returnMsg","用户为空");
			}
		}
		String json = JsonUtil.jsonUtil(returnMap);
		System.out.println(json);
		response.setContentType("application/json ; charset=utf-8");
		Cookie cookie = new Cookie("JSESSIONID",request.getSession().getId());
		response.addCookie(cookie);
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/unpublish")
	public void unPublish(HttpServletRequest request,HttpServletResponse response){
		String suser = request.getParameter("suser");
		String activeIdStr = request.getParameter("activeid");
		int activeId = 0;
		if(activeIdStr!=null){
			activeId = Integer.parseInt(activeIdStr);
		}
		UserBean userBean = userIFCProxy.getUserById(suser);
		ShareBean shareBean = shareIFCProxy.getShareById(activeId);
		boolean unPublish = false;
		boolean isUnpublish = false;
		if(userBean!=null ){
			if(shareBean != null&&shareBean.getMLaunch().equals(userBean.getSuser())){
				if(shareBean.getMState().equals("开放")){
					shareBean.setMState("关闭");;
					unPublish = shareIFCProxy.update(shareBean);
				}else {
					isUnpublish=true;
				}
			}
		}
		
		Map<String,Object> returnMap = new LinkedHashMap<String, Object>();
		if(unPublish){
			returnMap.put("returnCode",0);
			returnMap.put("returnMsg","取消活动成功");
		}else{
			returnMap.put("returnCode",1);
			if(isUnpublish){
				returnMap.put("returnMsg","本活动已关闭");
			}else {
				returnMap.put("returnMsg","用户为空");
			}
		}
		String json = JsonUtil.jsonUtil(returnMap);
		System.out.println(json);
		response.setContentType("application/json ; charset=utf-8");
		Cookie cookie = new Cookie("JSESSIONID",request.getSession().getId());
		response.addCookie(cookie);
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/quit")
	public void quit(HttpServletRequest request,HttpServletResponse response){
		String suser = request.getParameter("suser");
		String activeIdStr = request.getParameter("activeid");
		int activeId = 0;
		if(activeIdStr!=null){
			activeId = Integer.parseInt(activeIdStr);
		}
		UserBean userBean = userIFCProxy.getUserById(suser);
		ShareBean shareBean = shareIFCProxy.getShareById(activeId);
		boolean quitFlag = false;
		boolean existFlag = false;
		int quit=0;
		if(userBean!=null ){
			if(shareBean != null){
				List<UserBean> userSet = shareBean.getUserSet();
				for(UserBean user : userSet){
					String suserTemp = user.getSuser();
					if(suser!=null && suser.equals(suserTemp)){
						existFlag = true;
					}
				}
				if(existFlag){
					quit=shareIFCProxy.deleteJoinUser(suser, activeId);
					if (quit>0) {
						shareBean.setMJoinNum(shareBean.getMJoinNum()-1);
						quitFlag = shareIFCProxy.update(shareBean);
					}

				}
			}
		}
		
		Map<String,Object> returnMap = new LinkedHashMap<String, Object>();
		if(quitFlag&&(quit>0)){
			returnMap.put("returnCode",0);
			returnMap.put("returnMsg","退出活动成功");
		}else{
			returnMap.put("returnCode",1);
			if(!existFlag){
				returnMap.put("returnMsg","未参加此活动");
			}else if (quit<=0) {
				returnMap.put("returnMsg","退出活动失败");
			}else {
				returnMap.put("returnMsg","用户为空");
			}
		}
		String json = JsonUtil.jsonUtil(returnMap);
		System.out.println(json);
		response.setContentType("application/json ; charset=utf-8");
		Cookie cookie = new Cookie("JSESSIONID",request.getSession().getId());
		response.addCookie(cookie);
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/addShare")
	public void addShare(HttpServletRequest request,HttpServletResponse response){
		String title = request.getParameter("title");
		String category = request.getParameter("category");
		String placeCode = request.getParameter("placeCode");
		String placeStr = request.getParameter("placeStr");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String needNum = request.getParameter("needNum");
		String contact = request.getParameter("contact");
		String content = request.getParameter("content");
		String suser = request.getParameter("suser");
		try {
			ShareBean shareBean = new ShareBean();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
			shareBean.setMTitle(title);
			shareBean.setMCategory(category);
			shareBean.setMPlace(placeCode);
			shareBean.setMPlaceStr(placeStr);
			shareBean.setMStartDate(dateFormat.parse(startDate));
			shareBean.setMStartDateStr(startDate);
			if (!endDate.equals("")&&endDate!=null) {
				shareBean.setMEndDate(dateFormat.parse(endDate));
				shareBean.setMEndDateStr(endDate);
			}
			shareBean.setMState("开放");
			if(needNum != null && !"".equals(needNum)){
				shareBean.setMNeedNum(Integer.parseInt(needNum));
			}else {
				shareBean.setMNeedNum(0);
			}
			shareBean.setMContacts(contact);
			shareBean.setMContent(content);
			shareBean.setMLaunch(suser);
			boolean addFlag = shareIFCProxy.add(shareBean);
			Map<String,Object> returnMap = new LinkedHashMap<String, Object>();
			if(addFlag){
				returnMap.put("returnCode",0);
				returnMap.put("returnMsg","发起成功");
			}else{
				returnMap.put("returnCode",1);
				returnMap.put("returnMsg","发起失败");
			}
			String json = JsonUtil.jsonUtil(returnMap);
			System.out.println(json);
			response.setContentType("application/json ; charset=utf-8");
			Cookie cookie = new Cookie("JSESSIONID",request.getSession().getId());
			response.addCookie(cookie);
			try {
				response.getWriter().write(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getUserList")
	public void getUserList(HttpServletRequest request,HttpServletResponse response){
		String suser = request.getParameter("suser");
		String activeIdStr = request.getParameter("activeid");
		int activeId = 0;
		if(activeIdStr!=null){
			activeId = Integer.parseInt(activeIdStr);
		}

		ShareBean shareBean = shareIFCProxy.getShareById(activeId);
		Map<String,Object> returnMap = new LinkedHashMap<String, Object>();
		if(shareBean!=null ){
			returnMap.put("returnCode",0);
			List<UserBean> userList=shareBean.getUserSet();
			for(UserBean userBean : userList){
				userBean.setShareSet(null);
			}
			returnMap.put("userList",userList);
		}else {
			returnMap.put("returnCode",1);
			returnMap.put("returnMsg","获取用户列表失败");
		}
		
		String json = JsonUtil.jsonUtil(returnMap);
		System.out.println(json);
		response.setContentType("application/json ; charset=utf-8");
		Cookie cookie = new Cookie("JSESSIONID",request.getSession().getId());
		response.addCookie(cookie);
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

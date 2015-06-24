package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bean.IVCenterBean;
import com.bean.ReviewBean;
import com.bean.UserBean;
import com.ifc.IVCenterInterface;
import com.ifc.UserInterface;
import com.util.JsonUtil;

@Controller
@RequestMapping("/ivcenter")
public class IVCenterController {
	@Autowired
	private IVCenterInterface ivCenterIFCProxy;
	
	@Autowired
	private UserInterface userIFCProxy;
	PrintWriter out;
	
	@RequestMapping("/getList")
	public void getList(HttpServletRequest request,HttpServletResponse response){
		String limitStr = request.getParameter("limit");
		String startStr = request.getParameter("start");
		int limit = 10;
		int start = 0;
		if(limitStr != null){
			limit = Integer.parseInt(limitStr);
		}
		if(startStr != null){
			start = Integer.parseInt(startStr);
		}
		List<IVCenterBean> ivCenterList = ivCenterIFCProxy.getList(start,limit);
		for(IVCenterBean bean : ivCenterList){
			bean.setUser(null);
		}
		
		Map<String,Object> returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("returnCode",0);
		returnMap.put("ivCenterList",ivCenterList);
		
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
	
	@RequestMapping("/addMsg")
	public void addMsg(HttpServletRequest request,HttpServletResponse response){
		String userName = request.getParameter("username");
		String date = request.getParameter("date");
		String content = request.getParameter("content");
		
		IVCenterBean ivCenterBean = new IVCenterBean();
		UserBean user = userIFCProxy.getUserById(userName);
		ivCenterBean.setContent(content);
		ivCenterBean.setIvDate(date);
		ivCenterBean.setUser(user);
		
		boolean addFlag = ivCenterIFCProxy.addIVCenter(ivCenterBean);
		Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
		if(addFlag){
			returnMap.put("returnCode",0);
			returnMap.put("returnMsg","发表成功");
		}else{
			returnMap.put("returnCode",1);
			returnMap.put("returnMsg","发表失败");
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
	
	@RequestMapping("/getReview")
	public void getReView(HttpServletRequest request,HttpServletResponse response){
		
		String reFKeyStr = request.getParameter("ivkey");
		int reFKey = 0;
		if(reFKeyStr != null && !reFKeyStr.equals("")){
			reFKey = Integer.parseInt(reFKeyStr);
		}
		List<ReviewBean> reviewList = ivCenterIFCProxy.getReviewList(reFKey);
		Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
//		if(reviewList.size() > 0){
			returnMap.put("returnCode",0);
			returnMap.put("reviewList",reviewList);
//		}else{
//			returnMap.put("returnCode",1);
//			returnMap.put("returnMsg","没有评论");
//		}
		
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
	
	@RequestMapping("/review")
	public void reView(HttpServletRequest request,HttpServletResponse response){
		String ivKeyStr = request.getParameter("ivkey");
		String date = request.getParameter("date");
		String sender = request.getParameter("sender");
		String accepter = request.getParameter("accepter");
		String content = request.getParameter("content");
		int ivKey = 0;
		if(ivKeyStr !=null && !ivKeyStr.equals("")){
			ivKey = Integer.parseInt(ivKeyStr);
		}
		ReviewBean reviewBean = new ReviewBean();
		reviewBean.setReFkey(ivKey);
		reviewBean.setReDate(date);
		reviewBean.setReSender(sender);
		reviewBean.setReAccepter(accepter);
		reviewBean.setReContent(content);
		
		boolean addFlag = ivCenterIFCProxy.addReView(reviewBean);
		Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
		if(addFlag){
			returnMap.put("returnCode",0);
			returnMap.put("returnMsg","发表成功");
		}else{
			returnMap.put("returnCode",1);
			returnMap.put("returnMsg","发表失败");
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

package com.util;


public class BaseAction {
	/**
	 * 
	
	private static Log logger = LogFactory.getLog(BaseAction.class);
	
	private HttpServletResponse httpServletResponse = ServletContext.getResponse();
	private HttpServletRequest httpServletRequest = ServletActionContext.getRequest();

	public void renderJSON(String json){
		httpServletResponse.setContentType("application/json ; charset=utf-8");
		Cookie cookie = new Cookie("JSESSIONID",httpServletRequest.getSession().getId());
		httpServletResponse.addCookie(cookie);
		try {
			httpServletResponse.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	};
	
	public void renderJSONWithSessionId(String json){
		httpServletResponse.setContentType("application/json ; charset=utf-8");
		try {
			httpServletResponse.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	};
	
	public void writeSessionId(){
		Cookie cookie = new Cookie("JSESSIONID",httpServletRequest.getSession().getId());
		httpServletResponse.addCookie(cookie);
	}
	

	public final int REQUEST_ERROR = 1;
	public void renderError(int errorCode){
		Result result = new Result();
		result.setSuccess(false);
		
		switch(errorCode){
		case REQUEST_ERROR :{
			result.setMsg("请求参数错误�?);
			this.renderJSON(result.toJSONString());
		}break;
		default:{
			result.setMsg("请求服务错误�?);
			this.renderJSON(result.toJSONString());
		}
		}
	}
	
	public Integer start = 0;
	public Integer limit = 12;
	
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
		this.getRecord().setStart(start);
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
		this.getRecord().setLimit(limit);
	}
	
	private Records record;
	public Records getRecord() {
		if(this.record == null)
			this.record = new Records();
		return record;
	}
	
	
	public void renderResult(boolean success,String msg){
		Result result = new Result(success,msg);
		logger.debug(result.toJSONString());
		this.renderJSON(result.toJSONString());
	}
	 */
}

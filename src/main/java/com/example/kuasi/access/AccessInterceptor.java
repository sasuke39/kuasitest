package com.example.kuasi.access;

import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Service
public class AccessInterceptor extends HandlerInterceptorAdapter {

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(handler instanceof HandlerMethod) {

			HandlerMethod hm=(HandlerMethod)handler;
			AccessLimit aclimit=hm.getMethodAnnotation(AccessLimit.class);
			//无该注解的时候，那么就不进行拦截操作
			if(aclimit==null) {
				return true;
			}
			//获取参数
			int minute=aclimit.minute();
			int maxCount=aclimit.maxCount();
			String userIpAddr = request.getRemoteAddr();
			System.out.println("***用户客户端的IP地址："+userIpAddr);
			HttpSession session = request.getSession();
			String  attribute = (String) session.getAttribute(userIpAddr);
			String[] split;
			//有记录
			if (attribute==null){
				Date date =new Date();
				session.setAttribute(userIpAddr,date+"^0");
				return true;
			}else {
				split = attribute.split("^");
				Date date= new Date();
				Date date1= new Date(Long.parseLong(split[0]));
				long l = date1.getTime() - date.getTime();
				//是否过时
				if (l >1000*60*minute){
					session.setAttribute(userIpAddr,date+"^0");
					return true;
				}
				int i = Integer.parseInt(split[1]);
				//是否超过次数
				if (i<maxCount){
					i++;
					session.setAttribute(userIpAddr,split[0]+""+i);
					return true;
				}
			}


		return false;
		}
		return super.preHandle(request, response, handler);
	}
	

}

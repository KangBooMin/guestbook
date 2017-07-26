package com.javaex.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestbookVo;

@WebServlet("/gb")
public class guestbookSeverlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");	
		String actionName = request.getParameter("a");
		
		if("add".equals(actionName)) {
			System.out.println("add");
			
			String name = request.getParameter("name");
			String pw = request.getParameter("password");
			String content = request.getParameter("content");
			
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(cal.getTime());
			
			GuestbookDao dao = new GuestbookDao();
			dao.insert(new GuestbookVo(name, pw, content, date));
			
			response.sendRedirect("/guestbook2/gb");
			
		} else if("deleteform".equals(actionName)) {
			System.out.println("deleteform");
			
			String no = request.getParameter("no");
			request.setAttribute("no", no);
			RequestDispatcher rd = request.getRequestDispatcher("deleteform.jsp");
			rd.forward(request, response);
			
		} else if("delete".equals(actionName)) {
			System.out.println("delete");
			
			String no = request.getParameter("no");
			String pw = request.getParameter("password");
			
			GuestbookDao dao = new GuestbookDao();
			dao.delete(no, pw);	
			
			response.sendRedirect("/guestbook2/gb");
		} else {
			//리스트
			GuestbookDao dao = new GuestbookDao();
			List<GuestbookVo> list = dao.getlist();
			
			request.setAttribute("list", list);
			RequestDispatcher rd = request.getRequestDispatcher("list.jsp");
			rd.forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

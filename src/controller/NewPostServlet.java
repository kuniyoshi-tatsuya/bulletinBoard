package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import beans.Post;
import beans.User;
import service.PostService;

@WebServlet(urlPatterns = { "/newpost" })
public class NewPostServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<Post> categories = new PostService().getCategory();
		request.setAttribute("categories", categories);
		request.getRequestDispatcher("/newpost.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{

		User user = (User)request.getSession().getAttribute("loginUser");
		Post post = new Post();

		post.setSubject(request.getParameter("subject"));
		post.setText(request.getParameter("text"));
		int cnt = 0;
		if(!StringUtils.isEmpty(request.getParameter("categories"))){
			request.getSession().setAttribute("categoryValue", request.getParameter("categories"));
			post.setCategory(request.getParameter("categories"));
			cnt++;
		}else{
			request.getSession().setAttribute("categoryValue", null);
			post.setCategory(request.getParameter("category"));
		}
		if(!StringUtils.isEmpty(request.getParameter("category")))cnt++;

//		post.setCategory(request.getParameter("category"));
		post.setUserId(user.getId());

		if(isValid(request, post, cnt)){
			new PostService().register(post);
			response.sendRedirect("./");
		}else response.sendRedirect("./newpost");

	}

	private boolean isValid(HttpServletRequest request, Post post, int cnt){

		List<String> messages = new ArrayList<String>();
		if(post.getSubject().length() == 0) messages.add("件名が空です");
		if(post.getSubject().length() > 50) messages.add("件名は50文字以下です");
		if(post.getText().length() == 0) messages.add("本文が空です");
		if(post.getText().length() > 1000) messages.add("本文は1000文字以下です");
		if(cnt == 0) messages.add("カテゴリーが空です");
		if(cnt == 2) messages.add("カテゴリーを選択して追加することはできません");
		if(post.getCategory().length() > 10) messages.add("カテゴリーは10文字以下です");


		if(!messages.isEmpty()){
			request.getSession().setAttribute("wrongSubject", request.getParameter("subject"));
			request.getSession().setAttribute("wrongText", request.getParameter("text"));
			request.getSession().setAttribute("wrongCategory", request.getParameter("category"));
			request.getSession().setAttribute("errorMessages", messages);
			return false;
		}else return true;
	}
}
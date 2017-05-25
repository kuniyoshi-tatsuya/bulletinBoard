package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import service.LoginService;

@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");

		User user = new LoginService().login(loginId, password);

		if(isValid(request, user)){
			request.getSession().setAttribute("loginUser", user);
			response.sendRedirect("./");
		} else response.sendRedirect("./login");

	}

	private boolean isValid(HttpServletRequest request, User user){

		List<String> messages = new ArrayList<String>();

		if(user == null){
			messages.add("ログインに失敗しました");
			request.getSession().setAttribute("errorMessages", messages);
			request.getSession().setAttribute("wrongLoginId", request.getParameter("loginId"));
			return false;
		} else return true;

//		if(user.getLoginId().isEmpty())messages.add("ログインIDが入力されていません．");
//		else if(user.getLoginId().length() < 6) messages.add("ログインIDは6文字以上です．");
//		else if(user.getLoginId().length() > 20) messages.add("ログインIDは20文字以下です．");
//
//		if(user.getPassword().isEmpty()) messages.add("パスワードが入力されていません．");
//		else if(user.getPassword().length() < 6) messages.add("パスワードは6文字以上です．");
//		else if(user.getPassword().length() > 255) messages.add("パスワードは255文字以下です．");

	}
}

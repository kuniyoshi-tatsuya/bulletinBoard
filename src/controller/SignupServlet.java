package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Branch;
import beans.Department;
import beans.User;
import service.BranchService;
import service.DepartmentService;
import service.UserService;

@WebServlet(urlPatterns = { "/signup" })
public class SignupServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<Branch> branches = new BranchService().getBranch();
		List<Department> departments = new DepartmentService().getDepartment();
		request.setAttribute("branches", branches);
		request.setAttribute("departments", departments);

		request.getRequestDispatcher("/signup.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{

		String passwordCheck = request.getParameter("passwordCheck");
		Branch selectedBranch = new BranchService().selectBranch(request.getParameter("branchName"));
		Department selectedDepartment = new DepartmentService().selectDepartment(request.getParameter("departmentName"));
		User user = new User();


		user.setLoginId(request.getParameter("loginId"));
		if(request.getParameter("password").length() == 0) user.setPassword("");
		else user.setPassword(request.getParameter("password"));
		//user.setPassword(request.getParameter("password"));
		user.setName(request.getParameter("name"));
		user.setBranchId(String.valueOf(selectedBranch.getId()));
		user.setDepartmentId(String.valueOf(selectedDepartment.getId()));

		if(isValid(request, user, passwordCheck)){
			new UserService().register(user);
			response.sendRedirect("./useradmin");
		}
		else{
			String branchValue = request.getParameter("branchName");
			String departmentValue = request.getParameter("departmentName");
			request.getSession().setAttribute("branchValue", branchValue);
			request.getSession().setAttribute("departmentValue", departmentValue);
			response.sendRedirect("./signup");
		}

	}

	private boolean isValid(HttpServletRequest request, User user, String passwordCheck){

		List<String> messages = new ArrayList<String>();

		Pattern p = Pattern.compile("^[0-9a-zA-Z]+$");
		Matcher m = p.matcher(user.getLoginId());
		if(!m.matches() && user.getLoginId().length() != 0) messages.add("ログインIDは半角英数字でのみ登録できます");

		if(user.getLoginId().length() == 0) messages.add("ログインIDが空です");
		else if(user.getLoginId().length() < 6) messages.add("ログインIDは6文字以上です");
		else if (user.getLoginId().length() > 20) messages.add("ログインIDは20文字以下です");

		if(user.getPassword().length() == 0) messages.add("パスワードが空です");
		else if(user.getPassword().length() < 6) messages.add("パスワードは6文字以上です");
		else if (user.getPassword().length() > 20) messages.add("パスワードは255文字以下です");
		if(!passwordCheck.equals(user.getPassword())) messages.add("確認用パスワードと値が一致しません");

		if(user.getName().length() == 0) messages.add("名前が空です");
		if(user.getName().length() > 10) messages.add("名前は10文字以下です");

		if(user.getBranchId().equals("0")) messages.add("支社の選択をしてください");
		if(user.getDepartmentId().equals("0")) messages.add("部署の選択をしてください");

		List<User> usersList = new UserService().getUser();
		for(int i=0; i<usersList.size(); i++){
			if(usersList.get(i).getLoginId().equals(request.getParameter("loginId"))){
				messages.add("既に使用されているログインIDです");
			}
		}

		if(!messages.isEmpty()){
			request.getSession().setAttribute("wrongLoginId", user.getLoginId());
			request.getSession().setAttribute("wrongName", user.getName());
			request.getSession().setAttribute("errorMessages", messages);
			return false;
		}else return true;
	}
}

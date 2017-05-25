package filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;

@WebFilter("/*")
public class AdminCheckFilter implements Filter{

	String url = "http://localhost:8080/BulletinBoardSystem/";

	@Override
	public void destroy(){}
	@Override
	public void init(FilterConfig paramFilterConfig) throws ServletException {}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if(!whetherAdmin(request)){
			String path = String.valueOf(((HttpServletRequest) request).getRequestURL());
			if(path.equals(url + "useradmin") || path.equals(url + "useredit") || path.equals(url + "signup")){
				List<String> errorMessage = new ArrayList<String>();
				errorMessage.add("管理者ではないためアクセスできませんでした");
				((HttpServletRequest)request).getSession().setAttribute("errorMessages", errorMessage);
				((HttpServletResponse)response).sendRedirect("./");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	public boolean whetherAdmin(ServletRequest request){

		User loginUser = (User)((HttpServletRequest)request).getSession().getAttribute("loginUser");
		if(loginUser != null){
			if(loginUser.getDepartmentId().equals("4")) return true;
			else return false;
		}
		else return false;

	}
}

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

@WebFilter("/*")
public class LoginCheckFilter implements Filter{

	String url = "http://localhost:8080/BulletinBoardSystem/";

	@Override
	public void destroy(){}
	@Override
	public void init(FilterConfig paramFilterConfig) throws ServletException {}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if(!isLogined(request)){
			String path = String.valueOf(((HttpServletRequest) request).getRequestURL());
			if(!path.equals(url + "login") && !path.equals(url + "css/style.css")){
				List<String> loginErrorMessage = new ArrayList<String>();
				loginErrorMessage.add("ログインしてください");
				((HttpServletRequest)request).getSession().setAttribute("loginErrorMessages", loginErrorMessage);
				((HttpServletResponse)response).sendRedirect("./login");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	public boolean isLogined(ServletRequest request){

		if(((HttpServletRequest)request).getSession().getAttribute("loginUser") != null){
			return true;
		}else return false;
	}
}

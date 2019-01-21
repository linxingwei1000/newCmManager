package cm.lx.filter;


import cm.lx.util.UriUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
								
		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpServletResponse httpResp = (HttpServletResponse) resp;
		HttpSession session = httpReq.getSession();

		// 静态或无需登录的URI,已登录用户
		String uri = httpReq.getRequestURI();
		if (UriUtils.ignoreStaticResource(uri) || UriUtils.ignore(uri)
				|| session.getAttribute("account") != null) {
			chain.doFilter(req, resp);
		} else {
			httpResp.sendRedirect( ((HttpServletRequest) req).getContextPath() + "/index");
        }
	}

	@Override
	public void destroy() {

	}

}

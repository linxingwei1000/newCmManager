package cm.lx.filter;


import cm.lx.bean.DepartmentAuthority;
import cm.lx.util.UriUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PermissionFilter implements Filter {
    private static final Log log = LogFactory.getLog(PermissionFilter.class);

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpResp = (HttpServletResponse) resp;
        HttpSession session = httpReq.getSession();

        String uri = httpReq.getRequestURI();
        // 静态资源或忽略权限校验URI
        if (UriUtils.ignoreStaticResource(uri) || UriUtils.ignore(uri)) {
            chain.doFilter(req, resp);
            return;
        }

        // 用户默认都有的权限
        if (UriUtils.defaultPermission(uri)) {
            chain.doFilter(req, resp);
            return;
        }

        //  权限判断
        boolean hasPermission = false;
        DepartmentAuthority departmentAuthority = (DepartmentAuthority) session.getAttribute("myDepartmentAuthority");
        if (uri.contains("user")) {
            if (departmentAuthority.getAccountAble() == 1) hasPermission = true;
        } else if (uri.contains("car")) {
            if (departmentAuthority.getCarAble() == 1) hasPermission = true;
        } else if (uri.contains("insurance")) {
            if (departmentAuthority.getInsuranceAble() == 1) hasPermission = true;
        } else if (uri.contains("mortgage")) {
            if (departmentAuthority.getMortgageAble() == 1) hasPermission = true;
        } else if (uri.contains("newCar")) {
            if (departmentAuthority.getNewCarAble() == 1) hasPermission = true;
        } else if (uri.contains("money")) {
            if (departmentAuthority.getMoneyAble() == 1) hasPermission = true;
        } else if (uri.contains("stat")) {
            if (departmentAuthority.getStatAble() == 1) hasPermission = true;
        } else if (uri.contains("dossier")) {
            if (departmentAuthority.getDossierAble() == 1) hasPermission = true;
        } else {
            log.error("== uri[" + uri + "]未定义部门权限映射关系...");
            return;
        }
        if (hasPermission) {
            chain.doFilter(req, resp);
        } else {
            String destHttp = "nopermission?permission=" + uri;
            httpResp.sendRedirect(destHttp);
            //httpResp.sendRedirect(((HttpServletRequest) req).getContextPath() + "/nopermission?permission=" + key);
        }
    }

    @Override
    public void destroy() {

    }
}

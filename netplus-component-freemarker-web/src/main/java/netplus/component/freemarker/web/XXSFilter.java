package netplus.component.freemarker.web;

import netplus.component.authbase.utils.XssHttpServletRequestWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class XXSFilter implements Filter {

    private static Log logger = LogFactory.getLog(XXSFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(httpServletRequest);
        HttpServletResponse res = (HttpServletResponse) response;
        chain.doFilter(xssRequest, res);
    }

    @Override
    public void destroy() {
    }
}

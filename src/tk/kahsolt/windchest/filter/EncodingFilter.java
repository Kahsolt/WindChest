package tk.kahsolt.windchest.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by kahsolt on 17-6-7.
 */
@WebFilter(filterName = "EncodingFilter")
public class EncodingFilter implements Filter {
    private FilterConfig filterConfig;
    private String encoding = "utf-8";

    public void doFilter(javax.servlet.ServletRequest req,
                         javax.servlet.ServletResponse resp,
                         javax.servlet.FilterChain chain)
            throws javax.servlet.ServletException, IOException {
        req.setCharacterEncoding(encoding);
        chain.doFilter(req, resp);
    }

    @Override
    public void init(javax.servlet.FilterConfig config)
            throws javax.servlet.ServletException {
        this.filterConfig = config;
        String s = this.filterConfig.getInitParameter("encoding");
        if(s != null)
            this.encoding = s;  //支持初始化时重新自定义encoding
    }

    @Override
    public void destroy() {
        filterConfig = null;
    }

}

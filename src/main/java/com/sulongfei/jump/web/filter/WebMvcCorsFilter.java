package com.sulongfei.jump.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * <p>
 * 全局跨域配置
 * 1.解决基于Spring Security的跨域问题
 * 2.其余Spring MVC跨域问题详见{@link com.sulongfei.jump.web.interceptor.UserInterceptor}
 * 3.另外的跨域问题在Nginx中配置
 * </p>
 */
@Component
public class WebMvcCorsFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("请求URI:{}, 请求方法:{}, 请求参数 :{} ", request.getRequestURI(), request.getMethod(), getAllRequestParams(request));
        crossDomain(response);
        filterChain.doFilter(request, response);
    }

    // 跨域配置
    private void crossDomain(HttpServletResponse response) {
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,PATCH,DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers,Authorization");

    }

    private String getAllRequestParams(HttpServletRequest request) {
        StringBuffer buf = new StringBuffer();
        buf.append("{ ");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            buf.append(parameterName).append("=").append(request.getParameter(parameterName));
            buf.append(" | ");
        }
        if (buf.indexOf("|") > 0) {
            buf.deleteCharAt(buf.lastIndexOf("|"));
        }
        buf.append(" }");
        return buf.toString();
    }

}
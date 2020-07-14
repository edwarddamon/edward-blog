package com.lhamster.interceptor;

import com.lhamster.domain.Audience;
import com.lhamster.domain.response.ResultCode;
import com.lhamster.exception.ResultException;
import com.lhamster.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class JwtInterceptor implements HandlerInterceptor {
    // 前台指定拦截的url地址
    private List<String> urlList = new ArrayList<>(Arrays.asList("/user"));

    @Autowired
    private Audience audience;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String servletPath = request.getServletPath();// 获取Servlet路径
        log.info(servletPath);
        // 获取请求头信息lhamster_identity_info信息
        final String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        log.info("lhamster_identity_info= " + authHeader);
        if ("front".equals(request.getHeader("type"))) { /*前台*/
            if (urlList.contains(servletPath)) { // 前台指定拦截的
                if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
                    log.info("===用户未登录，请先登录===");
                    throw new ResultException(ResultCode.USER_NOT_LOGGED_IN);
                }
                // 获取token
                final String token = authHeader.substring(9);

                if (audience == null) {
                    BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
                    audience = (Audience) factory.getBean("audience");
                }

                // 验证token是否有效--无效已做异常抛出，由全局异常处理后返回对应信息
                JwtTokenUtil.parseJWT(token, audience.getBase64Secret());

                return true;
            } else {// 前台不指定拦截的，放行
                return true;
            }
        } else if ("back".equals(request.getHeader("type"))) { /*后台*/
            if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
                log.info("===用户未登录，请先登录===");
                throw new ResultException(ResultCode.USER_NOT_LOGGED_IN);
            }
            // 获取token
            final String token = authHeader.substring(9);

            if (audience == null) {
                BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
                audience = (Audience) factory.getBean("audience");
            }

            // 验证token是否有效--无效已做异常抛出，由全局异常处理后返回对应信息
            JwtTokenUtil.parseJWT(token, audience.getBase64Secret());

            return true;
        } else {
            throw new ResultException(ResultCode.PERMISSION_ORIGINAL_ERROR);
        }
    }
}

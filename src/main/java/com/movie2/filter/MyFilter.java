package com.movie2.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//@WebFilter(filterName="testFilter1",urlPatterns= {"/*"})
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp=(HttpServletResponse)response;
        resp.setContentType("text/html;charset=UTF-8");
        resp.setHeader("Content-type", "text/html;charset=UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        resp.setHeader("Access-Control-Max-Age", "0");
        resp.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("XDomainRequestAllowed","1");
        request.setCharacterEncoding("UTF-8");
        System.out.println("afdasfdasdfasdfdas");
        chain.doFilter(request,resp);
    }
}

package org.homework;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.util.Map;

@WebFilter(value = "/time/*")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req,
                            HttpServletResponse resp,
                            FilterChain chain) throws IOException, ServletException {

        String timeZone = req.getParameter("timezone");

        if (timeZone != null){
            try{
                timeZone = req.getQueryString().split("=")[1];
                timeZone = timeZone.contains("%")
                            ? URLDecoder.decode(timeZone, StandardCharsets.UTF_8)
                            : timeZone;
                ZoneId.of(timeZone);
                chain.doFilter(req, resp);
            }catch (DateTimeException e){
                resp.setStatus(400);
                resp.setContentType("text/html; charset=utf-8");
                resp.getWriter().write("Invalid timezone");
                resp.getWriter().close();
            }
        }else {
            chain.doFilter(req, resp);
        }

    }
}

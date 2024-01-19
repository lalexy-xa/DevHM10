package org.homework;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;


@WebServlet(value = "/time/*")

public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String timeZone = req.getParameter("timezone") != null
                ? req.getQueryString().split("=")[1]
                : "UTC";

        ZoneId zoneId = ZoneId.of(timeZone);


        ZoneOffset zoneOffset = zoneId.getRules().getOffset(Instant.now());

        LocalDateTime dateTime = LocalDateTime.now(zoneOffset);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(String.format("yyyy-MM-dd HH:mm:ss '%s'", timeZone));
        String formattedDateTime = dateTime.format(formatter);
        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().write(formattedDateTime);
        resp.getWriter().close();
    }


}

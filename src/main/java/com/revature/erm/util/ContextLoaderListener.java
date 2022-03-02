package com.revature.erm.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.erm.daos.UsersDAO;
import com.revature.erm.services.TokenService;
import com.revature.erm.services.UsersService;
import com.revature.erm.servlets.AuthServlet;
import com.revature.erm.servlets.UserServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Initializing ERS web application");

        ObjectMapper mapper = new ObjectMapper();
        JwtConfig jwtConfig = new JwtConfig();
        TokenService tokenService = new TokenService(jwtConfig);

        UsersDAO userDAO = new UsersDAO();
        UsersService usersService = new UsersService(userDAO);
        UserServlet usersServlet = new UserServlet(tokenService, usersService, mapper);
        AuthServlet authServlet = new AuthServlet(tokenService, usersService, mapper);

        ServletContext context = sce.getServletContext();
        context.addServlet("UserServlet", usersServlet).addMapping("/users/*");
        context.addServlet("AuthServlet", authServlet).addMapping("/auth");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Shutting down ERS web application");
    }
}
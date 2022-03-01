package com.revature.erm.servlets;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.erm.dtos.requests.NewUserRequest;
import com.revature.erm.dtos.responses.AppUserResponse;
import com.revature.erm.dtos.responses.Principal;
import com.revature.erm.dtos.responses.ResourceCreationResponse;
//import com.revature.erm.services.TokenService;TODO
import com.revature.erm.models.Users;
import com.revature.erm.services.UsersService;
import com.revature.erm.util.exceptions.InvalidRequestException;
import com.revature.erm.util.exceptions.ResourceConflictException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

// Mapping: /users/*
public class UserServlet extends HttpServlet {

    //    private final TokenService tokenService;
    private final UsersService userService;
    private final ObjectMapper mapper;

    public UserServlet(UsersService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    //----------------------Send a doGet request(READ request)---------------------//


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] reqFrags = req.getRequestURI().split("/");
        if (reqFrags.length == 4 && reqFrags[3].equals("availability")) {
            checkAvailability(req, resp);
            return; // necessary, otherwise we end up doing more work than was requested
        }

        // TODO implement some security logic here to protect sensitive operations

        // get users (all, by id, by w/e)
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.setStatus(401);//UNAUTHORIZED(client side)
            return;
        }

        Principal requester = (Principal) session.getAttribute("authUser");

        if (!requester.getRole().equals("ADMIN")) {
            resp.setStatus(403); // FORBIDDEN(client side)
            return;
        }
        List<AppUserResponse> users = userService.getAllUsers();
        String payload = mapper.writeValueAsString(users);
        resp.setContentType("application/json");
        resp.getWriter().write(payload);
        resp.setStatus(200);//OK(success)

    }

    //----------------------Send a doPost request(CREATE request)---------------------//

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter respWriter = resp.getWriter();

        try {

            NewUserRequest newUserRequest = mapper.readValue(req.getInputStream(), NewUserRequest.class);
            Users newUser = userService.register(newUserRequest);
            resp.setStatus(201); // CREATED(success)
            resp.setContentType("application/json");
            String payload = mapper.writeValueAsString(new ResourceCreationResponse(newUser.getUser_id()));
            respWriter.write(payload);

        } catch (InvalidRequestException | DatabindException e) {
            resp.setStatus(400); // BAD REQUEST error(client side)
        } catch (ResourceConflictException e) {
            resp.setStatus(409); // CONFLICT error(client side)
        } catch (Exception e) {
            e.printStackTrace();//debugging
            resp.setStatus(500);//INTERNAL SERVER ERROR
        }

    }

    //----------------------Check availability for username and email---------------------//

    protected void checkAvailability(HttpServletRequest req, HttpServletResponse resp) {
        String usernameValue = req.getParameter("username");
        String emailValue = req.getParameter("email");
        if (usernameValue != null) {
            if (userService.isUsernameAvailable(usernameValue)) {
                resp.setStatus(204); // NO CONTENT
            } else {
                resp.setStatus(409);
            }
        } else if (emailValue != null) {
            if (userService.isEmailAvailable(emailValue)) {
                resp.setStatus(204);
            } else {
                resp.setStatus(409);
            }
        }
    }
}
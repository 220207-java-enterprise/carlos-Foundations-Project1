package com.revature.erm.servlets;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.revature.erm.dtos.requests.LoginRequest;
import com.revature.erm.dtos.requests.NewReimbursementRequest;
import com.revature.erm.dtos.requests.UpdateReimbursementRequest;
import com.revature.erm.dtos.responses.AppUserResponse;
import com.revature.erm.dtos.responses.Principal;
import com.revature.erm.dtos.responses.ResourceCreationResponse;
import com.revature.erm.models.Reimbursements;
import com.revature.erm.services.ReimbursementService;
import com.revature.erm.services.TokenService;
import com.revature.erm.services.UsersService;
import com.revature.erm.util.exceptions.AuthenticationException;
import com.revature.erm.util.exceptions.InvalidRequestException;
//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ReimbursementServlet extends HttpServlet {

    private final TokenService tokenService;
    private final ReimbursementService reimbursementService;
    private final ObjectMapper mapper;

    public ReimbursementServlet(TokenService tokenService, ReimbursementService reimbursementService, ObjectMapper mapper) {
        this.tokenService = tokenService;
        this.reimbursementService = reimbursementService;
        this.mapper = mapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Principal requesterForReimb = tokenService.extractRequesterDetails(req.getHeader("Authorization"));
//
//        if (requesterForReimb.getRole().equals("EMPLOYEE")) {
//            resp.setStatus(403);//forbidden
//            return;
//        }
//        PrintWriter writer = resp.getWriter();
//        List<Reimbursements> users = reimbursementService.getAllReimbursements();
//
//        String payload = mapper.writeValueAsString(users);
//        resp.setContentType("application/json");
//        writer.write(payload);

        // get users (all, by id, by w/e)
        Principal requester = tokenService.extractRequesterDetails(req.getHeader("Authorization"));

        if (requester == null) {
            resp.setStatus(401);
            return;
        }

        if (!requester.getRole().equals("FINANCE_MANAGER")) {
            resp.setStatus(403); // FORBIDDEN(client side)
            return;
        }

        System.out.println("test1");
        List<Reimbursements> users = reimbursementService.getAllReimbursements();
        System.out.println("test2");
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String payload = mapper.writeValueAsString(users);
        System.out.println("test3");
        resp.setContentType("application/json");
        resp.getWriter().write(payload);
        resp.setStatus(200);//OK(success)
    }


    //-----------------------------create a reimbursement(EMPLOYEE ONLY)--------------------------//
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();

        try {

            Principal principal = tokenService.extractRequesterDetails(req.getHeader("Authorization"));//authorize requester
            if (!principal.getRole().equals("EMPLOYEE")) {//if requester role not equal to EMPLOYEE
                throw new InvalidRequestException("You are not authorized as an employee. Only employees are able to submit reimbursement requests.");
            }

            NewReimbursementRequest newReimbursementRequest = mapper.readValue(req.getInputStream(), NewReimbursementRequest.class);
            newReimbursementRequest.setAuthor_id(principal.getUser_id());
            // send newReimbReq to reimbService
            ResourceCreationResponse creationResp = reimbursementService.saveNewReimbursement(newReimbursementRequest);
            // get result and write it to the response body as a json string (using the mapper)
            String payload = mapper.writeValueAsString(creationResp);
            resp.setContentType("application/json");
            writer.write(payload);

        } catch (InvalidRequestException | DatabindException e) {
            e.printStackTrace();
            resp.setStatus(400);//BAD request(client side error)
        } catch (AuthenticationException e) {
            resp.setStatus(401); // UNAUTHORIZED (no user found with provided credentials)
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
    }

    //-----------------------------update a reimbursement(MANAGER ONLY)--------------------------//
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();

        try {
            Principal principal = tokenService.extractRequesterDetails(req.getHeader("Authorization"));//authorize requester
            if (!principal.getRole().equals("FINANCE_MANAGER")) {//if requester role not equal to FINANCE_MANAGER
                throw new InvalidRequestException("You are not authorized as an manager. Only managers are able to update reimbursement requests.");
            }

            UpdateReimbursementRequest updateReimbursementRequest = mapper.readValue(req.getInputStream(), UpdateReimbursementRequest.class);
            ResourceCreationResponse updatedReimbursement = reimbursementService.updateReimbursement(updateReimbursementRequest);

            String payload = mapper.writeValueAsString(updatedReimbursement);
            resp.setContentType("application/JSON");
            writer.write(payload);

        } catch (InvalidRequestException | DatabindException e) {
            e.printStackTrace();
            resp.setStatus(400);//BAD request(client side error)
        } catch (AuthenticationException e) {
            resp.setStatus(401); // UNAUTHORIZED (no user found with provided credentials)
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
    }
}
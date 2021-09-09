package test.web;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class LogInServlet extends HttpServlet {

    UserRepository UserCheck = new UserRepository("","","");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");

        Context context = new Context();

        templateEngine.process("logIn",context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (userRegistrationCheck(email,password)){
            resp.sendRedirect("/home");
            transitionHome(req,resp);
        }else {
            transitionLogIn(req,resp);
        }

    }

    private boolean userRegistrationCheck(String email, String password){
        boolean check = false;
        ArrayList<UserRepository> UserRepositoryList = (ArrayList<UserRepository>) getServletContext().getAttribute("UserRepositoryList");
        for(int i = 0;i<UserRepositoryList.size();i++){
            UserCheck = UserRepositoryList.get(i);
            if(UserCheck.getEmail().equals(email)){
                if(UserCheck.getPassword().equals(password)){
                    UserRepository LogInUser = (UserRepository)getServletContext().getAttribute("LogInUser");
                    LogInUser.setName(UserCheck.getName()); LogInUser.setEmail(UserCheck.getEmail()); LogInUser.setPassword(UserCheck.getPassword());
                    System.out.println("good");
                    check = true;
                }
            }
        }
        return check;
    }

    private void transitionLogIn (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");

        Context context = new Context();

        templateEngine.process("logIn",context, resp.getWriter());
    }

    private void transitionHome (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");

        Context context = new Context();

        templateEngine.process("home",context, resp.getWriter());
    }
}

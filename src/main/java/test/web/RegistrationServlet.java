package test.web;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;

public class RegistrationServlet extends HttpServlet {

    UserRepository user = new UserRepository("","","");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");

        Context context = new Context();

        templateEngine.process("registration",context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String twoPassword = req.getParameter("twoPassword");

        if(registrationUser(email,name,password,twoPassword)){
            UserRepository LogInUser = (UserRepository)getServletContext().getAttribute("LogInUser");
            LogInUser.setName(name); LogInUser.setEmail(email); LogInUser.setPassword(password);
            resp.sendRedirect("/home");
            transitionHome(resp);
        }else {
            transitionRegistration(req,resp);
        }

    }

    private boolean checkPassword (String password,String twoPassword){
        if(password.equals(twoPassword)){
            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkEmail (String email){
        boolean check = true;
        ArrayList<UserRepository> UserRepositoryList = (ArrayList<UserRepository>) getServletContext().getAttribute("UserRepositoryList");
        for(int i =0;i<UserRepositoryList.size();i++){
            user = UserRepositoryList.get(i);
            if(user.getEmail().equals(email)){
                check = false;
            }
        }
        return check;
    }

    private boolean registrationUser (String email,String name, String password,String twoPassword){
        if (checkPassword(password,twoPassword) && checkEmail(email)){
            UserRepository NewUser = new UserRepository("","","");
            ArrayList<UserRepository> UserRepositoryList = (ArrayList<UserRepository>) getServletContext().getAttribute("UserRepositoryList");
            NewUser.setEmail(email);NewUser.setName(name);NewUser.setPassword(password);
            UserRepositoryList.add(NewUser);
            System.out.println(UserRepositoryList.size());
            return true;
        }
        else {
            return false;
        }
    }

    private void transitionHome (HttpServletResponse resp) throws IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");

        Context context = new Context();

        templateEngine.process("home",context, resp.getWriter());
    }

    private void transitionRegistration (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");

        Context context = new Context();

        templateEngine.process("registration",context, resp.getWriter());
    }
}

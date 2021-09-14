package test.web;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import repository.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class RegistrationServlet extends HttpServlet {

    private ArrayList<User> userRepository;
    private TemplateEngine templateEngine;
    private String exception;

    @Override
    public void init() throws ServletException {
        userRepository = (ArrayList<User>) getServletContext().getAttribute("userRepository");
        templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Context context = new Context();

        templateEngine.process("registration",context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String twoPassword = req.getParameter("twoPassword");

        User user = registerUser(email,name,password,twoPassword);

        if(user != null){
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("/home");
        }else {
            Context context = new Context();
            context.setVariable("exception",exception);
            templateEngine.process("registration",context, resp.getWriter());
        }

    }

    private boolean checkPassword (String password,String twoPassword){
        if(password.equals(twoPassword)){
            return true;
        }
        else {
            exception ="Пароли не совпадают";
            return false;
        }
    }

    private boolean checkEmail (String email){
        for (User user : userRepository) {
            if (user.getEmail().equals(email)) {
                exception ="email занят";
                return false;
            }
        }
        return true;
    }

    private User registerUser (String email,String name, String password,String twoPassword){
        if (checkPassword(password,twoPassword) && checkEmail(email)){
            User newUser = new User(name,email,password);

            userRepository.add(newUser);
            System.out.println(userRepository.size());
            return newUser;
        }
        else {
            return null;
        }
    }
}

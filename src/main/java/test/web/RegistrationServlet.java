package test.web;

import exception.PasswordMismatchException;
import exception.WrongEmailException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import repository.CollectionUserRepository;
import repository.User;
import repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class RegistrationServlet extends HttpServlet {

    private UserRepository userRepository;
    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        userRepository = (CollectionUserRepository) getServletContext().getAttribute("collectionUserRepository");
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
        User newUser = new User(name,email,password);


        //User newUser2 = new User("111","111","111");
        //newUser.addChat(newUser2,"123");
        //newUser.addChat(newUser2,"124");

        try {
            //userRepository.addUser(newUser2,"111");
            userRepository.addUser(newUser,twoPassword);
            req.getSession().setAttribute("user", newUser);
            resp.sendRedirect("/home");
        }catch (WrongEmailException | PasswordMismatchException e){
            Context context = new Context();
            context.setVariable("exception",e.getMessage());
            templateEngine.process("registration",context, resp.getWriter());
        }
    }
}

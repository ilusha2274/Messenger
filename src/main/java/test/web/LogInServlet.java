package test.web;

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

public class LogInServlet extends HttpServlet {

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

        templateEngine.process("login",context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = userRepository.logInUser(email,password);

        if (user != null){
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("/home");
        }else {
            Context context = new Context();
            context.setVariable("exception",userRepository.getStatus());
            templateEngine.process("login",context, resp.getWriter());
        }
    }
}

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

public class LogInServlet extends HttpServlet {

    private ArrayList<User> userRepository;
    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        userRepository = (ArrayList<User>) getServletContext().getAttribute("userRepository");
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

        User user = userRegistrationCheck(email,password);
        if (user != null){
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("/home");
        }else {
            Context context = new Context();
            context.setVariable("exception","Неверное имя пользователя или пароль");
            templateEngine.process("login",context, resp.getWriter());
        }

    }

    private User userRegistrationCheck(String email, String password){
        for (User checkedUser : userRepository) {
            if (checkedUser.getEmail().equals(email)) {
                if (checkedUser.getPassword().equals(password)) {
                    System.out.println("good");
                    return checkedUser;
                }
            }
        }
        return null;
    }
}

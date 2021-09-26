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

public class NewMessageServlet extends HttpServlet {

    private UserRepository userRepository;
    private TemplateEngine templateEngine;


    @Override
    public void init() throws ServletException {
        userRepository = (CollectionUserRepository) getServletContext().getAttribute("collectionUserRepository");
        templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Context context = new Context();
        context.setVariable("newmessage",true);
        context.setVariable("title",user.getName());
        templateEngine.process("newmessage",context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String nameChat = req.getParameter("nameChat");

        User user = (User) req.getSession().getAttribute("user");

        if (userRepository.findUserByEmail(email)!= null){
            user.addChat(userRepository.findUserByEmail(email),nameChat);
            resp.sendRedirect("/posts");
        }else {
            Context context = new Context();
            context.setVariable("exception","Пользователь не найден");
            templateEngine.process("newmessage",context, resp.getWriter());

        }
    }
}

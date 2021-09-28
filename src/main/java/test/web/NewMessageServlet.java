package test.web;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import repository.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewMessageServlet extends HttpServlet {

    private UserRepository userRepository;
    private ChatRepository chatRepository;
    private TemplateEngine templateEngine;


    @Override
    public void init() throws ServletException {
        userRepository = (CollectionUserRepository) getServletContext().getAttribute("collectionUserRepository");
        templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
        chatRepository = (CollectionChatRepository)getServletContext().getAttribute("collectionChatRepository");

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

        User user = (User) req.getSession().getAttribute("user");

        if (userRepository.findUserByEmail(email)!= null){
            chatRepository.addChat(user,userRepository.findUserByEmail(email));
            resp.sendRedirect("/posts");
        }else {
            Context context = new Context();
            context.setVariable("exception","Пользователь не найден");
            templateEngine.process("newmessage",context, resp.getWriter());

        }
    }
}

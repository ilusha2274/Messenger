package test.web;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import repository.Chat;
import repository.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlockMenuServlet extends HttpServlet{

    private TemplateEngine templateEngine;
    public static final String HOME = "/home";
    public static final String POSTS = "/posts";
    public static final String PROFILE = "/profile";
    public static final String SETTINGS = "/settings";
    public static final String EXIT = "/exit";
    public static final String NEWMESSAGE = "/newmessage";


    @Override
    public void init() throws ServletException {
        templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String contextPath = req.getRequestURI();
        User user = (User) req.getSession().getAttribute("user");

        if (HOME.equals(contextPath)){
            Context context = new Context();

            context.setVariable("title",user.getName());

            templateEngine.process("home",context, resp.getWriter());
        }

        if (POSTS.equals(contextPath)){
            Context context = new Context();
            context.setVariable("posts",true);
            ArrayList<String> chats = new ArrayList<>();
            for(int i =0;i<user.getChats().size();i++){
                chats.add(user.getByNumberChat(i).getName());
            }
            context.setVariable("nameChat",chats);

            context.setVariable("title",user.getName());

            templateEngine.process("posts",context, resp.getWriter());
        }

        if (PROFILE.equals(contextPath)){
            Context context = new Context();
            context.setVariable("profile",true);

            context.setVariable("title",user.getName());

            templateEngine.process("profile",context, resp.getWriter());
        }

        if (SETTINGS.equals(contextPath)){
            Context context = new Context();
            context.setVariable("settings",true);

            context.setVariable("title",user.getName());

            templateEngine.process("settings",context, resp.getWriter());
        }

        if (EXIT.equals(contextPath)){
            req.getSession().invalidate();
            resp.sendRedirect("/login");
        }


    }
}

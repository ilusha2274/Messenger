package test.web;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.WebContext;
import repository.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlockMenuServlet extends HttpServlet{

    private TemplateEngine templateEngine;
    private ChatRepository chatRepository;
    public static final String HOME = "/home";
    public static final String POSTS = "/posts";
    public static final String PROFILE = "/profile";
    public static final String SETTINGS = "/settings";
    public static final String EXIT = "/exit";


    @Override
    public void init() throws ServletException {
        templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
        chatRepository = (CollectionChatRepository)getServletContext().getAttribute("collectionChatRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String contextPath = req.getRequestURI();
        User user = (User) req.getSession().getAttribute("user");
        Context context = new Context();
        WebContext webContext = new WebContext(req,resp,getServletContext());

        switch (contextPath){
            case ("/home"):
                context.setVariable("title",user.getName());

                templateEngine.process("home",context, resp.getWriter());
                break;
            case ("/posts"):
                webContext.setVariable("posts",true);
                ArrayList<PrintPost> printPosts = printChats(user);

                webContext.setVariable("printPosts",printPosts);

                webContext.setVariable("title",user.getName());

                templateEngine.process("posts",webContext, resp.getWriter());
                break;
            case ("/profile"):
                context.setVariable("profile",true);

                context.setVariable("title",user.getName());

                templateEngine.process("profile",context, resp.getWriter());
                break;
            case ("/settings"):
                context.setVariable("settings",true);

                context.setVariable("title",user.getName());

                templateEngine.process("settings",context, resp.getWriter());
                break;
            case ("/exit"):
                req.getSession().invalidate();
                resp.sendRedirect("/login");
                break;
            default:
                break;

        }
    }

    private ArrayList<PrintPost> printChats (User user){
        ArrayList<PrintPost> printPosts = new ArrayList<>();
        List<Chat> chat = chatRepository.findListChatByUser(user);
        for(int i =0;i<chat.size();i++){
            PrintPost printPost = new PrintPost();
            if (user == chat.get(i).getUser1()){
                printPost.setNameChat(chat.get(i).getUser2().getName());
                printPost.setIdChat(chat.get(i).getChatId());
            }else {
                printPost.setNameChat(chat.get(i).getUser1().getName());
                printPost.setIdChat(chat.get(i).getChatId());
            }
            printPosts.add(printPost);
        }
        return printPosts;
    }
}

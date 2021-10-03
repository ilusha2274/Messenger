package test.web;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import repository.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatServlet extends HttpServlet {

    private TemplateEngine templateEngine;
    private ChatRepository chatRepository;

    @Override
    public void init() throws ServletException {
        templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
        chatRepository = (CollectionChatRepository)getServletContext().getAttribute("collectionChatRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contextPath = req.getRequestURI();
        int id = findIdChat(contextPath,resp);
        WebContext webContext = new WebContext(req,resp,getServletContext());
        User user = (User) req.getSession().getAttribute("user");
        webContext.setVariable("posts",true);
        ArrayList<PrintPost> printPosts = printChats(user);
        ArrayList<PrintMessage> printMessages = printMessages(chatRepository.getByNumberChat(id),req);

        webContext.setVariable("printMessages",printMessages);

        webContext.setVariable("printPosts",printPosts);

        webContext.setVariable("title",user.getName());

        templateEngine.process("chat",webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = req.getParameter("message");
        User user = (User) req.getSession().getAttribute("user");
        String contextPath = req.getRequestURI();
        int id = findIdChat(contextPath,resp);
        chatRepository.getByNumberChat(id).addMessage(user,message);
        resp.sendRedirect("/chat/" + id);
    }

    private ArrayList<PrintPost> printChats (User user){
        ArrayList<PrintPost> printPosts = new ArrayList<>();
        List<Chat> chat = chatRepository.findListChatByUser(user);
        for(int i =0;i<chat.size();i++){
            PrintPost printPost = new PrintPost();
            if (user == chat.get(i).getUser1()){
                printPost.setNameChat(chat.get(i).getUser2().getName());
            }else {
                printPost.setNameChat(chat.get(i).getUser1().getName());
            }
            printPost.setIdChat(i);
            printPosts.add(printPost);
        }
        return printPosts;
    }

    private int findIdChat (String contextPath, HttpServletResponse resp) throws IOException {
        char[] contextPathChar = contextPath.toCharArray();
        int check = 0;
        StringBuilder chatId = new StringBuilder();
        for (char c : contextPathChar) {
            if (check < 2) {
                if (c == '/') {
                    check++;
                }
            } else {
                chatId.append(c);
            }
        }
        try {
            check = Integer.parseInt(chatId.toString());
        }catch (NumberFormatException e){
            resp.sendRedirect("/posts");
        }
        return check;
    }

    private ArrayList<PrintMessage> printMessages (Chat chat,HttpServletRequest req){
        ArrayList<PrintMessage> printMessages = new ArrayList<>();
        User user = (User) req.getSession().getAttribute("user");
        for (int i =0;i<chat.getMessages().size();i++){
            if (chat.getMessageByNumber(i).getAuthor() == user){
                PrintMessage printMessage = new PrintMessage(true,chat.getMessageByNumber(i).getText());
                printMessages.add(printMessage);
            }else {
                PrintMessage printMessage = new PrintMessage(false,chat.getMessageByNumber(i).getText());
                printMessages.add(printMessage);
            }
        }
        return printMessages;
    }
}

package test.web;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BlockMenuServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");

        String contextPath = req.getRequestURI();
        String home = "/home"; String posts = "/posts"; String profile = "/profile"; String settings = "/settings";

        if (contextPath.equals(home)){
            Context context = new Context();

            UserRepository LogInUser = (UserRepository)getServletContext().getAttribute("LogInUser");
            context.setVariable("title",LogInUser.getName());

            templateEngine.process("home",context, resp.getWriter());
        }

        if (contextPath.equals(posts)){
            Context context = new Context();
            context.setVariable("posts",true);

            UserRepository LogInUser = (UserRepository)getServletContext().getAttribute("LogInUser");
            context.setVariable("title",LogInUser.getName());

            templateEngine.process("posts",context, resp.getWriter());
        }

        if (contextPath.equals(profile)){
            Context context = new Context();
            context.setVariable("profile",true);

            UserRepository LogInUser = (UserRepository)getServletContext().getAttribute("LogInUser");
            context.setVariable("title",LogInUser.getName());;

            templateEngine.process("profile",context, resp.getWriter());
        }

        if (contextPath.equals(settings)){
            Context context = new Context();
            context.setVariable("settings",true);

            UserRepository LogInUser = (UserRepository)getServletContext().getAttribute("LogInUser");
            context.setVariable("title",LogInUser.getName());

            templateEngine.process("settings",context, resp.getWriter());
        }
    }
}

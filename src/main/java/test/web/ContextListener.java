package test.web;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import repository.CollectionChatRepository;
import repository.CollectionUserRepository;
import repository.User;
import repository.UserRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.util.ArrayList;

public class ContextListener implements javax.servlet.ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        TemplateEngine templateEngine = createTemplateEngine(servletContext);
        servletContext.setAttribute("templateEngine", templateEngine);

        CollectionUserRepository collectionUserRepository = new CollectionUserRepository();
        servletContext.setAttribute("collectionUserRepository", collectionUserRepository);

        CollectionChatRepository collectionChatRepository = new CollectionChatRepository();
        servletContext.setAttribute("collectionChatRepository", collectionChatRepository);
    }

    private TemplateEngine createTemplateEngine(ServletContext servletContext){
        TemplateEngine templateEngine = new TemplateEngine();

        templateEngine.setTemplateResolver(createTemplateResolver(servletContext));

        return templateEngine;
    }

    private ServletContextTemplateResolver createTemplateResolver (ServletContext servletContext){
        ServletContextTemplateResolver templateResolver =
                new ServletContextTemplateResolver(servletContext);

        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }
}

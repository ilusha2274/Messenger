package test.web;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements javax.servlet.ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        TemplateEngine templateEngine = createTemplateEngine(servletContext);

        servletContext.setAttribute("templateEngine", templateEngine);
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

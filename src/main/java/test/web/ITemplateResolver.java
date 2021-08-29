package test.web;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.templateresolver.TemplateResolution;

import java.util.Map;

public interface ITemplateResolver {

    public TemplateResolution resolveTemplate(
            final IEngineConfiguration configuration,
            final String ownerTemplate, final String template,
            final Map<String, Object> templateResolutionAttributes);
}

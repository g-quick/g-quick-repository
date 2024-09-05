package top.guyimaple.quick.framework;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import top.guyimaple.quick.common.QuickService;
import top.guyimaple.quick.common.entry.Project;
import top.guyimaple.quick.common.entry.ServiceParameterDefinition;
import top.guyimaple.quick.common.enums.ProjectType;
import top.guyimaple.quick.common.enums.QuickServiceType;
import top.guyimaple.quick.common.executor.template.TemplateExecutor;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author guyi
 * @date 2024/9/4
 */
public class DefaultFrameworkQuickService implements QuickService {

    private static final String PACKAGE_KEY = "package";

    @Override
    public List<ServiceParameterDefinition> parameters() {
        return List.of(
                new ServiceParameterDefinition(PACKAGE_KEY, "包名", null)
        );
    }

    @Override
    public QuickServiceType type() {
        return QuickServiceType.FRAMEWORK;
    }

    @Override
    @SneakyThrows
    public void main(Project project, Map<String, String> parameters) {
        String javaPath = "src/main/java/" + parameters.get(PACKAGE_KEY).replaceAll("\\.", "/");
        project.getWriter().createDirectory(ProjectType.SERVICE, javaPath);

        TemplateExecutor executor = new TemplateExecutor(project.getContext());
        Map<String, Object> params = Collections.singletonMap("parameters", parameters);

        String content = IOUtils.toString(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("templates/services/pom.vm")), StandardCharsets.UTF_8);
        content = executor.execute(content, params);
        project.getWriter().write(ProjectType.SERVICE, "pom.xml", content.getBytes(StandardCharsets.UTF_8));

        content = IOUtils.toString(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("templates/services/Application.vm")), StandardCharsets.UTF_8);
        content = executor.execute(content, params);
        project.getWriter().write(ProjectType.SERVICE, javaPath + "/Application.java", content.getBytes(StandardCharsets.UTF_8));
    }

}

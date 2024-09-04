package top.guyimaple.quick.framework;

import lombok.SneakyThrows;
import top.guyimaple.quick.common.QuickService;
import top.guyimaple.quick.common.entry.Project;
import top.guyimaple.quick.common.entry.ServiceParameterDefinition;
import top.guyimaple.quick.common.enums.QuickServiceType;

import java.util.List;
import java.util.Map;

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
        ServiceWriter writer = new ServiceWriter(project);
        writer.write(parameters.get(PACKAGE_KEY));
    }

}

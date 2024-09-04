package top.guyimaple.quick.framework;

import org.apache.commons.io.IOUtils;
import top.guyimaple.quick.common.entry.Project;
import top.guyimaple.quick.common.enums.ProjectType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author guyi
 * @date 2024/9/4
 */
public class ServiceWriter {

    private final Project project;
    public ServiceWriter(Project project) {
        this.project = project;
    }

    public void write(String packageName) throws IOException {
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("service.jar");
        ZipReader reader = new ZipReader(input);
        String path = packageName.replaceAll("\\.", "/");
        reader.read(
                entry -> !entry.isDirectory() && !entry.getName().startsWith("__MACOSX"),
                (entry, bytes) -> {
                    String name = entry.getName();
                    name = name.replace("top/guyimaple/quick/components/service", path);
                    String directory = name.substring(0, name.lastIndexOf("/"));
                    project.getWriter().createDirectory(ProjectType.SERVICE, "src/main/java/" + directory);
                    String content = new String(bytes).replaceAll("top.guyimaple.quick.components.service", packageName);
                    project.getWriter().write(ProjectType.SERVICE, "src/main/java/" + name, content.getBytes(StandardCharsets.UTF_8));
                }
        );

    }

}

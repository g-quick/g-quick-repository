package top.guyimaple.quick.framework;

import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author guyi
 * @date 2024/9/4
 */
public class ZipReader {

    private final ZipInputStream input;
    public ZipReader(InputStream in) throws IOException {
        input = new ZipInputStream(in);
    }

    public void read(Function<ZipEntry, Boolean> filter, BiConsumer<ZipEntry, byte[]> consumer) throws IOException {
        ZipEntry entry;
        while ((entry = input.getNextEntry()) != null) {
            if (filter.apply(entry)) {
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                int n;
                byte[] bytes = new byte[1024];
                while ((n = input.read(bytes)) != -1) {
                    output.write(bytes, 0, n);
                }
                consumer.accept(entry, output.toByteArray());
                output.close();
            }
            input.closeEntry();
        }
        input.close();
    }

}

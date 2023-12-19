package ee.balder.filters.util;

import lombok.experimental.UtilityClass;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@UtilityClass
public class FixtureUtils {

    public static String fixture(String filename) {
        try {
            return IOUtils.toString(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(filename)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

}

package pos.java.bora_comer.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;

public class IntegrationTestUtil {

    public static String fromJsonPath(final String jsonPath) {
        try (InputStream is = IntegrationTestUtil.class.getResourceAsStream(jsonPath)) {
            if (is == null) {
                throw new IllegalArgumentException("Arquivo não encontrado: " + jsonPath);
            }
            return IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo: " + jsonPath, e);
        }
    }
}

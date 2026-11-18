package br.univ.pharmasys.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private static final Properties properties = new Properties();

    static {
        carregarConfiguracoes();
    }

    private static void carregarConfiguracoes() {
        // 1. Tenta carregar de arquivo externo config.properties na raiz do projeto
        File arquivoExterno = new File("config.properties");
        if (arquivoExterno.exists()) {
            try (FileInputStream fis = new FileInputStream(arquivoExterno)) {
                properties.load(fis);
                return;
            } catch (Exception e) {
                System.err.println("Aviso: Falha ao carregar config.properties externo: " + e.getMessage());
            }
        }

        // 2. Tenta carregar do classpath
        try (InputStream is = ConfigLoader.class.getResourceAsStream("/config.properties")) {
            if (is != null) {
                properties.load(is);
            }
        } catch (Exception e) {
            System.err.println("Aviso: Falha ao carregar config.properties do classpath: " + e.getMessage());
        }
    }

    public static String get(String chave, String valorPadrao) {
        // 1. Prioriza propriedades carregadas do arquivo
        String valor = properties.getProperty(chave);
        if (valor != null && !valor.trim().isEmpty()) {
            return valor.trim();
        }

        // 2. Verifica variáveis de ambiente (convertendo '.' para '_')
        String envKey = chave.replace(".", "_").toUpperCase();
        String envVal = System.getenv(envKey);
        if (envVal != null && !envVal.trim().isEmpty()) {
            return envVal.trim();
        }

        // 3. Verifica System properties
        String sysVal = System.getProperty(chave);
        if (sysVal != null && !sysVal.trim().isEmpty()) {
            return sysVal.trim();
        }

        return valorPadrao;
    }
}

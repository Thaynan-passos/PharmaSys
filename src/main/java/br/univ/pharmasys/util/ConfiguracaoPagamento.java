package br.univ.pharmasys.util;
import com.mercadopago.MercadoPagoConfig;

public class ConfiguracaoPagamento {

    public static void inicializar() {
        String accessToken = ConfigLoader.get("mercadopago.access_token", "");
        if (!accessToken.isEmpty()) {
            MercadoPagoConfig.setAccessToken(accessToken);
        }
    }
}

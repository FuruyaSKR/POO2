package SitsFrameworkController.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Classe utilitária para requisições HTTP simplificadas.
 * Responsável por realizar chamadas GET para URLs informadas.
 */
public class ApiClient {

    /** Cliente HTTP compartilhado para requisições. */
    private static final HttpClient client = HttpClient.newHttpClient();

    /**
     * Realiza uma requisição HTTP GET para a URL especificada e retorna o corpo da
     * resposta como String.
     *
     * @param url URL de destino da requisição GET.
     * @return Corpo da resposta da requisição.
     * @throws IOException          Se houver erro de comunicação durante a
     *                              requisição.
     * @throws InterruptedException Se a operação for interrompida.
     */
    public static String get(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}

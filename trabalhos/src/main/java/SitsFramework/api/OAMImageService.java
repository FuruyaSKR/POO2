package SitsFramework.api;

import SitsFramework.config.Config;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OAMImageService {

    public static JsonArray buscarImagens() throws IOException, InterruptedException {

        String url = String.format(Locale.US,
                "https://api.openaerialmap.org/meta?bbox=%f,%f,%f,%f",
                Config.LONG_MIN, Config.LAT_MIN, Config.LONG_MAX, Config.LAT_MAX);

        System.out.println("URL consultada: " + url); // <- Adicione isso
        String response = ApiClient.get(url);

        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
        if (!json.get("results").isJsonArray()) {
            System.out.println("Erro retornado pela API: " + json.get("results").getAsString());
            return new JsonArray();
        }
        return json.getAsJsonArray("results");
    }

    public static List<OAMImageMeta> extrairMetadados(JsonArray imagens) {
        List<OAMImageMeta> lista = new ArrayList<>();
        for (JsonElement e : imagens) {
            JsonObject obj = e.getAsJsonObject();

            // Tente pegar o link HTTP/S do campo uuid
            String downloadUrl = obj.has("uuid") ? obj.get("uuid").getAsString() : null;
            if (downloadUrl == null || !downloadUrl.startsWith("http"))
                continue;

            String title = obj.has("title") ? obj.get("title").getAsString() : "img";
            String date = obj.has("acquisition_start") ? obj.get("acquisition_start").getAsString() : "";

            long fileSize = obj.has("file_size") ? obj.get("file_size").getAsLong() : 0L;
            lista.add(new OAMImageMeta(downloadUrl, title, date, fileSize));
        }
        return lista;
    }

    public static class OAMImageMeta {
        public String url;
        public String title;
        public String date;
        public long fileSize;

        public OAMImageMeta(String url, String title, String date, long fileSize) {
            this.url = url;
            this.title = title;
            this.date = date;
            this.fileSize = fileSize;
        }
    }

}

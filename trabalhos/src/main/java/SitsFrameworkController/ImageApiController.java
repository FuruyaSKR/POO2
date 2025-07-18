package SitsFrameworkController;

import SitsFrameworkController.config.Config;
import SitsFrameworkController.model.ImageTiffMeta;
import SitsFrameworkController.model.TipoProcessamento;
import SitsFrameworkController.api.ApiClient;
import SitsFrameworkController.util.ImageProcessingUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class ImageApiController {
    private Config config;

    public ImageApiController(Config config) {
        this.config = config;
    }

    public void imageDownloader(String fileUrl, String destino) throws Exception {
        try (InputStream in = new URL(fileUrl).openStream();
                FileOutputStream out = new FileOutputStream(destino)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    public List<ImageTiffMeta> buscarImagens(int quantidade) throws IOException, InterruptedException {
        String url = String.format(Locale.US,
                "https://api.openaerialmap.org/meta?bbox=%f,%f,%f,%f",
                config.LONG_MIN, config.LAT_MIN, config.LONG_MAX, config.LAT_MAX);

        System.out.println("URL consultada: " + url);
        String response = ApiClient.get(url);

        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
        JsonArray resultados = json.has("results") && json.get("results").isJsonArray()
                ? json.getAsJsonArray("results")
                : new JsonArray();

        List<ImageTiffMeta> lista = new ArrayList<>();
        int count = 0;
        for (JsonElement e : resultados) {
            if (quantidade > 0 && count >= quantidade)
                break;
            JsonObject obj = e.getAsJsonObject();
            String downloadUrl = obj.has("uuid") ? obj.get("uuid").getAsString() : null;
            if (downloadUrl == null || !downloadUrl.startsWith("http"))
                continue;
            String title = obj.has("title") ? obj.get("title").getAsString() : "img";
            String date = obj.has("acquisition_start") ? obj.get("acquisition_start").getAsString() : "";
            long fileSize = obj.has("file_size") ? obj.get("file_size").getAsLong() : 0L;
            lista.add(new ImageTiffMeta(downloadUrl, title, date, fileSize));
            count++;
        }
        return lista;
    }

    public void baixarImagens(List<ImageTiffMeta> lista, int quantidade) {
        new File(config.OUTPUT_DIR).mkdirs();
        int count = 0;
        for (ImageTiffMeta img : lista) {
            if (quantidade > 0 && count >= quantidade)
                break;
            String nomeArquivo = String.format("%s/%s_%s.tif",
                    config.OUTPUT_DIR,
                    img.title.replaceAll("[^a-zA-Z0-9]", "_"),
                    img.date);
            System.out.println("Baixando: " + img.url + " -> " + nomeArquivo);
            try {
                imageDownloader(img.url, nomeArquivo);
                System.out.println("Imagem salva com sucesso: " + nomeArquivo);
            } catch (Exception e) {
                System.out.println("Falha ao baixar imagem: " + img.url);
            }
            count++;
        }
        System.out.println("Download de imagens concluído.");
    }

    public void processarImagens(TipoProcessamento tipo, int quantidade) {
        java.io.File pasta = new java.io.File(config.OUTPUT_DIR);
        java.io.File[] arquivos = pasta.listFiles((dir, name) -> name.toLowerCase().endsWith(".tif"));
        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhuma imagem para processar.");
            return;
        }

        int count = 0;
        for (java.io.File file : arquivos) {
            if (quantidade > 0 && count >= quantidade)
                break;
            try {
                BufferedImage img = javax.imageio.ImageIO.read(file);
                BufferedImage result = null;
                String sufixo = "";

                switch (tipo) {
                    case CINZA:
                        result = ImageProcessingUtils.toGray(img);
                        sufixo = "_cinza";
                        break;
                    case BINARIZACAO:
                        result = ImageProcessingUtils.binarize(img, 128);
                        sufixo = "_binarizado";
                        break;
                    case BORDA:
                        result = ImageProcessingUtils.edgeDetect(img);
                        sufixo = "_borda";
                        break;
                    case NEGATIVO:
                        result = ImageProcessingUtils.negativo(img);
                        sufixo = "_negativo";
                        break;
                    case CONTRASTE:
                        result = ImageProcessingUtils.contraste(img);
                        sufixo = "_contraste";
                        break;
                }

                String nomeSaida = file.getAbsolutePath().replace(".tif", sufixo + ".tif");
                javax.imageio.ImageIO.write(result, "tif", new java.io.File(nomeSaida));
                System.out.println("Processado: " + nomeSaida);

            } catch (Exception e) {
                System.out.println("Falha ao processar imagem: " + file.getName());
                e.printStackTrace();
            }
            count++;
        }
        System.out.println("Processamento de imagens concluído.");
    }

    public Config getConfig() {
        return this.config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

}
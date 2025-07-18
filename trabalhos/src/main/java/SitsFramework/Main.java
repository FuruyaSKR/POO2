package SitsFramework;

// import SitsFramework.api.OAMImageService;
// import SitsFramework.config.Config;
// import SitsFramework.util.ImageDownloader;
// import com.google.gson.JsonArray;
// import java.util.List;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

public class Main {
    public static void limparPasta(String pasta) {
        File dir = new File(pasta);
        if (dir.exists() && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // new File(Config.OUTPUT_DIR).mkdirs();
        // limparPasta(Config.OUTPUT_DIR);

        // JsonArray imagens = OAMImageService.buscarImagens();
        // List<OAMImageService.OAMImageMeta> lista =
        // OAMImageService.extrairMetadados(imagens);

        // if (lista.isEmpty()) {
        // System.out.println("Nenhuma imagem encontrada para a região.");
        // } else {
        // for (OAMImageService.OAMImageMeta img : lista) {
        // // Crie um nome de arquivo único
        // String nomeArquivo = Config.OUTPUT_DIR + "/" +
        // img.title.replaceAll("[^a-zA-Z0-9]", "_") + "_"
        // + img.date + ".tif";
        // System.out.println("Baixando: " + img.url + " -> " + nomeArquivo);
        // try {
        // ImageDownloader.download(img.url, nomeArquivo);
        // System.out.println("Imagem salva com sucesso: " + nomeArquivo);
        // } catch (Exception e) {
        // System.out.println("Falha ao baixar imagem: " + img.url);
        // }
        // }
        // System.out.println("Download de todas as imagens concluído.");
        // }
        //
        File pasta = new File("imagens");
        File[] arquivos = pasta
                .listFiles((dir, name) -> name.toLowerCase().endsWith(".tif") || name.toLowerCase().endsWith(".tiff"));

        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhuma imagem TIFF encontrada na pasta 'imagens'.");
            return;
        }

        File primeiraImagem = arquivos[0];
        System.out.println("Processando imagem: " + primeiraImagem.getName());

        try {
            // Abrindo a imagem TIFF
            BufferedImage img = ImageIO.read(primeiraImagem);
            if (img == null) {
                System.out.println("Erro ao abrir a imagem TIFF com ImageIO.");
                return;
            }

            // Converter para escala de cinza
            BufferedImage gray = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            ColorConvertOp op = new ColorConvertOp(img.getColorModel().getColorSpace(),
                    gray.getColorModel().getColorSpace(), null);
            op.filter(img, gray);

            // Salvar como PNG
            String outputPath = pasta.getAbsolutePath() + File.separator + "resultado.png";
            ImageIO.write(gray, "png", new File(outputPath));
            System.out.println("Processamento concluído. Resultado salvo em: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

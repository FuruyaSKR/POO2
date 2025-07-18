package SitsFrameworkController;

import java.util.List;

import SitsFrameworkController.config.Config;
import SitsFrameworkController.model.ImageTiffMeta;
import SitsFrameworkController.model.TipoProcessamento;

public class Main {
    public static void main(String[] args) throws Exception {
        Config config = new Config(-53.5, -29.4, -48.3, -25.8, "./imagens");
        SitsFramework fw = new SitsFramework(config);
        fw.limparPasta();
        List<ImageTiffMeta> imagens = fw.buscarImagens(3);
        System.out.println(imagens);
        if (imagens.isEmpty()) {
            System.out.println("Nenhuma imagem encontrada para a região.");
        } else {
            fw.baixarImagens(imagens, 3);
            fw.processarImagens(TipoProcessamento.BORDA, 1);
        }
    }
}

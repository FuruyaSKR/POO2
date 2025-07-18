package SitsFrameworkController;

import java.io.File;
import java.io.IOException;
import java.util.List;

import SitsFrameworkController.config.Config;
import SitsFrameworkController.model.ImageTiffMeta;
import SitsFrameworkController.model.TipoProcessamento;

public class SitsFramework {
    private final ImageApiController apiController;

    public SitsFramework(Config config) {
        this.apiController = new ImageApiController(config);
    }

    public List<ImageTiffMeta> buscarImagens(int quantidade) throws IOException, InterruptedException {
        return apiController.buscarImagens(quantidade);
    }

    public void baixarImagens(List<ImageTiffMeta> imagens, int quantidade) {
        apiController.baixarImagens(imagens, quantidade);
    }

    public void processarImagens(TipoProcessamento tipo, int quantidade) {
        apiController.processarImagens(tipo, quantidade);
    }

    public void limparPasta() {
        File dir = new File(apiController.getConfig().OUTPUT_DIR);
        if (dir.exists() && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
    }

}

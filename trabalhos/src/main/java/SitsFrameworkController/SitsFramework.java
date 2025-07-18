package SitsFrameworkController;

import java.io.File;
import java.io.IOException;
import java.util.List;

import SitsFrameworkController.config.Config;
import SitsFrameworkController.model.ImageTiffMeta;
import SitsFrameworkController.model.TipoProcessamento;

/**
 * Classe principal que centraliza o uso do framework para buscar, baixar e
 * processar imagens de satélite
 * a partir de uma API. Atua como uma fachada simplificada para operações
 * comuns.
 */
public class SitsFramework {
    /**
     * Controlador responsável pelas operações de API e processamento de imagens.
     */
    private final ImageApiController apiController;

    /**
     * Construtor que inicializa o framework com a configuração especificada.
     * 
     * @param config Configuração contendo informações de área geográfica e
     *               diretório de saída.
     */
    public SitsFramework(Config config) {
        this.apiController = new ImageApiController(config);
    }

    /**
     * Busca imagens disponíveis na API para a área definida na configuração.
     * 
     * @param quantidade Quantidade máxima de imagens a buscar.
     * @return Lista de metadados das imagens encontradas.
     * @throws IOException          Se houver erro de comunicação com a API.
     * @throws InterruptedException Se a requisição for interrompida.
     */
    public List<ImageTiffMeta> buscarImagens(int quantidade) throws IOException, InterruptedException {
        return apiController.buscarImagens(quantidade);
    }

    /**
     * Baixa as imagens especificadas e salva no diretório configurado.
     * 
     * @param imagens    Lista de metadados das imagens a serem baixadas.
     * @param quantidade Quantidade máxima de imagens a baixar.
     */
    public void baixarImagens(List<ImageTiffMeta> imagens, int quantidade) {
        apiController.baixarImagens(imagens, quantidade);
    }

    /**
     * Processa as imagens do diretório de saída aplicando o tipo de processamento
     * especificado.
     * 
     * @param tipo       Tipo de processamento a ser aplicado (ex: CINZA, BORDA,
     *                   CONTRASTE).
     * @param quantidade Quantidade máxima de imagens a processar.
     */
    public void processarImagens(TipoProcessamento tipo, int quantidade) {
        apiController.processarImagens(tipo, quantidade);
    }

    /**
     * Limpa todos os arquivos do diretório de saída configurado.
     */
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

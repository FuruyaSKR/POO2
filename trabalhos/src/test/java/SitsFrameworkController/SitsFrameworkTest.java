package SitsFrameworkController;

import java.io.File;
import java.util.List;

import SitsFrameworkController.config.Config;
import SitsFrameworkController.model.ImageTiffMeta;
import SitsFrameworkController.model.TipoProcessamento;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SitsFrameworkTest {

    static final String DIR = "./imagens_testes";
    static Config config;
    static SitsFramework fw;
    List<ImageTiffMeta> imagens;

    @BeforeAll
    static void setup() throws Exception {
        // https://map.openaerialmap.org/#/-59.0625,13.966054081318314,4/square/032303/5b9eac86e7efd00008985448?resolution=low&_k=9o0a0r
        config = new Config(
                -61.114883,
                16.270741,
                -60.979591,
                16.364286,
                DIR);
        fw = new SitsFramework(config);
        fw.limparPasta();
    }

    @Test
    @Order(1)
    void testBuscarImagens() throws Exception {
        imagens = fw.buscarImagens(1);
        assertNotNull(imagens, "A lista de imagens não deve ser nula");
        assertFalse(imagens.isEmpty(), "Deveria retornar pelo menos uma imagem");
        imagens.forEach(System.out::println);
    }

    @Test
    @Order(2)
    void testBaixarImagens() throws Exception {
        if (imagens == null || imagens.isEmpty()) {
            imagens = fw.buscarImagens(1); // fallback
        }
        fw.baixarImagens(imagens, 1);
        for (ImageTiffMeta meta : imagens) {
            String nomeEsperado = String.format("%s/%s_%s.tif", DIR, meta.title.replaceAll("[^a-zA-Z0-9]", "_"),
                    meta.date);
            File file = new File(nomeEsperado);
            assertTrue(file.exists(), "Arquivo não foi baixado: " + nomeEsperado);
        }
    }

    @Test
    @Order(3)
    void testProcessarImagens() throws Exception {
        fw.processarImagens(TipoProcessamento.BORDA, 1);
        if (imagens == null || imagens.isEmpty()) {
            imagens = fw.buscarImagens(1); // fallback
            fw.baixarImagens(imagens, 1);
        }
        ImageTiffMeta meta = imagens.get(0);
        String nomeProcessado = String.format("%s/%s_%s_borda.tif", DIR, meta.title.replaceAll("[^a-zA-Z0-9]", "_"),
                meta.date);
        File file = new File(nomeProcessado);
        assertTrue(file.exists(), "Arquivo processado não foi criado: " + nomeProcessado);
    }
}

package SitsFramework;

// Título: Acessando imagens de satélite do mundo inteiro!
// Objetivo: Acessar provedores de imagens de satélites e criar cubo de dados.
// Elaboração: Ana Larissa Freitas - Dados & Geo.
// Referência: https://e-sensing.github.io/sitsbook/

// OBS: Não existe uma biblioteca sits para Java, então você usaria bibliotecas de requisição HTTP e manipulação de imagens.
// Exemplo usando pseudocódigo com HTTPClient + lógica organizada em classes.

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SatelliteImageApp {

    public static void main(String[] args) {
        // 1️⃣ Definir diretório de trabalho
        String dataDir = System.getProperty("user.home") + "/sits_ana/";

        // 2️⃣ Verificar coleções disponíveis (simulação)
        List<String> collections = SatelliteProvider.listCollections();
        System.out.println("Coleções disponíveis: " + collections);

        // 3️⃣ Verificar informações de um provedor específico
        List<String> mpcCollections = SatelliteProvider.listCollections("MPC");
        System.out.println("Coleções MPC: " + mpcCollections);

        // 4️⃣ Definir cubo de dados
        SatelliteCube cubeS2 = new SatelliteCube(
                "MPC",
                "SENTINEL-2-L2A",
                Arrays.asList("B02", "B8A", "B11", "CLOUD"),
                "22MGD",
                "2024-08-01",
                "2024-10-31");

        // 5️⃣ Verificar datas disponíveis
        List<String> timeline = cubeS2.getTimeline();
        System.out.println("Datas disponíveis: " + timeline);

        // 6️⃣ Visualizar imagens selecionadas
        cubeS2.viewImages("B11", "B02", "B8A",
                Arrays.asList("2024-09-06", "2024-10-06"));

        // 7️⃣ Selecionar data específica
        SatelliteCube selected = cubeS2.selectDates(Arrays.asList("2024-09-06"));

        // 8️⃣ Baixar imagens para o computador
        selected.copyImages(
                dataDir + "/images/",
                10 // Número de threads paralelas
        );
    }
}

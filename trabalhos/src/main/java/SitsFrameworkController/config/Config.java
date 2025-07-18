package SitsFrameworkController.config;

/**
 * Classe de configuração para definição dos limites geográficos e diretório de
 * saída das imagens processadas.
 */
public class Config {
    /** Longitude mínima da área de busca. */
    public double LONG_MIN;

    /** Latitude mínima da área de busca. */
    public double LAT_MIN;

    /** Longitude máxima da área de busca. */
    public double LONG_MAX;

    /** Latitude máxima da área de busca. */
    public double LAT_MAX;

    /** Diretório de saída para os arquivos de imagem. */
    public String OUTPUT_DIR;

    /**
     * Construtor para inicializar os parâmetros de configuração geográfica e
     * diretório de saída.
     * 
     * @param longMin   Longitude mínima da área.
     * @param latMin    Latitude mínima da área.
     * @param longMax   Longitude máxima da área.
     * @param latMax    Latitude máxima da área.
     * @param outputDir Caminho do diretório de saída.
     */
    public Config(double longMin, double latMin, double longMax, double latMax, String outputDir) {
        this.LONG_MIN = longMin;
        this.LAT_MIN = latMin;
        this.LONG_MAX = longMax;
        this.LAT_MAX = latMax;
        this.OUTPUT_DIR = outputDir;
    }
}

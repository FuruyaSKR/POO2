package SitsFrameworkController.model;

/**
 * Representa os metadados de uma imagem TIFF obtida via API.
 */
public class ImageTiffMeta {
    /** URL de download da imagem. */
    public String url;

    /** Título ou identificação da imagem. */
    public String title;

    /** Data de aquisição da imagem. */
    public String date;

    /** Tamanho do arquivo em bytes. */
    public long fileSize;

    /**
     * Construtor para inicializar os metadados da imagem.
     * 
     * @param url      URL de download da imagem.
     * @param title    Título ou identificação da imagem.
     * @param date     Data de aquisição.
     * @param fileSize Tamanho do arquivo em bytes.
     */
    public ImageTiffMeta(String url, String title, String date, long fileSize) {
        this.url = url;
        this.title = title;
        this.date = date;
        this.fileSize = fileSize;
    }
}

package SitsFrameworkController.model;

public class ImageTiffMeta {
    public String url;
    public String title;
    public String date;
    public long fileSize;

    public ImageTiffMeta(String url, String title, String date, long fileSize) {
        this.url = url;
        this.title = title;
        this.date = date;
        this.fileSize = fileSize;
    }
}

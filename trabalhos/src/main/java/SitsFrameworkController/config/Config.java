package SitsFrameworkController.config;

public class Config {
    public double LONG_MIN;
    public double LAT_MIN;
    public double LONG_MAX;
    public double LAT_MAX;
    public String OUTPUT_DIR;

    public Config(double longMin, double latMin, double longMax, double latMax, String outputDir) {
        this.LONG_MIN = longMin;
        this.LAT_MIN = latMin;
        this.LONG_MAX = longMax;
        this.LAT_MAX = latMax;
        this.OUTPUT_DIR = outputDir;
    }
}

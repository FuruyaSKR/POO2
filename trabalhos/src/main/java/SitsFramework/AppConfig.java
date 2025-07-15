package SitsFramework;

public class AppConfig {
    private String inputDir;
    private String outputDir;
    private int band;

    public AppConfig(String inputDir, String outputDir, int band) {
        this.inputDir = inputDir;
        this.outputDir = outputDir;
        this.band = band;
    }

    public String getInputDir() {
        return this.inputDir;
    }

    public void setInputDir(String inputDir) {
        this.inputDir = inputDir;
    }

    public String getOutputDir() {
        return this.outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public int getBand() {
        return this.band;
    }

    public void setBand(int band) {
        this.band = band;
    }
}

package SitsFrameworkController.util;

import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.WritableRaster;

/**
 * Classe utilitária para operações de processamento em imagens, incluindo
 * conversão para cinza,
 * binarização, detecção de bordas, negativo e realce de contraste.
 */
public class ImageProcessingUtils {

    /**
     * Converte uma imagem colorida para escala de cinza.
     * 
     * @param img Imagem de entrada.
     * @return Imagem em escala de cinza.
     */
    public static BufferedImage toGray(BufferedImage img) {
        BufferedImage gray = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        ColorConvertOp op = new ColorConvertOp(img.getColorModel().getColorSpace(),
                gray.getColorModel().getColorSpace(), null);
        op.filter(img, gray);
        return gray;
    }

    /**
     * Realiza a binarização da imagem aplicando um limiar (threshold).
     * 
     * @param img       Imagem de entrada.
     * @param threshold Limiar para binarização (0 a 255).
     * @return Imagem binarizada.
     */
    public static BufferedImage binarize(BufferedImage img, int threshold) {
        BufferedImage gray = toGray(img);
        WritableRaster raster = gray.getRaster();
        int w = gray.getWidth();
        int h = gray.getHeight();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int value = raster.getSample(x, y, 0);
                int binary = value < threshold ? 0 : 255;
                raster.setSample(x, y, 0, binary);
            }
        }
        return gray;
    }

    /**
     * Aplica um filtro de detecção de bordas (Sobel) na imagem.
     * 
     * @param img Imagem de entrada.
     * @return Imagem resultante com realce das bordas.
     */
    public static BufferedImage edgeDetect(BufferedImage img) {
        BufferedImage gray = toGray(img);
        int w = gray.getWidth();
        int h = gray.getHeight();
        BufferedImage edge = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);

        int[] gx = { -1, 0, 1, -2, 0, 2, -1, 0, 1 };
        int[] gy = { -1, -2, -1, 0, 0, 0, 1, 2, 1 };
        WritableRaster inRaster = gray.getRaster();
        WritableRaster outRaster = edge.getRaster();

        for (int y = 1; y < h - 1; y++) {
            for (int x = 1; x < w - 1; x++) {
                int sumX = 0, sumY = 0;
                int idx = 0;
                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        int pixel = inRaster.getSample(x + kx, y + ky, 0);
                        sumX += gx[idx] * pixel;
                        sumY += gy[idx] * pixel;
                        idx++;
                    }
                }
                int mag = (int) Math.min(255, Math.sqrt(sumX * sumX + sumY * sumY));
                outRaster.setSample(x, y, 0, mag);
            }
        }
        return edge;
    }

    /**
     * Gera o negativo da imagem, invertendo os tons de cinza.
     * 
     * @param img Imagem de entrada.
     * @return Imagem negativa.
     */
    public static BufferedImage negativo(BufferedImage img) {
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster inRaster = toGray(img).getRaster();
        WritableRaster outRaster = out.getRaster();
        int w = img.getWidth();
        int h = img.getHeight();

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int value = inRaster.getSample(x, y, 0);
                outRaster.setSample(x, y, 0, 255 - value);
            }
        }
        return out;
    }

    /**
     * Realça o contraste da imagem aplicando um estiramento linear dos valores de
     * pixel.
     * 
     * @param img Imagem de entrada.
     * @return Imagem com contraste realçado.
     */
    public static BufferedImage contraste(BufferedImage img) {
        BufferedImage gray = toGray(img);
        WritableRaster raster = gray.getRaster();
        int w = gray.getWidth();
        int h = gray.getHeight();
        int min = 255, max = 0;

        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++) {
                int value = raster.getSample(x, y, 0);
                if (value < min)
                    min = value;
                if (value > max)
                    max = value;
            }
        double scale = (max > min) ? 255.0 / (max - min) : 1.0;
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++) {
                int value = raster.getSample(x, y, 0);
                int stretched = (int) ((value - min) * scale);
                raster.setSample(x, y, 0, Math.max(0, Math.min(255, stretched)));
            }
        return gray;
    }

}

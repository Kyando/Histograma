import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by ribeiro.medeiros on 27/03/2017.
 */
public class Histograma {

    BufferedImage histogram(BufferedImage img){
        int[] hista = new int[256];
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int i = 0; i < 255; i++){
            hista[i] = 0;
        }

        //Histograma
        for(int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {

                int cor = img.getRGB(x, y);

                Color pixel = new Color(cor);

                int r = pixel.getRed();
                hista[r]++;
            }
        }
        //Histograma acomulado
        int[] ha = new int[256];
        int hmin = 0;
        ha[0] = hista[0];

        for(int i = 1; i < 256; i++){
            ha[i] = ha[i -1] + hista[i];

        }

        //Descobre minimo valor
        for(int i = 0; i < 255; i++){
            if(hista[i] != 0)
            {
                hmin = hista[i];
                break;
            }

        }

        int[] hv= new int[255];
        for(int i = 0; i < 255; i++){

            hv[i] = (int) (Math.round(((ha[i] - hmin) / ((float)img.getHeight() * img.getWidth()))* (256 - 1)) );

            if(hv[i] > 255){
                hv[i] = 255;
            }
            if (hv[i] < 0){
                hv[i] = 0;
            }
        }


        for(int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {

                int cor = img.getRGB(x, y);

                Color newPixel = new Color(cor);

                newPixel = new Color(hv[newPixel.getRed()],hv[newPixel.getGreen()],hv[newPixel.getBlue()]);

                out.setRGB(x,y,newPixel.getRGB());
            }
        }

        return out;
    }

    public void run() throws IOException {
        File PATH = new File("E:\\Aula\\img\\gray");
        BufferedImage arquivo = ImageIO.read(new File(PATH, "cars.jpg"));



        BufferedImage imgHistogram = histogram(arquivo);
        ImageIO.write(imgHistogram, "png", new File("histogram.png"));
    }

    public static void main (String[] args) throws IOException {
        Histograma atv3 = new Histograma();
        atv3.run();
    }
}

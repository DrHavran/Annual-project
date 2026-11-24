package test.annualproject.Compressor;

import org.springframework.stereotype.Service;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;

@Service
public class CompressorService {

    public void compressToCSV(Image img, int size){
        try {
            PrintWriter writer = new PrintWriter("output.csv");

                BufferedImage input = ImageIO.read((ImageInputStream) img);

                if(input.getHeight() % size != 0){
                    System.out.println("Please use output size that can be divided without decimal points");
                    return;
                }

                int pixelRatio = input.getHeight() / Settings.outputSize;
                StringBuilder output = new StringBuilder();
                output.append(Settings.Class).append(",");
                int count = 0;

                int r = 0; int g = 0; int b = 0;

                for(int row = 0; row < input.getHeight() - 1; row += pixelRatio){
                    for(int col = 0; col < input.getWidth(); col++){

                        for(int i = 0; i < pixelRatio; i++){
                            int rgb = input.getRGB(col, i + row);
                            r += (rgb >> 16) & 0xFF;
                            g += (rgb >> 8) & 0xFF;
                            b += rgb & 0xFF;
                        }
                        count++;
                        if(count == pixelRatio){
                            count = 0;
                            r = r/(pixelRatio*pixelRatio);
                            g = g/(pixelRatio*pixelRatio);
                            b = b/(pixelRatio*pixelRatio);
                            int finalRGB = (0xFF << 24) | (r << 16) | (g << 8) | b;
                            output.append(finalRGB).append(",");
                            r = 0; g = 0; b = 0;
                        }
                    }
                }
                output.deleteCharAt(output.length()-1);
                writer.write(output.toString());
                writer.write("\n");
            writer.close();
        }catch(Exception e){
            e.fillInStackTrace();
        }
    }
}

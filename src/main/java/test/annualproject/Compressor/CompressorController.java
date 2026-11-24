package test.annualproject.Compressor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

@RestController
@RequestMapping("/compressor")
public class CompressorController {
    private final CompressorService compressorService;

    public CompressorController() {
        this.compressorService = new CompressorService();
    }

    @PostMapping("/compressImage/{size}")
    public void compressImage (
            @RequestParam("image") MultipartFile image,
            @PathVariable int size
    ){
        try {
            BufferedImage img;
            try (InputStream in = image.getInputStream()) {
                img = ImageIO.read(in);
            }
            if (size < 1) return;
            if (img == null) return;

            compressorService.compressToCSV(img, size);
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }
}
package com.mealsfitbackend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

@Service
public class FileServiceImpl implements FileService{

    @Value("${app.upload.dir}")
    public String uploadDir;

    Logger logger = LoggerFactory.getLogger(getClass());

    public void uploadFile(MultipartFile file, String prefix) {
        try {
            InputStream inputStream = file.getInputStream();
            BufferedImage image = ImageIO.read(inputStream);
            BufferedImage resized = resize(image, 500, 500);
            File newImage = new File(uploadDir + StringUtils.cleanPath(prefix + file.getOriginalFilename()));
            ImageIO.write(resized, "png", newImage);
        } catch (Exception e) {
            logger.error("Unable to save the image: "+ e.getMessage());
        }
    }

    private static BufferedImage resize(BufferedImage img, int width, int height) {
        Dimension imageSize = new Dimension(img.getWidth(), img.getHeight());
        Dimension boundary = new Dimension(width, height);
        Dimension scaledDimension = getScaledDimension(imageSize, boundary);
        Image tmp = img.getScaledInstance(scaledDimension.width, scaledDimension.height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(scaledDimension.width, scaledDimension.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    private static Dimension getScaledDimension(Dimension imageSize, Dimension boundary) {
        double widthRatio = boundary.getWidth() / imageSize.getWidth();
        double heightRatio = boundary.getHeight() / imageSize.getHeight();
        double ratio = Math.min(widthRatio, heightRatio);

        return new Dimension((int) (imageSize.width  * ratio),
                (int) (imageSize.height * ratio));
    }
}

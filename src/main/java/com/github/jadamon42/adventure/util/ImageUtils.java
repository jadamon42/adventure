package com.github.jadamon42.adventure.util;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageUtils {
    public static Image scaleImage(Image image, double scaleFactor) {
        int newWidth = (int) (image.getWidth() * scaleFactor);
        int newHeight = (int) (image.getHeight() * scaleFactor);

        WritableImage scaledImage = new WritableImage(newWidth, newHeight);
        PixelReader pixelReader = image.getPixelReader();

        for (int x = 0; x < newWidth; x++) {
            for (int y = 0; y < newHeight; y++) {
                int originalX = (int) (x / scaleFactor);
                int originalY = (int) (y / scaleFactor);
                scaledImage.getPixelWriter().setArgb(x, y, pixelReader.getArgb(originalX, originalY));
            }
        }

        return scaledImage;
    }

    public static void setOpacity(WritableImage image, double opacity) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = image.getPixelReader().getColor(x, y);
                Color transparentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
                image.getPixelWriter().setColor(x, y, transparentColor);
            }
        }
    }
}

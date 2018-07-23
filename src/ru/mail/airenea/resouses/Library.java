package ru.mail.airenea.resouses;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Library {

    private static String dirPath = "C:\\Users\\Ирина\\Documents\\Программирование\\Нейронные сети\\Выборки для НС\\Numbers";

    private static File dir = new File(dirPath);
    private static File file;

    private static BufferedImage image;

    // Get matrix of pixels.
    public static byte[][] get()  {

        int width = image.getWidth();
        int height = image.getHeight();
        byte[][] pixels = new byte[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                pixels[row][col] = (byte) image.getRGB(col, row);
            }
        }
        return pixels;
    }


    //Get certain image from file system.
    private static void getImageFromFile()  {

        String imgURL = "num" + generateRandNumber() + ".jpg";
        file = new File(dir, imgURL);
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            System.err.println("Error in reading from file: " + e.getMessage());
        }
    }

    // Generating random number.
    private static int generateRandNumber()  {
        int num = 0;
        return  num;
    }
}

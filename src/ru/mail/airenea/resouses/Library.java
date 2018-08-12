package ru.mail.airenea.resouses;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Library {

    private static String dirPath = "C:\\Users\\Ирина\\Documents\\Программирование\\Нейронные сети\\Выборки для НС\\Numbers";

    private static File dir = new File(dirPath);
    private int num;

    // Returns set of training pairs of task and answer
    public ArrayList <TaskAnswerPair> getSet(int num) {
        ArrayList <TaskAnswerPair> set = new ArrayList<TaskAnswerPair>();
        for (int i = 0; i < num; i++)  {
            set.add(new TaskAnswerPair(getPixels(), this.num));
        }
        return set;
    }

    // Returns one training pair of task and answer
    public  TaskAnswerPair getOne() {
        return new TaskAnswerPair(getPixels(), num);
    }

    // Get matrix of pixels
    // fixme
    public byte[][] getPixels()  {

        BufferedImage image = getImageFromFile();
        int width = image.getWidth();
        int height = image.getHeight();
        byte[][] pixels = new byte[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                pixels[row][col] = (byte) image.getRGB(col, row);
                //pixels[row][col] /= 10;
            }
        }
        return pixels;
    }


    //Get certain image from file system
    private BufferedImage getImageFromFile()  {

        String imgURL = "num" + generateRandNumber() + ".jpg";
        File file = new File(dir, imgURL);
        try {
            BufferedImage image = new BufferedImage(200,200,BufferedImage.TYPE_BYTE_GRAY);
            image = ImageIO.read(file);
            return image;
        } catch (IOException e) {
            System.err.println("Error in reading from file: " + e.getMessage());
            return null;
        }
    }

    // Generating random number
    private int generateRandNumber()  {
        Random random = new Random();
        num = random.nextInt(10);
        return  num;
    }

}

package ru.mail.airenea.resouses;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Library {

    private static String dirPath = "C:\\Users\\Ирина\\Documents\\Программирование\\Нейронные сети\\Выборки для НС\\Numbers";

    private static File dir = new File(dirPath);
    private static int num;

    // Returns set of training pairs of task and answer
    public ArrayList <TaskAnswerPair> getSet(int num) {
        ArrayList <TaskAnswerPair> set = new ArrayList<TaskAnswerPair>();
        for (int i = 0; i < num; i++)  {
            set.add(new TaskAnswerPair(getPixels(), num));
        }
        return set;
    }

    // Returns one training pair of task and answer
    public  TaskAnswerPair getOne() {
        return new TaskAnswerPair(getPixels(), num);
    }

    // Get matrix of pixels
    public static byte[][] getPixels()  {

        BufferedImage image = getImageFromFile();
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


    //Get certain image from file system
    private static BufferedImage getImageFromFile()  {

        String imgURL = "num" + generateRandNumber() + ".jpg";
        File file = new File(dir, imgURL);
        try {
            BufferedImage image = ImageIO.read(file);
            return image;
        } catch (IOException e) {
            System.err.println("Error in reading from file: " + e.getMessage());
            return null;
        }
    }

    // Generating random number
    private static int generateRandNumber()  {
        int number = (int) Math.random() * 10;
        num = number;
        return  number;
    }

    // Class, to store a pair: task and right answer
    class TaskAnswerPair {

        private byte[][] task;
        private int number;

        public TaskAnswerPair(byte[][] task, int number) {
            this.task = task;
            this.number = number;
        }

        public byte[][] getTask() {
            return task;
        }

        public void setTask(byte[][] task) {
            this.task = task;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
}

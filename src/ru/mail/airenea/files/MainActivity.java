package ru.mail.airenea.files;

import java.util.ArrayList;

import ru.mail.airenea.resouses.Library;
import ru.mail.airenea.resouses.Library.*;
import ru.mail.airenea.resouses.TaskAnswerPair;

public class MainActivity {

    public static void main(String[] args) {

// Testing library
/*        Library library = new Library();
        TaskAnswerPair tap = library.getOne();
        System.out.println("Number is " + tap.getNumber());
        System.out.println("Task is ");
        byte[][] t = tap.getTask();
        for (int row = 0; row < 28; row++) {
            for (int col = 0; col < 28; col++) {
                System.out.print (t[row][col] + " ");
            }
            System.out.println();
        }*/

// Testing network
        int[] neuronsInLayers = {784, 16, 16, 10};
        Teacher teacher = new Teacher(4, neuronsInLayers, 0.0001, 1000, 50);
        teacher.train();
    }
}

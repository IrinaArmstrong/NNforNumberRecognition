package ru.mail.airenea.files;

import ru.mail.airenea.resouses.Library;
import ru.mail.airenea.resouses.TaskAnswerPair;

import java.util.ArrayList;

/*
* Class that
* */
public class Teacher {

    // Neural network
    private Network network;

    // Library
    private Library library;

    // Learning coefficient
    private double coefficient;

    // Number of iterations
    private int iterations;

    // Accuracy of results
    private double accuracy;

    // Number of layers
    private int layersNumber;

    // Number of neurons in each layer
    private int[] neuronsInLayers;

    // Number of tasks in set
    private int numberOfTasks;

    // Current results of network
    private double[] results;

    // Constructor
    public Teacher(int layersNumber, int[] neuronsInLayers, double coefficient, int iterations, int numberOfTasks) {
        this.layersNumber = layersNumber;
        this.neuronsInLayers = neuronsInLayers;
        this.network = new Network(layersNumber, neuronsInLayers);
        this.library = new Library();
        this.coefficient = coefficient;
        this.iterations = iterations;
        this.numberOfTasks = numberOfTasks;
    }

    // Accuracy setter
    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    // Train network
    public void train()  {
        ArrayList<TaskAnswerPair> tasks = this.library.getSet(numberOfTasks);
        // Get array of right answers
        double[] answers = new double[tasks.size()];
        for (int i = 0; i < tasks.size(); i++)  {
            answers[i] = tasks.get(i).getNumber();
        }
        // TODO: continue here
        // Training network
        network.learn(tasks, coefficient, iterations);
        //Testing on one example
        TaskAnswerPair example = this.library.getOne();
        this.results = network.testing(example, coefficient);
        // Interpreting results: finding max output of neuron (in last layer) this will be an answer
        double max = Double.MIN_VALUE;
        int maxIdx = 0;
        for (int i = 0; i < results.length; i++) {
            if (max < results[i])  {
                max = results[i];
                maxIdx = i;
            }
        }
        System.out.printf("Answer of network is: %d, right answer is: %d.%n", maxIdx, example.getNumber());
        System.out.println("All possibilities: ");
        for (int i = 0; i < results.length; i++) {
            System.out.println("#" + i + " is: " + results[i]);
        }

    }
}

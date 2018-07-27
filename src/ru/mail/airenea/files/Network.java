package ru.mail.airenea.files;

// Class that realizes structure of whole neuron network

import ru.mail.airenea.resouses.TaskAnswerPair;

import java.util.ArrayList;

public class Network {

    // Number of this network layers
    private int layersNumber;

    // Array of layers
    private Layer[] layers;

    // Array that holds number of neurons in each layer
    private int[] neuronsInLayers;

    // Array of result signals
    public double[] results;


    // Constructor of this network
    public Network(int layersNumber, int[] neuronsInLayers) {
        this.layersNumber = layersNumber;
        for (int i = 0; i < this.layersNumber; i++)  {
            this.neuronsInLayers[i] = neuronsInLayers[i];
        }
        createLayers();
    }

    // Create array of layers
    private void createLayers()  {
        this.layers[0] = new Layer(0, this.neuronsInLayers[0], this.neuronsInLayers[0]);
        for (int i = 1; i < this.layersNumber; i++)  {
            this.layers[i] = new Layer(i, this.neuronsInLayers[i], this.neuronsInLayers[i - 1]);
        }
    }

    // Get input signal to the sensors and send it to the first layer (sensors)
    private void getInputSignal(double[] inputSignal)  {
        this.layers[0].getInputSignalToLayer(inputSignal);
    }

    // Get output signal from one layer and send it to the next layer
    private void transmitSignals(int firstLayer, int secondLayer)  {
        this.layers[secondLayer].getInputSignalToLayer(this.layers[firstLayer].giveOutputSignalToLayer());
    }

    // Get final output signal (result)
    private double[] getOutputSignal()  {
        return this.layers[this.layersNumber - 1].giveOutputSignalToLayer();
    }

    // Get errors from one layer and send it to the next layer: from secondLayer to firstLayer
    private void transmitErrors(int firstLayer, int secondLayer)  {
        this.layers[secondLayer].getErrors(this.layers[firstLayer].giveErrorsToLayer());
    }

    // Learning method !!!!!!!!!!!!!!!!!!!!!!!!!
    public double[] learn(ArrayList<TaskAnswerPair> tasks, double learningRate, double accuracy)  {

        // Get array of right answers
        double[] answers = new double[tasks.size()];
        for (int i = 0; i < tasks.size(); i++)  {
            answers[i] = tasks.get(i).getNumber();
        }

        // (Сделать в цикле !!!!)
        // Tasks to linear structure
        int taskLenght = tasks.get(0).getTask().length;
        double[] task = new double[taskLenght * taskLenght];
        for (int row = 0; row < taskLenght; row++) {
            for (int col = 0; col < taskLenght; col++) {
                task[row + col] = tasks.get(0).getTask()[row][col];
            }
        }

            for (int i = 0; i < this.layersNumber; i++)  {
                if (i == 0) this.layers[i].getInputSignalToLayer(task);
                else {
                    transmitSignals(i - 1, i);
                }
            }

        return this.getOutputSignal();
    }

    // Check if the answer of the network is right
    private boolean isRight(double[] networkAnswers, double[] rightAnswers)  {
        boolean right = true;
        for (int i = 1; i < networkAnswers.length; i++)  {
            if (networkAnswers[i] != rightAnswers[i]) right = false;
        }
        return  right;
    }

    //
}

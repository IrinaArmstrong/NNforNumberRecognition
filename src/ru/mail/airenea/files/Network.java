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

    //

    // Constructor of this network
    public Network(int layersNumber, int[] neuronsInLayers) {
        if (layersNumber != neuronsInLayers.length) {
            System.err.println("Number of layers != number of elements in neuron's number array!");
        }
        this.layersNumber = layersNumber;
        this.neuronsInLayers = new int[this.layersNumber];
        for (int i = 0; i < this.layersNumber; i++)  {
            this.neuronsInLayers[i] = neuronsInLayers[i];
        }
        createLayers();
    }

    // Create array of layers
    private void createLayers()  {
        this.layers = new Layer[this.layersNumber];
        this.layers[0] = new Layer(0, this.neuronsInLayers[0], 1);
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
    public double[] learn(ArrayList<TaskAnswerPair> tasks, double learningRate, int iterations)  {

        // Get array of right answers
        double[] answers = new double[tasks.size()];
        System.out.println("Answers: ");
        for (int i = 0; i < tasks.size(); i++)  {
            answers[i] = tasks.get(i).getNumber();
            System.out.print(answers[i] + "; ");
        }

        // Iterate chosen number of times
        for (int iter = 0; iter < iterations; iter++)  {

            // Go through all tasks from the set
            for (int taskNum = 0; taskNum < tasks.size(); taskNum++)  {

                // Create answer
                double[] answer = new double[neuronsInLayers[layersNumber - 1]];
                for (int i = 0; i < answer.length; i++)  {
                    if (i == answers[taskNum]) answer[i] = 1;
                    else answer[i] = 0.0;
                }

                // Tasks to linear structure
                int taskLenght = tasks.get(taskNum).getTask().length;
                double[] task = new double[taskLenght * taskLenght];
                int taskIterator = 0;
                for (int row = 0; row < taskLenght; row++) {
                    for (int col = 0; col < taskLenght; col++) {
                        task[taskIterator] = tasks.get(taskNum).getTask()[row][col];
                        taskIterator++;
                    }
                }
                // Send signals through all layers
                for (int i = 0; i < this.layersNumber; i++)  {
                    if (i == 0) this.layers[i].getInputSignalToLayer(task);
                    else {
                        transmitSignals(i - 1, i);
                    }
                }
                // Send final signal from the last layer to the output
                this.results = this.getOutputSignal();
                this.printState();

                    // Mistake for the last layer
                    for (int i = 0; i < this.layers[layersNumber - 1].getNeuronsNumber(); i++)  {
                        layers[layersNumber - 1].getNeurons()[i].lastLayerCountError(answer[i]);

                    }
                    double[] lastLayerErrors =  layers[layersNumber - 1].giveErrorsToLayer();
                    layers[layersNumber - 2].getErrors(lastLayerErrors);
                    // Mistake for other layers
                    for (int i = this.layersNumber - 2; i > 1 ; i--)  {
                        double[] errors = layers[i].giveErrorsToLayer();
                        layers[i - 1].getErrors(errors);
                    }
                    // Correcting weights and biases, from 2nd layer to the last
                    for (int i = 1; i < this.layersNumber; i++)  {
                        layers[i].correctWeightsOfLayer(learningRate);
                    }
                    System.out.println("\nIteration: " + iter + ";  TaskNum = " + taskNum);
                    this.printState();
                    results = this.getOutputSignal();
            }

        }

        System.out.println("Iterated " + iterations + " of times. Results returned.");
        return this.results;
    }

    // Check if the answer of the network is right
    private boolean isRight(double[] networkAnswers, double[] rightAnswers)  {
        boolean right = true;
        for (int i = 1; i < networkAnswers.length; i++)  {
            if (networkAnswers[i] != rightAnswers[i]) right = false;
        }
        return  right;
    }

    // Testing on example
    public double[] testing (TaskAnswerPair exampleTask, double learningRate)  {
        // Tasks to linear structure
        int taskLenght = exampleTask.getTask().length;
        double[] task = new double[taskLenght * taskLenght];
        for (int row = 0; row < taskLenght; row++) {
            for (int col = 0; col < taskLenght; col++) {
                task[row + col] = exampleTask.getTask()[row][col];
            }
        }
        // Send signals through all layers
        for (int i = 0; i < this.layersNumber; i++)  {
            if (i == 0) this.layers[i].getInputSignalToLayer(task);
            else {
                transmitSignals(i - 1, i);
            }
        }
        // Send final signal from the last layer to the output
        this.results = this.getOutputSignal();
        return this.results;
    }

    // Show state of weights in network
    public void printState()  {
        //new double[neuronsInLayers[layersNumber - 1]];
        double[] outputs = this.getOutputSignal();
       // outputs =
        System.out.println("Outputs: ");
        for (int i = 0; i < outputs.length; i++) {
            System.out.print(outputs[i] + "; ");
        }
        System.out.println();
    }
}

package ru.mail.airenea.files;

// Class that realizes structure of whole neuron network

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
    }

    // Create array of layers
    private void createLayers()  {
        this.layers[0] = new Layer(0, this.neuronsInLayers[0], this.neuronsInLayers[0]);
        for (int i = 1; i < this.layersNumber; i++)  {
            this.layers[i] = new Layer(i, this.neuronsInLayers[i], this.neuronsInLayers[i - 1]);
        }
    }
}

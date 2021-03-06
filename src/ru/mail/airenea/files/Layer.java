package ru.mail.airenea.files;

// Class that realizes single layer of neurons structure

public class Layer {

    // Number of this layer
    private int serialNumber;

    // Number of neurons in this layer
    private int neuronsNumber;

    // Number of neurons in previous layer
    private int prevNeuronsNumber;

    // Number of neurons in next layer
    //private int nextNeuronsNumber;

    // Array of neurons
    private Neuron[] neurons;

    // Get array of neurons
    public Neuron[] getNeurons() {
        return neurons;
    }

    // Get number of neurons in this layer
    public int getNeuronsNumber() {
        return neuronsNumber;
    }

    // Constructor of this layer
    public Layer(int serialNumber, int neuronsNumber, int prevNeuronsNumber) {
        this.serialNumber = serialNumber;
        this.neuronsNumber = neuronsNumber;
        this.prevNeuronsNumber = prevNeuronsNumber;
        this.createNeurons();
    }

    // Create array of neurons
    private void createNeurons() {
        this.neurons = new Neuron[this.neuronsNumber];
        for (int i = 0; i < this.neuronsNumber; i++)  {
            this.neurons[i] = new Neuron(this.prevNeuronsNumber);
        }
    }

    // Transmit output signal from this layer's neurons to next layer's neurons
    public double[] giveOutputSignalToLayer() {
        double[] outputSignals = new double[this.neuronsNumber];
        for (int i = 0; i < this.neuronsNumber; i++)  {
            outputSignals[i] = this.neurons[i].getOutputSignal();
        }
        return outputSignals;
    }

    // Get output signal from previous layer's neurons to this layer's neurons
    // If this is the first layer of network, just get input signals
    public void getInputSignalToLayer(double[] outputSignals) {
        if (this.serialNumber == 0) {
            for (int i = 0; i < this.neuronsNumber; i++)  {
                this.neurons[i].getInputSygnal(outputSignals[i]);
            }
        }
        else {
            for (int i = 0; i < this.neuronsNumber; i++)  {
                this.neurons[i].getSignals(outputSignals);
            }
        }

    }



    // Get errors from next layer's neurons
    public void getErrors(double[] errors) {
        for (int i = 0; i < this.neuronsNumber; i++)  {
            this.neurons[i].countError(errors);
        }
    }

    // Give errors to previous layer's neurons
    public double[] giveErrorsToLayer() {
        double[] layerErrors = new double[this.prevNeuronsNumber];
        for (int i = 0; i < this.prevNeuronsNumber; i++)  {
            for (int j = 0; j < this.neuronsNumber; j++)  {
                layerErrors[i] += this.neurons[j].giveErrors()[i];
            }
        }
        return layerErrors;
    }

    // Corrects weights and biases of all this layer's neurons
    public void correctWeightsOfLayer(double learningRate)  {
        for (int i = 0; i < this.neuronsNumber; i++)  {
            this.neurons[i].correctWeights(learningRate);
        }
    }

    // Print state of layer
    public void printStateLayer() {
        System.out.println("Layer #" + serialNumber);
        System.out.println("Neurons number = " + neuronsNumber);
        int cnt=0;
        for (Neuron neyron : neurons) {
            System.out.println("");
            System.out.println("Neuron #" + cnt);
            neyron.printStateNeuron();
            cnt++;
        }
    }
}


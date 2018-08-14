package ru.mail.airenea.files;

import java.util.Random;

// Class that realizes single neuron structure
public class Neuron {

    // An offset (bias) of neuron's output signal
    private double bias;

    // Array of dendrits weights
    public double[] dendritWeights;

    // Output signal of neuron
    public double output;

    // Number of dendrits
    private int dendritNumber;

    // Error of neuron
    private double error;

    // Summ of weights * outputs from previous layer = z
    private double weightsOutputsSumm;

    // Speed of learning or a step of grad descending
    private double alpha;

    // Constructor, that init weights, bias as random numbers (at first time using neuron)
    public Neuron( int dendritNumber) {
        this.dendritNumber = dendritNumber;
        Random random = new Random();
        this.bias = random.nextDouble() / 1000;
        this.dendritWeights = new double[this.dendritNumber];
        if (dendritNumber == 1) {
            this.dendritWeights[0] = 1;
        }
        else {
            for (int i = 0; i < dendritNumber; i++)  {
                this.dendritWeights[i] = random.nextDouble() / 1000;
            }
        }
        this.error = 0.0;
        this.output = sigmoid();
    }

    // Activation (sigmoid) function
    private double sigmoid()  {
        double sigmoida = 1 / (1 + Math.exp(-this.weightsOutputsSumm));
        return sigmoida;
    }

    // Derivate of sigmoid function
    private double derivateSigmoid() {
        return sigmoid() * (1 - sigmoid());
    }

    // Get input signal to the neuron of FIRST LAYER
    public void getInputSygnal(double inputSignal)  {
        this.weightsOutputsSumm = inputSignal;
        this.setOutput();
    }

    // Get signals on dendrits from previous layer
    public void getSignals(double[] prevOutputs)  {
        for (int i = 0; i < dendritNumber; i++)  {
            this.weightsOutputsSumm += prevOutputs[i] * this.dendritWeights[i];
        }
        this.weightsOutputsSumm += this.bias;
        this.setOutput();
    }


    // Give errors to previous layer
    public double[] giveErrors() {
        double[] returnedErrors = new double[this.dendritNumber];
        for (int i = 0; i < dendritNumber; i++)  {
            returnedErrors[i] = this.dendritWeights[i] * this.error;
        }
        return returnedErrors;
    }

    // Count error for this neuron, if it is in hidden layers
    public void countError(double[] nextErrors) {
        for (int i = 0; i < nextErrors.length; i++)  {
            this.error += nextErrors[i];
        }
        this.error *= this.derivateSigmoid();
    }

    // Count error for this neuron, if it is in last layer
    public void lastLayerCountError(double rightAnswer)  {
        this.error = -(rightAnswer - this.output) * derivateSigmoid();
    }

    // Recount weights and bias using error
    public void correctWeights(double learningRate)  {
        this.alpha = learningRate;
        for (int i = 0; i < dendritNumber; i++)  {
            double deltaW = this.alpha * this.error * this.output;
            this.dendritWeights[i] = this.dendritWeights[i] - deltaW;
        }
        this.bias -= this.alpha * this.error;
    }

    // Give output signal to next layer's neurons
    public double getOutputSignal() {
        return output;
    }

    // Count and set output signal
    public void setOutput() {
        this.output = sigmoid();
    }

    // Print state of neuron
    public void printStateNeuron() {
        System.out.println("Dendrit number = "+ dendritNumber);
        /*int cnt = 0;
        for (double dendritWeight : dendritWeights) {
            System.out.println("Dendrit w #" + cnt + " = " + dendritWeight);
            cnt++;
        }*/
        System.out.println("Bias = " + bias);
        System.out.println("Weights summ = " + weightsOutputsSumm);
        System.out.println("Error = " + error);
        System.out.println("Output = " + output);
        System.out.println("Deriviate sigmoid = " + this.derivateSigmoid());
    }
}



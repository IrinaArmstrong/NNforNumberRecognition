package ru.mail.airenea.files;

// Class that realizes single neuron structure
public class Neuron {

    // An offset (bias) of neuron's output signal
    private double bias;

    // Array of dendrits weights
    private double[] dendritWeights;

    // Output signal of neuron
    private double output;

    // Number of dendrits
    private int dendritNumber;

    // Error of neuron
    private double error;

    // Summ of weights * outputs from previous layer = z
    private double weightsOutputsSumm;

    // Constructor, that init weights, bias as random numbers (at first time using neuron)
    public Neuron( int dendritNumber) {
        this.dendritNumber = dendritNumber;
        this.bias = Math.random();
        for (int i = 0; i < dendritNumber; i++)  {
            this.dendritWeights[i] = Math.random();
        }
        this.error = 0.0;
        this.output = sigmoid();
    }

    // Activation (sigmoid) function
    private double sigmoid()  {
        return 1 / (1 + Math.exp(this.weightsOutputsSumm));
    }

    // Derivate of sigmoid function
    private double derivateSigmoid() {
        return sigmoid() * (1 - sigmoid());
    }

    // Get signals on dendrits from previous layer
    private void getSignals(double[] prevOutputs)  {
        for (int i = 0; i < dendritNumber; i++)  {
            this.weightsOutputsSumm += prevOutputs[i] * this.dendritWeights[i];
        }
        this.weightsOutputsSumm += this.bias;
    }

    // Get error from next layer
    public void setError(double error) {
        this.error = error;
    }

    // Give errors to previous layer
    public double[] giveErrors() {
        double[] returnedErrors = new double[this.dendritNumber];
        for (int i = 0; i < dendritNumber; i++)  {
            returnedErrors[i] = this.dendritWeights[i] * this.error;
        }
        return returnedErrors;
    }

    // Count error for this neuron
    private void countError(double[] nextErrors) {
        for (int i = 0; i < nextErrors.length; i++)  {
            this.error += nextErrors[i];
        }
        this.error *= this.derivateSigmoid();
    }
}

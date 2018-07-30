package ru.mail.airenea.files;

// Class that realizes single neuron structure
public class Neuron {

    // An offset (bias) of neuron's output signal
    private double bias;

    // Array of dendrits weights
    private double[] dendritWeights;

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
        this.bias = Math.random();
        this.dendritWeights = new double[this.dendritNumber];
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
    public void getSignals(double[] prevOutputs)  {
        for (int i = 0; i < dendritNumber; i++)  {
            this.weightsOutputsSumm += prevOutputs[i] * this.dendritWeights[i];
        }
        this.weightsOutputsSumm += this.bias;
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
    public void lastLayerCountError(double rightAnswers)  {
        this.error = -(rightAnswers - this.output) * derivateSigmoid();
    }

    // Recount weights and bias using error
    public void correctWeights(double learningRate)  {
        this.alpha = learningRate;
        for (int i = 0; i < dendritNumber; i++)  {
            this.dendritWeights[i] -= this.alpha * this.error * this.output;
        }
        this.bias -= this.alpha * this.error;
    }

    // Give output signal to next layer's neurons
    public double getOutputSignal() {
        return output;
    }
}

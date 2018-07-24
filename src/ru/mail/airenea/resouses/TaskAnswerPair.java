package ru.mail.airenea.resouses;

// Class, to store a pair: task and right answer

public class TaskAnswerPair {

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

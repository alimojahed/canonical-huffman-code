package encoder;

import lombok.ToString;

@ToString
public class FrequencyPair extends Node{
    Character character;
    FrequencyPair leftChild = null;
    FrequencyPair rightChild = null;
    boolean isInterval = false;
    public FrequencyPair(int frequency, char character) {
        this.key = frequency;
        this.character = character;
    }

    public FrequencyPair(int frequency,FrequencyPair leftChild, FrequencyPair rightChild, boolean isInterval) {
        this.key = frequency;
        this.character = null;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.isInterval = isInterval;
    }
}

package encoder;

import java.util.ArrayList;

public class MinHeap<T extends Node> {
    private ArrayList<T> items = new ArrayList<>();


    public void insert(T item) {
        
        items.add(item);

        bubbleUp();
    }

    public T remove() {
        if (isEmpty())
            throw new IllegalStateException();
        var root = items.get(0);
        if (size()> 1) {
            items.set(0, items.remove(size() - 1));

            bubbleDown();
        }
        else{
            items.remove(0);
        }
        return root;
    }

    private void bubbleDown() {
        var index = 0;
        while (index <= size() && !isValidParent(index)) {
            var smallerChildIndex = smallerChildIndex(index);
            swap(index, smallerChildIndex);
            index = smallerChildIndex;
        }
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    private int smallerChildIndex(int index) {
        if (!hasLeftChild(index))
            return index;

        if (!hasRightChild(index))
            return leftChildIndex(index);

        return (leftChild(index).key < rightChild(index).key) ?
                leftChildIndex(index) :
                rightChildIndex(index);
    }

    private boolean hasLeftChild(int index) {
        return leftChildIndex(index) < size();
    }

    private boolean hasRightChild(int index) {
        return rightChildIndex(index) < size();
    }

    private boolean isValidParent(int index) {
        if (!hasLeftChild(index))
            return true;

        var isValid = items.get(index).key <= leftChild(index).key;

        if (hasRightChild(index))
            isValid &= items.get(index).key <= rightChild(index).key;

        return isValid;
    }

    private T rightChild(int index) {
        return items.get(rightChildIndex(index));
    }

    private T leftChild(int index) {
        return items.get(leftChildIndex(index));
    }

    private int leftChildIndex(int index) {
        return index * 2 + 1;
    }

    private int rightChildIndex(int index) {
        return index * 2 + 2;
    }


    private void bubbleUp() {
        var index = size() - 1;
        while (index > 0 && items.get(index).key < items.get(parent(index)).key) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private void swap(int first, int second) {
        var temp = items.get(first);
        items.set(first, items.get(second));
        items.set(second, temp);
    }
    
    public int size()
    {
        return items.size();
    }
    
}

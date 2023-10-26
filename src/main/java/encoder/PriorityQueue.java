package encoder;

public class PriorityQueue <T extends Node> {
    private MinHeap<T> heap = new MinHeap();
    public void enqueue(T item)
    {
        heap.insert(item);
    }

    public T dequeue()
    {
        return heap.remove();
    }

    public int size ()
    {
        return heap.size();
    }

    public boolean isEmpty()
    {
        return heap.isEmpty();
    }
}

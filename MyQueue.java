import java.util.ArrayList;

public class MyQueue<T> implements QueueInterface<T> {
    private T[] array;      // Array to store elements
    private int front;      // Index of the front element
    private int rear;       // Index of the rear element
    private int size;       // Current number of elements in the queue
    private int capacity;   // Maximum capacity of the queue

    // Default constructor with a default capacity of 10
    public MyQueue() {
        this(10);
    }

    // Constructor with a specified capacity
    public MyQueue(int capacity) {
        this.array = (T[]) new Object[capacity];
        this.capacity = capacity;
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == capacity;
    }

    @Override
    public T dequeue() throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException("Queue is empty");
        }
        T removedElement = array[front];
        // Move the front pointer to the next element
        front = (front + 1) % capacity;
        size--;
        return removedElement;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean enqueue(T e) throws QueueOverflowException {
        if (isFull()) {
            throw new QueueOverflowException("Queue is full");
        }
        // Move the rear pointer to the next available position
        rear = (rear + 1) % capacity;
        array[rear] = e;
        size++;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(array[(front + i) % capacity]);
            if (i < size - 1) {
                sb.append("");
            }
        }
        return sb.toString();
    }

    @Override
    public String toString(String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(array[(front + i) % capacity]);
            if (i < size - 1) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }

    @Override
    public void fill(ArrayList<T> list) throws QueueOverflowException {
        if (list.size() + size > capacity) {
            throw new QueueOverflowException("Adding elements would exceed queue capacity");
        }
        for (T element : list) {
            enqueue(element);
        }
    }
}

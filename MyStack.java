import java.util.ArrayList;

public class MyStack<T> implements StackInterface<T> {
    private T[] array;      // Array to store elements
    private int top;        // Index of the top element
    private int size;       // Current number of elements in the stack
    private int capacity;   // Maximum capacity of the stack

    // Default constructor with a default capacity of 10
    public MyStack() {
        this(10);
    }

    // Constructor with a specified capacity
    public MyStack(int capacity) {
        this.array = (T[]) new Object[capacity];
        this.capacity = capacity;
        this.top = -1;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public T pop() throws StackUnderflowException {
        if (isEmpty()) {
            throw new StackUnderflowException("Stack is empty");
        }
        T poppedElement = array[top];
        array[top] = null;  // Clear the reference to the popped element
        top--;
        size--;
        return poppedElement;
    }

    public T top() throws StackUnderflowException {
        if (isEmpty()) {
            throw new StackUnderflowException("Stack is empty");
        }
        return array[top];
    }

    public int size() {
        return size;
    }

    public boolean push(T e) throws StackOverflowException {
        if (isFull()) {
            throw new StackOverflowException("Stack is full");
        }
        top++;
        array[top] = e;
        size++;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(array[i]);
            if (i < size - 1) {
                sb.append("");
            }
        }
        return sb.toString();
    }

    public String toString(String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(array[i]);
            if (i < size - 1) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }

    @Override
    public void fill(ArrayList<T> list) throws StackOverflowException {
        if (list.size() + size > capacity) {
            throw new StackOverflowException("Adding elements would exceed stack capacity");
        }
        for (T element : list) {
            push(element);
        }
    }
}

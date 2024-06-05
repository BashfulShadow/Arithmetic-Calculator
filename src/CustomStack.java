public class CustomStack<T> {
    private T[] array;
    private int size;

    @SuppressWarnings("unchecked")
    public CustomStack() {
        array = (T[]) new Object[10]; // initial capacity
        size = 0;
    }

    public void push(T value) {
        if (size == array.length) {
            resize();
        }
        array[size++] = value;
    }

    public T pop() {
        if (size == 0) {
            throw new IllegalStateException("Stack is empty");
        }
        T value = array[--size];
        array[size] = null; // avoid memory leak
        return value;
    }

    public T peek() {
        if (size == 0) {
            throw new IllegalStateException("Stack is empty");
        }
        return array[size - 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        T[] newArray = (T[]) new Object[array.length * 2];
        System.arraycopy(array, 0, newArray, 0, array.length);
        array = newArray;
    }
}

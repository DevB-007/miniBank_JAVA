package minibank;

import java.util.ArrayList;
import java.util.List;

public class TransactionStack<T> {
    private List<T> stack = new ArrayList<>();

    public void push(T item) {
        stack.add(item);
    }

    public T pop() {
        if (stack.isEmpty()) return null;
        return stack.remove(stack.size() - 1); // LIFO logic
    }

    public List<T> getHistory() {
        // Return a copy so the stack can't be modified from outside
        List<T> copy = new ArrayList<>(stack);
        java.util.Collections.reverse(copy); // Newest first
        return copy;
    }
}
package cn.ms22.model.memento;

import java.util.Stack;

public class SnapshotHolder {
    private Stack<Snapshot> snapshotStack = new Stack<>();

    public void pushSnapshot(Snapshot snapshot) {
        snapshotStack.push(snapshot);
    }

    public Snapshot pop() {
        return snapshotStack.pop();
    }

    public Snapshot peek() {
        return snapshotStack.peek();
    }

}

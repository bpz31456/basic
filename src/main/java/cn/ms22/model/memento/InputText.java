package cn.ms22.model.memento;

public class InputText {
    private StringBuilder text = new StringBuilder();
    private SnapshotHolder snapshotHolder;

    public InputText(SnapshotHolder snapshotHolder) {
        this.snapshotHolder = snapshotHolder;
    }

    public void append(String txt) {
        text.append(txt);
        createSnapshot();
    }

    private void createSnapshot() {
        snapshotHolder.pushSnapshot(new Snapshot(text.toString()));
    }

    public void undo() {
        snapshotHolder.pop();
        text = new StringBuilder(snapshotHolder.peek().getText());
    }

    public String getText() {
        return text.toString();
    }

}

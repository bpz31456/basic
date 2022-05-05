package cn.ms22.model.memento;

public class MementoContext {
    public static void main(String[] args) {
        InputText inputText = new InputText(new SnapshotHolder());
        inputText.append("abcd");
        inputText.append("efg");
        inputText.append("hij");
        inputText.append("helloc");
        System.out.println(inputText.getText());
        inputText.undo();
        System.out.println(inputText.getText());
        inputText.undo();
        System.out.println(inputText.getText());
        inputText.undo();
        System.out.println(inputText.getText());
        inputText.append("你好");
        System.out.println(inputText.getText());
        inputText.undo();
        System.out.println(inputText.getText());
    }
}

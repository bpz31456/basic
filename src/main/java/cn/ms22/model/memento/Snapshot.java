package cn.ms22.model.memento;

/**
 * 快照
 */
public class Snapshot {
    private String text;

    public Snapshot(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }
}

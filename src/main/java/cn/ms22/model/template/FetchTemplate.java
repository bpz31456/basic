package cn.ms22.model.template;

public abstract class FetchTemplate {
    protected abstract void fetch();

    protected abstract void analyse();

    public final void execute() {
        fetch();
        analyse();
    }
}

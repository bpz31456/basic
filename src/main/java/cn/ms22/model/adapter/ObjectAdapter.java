package cn.ms22.model.adapter;

/**
 * 基于对象适配
 */
public class ObjectAdapter implements ITaget {
    private Adaptee adaptee;

    public ObjectAdapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void a() {
        adaptee.c();
    }

    @Override
    public void b() {
        adaptee.d();
    }
}

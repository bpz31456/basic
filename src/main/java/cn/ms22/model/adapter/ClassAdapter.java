package cn.ms22.model.adapter;

/**
 * 基于类适配
 */
public class ClassAdapter extends Adaptee implements ITaget {
    @Override
    public void a() {
        c();
    }

    @Override
    public void b() {
        d();
    }
}

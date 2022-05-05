package cn.ms22.model.adapter;

public class AdapterContext {
    public static void main(String[] args) {
        ITaget iTaget = new ClassAdapter();
        iTaget.a();
        iTaget.b();

        iTaget = new ObjectAdapter(new Adaptee());
        iTaget.a();
        iTaget.b();
    }
}

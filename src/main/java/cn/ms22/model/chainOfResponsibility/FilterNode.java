package cn.ms22.model.chainOfResponsibility;

public  abstract class FilterNode implements Filter {
    protected FilterNode next;
    @Override
    public void validate() {
        validate0();
        if(next!=null){
            next.validate();
        }
    }
    public abstract void validate0();

}

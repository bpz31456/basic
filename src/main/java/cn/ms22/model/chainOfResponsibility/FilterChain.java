package cn.ms22.model.chainOfResponsibility;

public class FilterChain {
    private FilterNode head;
    private FilterNode tail;

    public void addFilter(FilterNode filter) {
        if (head == null) {
            head = filter;
            tail = filter;
        }else{
            tail.next = filter;
            tail = filter;
        }
    }

    public void validate(){
        FilterNode cur = head;
        if(cur!=null){
            cur.validate();
        }
    }
}

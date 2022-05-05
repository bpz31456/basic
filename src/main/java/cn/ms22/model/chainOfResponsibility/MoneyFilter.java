package cn.ms22.model.chainOfResponsibility;

public class MoneyFilter extends FilterNode {
    @Override
    public void validate0() {
        System.out.println("金额过滤器");
    }
}

package cn.ms22.model.chainOfResponsibility;

public class SafeFilter extends FilterNode {
    @Override
   public void validate0() {
        System.out.println("这是安全过滤");
    }
}

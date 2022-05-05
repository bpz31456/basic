package cn.ms22.model.chainOfResponsibility;

public class WorldFilter extends FilterNode {
    @Override
   public void validate0() {
        System.out.println("文字过滤");
    }
}

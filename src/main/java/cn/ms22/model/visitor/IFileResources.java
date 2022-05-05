package cn.ms22.model.visitor;

/**
 * 观察者模式
 */
public interface IFileResources {
    //这里设置的很巧妙，往往在事物和动作之间，是访问事物来调用动作
    void visit(IFileVisitor visitor);
}

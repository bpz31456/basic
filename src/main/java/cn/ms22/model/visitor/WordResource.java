package cn.ms22.model.visitor;

public class WordResource implements IFileResources {
    @Override
    public void visit(IFileVisitor visitor) {
        visitor.visit(this);
    }

}

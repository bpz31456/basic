package cn.ms22.model.visitor;

public class PdfResource implements IFileResources {

    @Override
    public void visit(IFileVisitor visitor) {
        visitor.visit(this);
    }

}

package cn.ms22.model.visitor;

public class Compressor implements IFileVisitor {
    @Override
    public void visit(PdfResource pdfResource) {
        System.out.println("compressor pdf");
    }

    @Override
    public void visit(WordResource pdfResource) {
        System.out.println("compressor word");
    }

    @Override
    public void visit(ExcelResource pdfResource) {
        System.out.println("compressor excel");
    }
}

package cn.ms22.model.visitor;

public class Extractor implements IFileVisitor {
    @Override
    public void visit(PdfResource pdfResource) {
        System.out.println("Extractor pdf");
    }

    @Override
    public void visit(WordResource pdfResource) {
        System.out.println("Extractor word");
    }

    @Override
    public void visit(ExcelResource pdfResource) {
        System.out.println("Extractor excel");
    }
}

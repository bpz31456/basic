package cn.ms22.model.visitor;

public interface IFileVisitor {
    void visit(PdfResource pdfResource);
    void visit(WordResource pdfResource);
    void visit(ExcelResource pdfResource);
}

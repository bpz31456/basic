package cn.ms22.model.visitor;

import java.util.ArrayList;

public class VisitorContext {
    public static void main(String[] args) {
        ArrayList<IFileResources> fileResources = new ArrayList<>();
        fileResources.add(new PdfResource());
        fileResources.add(new WordResource());
        fileResources.add(new ExcelResource());
        for (IFileResources fileResource : fileResources) {
            fileResource.visit(new Compressor());
            fileResource.visit(new Extractor());
        }

    }
}

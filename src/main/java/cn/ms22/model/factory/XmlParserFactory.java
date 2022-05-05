package cn.ms22.model.factory;

public class XmlParserFactory implements IParserFactory {
    @Override
    public IParser create() {
        return new XmlParser();
    }
}

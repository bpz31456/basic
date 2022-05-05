package cn.ms22.model.factory;

public class JsonParserFactory implements IParserFactory {
    @Override
    public IParser create() {
        return new JsonParser();
    }
}

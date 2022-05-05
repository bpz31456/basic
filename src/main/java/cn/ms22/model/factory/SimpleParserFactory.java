package cn.ms22.model.factory;


public class SimpleParserFactory {
    /**
     * 简单工厂
     * @param fileFormat
     * @return
     * @throws NotfoundParserTypeException
     */
    public IParser getParser(String fileFormat) throws NotfoundParserTypeException {
        if("xml".equals(fileFormat)){
            return new XmlParser();
        }else if("json".equals(fileFormat)){
            return new JsonParser();
        }else{
            throw new NotfoundParserTypeException();
        }
    }
}

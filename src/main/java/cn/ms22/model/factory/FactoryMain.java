package cn.ms22.model.factory;

public class FactoryMain {
    public static void main(String[] args) {
        FactoryMain factoryMain = new FactoryMain();
       /* try {
            factoryMain.parse("xml");
            factoryMain.parse("json");
        } catch (NotfoundParserTypeException e) {
            e.printStackTrace();
        }
*/
//        factoryMain.parse3("xml");
//        factoryMain.parse3("json");
        factoryMain.parse4("xml");
        factoryMain.parse4("json");
    }

    /**
     * 为工厂方法的创建提供简单工厂
     *
     * @param fileFormat
     * @throws NotfoundParserTypeException
     */
    public void parse3(String fileFormat)  {
        IParserFactory iParserFactory = ParserFactoryCaches.getParserFactoryInstance(fileFormat);
        IParser iParser = iParserFactory.create();
        iParser.parse();
    }


    /**
     * 抽象工厂
     * @param fileType
     */
    public void parse4(String fileType){
        IConfigParserFactory iConfigParserFactory = new ConfigParserFactory();
        IRuleParser ruleParser = iConfigParserFactory.createRuleParser(fileType);
        ISystemParser systemParserFactory = iConfigParserFactory.createSystemParser(fileType);
        ruleParser.parse();
        systemParserFactory.parse();
    }

    /**
     * 工厂方法的使用
     *
     * @param fileFormat
     * @throws NotfoundParserTypeException
     */
    public void parse(String fileFormat) throws NotfoundParserTypeException {
        IParserFactory parserFactory = null;
        IParser parser = null;
        if ("xml".equals(fileFormat)) {
            parserFactory = new XmlParserFactory();
        } else if ("json".equals(fileFormat)) {
            parserFactory = new JsonParserFactory();
        } else {
            throw new NotfoundParserTypeException();
        }
        parser = parserFactory.create();
        parser.parse();
    }
}

package cn.ms22.model.factory;

public class ConfigParserFactory implements IConfigParserFactory {
    @Override
    public ISystemParser createSystemParser(String fileType) {
        if("json".equals(fileType)){
            return new JsonSystemParser();
        }else if("xml".equals(fileType)){
            return new XmlSystemParser();
        }
        return null;
    }

    @Override
    public IRuleParser createRuleParser(String fileType) {
        if("json".equals(fileType)){
            return new JsonRuleParser();
        }else if("xml".equals(fileType)){
            return new XmlRuleParser();
        }
        return null;
    }
}

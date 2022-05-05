package cn.ms22.model.factory;

public interface IConfigParserFactory {
    /**
     * 生产system配置的Parser类
     */
    ISystemParser createSystemParser(String fileType);

    /**
     * 生产Rule配置的Parser类
     * @return
     */
    IRuleParser createRuleParser(String fileType);
}

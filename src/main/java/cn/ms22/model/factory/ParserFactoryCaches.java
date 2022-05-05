package cn.ms22.model.factory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 为工厂方法使用的简单工厂模式
 */
public class ParserFactoryCaches {
    public static ConcurrentHashMap<String, IParserFactory> caches = new ConcurrentHashMap();

    static {
        caches.put("xml", new XmlParserFactory());
        caches.put("json", new JsonParserFactory());
    }

    public static IParserFactory getParserFactoryInstance(String fileType) {
        return caches.get(fileType);
    }
}

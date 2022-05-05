package cn.ms22;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class JavaINteger {
    public static void main(String[] args) {
        Map<String, Date> lastLogin = Collections.synchronizedMap(new HashMap<>());
    }
}

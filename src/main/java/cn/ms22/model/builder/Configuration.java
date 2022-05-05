package cn.ms22.model.builder;

public class Configuration {
    private String name;
    private int maxTotal;
    private int maxIdle;
    private int minIdle;

    @Override
    public String toString() {
        return "Configuration{" +
                "name='" + name + '\'' +
                ", maxTotal=" + maxTotal +
                ", maxIdle=" + maxIdle +
                ", minIdle=" + minIdle +
                '}';
    }

    public Configuration(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        Configuration configuration = new Configuration.Builder()
                .setMaxIdle(30)
                .setMinIdle(40)
                .setMaxTotal(50)
                .setName("pool")
                .build();
        System.out.println(configuration);
    }

    public static class Builder {
        private String name;
        private int maxTotal;
        private int maxIdle;
        private int minIdle;

        private int DEFAULT_MAX_TOTAL = 20;
        private int DEFAULT_MAX_IDLE = 60;
        private int DEFAULT_MIN_IDLE = 20;

        private Configuration configuration = null;

        public Configuration build() {
            if (name == null) {
                throw new IllegalArgumentException("必须有配置名称。");
            }
            configuration = new Configuration(name);
            configuration.maxTotal = this.maxTotal;
            configuration.maxIdle = this.maxIdle;
            configuration.minIdle = this.minIdle;
            if (maxTotal == 0) {
                configuration.maxTotal = DEFAULT_MAX_TOTAL;
            }
            if (maxIdle == 0) {
                configuration.maxIdle = DEFAULT_MAX_IDLE;
            }

            if (minIdle == 0) {
                configuration.minIdle = DEFAULT_MIN_IDLE;
            }
            return configuration;
        }

        public Builder setMinIdle(int minIdle) {
            this.minIdle = minIdle;
            return this;
        }

        public Builder setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
            return this;
        }

        public Builder setMaxTotal(int maxTotal) {
            this.maxTotal = maxTotal;
            return this;
        }

        public Builder setName(String pool) {
            this.name = pool;
            return this;
        }
    }
}

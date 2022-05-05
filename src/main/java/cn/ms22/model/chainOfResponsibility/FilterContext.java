package cn.ms22.model.chainOfResponsibility;

public class FilterContext {
    public static void main(String[] args) {
        FilterChain filterChain = new FilterChain();
        filterChain.addFilter(new SafeFilter());
        filterChain.addFilter(new SafeFilter());
        filterChain.addFilter(new SafeFilter());
        filterChain.addFilter(new WorldFilter());
        filterChain.addFilter(new WorldFilter());
        filterChain.addFilter(new WorldFilter());
        filterChain.addFilter(new MoneyFilter());
        filterChain.addFilter(new MoneyFilter());
        filterChain.addFilter(new MoneyFilter());
        filterChain.addFilter(new MoneyFilter());
        filterChain.addFilter(new MoneyFilter());
        filterChain.validate();
    }
}

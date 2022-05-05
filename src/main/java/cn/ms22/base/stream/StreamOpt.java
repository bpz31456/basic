package cn.ms22.base.stream;

import cn.ms22.base.stream.entity.CaloricLevel;
import cn.ms22.base.stream.entity.Dish;
import cn.ms22.base.stream.entity.Trader;
import cn.ms22.base.stream.entity.Transaction;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * @author baopz
 */
public class StreamOpt {
    public static void main(String[] args) {
        test03();
    }

    public static void test03() {
        List<Dish> dishes = new ArrayList<>();
        dishes.add(new Dish("FISH", 900, "prawns", false));
        dishes.add(new Dish("FISH", 200, "salmon", false));
        dishes.add(new Dish("OTHER", 300, "french fries", true));
        dishes.add(new Dish("OTHER", 100, "rice", true));
        dishes.add(new Dish("OTHER", 200, "season fruit", true));
        dishes.add(new Dish("OTHER", 500, "pizza", true));
        dishes.add(new Dish("MEAT", 500, "pork", true));
        dishes.add(new Dish("MEAT", 300, "beef", false));
        dishes.add(new Dish("MEAT", 700, "chicken", false));
        Map<String, List<Dish>> collect = dishes.stream().collect(Collectors.groupingBy(Dish::getType));
        collect.forEach((s, dishes1) -> System.out.println(s + dishes1));
        System.out.println();
        dishes.stream().collect(Collectors.groupingBy(Dish::getType,
                Collectors.groupingBy(o -> {
                    if (o.getCalories() <= 400) {
                        return CaloricLevel.DIET;
                    } else if (o.getCalories() <= 700) {
                        return CaloricLevel.NORMAL;
                    } else {
                        return CaloricLevel.FAT;
                    }
                })
        )).forEach((s, caloricLevelListMap) -> caloricLevelListMap.forEach((caloricLevel, dishes1) -> {
            System.out.println(s + caloricLevel + dishes1);
        }));
        dishes.stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))))
                .forEach((s, dish) -> System.out.println(s + dish));
        System.out.println();
        dishes.stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))
                                , Optional::get))).forEach((s, dish) -> System.out.println(s + dish));
        dishes.stream().collect(Collectors.partitioningBy(Dish::isVegetarian,
                Collectors.groupingBy(Dish::getType)))
                .forEach((aBoolean, stringListMap) -> stringListMap
                .forEach((s, dishes1) -> System.out.println(aBoolean + s + dishes1)));
    }

    public static void test02() {
        List<String> org = new ArrayList<>();
        org.add("1");
        org.add("1");
        org.add("1");
        org.add("1");
        int sum = org.stream().map(s -> 1).reduce(0, (a, b) -> a * b);
        System.out.println(sum);
    }

    public static void test01() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
//  * (1) 找出2011年发生的所有交易，并按交易额排序（从低到高）。
        List<Transaction> collect = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparingInt(Transaction::getValue))
                .collect(Collectors.toList());
        collect.stream().forEach(System.out::println);
        System.out.println();

//  * (2) 交易员都在哪些不同的城市工作过？
        List<String> collect1 = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
        collect1.stream().forEach(System.out::println);
        System.out.println();

//  * (3) 查找所有来自于剑桥的交易员，并按姓名排序。
        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(transaction -> transaction.getTrader().getName()))
                .collect(Collectors.toList())
                .forEach(System.out::println);
        System.out.println();
//  * (4) 返回所有交易员的姓名字符串，按字母顺序排序。
        transactions.stream().map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted().forEach(System.out::println);
        System.out.println();
//  * (5) 有没有交易员是在米兰工作的？
        System.out.println(transactions.stream().anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan")));
        System.out.println();
//  * (6) 打印生活在剑桥的交易员的所有交易额。
        System.out.println(transactions.stream().filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue).reduce(Integer::sum));
        System.out.println();
//  * (7) 所有交易中，最高的交易额是多少？
        System.out.println(transactions.stream().map(Transaction::getValue).reduce(Integer::max).get());
//  * (8) 找到交易额最小的交易。
        System.out.println(transactions.stream().map(Transaction::getValue).reduce(Integer::min).get());
        System.out.println(transactions.stream().mapToInt(Transaction::getValue).min().getAsInt());
        transactions.stream().mapToInt(Transaction::getValue).min().orElse(1);
        System.out.println(IntStream.rangeClosed(1, 100).filter(value -> value % 2 == 0).sum());
        Stream.of("java", "8", "hello", "world");
       /* try {
            Files.lines(Paths.get("D:\\GithubPro\\Basic\\src\\main\\resources\\collection")).forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

        System.out.println(Stream.iterate(0, n -> n + 2).limit(10).mapToInt(value -> value).sum());
        transactions.stream()
                .collect(Collectors.maxBy(Comparator.comparingInt(Transaction::getValue)));
        transactions.stream()
                .collect(Collectors.summingInt(value -> value.getValue()));
        System.out.println(transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .collect(Collectors.joining(",")));


    }

}

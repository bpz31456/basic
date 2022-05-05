## java基础
1. 整型（byte、short、int、long）、浮点型（float、double）、字符型（char）、布尔类型（boolean）

| 数据类型名称 | 占用字符 | 默认值 | 封装类型 |占位|范围|
| :------- | :------- | :------- | :------- |---|---|
|byte|1|0|Byte|8|-128~127|
|short|2|0|Short|16|-32768~32767|
|int|4|0|Integer|32|-2<sup>32</sup>~2<sup>32</sup>-1
|long|8|0|Long|64|-2<sup>64</sup>~2<sup>64</sup>-1|
|float|4|0.0F|Float|32|1.4E-45~3.4028235E38|
|double|8|0.0D|Double|64|4.9E-324~1.7976931348623157E308|
|char|2|'\u0000'|Character|16|\u0000~\uffff,0-65535|
|boolean|1|false|Boolean|8|false、true|

float 和 double 类型的数值计算
In Java SE 15 and later, the Java programming language uses the 2019 version of the IEEE 754 Standard.
expressed: s\*m\*2<sup>(e-N+1)</sup>
> * s = +1|-1
> * m 是一个小于 2<sup>N</sup>的正整数
> * e 界于-(2<sup>k-1</sup>-2) 和2<sup>k-1</sup>-1之间

|参数|float	|double|
|---|---|---|
|N	|24	|53|
|K	|8	|11|
|Emax|	+127|	+1023|
|Emin|	-126|	-1022|

1.1. 编译阶段、运行时，自动装箱 / 自动拆箱是发生在什么阶段？

1.2.  java.lang.Integer.valueOf(java.lang.String, int) 会使用到缓存机制，那么自动装箱的时候，缓存机制起作用吗？
1.3. 为什么我们需要原始数据类型，Java 的对象似乎也很高效，应用中具体会产生哪些差异？
1.4. 阅读过 Integer 源码吗？分析下类或某些方法的设计要点。
1.5. 基础类型缓存
* Boolean 类型缓存true\false
* Integer 缓存 -128 ~ 127
* Short 缓存 -128 ~ 127
* Byte 缓存所有
* Character 缓存'/u0000' ~ '/u007f'
* 自动装箱拆箱语法糖在性能敏感程序需要尽量避免
* `java.lang.Integer.IntegerCache` 描述了缓存数值并注释了需要修改缓存的vm设置`-XX:AutoBoxCacheMax=<size>`
* `java.lang.Character.CharacterCache` 描述了char 的缓存 0~128
* `java.lang.Byte.ByteCache`
* `java.lang.Short.ShortCache`

2. 类型之间转换

|to\from|byte|short|char|int|long|float|double|
|---|---|---|---|---|---|---|---|
|byte|-|(byte)|(byte)|(byte)|(byte)|(byte)|(byte)|
|short|-|-|(short)|(short)|(short)|(short)|(short)|
|char|-|(char)|-|(char)|(char)|(char)|(char)|
|int|-|-|-|-|(int)|(int)|(int)|
|long|-|-|-|-|-|(long)|(long)|
|float|-|-|-|-|-|-|(float)|
|double|-|-|-|-|-|-|-|

>需要说明一下的是，在long（8个字节）转为float(4个字节)的时候为什么没有报错，因为float\double的值是通过公式计算出来的，表达的范围包含了long的值范围


3. final 并不等同于 immutable
   final 表示引用不被修改，immutable表达的是引用和值都不能被修改

4. 如何实现immutable的类
* 将class申明final不被继承
* 将属性定义为private、final,不实现setter方法
* 通常构造对象时，成员变量使用深度拷贝来初始化，而不是直接赋值，这是一种防御措施，因为你无法确定输入对象不被其他人修改。
* 如果确实需要实现 getter 方法，或者其他可能会返回内部状态的方法，使用 copy-on-write 原则，创建私有的 copy。

5. 什么是防御性拷贝（defensive copy）
```
// Broken "immutable" time period class
public final class Period {
    private final Date start;
    private final Date end;
    
    /**
     * @param  start the beginning of the period
     * @param  end the end of the period; must not precede start
     * @throws IllegalArgumentException if start is after end
     * @throws NullPointerException if start or end is null
     */
    public Period(Date start, Date end) {
        if (start.compareTo(end) > 0)
            throw new IllegalArgumentException(
                start + " after " + end);
        this.start = start;
        this.end   = end;
    }

    public Date start() {
        return start;
    }

    public Date end() {
        return end;
    }
    ...    // Remainder omitted
}

// Attack the internals of a Period instance
Date start = new Date();
Date end = new Date();
Period p = new Period(start, end);
end.setYear(78);  // Modifies internals of p!
```
> 这里的start和end是Date引用类型的参数，直接传递进构造函数，之后可能修改引用内部的值，所以需要设计防御性赋值
```java
// Repaired constructor - makes defensive copies of parameters

public Period(Date start, Date end) {
    this.start = new Date(start.getTime());
    this.end   = new Date(end.getTime());

    if (this.start.compareTo(this.end) > 0)
      throw new IllegalArgumentException(
          this.start + " after " + this.end);
}
```
> start()和 end()也可能出现类似问题
```java
// Repaired accessors - make defensive copies of internal fields
public Date start() {
    return new Date(start.getTime());
}

public Date end() {
    return new Date(end.getTime());
}

```

6. 什么是copy-on-write
   写时复制，在读多写少时只用，原理是，在读的时候，程序直接读取原始数据，如果需要写的时候，就深拷贝一次，在堆中会另外建立一个新的对象，
   做写操作，写完后，再把原引用指向新的对象。使用场景如，黑名单、白名单

7. 深拷贝、浅拷贝
* 引用赋值是把新创建一个引用指向原对象，即是，两个引用都指向同一块内存空间，原对象发生改变，新引用的数据也会发生改变。
* 浅拷贝是新建一个引用，把原引用的值拷贝过来，对象利用引用赋值的方式，Object对象自带的clone方法就是这种方式
* 深拷贝是重新创建一个对象，把原对象的属性状态等复制过来，两个对象的在堆中是独立的，相互之间数据不影响

8. 深拷贝的实现,//TODO
* 实现`java.lang.Cloneable`接口，并通过public访问权限重写Object.clone()方法，并且每个被引用类型都需要实现clone方式才行
```java
public class Student implements Cloneable {
    private Course course;
    private String name;

    public Student(Course course, String name) {
        this.course = course;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "course=" + course +
                ", name='" + name + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student(Course course) {
        this.course = course;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Student student = (Student) super.clone();
        student.setCourse((Course) course.clone());
        return student;
    }

}
```
需要显示在Student.clone()方法中执行其他引用类型（course类）的clone方法
* 要调用clone方法`java.lang.Cloneable`这个接口中已经说明了需要实现为`public`方法

9. java对象的生命周期  
   [jvm 规范中loading、linking、initial](https://docs.oracle.com/javase/specs/jvms/se17/html/jvms-5.html)

10. 强引用、弱引用、软引用、虚引用
    引用的不同类型，主要表现在程序对对象的访问可达性状态的不同，以及对垃圾收集的行为上影响的不同。
* 强引用，常见的对象的引用，强引用对象不会被回收，就算抛出OOM错误也不会被回收，只有在对象超过作用域以及制定引用为null才会被GC回收
> 对象在线程中不用通过各种引用（`java.lang.ref.PhantomReference` `java.lang.ref.SoftReference` `java.lang.ref.WeakReference`）
> 访问能直接访问的对象状态，视为强可达状态，如new的对象可以直接访问的可达性

* 软引用（SoftReference）,弱引用，比强引用弱化一些的引用，JVM在判断内存不足时，才会试图回收软引用对象，在抛出OOM之前会先清理SoftReference对象
> 只能通过软引用（`java.lang.ref.SoftReference`）访问的对象的可达性状态，视为软可达

* 弱引用（WeakReference），弱引用，更弱的一种引用类型，不能是对象豁免垃圾收集，提供在访问弱引用状态的对象的一种途径，可以用来建立一种对象映射关系，比如：缓存实现，
  如果试图获取对象时，对象还在就返回对象，如果对象不在就重新实例化
> 只能通过弱引用（`java.lang.ref.WeakReference`）访问的对象的可达性状态，视为弱可达，非常接近finalize的状态

* 虚引用（幻想引用PhantomReference），最弱的引用类型，监控对象的创建和销毁
> 只能通过幻想引用（`java.lang.ref.PhantomReference`）访问的对象的可达性状态，视为幻象可达，这种状态，执行了finalize方法后的状态

* 各种引用（继承至`java.lang.ref.Reference`）的使用
> 1. java.lang.ref.SoftReference，通过java.lang.ref.SoftReference#get方法访问弱引用对象
> 2. java.lang.ref.WeakReference，通过java.lang.ref.Reference#get访问
> 3. java.lang.ref.PhantomReference，幻想引用在代码实现上就是 public T get() { return null; }，所以需要访问时，需要通过引用队列
```java
    public PhantomReference(T referent, ReferenceQueue<? super T> q) {
        super(referent, q);
    }
	
```
* 各种引用之间的转换，强转弱，强转软，软转强，软转强，软转弱，//TODO

* 诊断引用类型GC情况
  jdk8 打印日志 -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintReferenceGC
  jdk9 对日志有了改进//TODO

11. 访问控制权限

|位置\权限|private|default|protectd|public|
|---|---|---|---|---|
|同类|*|*|*|*|
|同包|-|*|*|*|
|子类|-|-|*|*|
|其他位置|-|-|-|*|

12. String StringBuffer StringBuilder
* String String是不可变类，immutable，final class，所有属性都是final，所以拼接、截取都需要产生新的对象，效率会产生影响
* StringBuffer 是线程安全的可变字符序列，可以appen、add字符串，解决了String的拼接、截取字符串的问题，在单线程程序中有额外的性能开销
* StringBuilder 和StringBuffer一样，不是线程安全的
* String 的不可变类设计本身就是线程安全的设计方案，没有可以修改数据的入口
* StringBuffer 在方法申明中添加了synchronized 关键字，简单、明了的实现了线程安全
* StringBuilder 内部和StringBuffer都是继承至AbstractStringBuilder类，内部byte数组实现（java9+，在java8-是char数组），初始化数组长度16，
  如果预先知道大概长度，可以预设长度减少扩容次数，如果append长度不够就需要扩容，通过System.arrayCopy方法添加信息
* String 内部由byte数组构成（java9+，在java8-是char数组）

13. JVM对象缓存机制，String、Integer等
    jvm为了提高效率，提供了内部缓存机制，在java1.6时，String可以通过String.intern()方法提供缓存，对象缓存到堆中的方法区的永久代中，不会被fullgc，容易导致内存溢出
    在java8中的内存结构（主要得益于G1的垃圾收集器）发生改变，String缓存到系统提供的元数据区中存放，其中存放的数量不断在改变，截止java17已经可以存放65536个String对象

14. String 类型演化 //merge

15. System.arrayCopy() Arrays.copyOf()
    Arrays.copyOf()是数组工具类提供的方法，内部是由System.arrayCopy() native方法实现，System.arrayCopy方法属于浅复制范畴


### 基础语法

### 反射
1. java语言概述
* java是静态强类型编程语言，体现在语言类型信息是在编译器检查，同时java的反射机制也提供了动态语言类型的能力
  * 反射本身可以扩展类的功能、访问类的成员变量、改变运行时状态等
  * 反射涉及到性能开销，在性能敏感的程序中尽量避免使用反射
  * 反射涉及到需要访问private权限成员变量，所以涉及到安全问题
  * 反射本身在java版本中限制不同，如：1.8以前可以直接使用`java.lang.reflect.AccessibleObject#setAccessible(boolean)`,
  * `java.lang.Class`，所有引用类型（类、接口、包装类、数组、枚举）都有对应的immutable 的Class类，是反射编程的入口，
  可以创建class，实例并且可以访问内部成员变量，方法，父类接口等
* 对象得到Class，声明得到Class，全路径得到Class，包装类得到Class
```java
//对象得到Class
"String".getClass();
//声明得到类.class语法
Class c = boolean.class; 
//全路径限定字符得到Class Class.forName("");
Class c = Class.forName("com.duke.MyLocaleServiceProvider");
Class cDoubleArray = Class.forName("[D");
Class cStringArray = Class.forName("[[Ljava.lang.String;");
//包装类.TYPE()
Class c = Double.TYPE;
Class c = Void.TYPE;
```
1.6. `java.lang.Class`创建对象
* 构造器创建对象
* Class.newInstance创建对象
```java
public class ClassTrouble {
    public static void main(String... args) {
        try {
            Class<?> c = Class.forName("cn.ms22.proxy.Cls");
            Constructor<?>[] constructors = c.getConstructors();
            for (Constructor<?> constructor : constructors) {
                try {
				//1.构造器 创建对象
                    constructor.newInstance();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            //2.Class 默认构造器创建对象
            c.newInstance(); 
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException x) {
            x.printStackTrace();
        }
    }

}

class Cls {
    public Cls() {
        System.out.println("hello");
    }
}
```
1.7. `java.lang.reflect.Field`设置属性值
* 访问控制符权限如果不够，需要设置setAccessible(true)
```java
public class FieldDeclaration {
    public static void main(String[] args) {
        try {
            Student student = Student.class.newInstance();
            Class<? extends Student> aClass = student.getClass();
            Field sex = aClass.getField("sex");
            sex.set(student, "nv");
            Field name = aClass.getDeclaredField("name");
            //这里name是private 修饰符，所以需要设置修改权限
            name.setAccessible(true);
            name.set(student, "zhangsan");
            Field age = aClass.getField("age");
            age.set(student, 19);
            age.setInt(student, 19);
            System.out.println(student);
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}

class Student {
    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';
    }

    private String name;
    public String sex;
    public int age;
}
```
1.8. Method获取访问控制符、获取参数列表、获取方法类型信息、方法执行
```java
public class MethodsDeclaration {
    public static void main(String... args) {
        try {
            Method print = Printer.class.getMethod("print", String.class);
            print.invoke(new Printer(), "hello methods");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    static class Printer {
        public void print(String info) {
            System.out.println(info);
        }
    }
}
```
1.9. Constructors 获取访问控制权，获取参数，构造实例
```java
public class ConstructorDeclaration {
    public static void main(String[] args) {
        Constructor<?>[] declaredConstructors = Exe.class.getDeclaredConstructors();
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            if(Modifier.isPrivate(declaredConstructor.getModifiers())){
                try {
                    Exe exe = (Exe) declaredConstructor.newInstance("command");
                    exe.print();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Exe {
        private String command;

        private Exe(String command) {
            this.command = command;
        }

        public void print(){
            System.out.println(command);
        }
    }
}
```

在java9之后设计到模块Jigsaw后，就需要使用~~open关键字做权限管控~~。
2. 动态编译//todo
3. 动态代理
* 动态代理是方便程序运行时构建代理、动态代理方法的运行机制，常见的场景如包装RPC调用，面向切面编程AOP
* 动态代理可以由java语言自带的反射机制实现，也可以使用更高性能的运行时字节码操作实现如cglib（内部使用asm）、asm
* java反射机制，是java提供的运行时内省机制，通过反射可以直接操作类或对象，如获取类的定义、属性、方法，构造对象，甚至可以修改类定义

4. cglib
* CGLIB是一个强大的、高性能的代码生成库。CGLIB底层使用了ASM（一个短小精悍的字节码操作框架）来操作字节码生成新的类。
* cglib 不能代理final类，不能代理static类
> 1. `net.sf.cglib.proxy.Enhancer`,cglib的动态代理主要类
> 2. `net.sf.cglib.proxy.Enhancer#setCallback`,`net.sf.cglib.proxy.Callback`接口需要实现才能诊断不同业务作出相应的代理

4.0.
* 类加载，`net.sf.cglib.proxy.Dispatcher`每次使用对象时，都需要重新创建对象`net.sf.cglib.proxy.LazyLoader`,如果不存在就会新建并缓存
```java
//每次调用getMathSchedule() 都会使用LazyLoader缓存的对象，每次使用过getEnglishSchedule()，都会重新生成新的对象
public class Student {
    private Schedule mathSchedule;
    private Schedule englishSchedule;

    public Schedule getMathSchedule() {
        return mathSchedule;
    }

    public Student() {
        this.mathSchedule = mathScheduleGenerator();
        this.englishSchedule = englishScheduleGenerator();
    }

    private Schedule englishScheduleGenerator() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Schedule.class);
        enhancer.setCallback((Dispatcher) () -> new Schedule("english", Calendar.getInstance().getTimeInMillis()));
        return (Schedule) enhancer.create();
    }

    private Schedule mathScheduleGenerator() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Schedule.class);
        enhancer.setCallback((LazyLoader) () -> new Schedule("match", Calendar.getInstance().getTimeInMillis()));
        return (Schedule) enhancer.create();
    }

    public Schedule getEnglishSchedule() {
        return englishSchedule;
    }

    static class Schedule {
        private Long time;
        private String name;
        public Schedule(){}

        public Schedule(String name, Long time) {
            this.time = time;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Schedule{" +
                    "time='" + time + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
```
* 空回调`net.sf.cglib.proxy.NoOp`通常在一些需要过滤器的时候使用

* 方法拦截器`net.sf.cglib.proxy.MethodInterceptor`
```java
    public void methodInterceptor() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Test.class);
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
        Object obj = null;
        if (method.getName().equals("print")) {
        System.out.println("before");
        obj = methodProxy.invokeSuper(o, objects);
        System.out.println("end");
        }
        return obj;
        });
        Test test = (Test) enhancer.create();
        test.print();
        }
```
* 执行处理器`net.sf.cglib.proxy.InvocationHandler` //todo 遇到无限循环导致栈溢出

* `net.sf.cglib.proxy.FixedValue`,返回固定值，还没有弄清楚这么使用//todo

* `net.sf.cglib.proxy.CallbackFilter` 根据`net.sf.cglib.proxy.CallbackFilter#accept`返回的integer值确定callback下标对应的callback回调类
```java
  Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Test.class);
        Callback[] callbacks = new Callback[]{(FixedValue) () -> 2
                ,(InvocationHandler) (proxy, method, args) -> {
             out.println(proxy.getClass().getName());
             return null;
        }};
        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackFilter(method ->{
            out.println(method.getName());
            return 1;
        });
        Test t = (Test) enhancer.create();
        t.print();
```

4.1 java proxy
java动态代理工具类，实现在运行期间动态创建对象
1. 定义接口 Hello
2. 实现接口 HelloImpl
3. 实现 InvocationHandler 的类为包装类MyInvocationHandler
4. 根据HelloImpl Class的ClassLoader 以及 interface 以及MyInvocationHandler包装行为创建新的代理类
```java
public class JavaProxy {
    public static void main(String[] args) {
        Hello hello = new HelloImpl();
        MyInvocationHandler handler = new MyInvocationHandler(hello);
        Hello proxyHello =
                (Hello) Proxy.newProxyInstance(HelloImpl.class.getClassLoader(),
                        HelloImpl.class.getInterfaces(),
                        handler);
        proxyHello.sayHello();
    }
    interface Hello{
        void sayHello();
    }

    static class HelloImpl implements Hello{

        @Override
        public void sayHello() {
            System.out.println("hello proxy");
        }
    }

    static class MyInvocationHandler implements InvocationHandler{
        private Object target;

        public MyInvocationHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("invocation say Hello");
            return  method.invoke(target,args);
        }
    }
}
```

5. 动态代理解决了什么问题
* 代理机制是对调用目标进行包装，这样我们对目标代码的调用不是直接发生，而是通过代理实现，这样可以让调用者和实现者之间解耦，比如在RPC调用时
  框架内部寻址，序列化反序列法都不是业务需要关心的，这部分实现交给代理类处理
* netty内部的序列化反序列化实现//TODO
6. 动态代理的演进
*  代理由静态代理到动态代理，是一个演进的过程，由rmic手动构建stub文档，RMI动态构建stub文档
* RMI 流程[JAVA1.8Rmi官方文档](https://docs.oracle.com/javase/tutorial/rmi/index.html)
> 1. 定义一个远程服务接口，并extends `java.rmi.Remote`
> 2. 实现这个接口，并extends `java.rmi.server.UnicastRemoteObject`
> 3. 启动注册服务，并绑定地址和端口
> 4. 实现clent并绑定请求地址，提交请求
```java
//服务接口
public interface UserServer extends Remote {
    public String getUserInfo(String username) throws RemoteException;
}

//实现接口
public class UserServerImpl extends UnicastRemoteObject implements UserServer {
    public UserServerImpl() throws RemoteException {
    }

    @Override
    public String getUserInfo(String username) throws RemoteException {
        Student student = new Student();
        student.setAge(18);
        student.setName("zhangsan");
        student.setSex("nv");
        return student.toString();
    }
}

//注解服务
public class Register {
    public static void main(String[] args) {
        try {
            UserServer userServer = new UserServerImpl();
            LocateRegistry.createRegistry(8888);
            Naming.bind("rmi://localhost:8888/userServer",userServer);
            System.out.println("Register Running.");
        } catch (RemoteException | AlreadyBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}

//请求服务
public class UserClient {
    public static void main(String[] args) {
        try {
            UserServer userServer = (UserServer) Naming.lookup("rmi://localhost:8888/userServer");
            String info = userServer.getUserInfo("zhangsan");
            System.out.println("info:" + info);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }
}

//辅助类

public class Student implements Serializable {
    private String name;
    private int age;
    private String sex;
//getter、setter
}
```

7. jdk和cglib的区别
* jdk Proxy中只能动态代理接口，对于普通类则不能代理，cglib可以代理普通类和接口

### RMI和WebService的区别
1. RMI 是java提供的远程过程调度基于tcp/ip协议传输高效，性能优异，但无法跨语言
2. WebService是基于Soap协议，协议本生是基于http协议，传输体是xml格式，易读，可跨平台，性能相较RMI有差异

### 异常
* 基础类 Throwable ,Exception,Error类继承自Throwable
* Error类是程序错误，程序无法恢复。
* Exception是异常，分为显示抛出异常和运行时异常，往往可能捕获并处理，使程序可以继续运行
* RuntimeException，是运行时异常，不需要显示抛出，在程序运行期间如有异常会自动抛出，
  子类NullPointException、ArrayIndexOutOfBoundsException、ClassCastException（类型转换不兼容时抛出的异常）
* 显示抛出异常，发送在程序编译阶段，程序编码时必须显示try/catch的异常行为，ClassNotFoundException、IOException、SQLException
* NoClassDefFoundError 和 ClassNotFoundException 有什么区别？
> 1. NoclassDefFoundError是在编译期间没有发现定义的Class抛出的错误
> 2. ClassNotFoundException是在程序运行期间，如使用了如：Class.forName("xx.class")需要监测的异常
* throw throws throwable 的区别？
> 1. throw是一个关键字，在方法体里面显示抛出一个异常，提供给程序捕获
> 2. throws是一个关键字，是在方法定义的时候显示定义方法需要抛出的异常类型
> 3. Throwable是一个类，在java中所有的error和Exception的超类
* final finally finalize的区别
> 1.final是一个关键字，可以修饰类，变量，方法，修饰类表明类不能被继承，不能修饰被abstract修饰的类，不能修饰接口
> 修饰变量，表明变量是不能修改值或不能修改变量指向的引用（List等，是可以修改List里面的值）  
> 2.finally关键字是在异常处理过程中的finally块，用于资源回收的作用
> finalize，是一个Object类中的方法，是在内存回收之前时，类需要执行的方法，但不要用这种方式实现资源回收，因为类的回收时间是不能确定的
* 了解try/catch的运行机制，包括云return连用时候的结果返回 //`TODO`
* try with resources 机制以及multiple catch机制
> try-with-resources java1.7机制是为了解决关闭资源编码繁琐的问题出现的机制，任何实现了`java.lang.AutoCloseable`,以及`java.io.Closeable`接口的对象都可以使用，
> 通过程序自动关闭回收资源如socket、BufferedReader、RandomAccessFile等
```java
static String readFirstLineFromFile(String path) throws IOException {
    try (BufferedReader br =
                   new BufferedReader(new FileReader(path))) {
        return br.readLine();
    }
}
```
> 在java7及之后可以在catch块中可以同时捕获多种异常,这就是multiple-catch
```java
catch (IOException|SQLException ex) {
    logger.log(ex);
    throw ex;
}
```
* Throw early, catch late原则，异常需要及时抛出，延迟捕获，异常需要在异常发生的地方抛出，不然可能会导致相关变量处在异常中带来定位麻烦，catch-late是说如果不知道如何处理catch的异常就往外层继续抛
* Reactive Stream反应式编程中异常处理方式？//`TODO`
* 注意事项
> 1. 不要抛出Exception这样的超类，不然程序无法具体定位错误并处理，需要定义专门的Exception子类来抛出同类型异常
> 2. 不要生吞异常，不然程序会很难定位错误信息
> 3. 自定义异常的初衷是能够使程序能够继续运行，在自定义异常时需要考虑是否需要定义为Checked Exception
> 4. lambda表达式中functional编程是不支持Checked Exception的
> 5. 捕获异常代价是很高，因为java每实例化一个Exception时，都会进程栈快照，发生非常平凡就会带来很大开销，
     不要使用try-catch语句块来做程序分支，可以使用if...else...等程序分支语句
### 注解

## lambda
* 流处理，上一个流的输出，变为下一个流的输入
* java 8 中行为参数化，把一个方法或一段代码通过参数的形式传递进入方法参数中
    * 可以因对需求的不断变化
* 可以在多核处理器中的处理并行编程
* 让方法变为一等公民，是steam编程的基础
* lambda-匿名函数
* stream api，内部迭代，相比的是for-each的外部迭代
* stream api,关注的是将数据分片，不建议使用synchnized方式编程，synchnized是关注协调处理
* collection主要是存储和访问，而stream关注处理
* 默认方法，主要解决的是刚容易改进接口，给接口添加一个新方法，所有实现类都需要重新实现方法，为了解决这个问题，提供了在接口
  中实现默认方法，一个类实现多个接口，多个接口中都有默认方法，那么这个类就变为了多重继承
* Optional<T> 可以避免空指针异常
* 模式匹配
### 行为参数化
> 就是一个方法接受多个不同的行为作为参数，并在内部使用它们， 完成不同行为的能力
* 行为参数化可让代码更好地适应不断变化的要求，减轻未来的工作量
* 传递代码，就是将新行为作为参数传递给方法,为接口声明许多只用一次的实体类而造成的啰嗦代码，在Java 8之前可以用匿名类来处理。
* Java API包含很多可以用不同行为进行参数化的方法，包括排序、线程和GUI处理(监听器)
* 行为参数化，类似策略模式，一个是传递进入不同行为的策略类型，并在内部执行同名的不同的逻辑，而行为参数化，就是传递具体的不同行为的逻辑
    * 不同行为的逻辑也需要统一的方法入口，就是函数式接口
    * 函数式接口中通常使用泛型来定义传入和输出的值类型
* 行为参数化的好处在于你可以把迭代要筛选的集合的逻辑与对集合中每个元素应用的行为区分开来
* 谓词，就是一个行为，行为参数化，匿名类也可以实现
### lambda 表达式
* 可以让你很简洁地表示一个行为或传递代码，可以把Lambda表达式看作匿名功能
    * 它基本上就是没有声明名称的方法，但和匿名类一样，它也可以作为参数传递给一个方法
    * 可能还有一串异常列表
    * Java 8之前做不了的事情， Lambda也做不了，lambda让代码变得更清晰、更灵活，
    * 参数，箭头，主体，表达式就是返回值
#### 函数式接口
* 函数式接口
    * 只定义了一个抽象方法的接口，不管接口定义了多个默认方法、静态方法，只要接口只定义了一个抽象方法都是函数式接口
    * 接口继承了其他的接口的抽象方法加上自身的抽象方法后也不是函数式接口
    * 接口没有定义抽象方法，也不是函数式接口
    * 函数式接口很有用，抽象方法的签名可以描述Lambda表达式的签名，可以自己定义函数式接口，但java8后为了应对不同的lambda表达式api提供了多种函数式接口
    * 只有在接收函数式接口的地方才能使用lambda表达式
    * lambda表达式允许直接内联，为函数式接口提供实现，将整个表达式作为参数传递
* 函数描述符
    * 函数式接口的抽象方法的签名基本上就是Lambda表达式的签
    * 函数式接口中的抽象方法叫作函数描述符
    * 可以帮助lambda表达式做类型推断
* @FunctionalInterface,标注一个接口为函数式接口，类似@Override,不是必须的，但会是一个有效的提示
* java 自带的函数式接口在包java.util.function中
    * java.util.function.Predicate<T>
        * 接口定义了一个名叫test的抽象方法，它接受泛型T对象，并返回一个boolean。
    * java.util.function.Consumer<T>
        * 定义了一个名叫accept的抽象方法，它接受泛型T的对象，没有返回（ void）
    * java.util.function.Function<T, R>
        * 接口定义了一个叫作apply的方法，它接受一个泛型T的对象，并返回一个泛型R的对象
    * java.util.function.Supplier<T>
        * 提供了一个get方法，返回一个T泛型对象
    * 原始类型特化
        * 为了避免装箱操作，api提供了一些原始类型的特例化实现，如java.util.function.IntConsumer，java.util.function.IntFunction等
* 环绕执行模式，可以配合lambda提高灵活性和可重用性
    * 常见的模式就是打开一个资源，做一些处理，然后关闭资源，文件的读取以及之后的文件资源的关闭
* 泛型只能绑定引用类型
* 任何api自带的函数式接口都不允许抛出受检异常（ checked exception）
    * 需要Lambda表达式来抛出异常
        * 定义一个自己的函数式接口
        * Lambda包在一个try/catch块
* lambda表达式的类型是上下文推断出来的
* 闭包就是一个函数的实例，且它可以无限制地访问那个函数的非本地变量
* 使用局部变量
    * Lambda可以没有限制地捕获（也就是在其主体中引用）实例变量和静态变量。但局部变量必须显式声明为final
    * 实例变量都存储在堆中，而局部变量则保存在栈上，如果允许捕获可改变的局部变量，就会引发造成线程不安全的新的可能性
    * 这一限制不鼓励你使用改变外部变量的典型命令式编程模式
    * Java 8的Lambda和匿名类可以做类似于闭包的事情，
        * 它们可以作为参数传递给方法，并且可以访问其作用域之外的变量。但有一个限制：它们不能修改定义Lambda的方法的局部变量的内容。
#### 方法引用和构造函数引用
* 方法引用，lambda表达式的语法糖
    * 方法引用可以被看作仅仅调用特定方法的Lambda的一种快捷写法，让阅读更加自然
    * 需要使用方法引用时，目标引用放在分隔符::前，方法的名称放在后面
    * 针对仅仅涉及单一方法的Lambda的语法糖
        * (Apple a) -> a.getWeight() Apple::getWeight
        * (String s) -> System.out.println(s) System.out::println
        * (str, i) -> str.substring(i) String::substring
    * 构建方法引用
        * 指向静态方法的方法引用，如Integer.parseInt(),Integer::parseInt
        * 执行任意类型实例方法的方法引用,如String的toUpperCase()方法,String::toUpperCase，(String s) -> s.toUpperCase()的语法糖
        * 指向现有对象的实例方法的方法引用，调用局部变量的一个方法expensiveTransaction::getValue，()->expensiveTransaction.getValue()的语法糖
* 构造函数引用
    * BiFunction<String, Integer, Apple> c3 = Apple::new;//自带有两个参数的方法的函数式接口
    * TriFunction<Integer, Integer, Integer, Color> colorFactory = Color::new;//自定义的带有三个参数的函数式接口
      public interface TriFunction<T, U, V, R>{
      R apply(T t, U u, V v);
      }
#### lambda演进过程
* 传递代码，在方法参数中new一个实例对象，传递进入，执行实例代码中的方法
* 使用匿名内部类，通过临时构建一个内部类来实现具体代码
* 使用lambda表达式简化内部类的语法，是一个语法糖
* 方法引用，再次都lambda表达式简化
* 复核lambda表达式，java api中的函数式接口定义了一些默认方法default
    * 通过默认方法来做谓词的复核实现更加复制的逻辑
### 函数式数据处理
#### 流
* 流是Java API的新成员，它允许你以声明性方式处理数据集合
    * 代码是声明式方式编写，说明需要完成什么任务而不是说如何实现
    * 可以把几个基础的操作链接起来，达到负责的数据处理流水线
    * 声明式，更加简洁、更易读
    * 可复用，更加灵活
    * 可并行，性能更好
* 流，从支持数据处理的元生成的元素序列
    * 元素序列，就像集合，集合是存储和访问的数据集合，而流是计算
    * 源，流会使用提供数据的源，如列表，流的顺序与源中元素顺序一致
    * 数据处理操作，函数式操作中的常用操作，filter、map、reduce、find、match、sort
    * 流水线，很多流操作会返回另一个流，从而把操作链接起来，形成流水线
    * 内部迭代，流操作是做的内部迭代
* 流与集合
    * 集合是有限的，固定的，而流是实时的，按需的
    * 流只能被消费一次
    * 集合是外部迭代（并行很困难），流是内部迭代（迭代被filter、map、sorted等操作抽象了，同时可以透明并行处理）
* 流操作，中间操作和终端操作
    * 如果没有终端操作执行，中间操作是不会执行的
    * 终端操作，会执行并关闭流，返回一个非流的值
    * 中间操作，会产生流水线，一遍终端操作处理，返回另一个流
* 使用流
    * 数据源、中间操作、终端操作
#### 使用流
* stream操作类型
* 筛选和切片
    * java.util.stream.Stream.filter
    * java.util.stream.Stream.distinct
    * java.util.stream.Stream.limit
    * java.util.stream.Stream.skip
* 映射（map），类似转换，但是确实新建的一个类型，不是在原有的数据上修改
    * java.util.stream.Stream.map
    * 流的扁平化java.util.stream.Stream.flatMap，可以把流中的每一个值都换成另一个流，并把所有的流连接起来为新的流
* 查找和匹配
    * java.util.stream.Stream.allMatch
    * java.util.stream.Stream.anyMatch
    * java.util.stream.Stream.noneMatch
    * java.util.stream.Stream.findFirst
    * java.util.stream.Stream.findAny
        * java.util.Optional,本身是一个容器类，处理是否null问题引入的类，表示存在或不存在，提供一些操作方便调用
* 归约（reduce）
    * 函数式编程语言称为折叠，如数字累加，最大值，最小值，进行内部迭代做处理
    * int sum = numbers.stream().reduce(0, (a, b) -> a + b);
    * 最大值和最小值
* 实例
    * (1) 找出2011年发生的所有交易，并按交易额排序（从低到高）。
    * (2) 交易员都在哪些不同的城市工作过？
    * (3) 查找所有来自于剑桥的交易员，并按姓名排序。
    * (4) 返回所有交易员的姓名字符串，按字母顺序排序。
    * (5) 有没有交易员是在米兰工作的？
    * (6) 打印生活在剑桥的交易员的所有交易额。
    * (7) 所有交易中，最高的交易额是多少？
    * (8) 找到交易额最小的交易。
```java
public static void main(String[]args){
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
        List<Transaction> transactions = Arrays.asList(
        new Transaction(brian, 2011, 300),
        new Transaction(raoul, 2012, 1000),
        new Transaction(raoul, 2011, 400),
        new Transaction(mario, 2012, 710),
        new Transaction(mario, 2012, 700),
        new Transaction(alan, 2012, 950)
        );
        }

public class Trader{
    private final String name;
    private final String city;
    public Trader(String n, String c){
        this.name = n;
        this.city = c;
    }
    public String getName(){
        return this.name;
    }
    public String getCity(){
        return this.city;
    }
    public String toString(){
        return "Trader:"+this.name + " in " + this.city;
    }
}
public class Transaction{
    private final Trader trader;
    private final int year;
    private final int value;
    public Transaction(Trader trader, int year, int value){
        this.trader = trader;
        this.year = year;
        this.value = value;
    }
    public Trader getTrader(){
        return this.trader;
    }
    public int getYear(){
        return this.year;
    }
    public int getValue(){
        return this.value;
    }
    public String toString(){
        return "{" + this.trader + ", " +
                "year: "+this.year+", " +
                "value:" + this.value +"}";
    }
}
```
#### 数值流
> IntStream、 DoubleStream和LongStream,避免装箱成本
* mapToInt,将一般对象的流转换为特定的int类型的流，避免装箱成本
    * transactions.stream().mapToInt(Transaction::getValue).min().getAsInt()
* 将特定类型的流转换为一般对象的流
    * Stream<Integer> stream = intStream.boxed()
* 生成数据类型的区间流
#### 创建流
* 由值直接创建
    * Stream.of("java","8","hello","world");
* 由数组创建
    * Arrays.stream(numbers)
* 由文件生成，java.nio.file.Files很多静态方法可以生成流
    * Files.lines(Paths.get("D:/")).forEach(System.out::println);
* 函数生成无限流
    * Stream.iterate和Stream.generate
#### java.util.stream.Collectors收集器工具
* 最大值和最小值maxBy
    * transactions.stream()
      .collect(Collectors.maxBy(Comparator.comparingInt(Transaction::getValue)));
* 汇总summingInt
    * transactions.stream()
      .collect(Collectors.summingInt(value -> value.getValue()));
* 连接字符串joining
    * transactions.stream()
      .map(transaction -> transaction.getTrader().getName())
      .collect(Collectors.joining(","))
* 分组groupingBy
    * Map<String, List<Dish>> collect = dishes.stream().collect(Collectors.groupingBy(Dish::getType))
* 二级分组，groupingBy中嵌套groupingBy
    * dishes.stream().collect(Collectors.groupingBy(Dish::getType,
      Collectors.groupingBy(o -> {
      if(o.getCalories()<=400){
      return CaloricLevel.DIET;
      }else if(o.getCalories()<=700){
      return CaloricLevel.NORMAL;
      }else {
      return CaloricLevel.FAT;
      }
      })
      )).forEach((s, caloricLevelListMap) -> caloricLevelListMap.forEach((caloricLevel, dishes1) -> {
      System.out.println(s+caloricLevel+dishes1);
      }));
* groupingBy配合counting()、maxBy()
    * dishes.stream()
      .collect(Collectors.groupingBy(Dish::getType,
      Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)) ))
      .forEach((s, dish) -> System.out.println(s+dish));
* groupingBy配合collectingAndThen(),转换分组信息内容
  *dishes.stream()
  .collect(Collectors.groupingBy(Dish::getType,
  Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))
  ,Optional::get))).forEach((s, dish) -> System.out.println(s+dish));
* 分区，partitioningBy(),把数据分为true和false
    * dishes.stream().collect(Collectors.partitioningBy(Dish::isVegetarian,
      Collectors.groupingBy(Dish::getType)))
      .forEach((aBoolean, stringListMap) -> stringListMap
      .forEach((s, dishes1) -> System.out.println(aBoolean + s + dishes1)));
#### 默认方法
> java8 在接口上提供了默认方法和静态方法的实现
*
## java集合
### list

### set

### HashMap

### ConcurrentHashMap

## java多线程
### 线程状态以及转移(线程生命周期)
7个状态
`start` `ready-to-run` `running` `sleeping` `waiting` `blocked` `dead`
start -> ready-to-run,执行Thread.start();后直接进入就绪状态
ready-to-run -> running cpu时间片选择后进入running状态
ready-to-run -> dead stop();run() exits 程序终止时直接结束

running -> sleeping Thread.sleep(time); 进入超时等待状态
running -> waiting Object.wait(); 进入等待状态
running -> blocked data received for I/O or synchronized code,I/O等待 同步代码块执行
running -> dead 线程执行完毕
running -> ready-to-run cpu执行时间片交换，Thread.yield();进入就绪状态

sleeping -> ready-to-run ;等待超时时间过后直接进入ready-to-run就绪状态
waiting -> ready-to-run;Object.notify()/Object.notifyAll() 进入就绪状态
blocked -> running data received/lock obtained 数据接收成功或同步代码块释放锁
blocked -> dead tread close 线程close

### 线程创建
* 继承 Thread 类
* 实现Runnable 接口
* 匿名内部类的方式
* 带返回值的线程
* 定时器(Timer/Quartz)
* 线程池实现
* lambda方式实现
* spring 中的多线程实现
  前三种无法抛出异常

#### Thread 初始化
* java.lang.Thread.Thread(java.lang.Runnable)

#### Runnable、Callable、FutureTask
* java.lang.Runnable，
* java.util.concurrent.FutureTask 本身实现了Runnable和Future，在Thread 调用run方法时，去调用了call()方法并返回值
* java.util.concurrent.Callable//A task that returns a result and may throw an exception. 带返回值的任务，可以抛出异常，提供给FutureTask的Run方法回调使用的
* java.util.concurrent.FutureTask//A cancellable asynchronous computation,可以取消的异步操作
* Runnable.run()在线程中是异步执行的
* Callable.run()在线程中不是异步执行的，是由FutureTask.run()方法调用的
* 由java.util.concurrent.FutureTask.FutureTask(java.lang.Runnable, V)和java.util.concurrent.FutureTask.FutureTask(java.util.concurrent.Callable<V>)
  可知，在Thread.exe()中，可以传递Runnable代替Callable，内部会把单个Runnable+Value封装为一个FutureTask，在线程池中也是如此


#### Thread 中断
线程强制停止，java.lang.Thread.stop()，但该方法不会释放资源，线程会无限期等待下去
线程外部调用改方法，会给线程发送一个中断信号，java.lang.Thread.interrupt()，编码时，需要在线程程序中接受这种中断信号并释放相应的资源
java.lang.Thread.isInterrupted(),监测线程是否中断，不会清空中断信号
java.lang.Thread.interrupted(),监测当前线程是否中，会清空中断信息

#### 继承线程和实现Runnable方式比较
实现接口的方式，做到线程和任务分离
### 线程安全的设计与实现
#### 线程带来的风险
* 线程安全性问题
* 活跃性问题
* 性能问题
  活跃性问题：
  死锁：多个线程在竞争资源而造成的一种僵局（相互等待），若无外力的情况下，线程将无法向前推进，
  避免办法，减少潜在锁之间的交互数量（在锁中再去获得其他锁），准守并文档化获取锁的顺序
  显示使用Lock类中的tryLock超时锁，这比内部锁更能好控制，如果超时就返回失败
  饥饿：多线程情况下，线程优先级最低的线程无法执行
  活锁：多线程情况下，不多重试相同的操作，却总是失败，线程没有阻塞却一直不能往下执行，如，错误消息每次处理都失败，然后放到队列中，继续下次处理，
  却总是失败，解决方法是重试机制引入随机性。
* 线程是越多越好吗？不是，如果线程过多，cpu计算时，会过多的产生上下文切换
  线程安全性问题：
  一个方法当且仅当同时被多个线程反复调用时，它一直会产生正确的结果，这就说明这个方法是线程安全的
  原子性操作
  可重入性，当线程拥有该对象所有权就可以重入？//TODO
  多线程共享一个资源
  锁重入，synchronized和lock都可以对同一个线程重入同一个对象

#### synchronized 原理
* 内置锁，每个对象都与monitor进行关联，用ObjectMonitor来管理的就是内置锁
* 锁是互斥的
* 修饰普通方法，synchronized锁住的是当前对象
* 修饰静态方法，synchronized锁住的是Class字节对象
* 修饰代码块，所有内置锁都可当做参数
* JVM在执行`Synchronized代码块`时，是两个字节码指令做控制的，monitorenter，monitorexit（通过执行javap -verbose xx.class 查询）
  [monitorenter](https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.monitorenter)
  同步方法和同步代码块底层都是通过monitor来实现同步的。
  两者的区别：同步方式是通过方法中的access_flags中设置ACC_SYNCHRONIZED标志来实现；同步代码块是通过monitorenter和monitorexit来实现
  我们知道了每个对象都与一个monitor相关联。而monitor可以被线程拥有或释放。

Mark Word，存储对象的hashCode、GC分代年龄以及锁信息。只有1个JVM位数，那么到底存的是啥？官方解释为：因为每个对象都会有，为了节省空间，JVM采用了空间复用。即当对象处于不同状态的时候，Mark Word存储的内容是不一样的。
无锁态时：存储的是hashCode、分代年龄。
重量级锁时：存储的是指向monitor的指针。synchronized是重量级锁。

Java对象与monitor的关联：通过在Java对象头中的mark word中存储了指向monitor的指针。
明白了Java中的管程ObjectMonitor的工作原理，知道了Java语法中的wait/notify/notifyAll方法底层都是调用了ObjectMonitor的方法。
明白了管程的概念，一种程序结构，封装了同步操作，避免直接使用PV信号量。

* 重量级锁，系统中的锁，需要上下文切换，开销大
* 轻量级锁-自旋
* 偏向锁，只有一个线程第一次进入锁的时候，优先获取偏向锁，当之后当前线程多次进入当前对象，锁有偏向
* 公平锁，公平是对锁的获取时间而言的，如果一个锁是公平锁，那么获取锁的时间应该符合请求顺序的绝对时间

### happens-before
> happens-before 是描述多个线程之间的内存可见性问题，比如X操作 happens-before Y 操作
> Java 内存模型通过定义了一系列的 happens-before 操作，让应用程序开发者能够轻易地表达不同线程的操作之间的内存可见性（一个操作的结果对另一个操作可见）。
* 数据依赖性 as-if serial（两个数据之间有无关系）
    * 写后读
    * 读后写
    * 写后写
```java
private int a;
private int b;
private int c;
public void func(){
   a = 1;//写
   b = 2;//写
   c = a;//针对c来说，先读取了a，再写了c，就是读后写数据依赖
   b = c + a;
}
```
* 重排序
    * 在单线程内部，字节码的先后顺序暗示了happens-before，即是程序控制流路径中靠前的字节码happens-before靠后的字节码。（但如果在靠后的
      字节码执行不依赖（数据依赖，as-if-serial）之前的字节码结果的话，可能被java编译器重排序）
* 重排序种类
    * 编译器重排序和处理器重排序
* 重排序会带来性能的提升
* 重排序带来的影响
    * 单线程重排序，不能影响最终结果
* 解锁操作 happens-before 同一个对象（同一把锁）的再加锁操作（锁操作的happens-before规则，这里线程对同一个把对象加锁的话，编译器能够
  确认该对象是同一把锁进入的话【逃逸分析】，就会移除加锁的操作，如synchronized(new Object()),者new的Object只能被当前锁获取）
* volatile 修饰的对象的写操作 happens-before 对该对象的读操作（JVM里面有写读屏障（barrier）实现，CPU根据JVM里面写读屏障而替换为具体的CPU指令，
  但频繁地访问 volatile 字段也会因为不断地强制刷新缓存而严重影响程序的性能，一般用在读多写少的变量中）
* 线程的启动操作（Thread.start） happens-before 该线程的第一个操作
* 线程的最后操作 happens-before 该线程的终止操作（即其他线程通过Thread.isAlive(),Thread.join()判断该线程是否终止）
* 构造器的最后一个操作 happens-before 该对象的析构器的最后一个操作
* 线程对其他线程的终止操作（thread1.interrupt(),即当前线程对thread1线程发送了中断操作，这其实是发送了一个中断信号），happens-before
  被中断线程接受到中断信号事件（即被中断线程的InterruptedException异常，或第三个线程对被中断线程的Thread.interrupted  
  或者 Thread.isInterrupted 调用）
* happens-before 具有传递性，即，X happens-before Y，Y happens-before Z，则 X happens-before Z
* final实例字段涉及对象的发布问题（向共享内存发布对象指针），即是，final修饰的字段在对象初始化是被赋值后才能被其他线程使用
* 新建对象的安全发布（safe publication）问题不仅仅包括 final 实例字段的可见性，还包括其他实例字段的可见性。
  所以，解决数据竞争问题的关键在于构造一个跨线程的 happens-before 关系 ：操作 X happens-before 操作 Y，
  使得操作 X 之前的字节码的结果对操作 Y 之后的字节码可见。

* 以下是Java类库中提供的工具被推导出来的happens-before规则（主要是一种线程同步信号的使用）
    * 一个线程将内容放置到安全容器 happens-before 另一个线程从该容器中获得
    * 执行 CountDownLatch 中的信号 happens-before latch 的 await
    * Semaphore释放一个信号量 happens-before 从Semaphore中获取一个信号量
    * Future 任务发布 happens-before 另一个线程Future.get()
    * Executor提交一个Runnable或Callable happens-before 开始执行任务
    * 一个线程到达CyclicBarrier，Exchanger happens-before 相同屏障（barrier）或exchanger点中其他其他线程的释放

* 以下是volatile的解释
    * volatile 只能保证修饰变量在内存中的可见性，无法保证对该变量的原子性操作（get和set，eg：i++）
    * volatile 比Synchronized更加轻量级，如果是原子性操作（get和set），可以使用volatile
    * volatile 修饰的变量写操作的内存语义，在某个线程栈中修改了改值，会把值强制刷新到共享内存中，并通知持有改对象引用的线程，数据已经失效，需要重新再内存中获取
* final内存语义 //TODO
    *
### 对象的安全发布
> 向共享内存发布对象指针

如：没有被happens-before规则限制导致对象的不安全发布
```java
    public static Singleton getInstance4() {
        if (instance4 == null) {
            instance4 = new Singleton();//不安全发布，当前程序没有被happens-before规则限制
        }
        return instance4;
    }
```
#### 对象安全发布方式
* 通过静态初始化构造器初始化对象应用，~~单例模式中的静态内部类构建~~，单例模式的饿汉模式（最简单、最安全的发布）
* 将它的引用存储到volatile或AtomicReference（long、double是双字节，需要在原子性操作中才能安全）中
* 将对象的应用存储到正确创建对象的final字段中
* 将对象应用存储到锁保护域中（HashTable、ConcurrentHashMap、等线程安全容器）
#### 构建安全发布对象
* 不可变对象，状态不能在创建后修改，所有属性都是final修饰的不可变变量（如果final的是可变对象，如HashMap中，Value是可修改的值），被正确创建，任何时候发布都是
* 高效不可变对象（一个对象在发布后状态就不会被修改，只要通过上诉安全发布方式发布，也是线程安全的，所有线程都是`只读操作`,如
  Map<String, Date> lastLogin = Collections.synchronizedMap(new HashMap<>())，本身Date是可变的，但是在安全发布后，Date只做读操作）
* 可变对象需要保证线程安全必须遵循，1.安全发布，2.对象操作需要锁保护

#### 安全的共享对象（这个需要在编码中注意）
>  获得一个对象引用时，需要知道该对象可以被如何访问
* 线程限制，该对象是否被限制在特定线程使用
* 共享只读，该对象是否为状态只读，多线程无需添加锁的情况下都可以进行安全读操作
* 共享线程安全，安全发布的对象，在起内部就已经做好了安全访问，其他线程无需再添加额外的锁来使用
* 被保护的对象（guarded），只能通过特定的锁来访问的对象引用，如被线程安全的对象封装的对象，被特定锁保护起来已发布的对象//TODO

### 为什么会存在happens-before解决方案呢？
早期语言没有内存模型概念，程序的执行顺序依赖于处理器的`内存一致性模型`，java里面的`及时编译`（）技术会对java字节码指令进行重排序，cpu
里面`乱序执行`（cpu乱序：在保证结果一致的情况下,把原来有序的指令列表,按照指令依赖关系和指令执行周期,重新安排执行顺序.
乱序优化在一定程度上可以提高程序的运行速度,在多核情况下,由于CPU内部的高速缓存,
乱序执行对访问指令的影响可能导致对数据的影响不能及时的反映到主存上,从而导致结果错误.）,`编译器乱序`（从编译器的角度来看，
编译器能够对很大一个范围的代码进行分析，能够从更大的范围内分辨出可以并发的指令，并将其尽量靠近排列让处理器更容预取和并发执行，
充分利用处理器的乱序并发功能。 ），这些功能、特性如果没有一套约定的可见性问题处理方式，就可能让程序产生不可以预知的结果，所以
java语言就提出了JMM（java内存模型）解决方案，而JMM里面主要就约定了happens-before关系，主要就解决多线程的内存可见性问题。

### JDK提供的原子性操作类(java.util.concurrent.atomic.*)
* 原子更新基本操作
* 原子更新数组
* 原子更新抽象类型
* 原子更新字段
* java.util.concurrent.atomic.LongAdder
* java.util.concurrent.atomic.AtomicInteger
* java.util.concurrent.atomic.AtomicIntegerArray
### CAS操作（compareAndSet操作）
> boolean b = cas(pre,next)//比较当前获取的值，是否被其他线程获取了，如果被获取就返回false然后重新获取并cas操作，
> 如果返回true，就设置next的进入到变量pre中并返回到当前线程，这里的pre是被volatile修饰（在原子类处理的实现）
* java.util.concurrent.atomic.AtomicInteger.compareAndSet

### synchronized、volatile、AtomicXXX、Lock
* volatile变量的线程之间的可见性，不能保证原子性、AtomicXX实现原子操作，功能受限 ，如果需要进行一系列判断的话就很有限
* synchronized锁笨重，volatile很轻量级，AtomicXXX操作类很有限，Lock很灵活
* Lock 需要显示获取和释放锁，繁琐，synchronized是隐式获取和释放锁，简单
* Lock使代码更加灵活，可以在获取某个锁的时候，释放其他锁
* Lock可以方便实现公平性
* Lock非阻塞获取锁
* Lock能被中断获取锁
* Lock能超时获取锁

### Lock（java.util.concurrent.locks.Lock）
* java.util.concurrent.locks.Lock.lock()
* java.util.concurrent.locks.Lock.unlock()
* java.util.concurrent.locks.Lock.tryLock()
* java.util.concurrent.locks.Lock.unlock()
* java.util.concurrent.locks.Lock.lockInterruptibly()
* java.util.concurrent.locks.Lock.newCondition()
* lock()和lockInterruptibly()的区别，当线程调用lockInterruptibly()获取锁时，其他线程可以对它调用一个Thread.interrupt()中断操作，
  当接收到中断信号后，就立即抛出一个InterruptedException异常，并不会在继续获取锁，如果该线程调用lock()获取锁的话，接收到一个Thread.interrupt()中断操作
  并不会抛出异常，而是会继续等待获取线程，当最后获取线程后再抛出InterruptedException异常
* lock()和tryLock()，当线程调用lock()获取锁时，会一直等待直到获取到锁，tryLock()非阻塞的获取锁，如果获取到不到锁就立即返回false，而带参数的tryLock()
  获取锁有一个超时等待，如果超时，才会返回false

### AQS(java.util.concurrent.locks.AbstractQueuedSynchronizer)抽象队列同步器，本身是一个模板类 
> 为实现依赖于先进先出（FIFO）等待队列的阻塞锁或同步器（信号量、事件等）提供一个框架。 
> 为依靠单个原子int值来表示状态的同步器提供一个有用基础 
> 子类必须定义更改这个int值状态的受保护的方法,并定义哪些值表示何种状态 
> AQS已经根据这个状态值来实现了阻塞和排队  
> 子类在维护这个状态值时，建议调用AQS中已经实现的final方法（getState、setState、compareAndSetState） 
> 子类应该实现为非公共的内部帮助类（就像这个内部类一样，java.util.concurrent.locks.ReentrantLock.Sync），子类的类似lock方法 
>可以直接调用内部类的相关方法
> AQS支持独占锁和共享锁，以及与独占锁相关的ConditionObject类
> AQS提供了监测内部队列状态的方法，也提供了监测ConditionObject对象的方法
* 具体实现需要根据需要实现的同步器（如：java.util.concurrent.locks.ReentrantLock.Sync）类型（共享或独占）来实现以下五个方法，
  使用已经在AQS中定义的final方法来做状态变更，getState(), setState(int) and/or compareAndSetState(int, int)

  * java.util.concurrent.locks.AbstractQueuedSynchronizer.tryAcquire//独占锁获取
  * java.util.concurrent.locks.AbstractQueuedSynchronizer.tryRelease//独占锁释放
  * java.util.concurrent.locks.AbstractQueuedSynchronizer.tryAcquireShared//共享锁获取
  * java.util.concurrent.locks.AbstractQueuedSynchronizer.tryReleaseShared//共享锁释放
  * java.util.concurrent.locks.AbstractQueuedSynchronizer.isHeldExclusively//是否独占
* AQS中主要维护节点为Node（java.util.concurrent.locks.AbstractQueuedSynchronizer.Node）类型的FIFO队列（双端链表）根据
  每个Node的waitStatus（等待状态）的进行设置进入和退出和修改状态

* 子类继承AQS成为了一个Helper，而根据定义Helper本身为一个Lock子类（java.util.concurrent.locks.Lock）的内部类，所以
  外部客户端使用的时候，只会调用Lock子类的实现方法，具体调用对应如下：[AbstractQueuedSynchronizer API](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/locks/AbstractQueuedSynchronizer.html)
```java
private final Sync sync = new Sync();//同步器

public void lock()              { sync.acquire(1); }
public boolean tryLock()        { return sync.tryAcquire(1); }
public void unlock()            { sync.release(1); }
public Condition newCondition() { return sync.newCondition(); }
public boolean isLocked()       { return sync.isLocked(); }
public boolean isHeldByCurrentThread() {
 return sync.isHeldExclusively();
 }
public boolean hasQueuedThreads() {
 return sync.hasQueuedThreads();
}
public void lockInterruptibly() throws InterruptedException {
 sync.acquireInterruptibly(1);
}
public boolean tryLock(long timeout, TimeUnit unit)
   throws InterruptedException {
 return sync.tryAcquireNanos(1, unit.toNanos(timeout));
}
```

### ReentrantReadWriteLock（可重入读写锁）
* 读写锁，分为读锁（是共享锁）和写锁（是排它锁,java.util.concurrent.locks.ReadWriteLock.writeLock）
* 排它锁，synchronized，Lock（java.util.concurrent.locks.ReentrantLock），同一时刻运行一个线程访问
* 共享锁，同一时刻可以多个锁访问(java.util.concurrent.locks.ReadWriteLock.readLock)
* 读写锁中需要保存的状态值（int），AQS中维护的int值就是线程的重入个数，而读写锁本身有独占的读锁和共享的写锁，所以需要保存
  `写锁重入次数（当写锁重入次数为0的时候，其他写锁或读锁才能进入）`，`读锁个数（当读锁个数为0的时候，写锁才能进入）`，`每个读锁的重入次数（每个读锁的重入次数为零后才会把读锁个数减一）`
* int类型（4个字节32位）的state同时表示写锁读锁的个数时候采用2进制（1111 1111 1111 1111-1111 1111 1111 1111）
  前16位（高位）表示读锁的个数，后16位（低位）表示写锁的个数，所以一个锁不管是读锁还是写锁都可以只用一个state值来表示，
* 每个读锁的重入次数由`java.util.concurrent.locks.ReentrantReadWriteLock.Sync.ThreadLocalHoldCounter`记录，及时就是一个`ThreadLocal`
* 读写`锁降级`，把写锁降级为读锁，1.具体操作是在写锁释放之前加一把读锁（同一个线程进入虽然是读锁也是锁重入），2.在写锁释放之后，3.最后再释放之前添加的读锁//TODO?
* `锁升级`，把读锁升级为写锁，在读锁还未释放的时候获取写锁，`ReentrantReadWriteLock`并不支持，在读锁中添加写锁是会阻塞的，不能获取到写锁的。

### java.util.concurrent.locks.StampedLock since1.8（防止ReentrantReadWriteLock的写饥饿问题）
* 写锁互斥（ReentrantReadWriteLock）
    * 读-写
    * 写-写
* StampedLock 读锁不会阻塞写锁（在读的时候发现其他线程再写，就重新去读一次）

### 线程安全性问题总结
* 线程安全性问题
1. 多线程环境下
2. 存在共享资源，产生竞争
3. 原子性操作问题
* 如何解决
1. synchronized（偏向锁、轻量级锁（自旋锁）、重量级锁、可重入、排他、简单） 锁
2. volatile（资源在各线程之间的可见性问题）
3. Lock（灵活、读写锁、可重入锁、公平锁、共享锁、排它锁、AQS）
4. JDK提供的原子性操作的容器、类
* 各种锁

### 线程间通信
> 线程间通信是指线程间能够相互发送信号，同时线程通信能够等待其他线程信号，主要是解决多个线程之间协作的问题

#### wait()\notify()
Object.wait()//会释放当前的synchronized锁
Object.notify()//会随机侥幸一个wait的线程，notify执行后退出当前synchronized锁后，再叫醒的线程会再次拿到锁
Object.notifyAll()//叫醒所有wait的线程，notifyAll执行后退出当前synchronized锁后，再叫醒的随机一个线程会拿到锁
Object.notify() 发生在Object.wait()之前，会发生什么情况？//TODO Question(cn.ms22.thread.communication.SyncSignal2)
#### Condition
`java.util.concurrent.locks.Condition`,Condition.await()/Condition.signal()
* 每个Condition 对象中包含一个等外队列，waiter，而wait()和notify()只有一个等待队列
* 当await()时，就构造一个Node（lastWaiter和同步队列里面的Node是一样的数据结构），放到waiter里面
* 当signal()时，就拿出头部Node（firstWaiter），放到同步队列中，同步队列就会有机会获得cpu竞争而执行
* await()时，会释放锁，wait()也会释放锁
#### join()
* join(),加塞线程，让调用线程等待(wait())，让加塞线程执行(在join线程执行完成后在dead之前会执行notifyAll()唤醒锁内的所有线程)

#### ThreadLocal
> 每个线程的局部变量

#### CountDownLatch(java.util.concurrent.CountDownLatch)
> 当规定线程数量执行完毕后才会往后继续执行其他线程，可以用Thread.activeCount()做模拟，本身是共享锁，内部sync同步器继承至AQS
* countDown()/await(),每个线程执行完成后就countDown()一次，在调用线程里面执行await(),调用线程等到countDown计数为0的时候，释放await线程执行

#### CyclicBarrier(java.util.concurrent.CyclicBarrier)
> 循环屏障使用，当规定线程数到达后继续执行后面的任务
* await(),每个线程到达await()就等其他线程到来，当达到规定线程数时才会往下继续执行，
  如果一直没有到达（某个线程在调用await之前抛异常或刚好线程数不够数，达不到规定数量），线程会一直等待下去。//TODO questing?如何处理
* reset()，循环使用，在线程调度逻辑处理处，可以reset()重置状态，循环使用。

#### Semaphore(java.util.concurrent.Semaphore)
> 信号量，限制流量的，现在每次执行线程的数量，主要是为了设置cpu执行时间，不要过度在这块消耗资源
* acquire()/release(),在方法中先获取许可，执行完成后再释放许可
* 和`java.util.concurrent.Executors.newFixedThreadPool(int)`这个固定线程池类似，同时只能这么多个线程执行

#### Exchanger(java.util.concurrent.Exchanger)
> 两个线程间交换数据，如果是单数线程个数，会有一个线程一直阻塞，等待其他线程exchange，或者通过超时等待方法
* java.util.concurrent.Exchanger.exchange(V)//

#### 生产者消费者模式

### 同步容器和并发容器
> 保证线程安全性（synchronized\volatile\cas\Lock..），保证并发效率
> 多线程环境使用的时候，如果是同步容器，就相当于单线程进行操作，效率不高，并发容器可以同时多线程操作
#### 基础队列
* java.util.Vector/java.util.ArrayList,Vector线程安全的（方法上添加synchronized同步容器），ArrayList线程不安全（扩容oldCapacity + (oldCapacity >> 1)）
* java.util.Collections.SynchronizedList,是在方法内部添加了synchronized关键字加锁，也是同步容器
* java.util.HashMap/java.util.Hashtable，Hashtable线程安全的（添加的synchronized方法修饰同步容器），HashMap是线程不安全的
* java.util.Collections.synchronizedMap，在方法内部添加了synchronized修饰代码块加锁在java.util.Collections.SynchronizedMap.mutex上
* java.util.concurrent.CopyOnWriteArrayList，并发容器，添加了java.util.concurrent.locks.ReentrantLock锁，在内部通过复制，然后添加，最后把新的数组赋值给原引用变量，
  类似读、写分离，只对写加独占锁，读可以随便读，不管是删除还是添加，修改，都是通过复制原有的数组到新数组上，读操作占比高，就可以使用，如果是写比较多，使用vector可能还要高些。
* java.util.concurrent.ConcurrentHashMap，并发容器，分段加锁（synchronized）操作
* java.util.concurrent.ConcurrentLinkedQueue，并发容器，链表实现的队列，在链表中如果为空，这有一个null节点，需要画图走流程，并且需要添加3-5个，删除3-5个才能看出全流程

#### 阻塞队列 java.util.concurrent.BlockingQueue，阻塞队列，
* java.util.concurrent.ArrayBlockingQueue，put/take是阻塞方法，add和remove是抛出异常的，offer（true或false）/poll是返回值或null
    * 使用了java.util.concurrent.locks.ReentrantLock来锁住添加和删除等操作
    * 使用了Condition notEmpty = lock.newCondition()，Condition在做空时等待(notEmpty.await())，并在非空时唤醒添加线程继续添加notFull.signal()
    * 使用Condition notFull =  lock.newCondition()， Condition在做满时等待（notFull.await()），不满时添加并做notEmpty.signal()唤醒消费线程

* java.util.concurrent.LinkedBlockingQueue
* java.util.concurrent.SynchronousQueue
* java.util.concurrent.PriorityBlockingQueue

### 线程池
> 使用缓存的策略来减少创建和销毁线程的次数带来的性能开销，线程池中存在一定数量的线程，达到重复使用的效果
* 为什么要使用线程池
    * 降低资源开销，通过重复利用创建好的线程，减小创建和销毁线程的开销
    * 提高响应速度，通过已经创建好的线程直接执行任务，而不需要重新创建
    * 提高线程的可管理性，线程不能无限创建，不然会对性能带来很大消耗，通过线程池可以很好的分配、调优和监控
#### java.util.concurrent.ThreadPoolExecutor
* java.util.concurrent.ThreadPoolExecutor 线程池java.util.concurrent.ThreadPoolExecutor.ThreadPoolExecutor(int, int, long, java.util.concurrent.TimeUnit,
  java.util.concurrent.BlockingQueue<java.lang.Runnable>, java.util.concurrent.ThreadFactory, java.util.concurrent.RejectedExecutionHandler)
    * `int corePoolSize`,核心线程数量,当提交一个任务进入线程池时，会创建一个线程，即使及基本线程会能执行也会创建新的线程，知道达到core线程数为止，
      线程池调用了`java.util.concurrent.ThreadPoolExecutor.prestartAllCoreThreads()`方法后，会提前把最大线程数启动
    * int maximumPoolSize,线程池允许的最大线程数量，任务等待队列满了，并且创建的线程小于最大线程数，则会在创建新的线程执行任务，如果是用
      是无界阻塞队列，这这个参数无效。
    * `long keepAliveTime`,线程空闲存活时间，如果任务很多，而且运行很短，可以调大这个值
    * `TimeUnit unit`,线程存活时间单位（DAYS、HOURS、MINUTES、SECONDS、MILLISECONDS、MICROSECONDS、NANOSECONDS）
    * `BlockingQueue<Runnable> workQueue`,阻塞队列，用于保存等待执行的任务
        * java.util.concurrent.ArrayBlockingQueue,基于数组结构的有界阻塞队列，按照FIFO原则进行任务调度
        * java.util.concurrent.LinkedBlockingQueue，基于链表结构的有界阻塞队列，按照FIFO原则进行任务调度，吞吐量高于ArrayListBlockQueue,
          静态工厂方法java.util.concurrent.Executors.newFixedThreadPool(int),默认使用的阻塞队列
        * java.util.concurrent.SynchronousQueue，不存储元素的阻塞队列，每个插入操作必须等另一个线程调用移除操作，否则插入操作一直处于阻塞状态，
          通常吞吐量高于LinkedBlockingQueue，java.util.concurrent.Executors.newCachedThreadPool()默认使用这种阻塞队列
          *java.util.concurrent.ScheduledThreadPoolExecutor.DelayedWorkQueue，延时执行的queue，用于ScheduledThreadPoolExecutor做计划任务
    * `ThreadFactory threadFactory`,创建线程的工厂，用于指定线程名字，方便debug
    * `RejectedExecutionHandler handler`，拒绝策略，当等待队列满了并且最大线程数也满了，表示无法再处理新提交的任务时的拒绝策略，默认是
      java.util.concurrent.ThreadPoolExecutor.AbortPolicy，当满了后再提交就抛出异常
        * java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy，只用调用者所在线程来完成任务
        * java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy，丢弃队列的最近的一个任务，并执行当前任务
        * java.util.concurrent.ThreadPoolExecutor.DiscardPolicy，不处理，直接丢弃
        * 根据业务情况自己实现一个策略，如，任务记录到日志中或持久化，等待空闲时重试
    * 其他属性
        * private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0))//线程池的控制状态：用来表示线程池的运行状态（整型的高三位）和运行worker数量（低29位）
        * private static final int COUNT_BITS = Integer.SIZE - 3;//29位偏移量
        * private static final int CAPACITY   = (1 << COUNT_BITS) - 1;//最大容量(2^29-1)
        *
        * // runState is stored in the high-order bits
        * private static final int RUNNING    = -1 << COUNT_BITS;//接受新任务并且已经处理已经进入阻塞队列的任务
        * private static final int SHUTDOWN   =  0 << COUNT_BITS;//不接受新任务，但处理已经进入阻塞队列的任务，调用了shutdown()
        * private static final int STOP       =  1 << COUNT_BITS;//不接受新任务，不处理阻塞队列的任务，中断当前正在执行的任务,调用了shutdownNow()
        * private static final int TIDYING    =  2 << COUNT_BITS;//所有任务都已经终止，workerCount为0，线程转化为TIDYING状态并且调用terminated钩子方法
        * private static final int TERMINATED =  3 << COUNT_BITS;//terminated钩子方法已经执行完成
* 提交任务 //TODO
* 关闭线程池  //TODO

#### java.util.concurrent.Executor
> 简化了ThreadPoolExecutor的构造过程
* 通过java.util.concurrent.Executors工厂方法构建几种常见的线程池
* java.util.concurrent.Executors.newFixedThreadPool(int)//固定大小线程池
* java.util.concurrent.Executors.newCachedThreadPool()//带缓存的线程池
* java.util.concurrent.Executors.newSingleThreadExecutor()//单例
* java.util.concurrent.Executors.newScheduledThreadPool(int)//计划任务
* java.util.concurrent.Executors.newWorkStealingPool(int),fork/join Task线程池

#### fork/join(java.util.concurrent.ForkJoinPool)
> 分治思想的fork/join
* java.util.concurrent.RecursiveTask//带返回值的抽象递归处理类，如斐波拉契数列求值
* java.util.concurrent.RecursiveAction//不带返回结果的递归处理，如：归并排序中的，任务划分和每次的merge流程
* java.util.concurrent.ForkJoinPool//fork/join线程池，分支思维的框架
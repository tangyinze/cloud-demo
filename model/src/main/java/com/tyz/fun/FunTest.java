package com.tyz.fun;

import com.tyz.procuct.bean.ProductVO;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @program: cloud-demo
 * @description: 函数式学习
 * @author: tyz
 * @create: 2025-04-09
 */
public class FunTest {
    public static void main(String[] args) {
        // 面向对象  函数编程 大道无情 函数无情
        // 大道本无形,化形才亦真

        System.out.println(add(1));
        System.out.println(add(1));

        System.out.println("普通函数add===>" + add(1, 2));

        // lambda 函数对象
        LambdaAdd lambdaAdd = (a0, b0) -> {
            return a0 + b0;
        };
        System.out.println("lambdaAdd函数===>" + lambdaAdd.add(1,2));

        //lambda定义： 参数-> 逻辑部分  就是一个函数对象
        // 函数好处：行为参数化和延迟执行。

        List<ProductVO> pList = new ArrayList<>(3);
        List<ProductVO> pList2 = new ArrayList<>(7);
        List<ProductVO> pList3 = new ArrayList<>(7);
        for (int ii = 0; ii < 7; ii++) {
            ProductVO vo = new ProductVO();
            vo.setNum(ii);
            vo.setProductName("apple-" + ii);
            pList.add(vo);
            pList2.add(vo);
            pList3.add(vo);
        }

        System.out.println("函数 商品数为偶数行为参数化 old===>" +filter(pList));

        System.out.println("函数 商品数为偶数行为参数化 new===>" + filterLam(pList, (vo) -> {
            return vo.getNum() % 2 == 0;
        }));
        System.out.println("函数 商品数为奇数行为参数化 new===>" + filterLam(pList, (vo) -> {
            return vo.getNum() % 2 == 1;
        }));
        // 函数表现形式：1）Lambda 表达式（功能更全面些） 2）方法引用（写法更简单）
        /**
         * <p>
         *     1)Lambda 表达式(函数对象):
         *     参数 -> 业务逻辑
         *     (int a,int b) -> a+b;
         *     // 多于一行，不能省略{}且最后一行有return
         *     (int a,int b) -> {int c= a+b; return c;}

         *     Lambda 表达式是一个对函数对象
         *     (int a,int b) -> a+b;

         *     对应的对象如下Lambda1类型对象：
         *     Lambda1 lambda = (int a,int b) -> a+b;
         *     interface Lambda1 {
         *         int op(int a,int b);
         *     }
         *     只一个参数时，可以省略（）
         *     a -> a;
         * </p>
         */

        /**
         * <p>
         *     2）方法引用
         *     // max缺什么才可以调用就是参数 ，参数为(int a,int b)
         *      Math::max          等价-->  (int a,int b)->Math.max(a,b);
         *      // getNum缺什么才可以调用就是参数 ，参数为ProductVO vo
         *      ProductVO::getNum  等价--> （ProductVO vo）-> vo.getNum();
         *      // println 缺什么才可以调用就是参数,参数为要打印的对象 Object vo
         *      System.out.println 等价--> （Object vo）-> System.out.println(obj);
         *      // 无参数构造器
         *      ProductVO::new   等价--> ()-> new ProductVO();
         * </p>
         */

        /**
         * <p>
         *     函数类型：
         *           可以根据参数 类型相同
         *           和
         *           返回值      类型相同
         *           来进行归类 一个函数式接口来表示（仅包括一个抽象接口）
         *           @FunctionInterface 来标注
         *
         * </p>
         */
        Type1<ProductVO> type1  = () -> new ProductVO();

        Type1<List<ProductVO>> type2  = () -> new ArrayList<ProductVO>();
        // jdk 中定义的类型
        IntPredicate boj1 = a -> (a & 1) == 0;
        Function<String, ProductVO> obj2 = (String s) -> new ProductVO();

        /**
         * <p>
         *     常见函数接口
         *     1)Runnable
         *      () -> void
         *     2) Callable
         *      () -> T
         *     3) Comparator
         *      (T,T) -> int

         *     4) Consumer，BiConsumer,IntConsumer,LongConsumer,DoubleConsumer
         *       (T) -> void 无返回值,Bi是两个参数，Int 指参数是int
         *     5) Function ,BiFunction ,Int Long Double
         *       (T) ->R ， R返回值 ,Bi是两个参数，Int 指参数是int
         *     6）Predicate,BiPredicate,Int Long Double
         *       (T)-> boolean,Bi是两个参数，Int 指参数是int
         *     7) Supplier ,Int Long Double ...
         *       ()-> T ,Int 指参数是int
         *     8) UnaryOperator, BinaryOperator,Int Long Double
         *      (T)-> T,Unary 一个参，Binary 两个参数 ，Int 指参数是int
         *        参数和返回类型一样
         * </p>
         */
        // Predicate 返回 boolean
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        System.out.println("函数 偶数行为参数化 filterNum===>" + filterNum(integers));
        System.out.println("JDK函数 偶数行为参数化 filterNumPredicate===>" + filterNumPredicate(integers, (a) -> (a & 1) == 0));
        // 生产者 Supplier
        System.out.println("函数生成一个随机数组old supply===>" + supply(3));
        System.out.println("JDK函数生成一个随机数组行为参数化 supply===>" + supply(3, () -> ThreadLocalRandom.current().nextInt()));

        /**
         * <p>
         *     方法引用
         *     1）类名::静态方法  函数对象？
         *        逻辑，就是执行此静态方法
         *        参数，就是静态方法的参数
               eg:
         *        Math::abs  -> Math.abs(n)      (n)-> Math.abs(n)
         *        Math:max   -> Math.max(a,b)    (a,b)-> Math.max(a,b)
         *
         *     2) 类名::非静态方法   函数对象？
         *         逻辑，就是执行此类非静态方法
         *         参数，一是此类对象，一是非静态方法的参数
         *       eg:
         *         ProductVO::getNum   -> pro.getNum()     (pro)->pro.getNum()
         *         ProductVO::setNum   -> pro.setNum(num)  (pro)->pro.setNum(num)

         *     3）对象::非静态方法  函数对象？
         *        逻辑，就是执行此对象的非静态方法
         *        参数，(对象已知就不需要了)就是非静态方法的参数
         *       eg:
         *        System.out::println  -> System.out.println(obj)  (obj)-> System.out.println(obj)

         *     4) 类名::new 函数对象？
         *        逻辑，就是执行此构造方法
         *        参数，就是构造方法的参数
         *        eg:
         *        无参构造
         *        ProductVO::new   -> new ProductVO()         ()-> new ProductVO()
         *        有参数构造
         *        ProductVO::new   -> new ProductVO(name)     (name)-> new ProductVO(name)

         *     5）this::非静态方法
         *     6）supper::非静态方法
         *        子类调用父类中的非静态方法
         *
         *      5和6 都是 3）对象::非静态方法 的特例 是类中有子类，在其内部使用
         * </p>
         */
        // 1 类名::静态方法
        System.out.println("方法引用--》类名::静态方法");
        pList.stream()
                .forEach(FunTest::printPO);
                //.forEach(po -> System.out.println(po));
        // 2 类名::非静态方法
        System.out.println("方法引用--》类名::非静态方法");
        pList2.stream().forEach(ProductVO::toString);
        // 3 对象::非静态方法
        System.out.println("方法引用--》out 对象::非静态方法");
        // po -> System.out.println(po)
        pList3.stream().forEach(System.out::println);

        // 4 类名::new
        Supplier<ProductVO> provo = ProductVO::new;
        System.out.println("方法引用--》类名::new ====>  " +provo);
        System.out.println("方法引用--》类名::new ====> 执行get获取对应的vo" + provo.get());

        /**
         * <p>
         *     函数的闭包
         *      int x =0;
         *      (int y) -> x+y
         *      执行函数时，可以正确引用到x 并计算正确。
         *      函数逻辑部份引用到外界的变量，绑定在一起形成闭包现象，这个变量不可被修改。
         *      闭包作用：
         *        给函数对象提供参数以外的数据的手段。
         * </p>
         */

        List<Runnable> listBB = new ArrayList<>(4);
        System.out.println("函数的闭包begin");
        for (int k = 0; k < 3; k++) {
            int u = k;
            Runnable task1 = () -> System.out.println("执行任务:" + u);
            listBB.add(task1);
        }
        ExecutorService service = Executors.newFixedThreadPool(3);
        listBB.forEach(service::execute);
        service.shutdown();
        System.out.println("函数的闭包end");

        /**
         * <p>
         *     函数的柯里化

         *     如把三个数据合在一起，逻辑限定，但数据不能一次得到
         *     a-> 函数对象
         *         b-> 函数对象
         *             c-> 完成合并

         *    高阶函数：就是指它是其它函数对象的使用者
         *    作用：
         *       将通用 复杂的逻辑含在高阶函数内
         *       将易变 未定的逻辑放在外部的函数对象中。

         *    Stream 流
         * </p>
         */

        /**
         * <p>
         *     Stream api
         *     1) filter predicate
         *     2) map function
         *     3) flatmap 降维
         * </p>
         */
        System.out.println("Stream api filter ====>");
        List<Integer> filters = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        filters.stream()
                .filter(a -> (a & 1) == 0)
                .forEach(System.out::println);

        System.out.println("Stream api map ====>");
        List<Integer> maps = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        maps.stream()
                .map(s->Integer.toString(s)+"_A")
                .map(String::toUpperCase)
                .forEach(System.out::println);
        System.out.println("Stream api flatmap ====>");
        Integer[][] array2D ={
                {1,2,3},
                {5,6,7},
                {4,8,9}
        };
        Arrays.stream(array2D)
                .flatMap(Arrays::stream)
                .forEach(System.out::println);

        /**
         * <p>
         *     stream 构建
         *     1）集合类变stream
         *        List.of(1,2,3).stream()
         *        Set.of(1,2,3).stream()
         *        Map.of("key","1").entrySet().stream()
         *     2) 数组 变stream
         *         int[] a1 = {1,2,3};
         *        Arrays.stream(a1)
         *     3) 对象构建stream
         *        Stream.of(1,2,3)
         *     stream 合并与截取
         *     concat 合并
         *     skip(long a) 跳过a 个数据 保留剩下的
         *     limit(long a) 保留数据，剩下的不要了
         *
         *     takeWhile(Predicate a) 条件成立保留，一旦不成立，剩下的不要
         *     dropWhile(Predicate a) 条件成立舍弃，一旦不成立，剩下的保留
         * </p>
         */
        System.out.println("Stream api concat ====>");
        Stream<Integer> s1 = Stream.of(1, 2, 3);
        Stream<String> s2 = Stream.of("a", "b","c");
        Stream.concat(s2, s1.map(a -> Integer.toString(a)))
                .skip(1)
                .limit(3)
                .forEach(System.out::print);

        System.out.println("Stream api takeWhile ====> ab");
        // ab
        Stream.of("a", "b","1","2","K")
                .takeWhile(s->"a".equals(s) ||"b".equals(s))
                .forEach(System.out::println);
        System.out.println("Stream api dropWhile ====> 12");
        // 12
        Stream.of("a", "b","1","2")
                .dropWhile(s->"a".equals(s) ||"b".equals(s))
                .forEach(System.out::println);

        // 无数据生成流 Stream
        // 1~4的数字
        System.out.println("IntStream.range ====>");
        IntStream.range(1,5).forEach(System.out::print);
        System.out.println("---------");
        System.out.println("IntStream.rangeClosed ====>");
        IntStream.rangeClosed(1,4).forEach(System.out::print);
        System.out.println("---------");
        System.out.println("IntStream.iterate ====>");
        // IntStream.iterate 生成 1 3 5 7 9 11 奇数序列 可以根据上一个元素生成当前元素
        IntStream.iterate(1, s -> s + 2)
                .limit(5)
                .forEach(System.out::print);
        System.out.println("---------");
        System.out.println("IntStream.iterate2 ====>");
        IntStream.iterate(
                1,
                 s0 -> s0 <= 7,
                 s -> s + 2)
                .forEach(System.out::print);
        System.out.println("---------");
        System.out.println("IntStream.generate ====>");
        IntStream.generate(()->ThreadLocalRandom.current().nextInt(100))
                .limit(5)
                .forEach(System.out::print);

        // anyMatch nonMatch allMatch
        System.out.println("anyMatch");
        boolean b = List.of(1, 7, 3).stream().anyMatch(x -> (x & 1) == 0);
        System.out.println("anyMatch==>" + b);

        boolean b1 = List.of(2, 4, 6).stream().allMatch(x -> (x & 1) == 0);
        System.out.println("allMatch==>" + b1);

        boolean b2 = List.of(1, 9, 3).stream().noneMatch(x -> (x & 1) == 0);
        System.out.println("allMatch==>" + b2);

        // findFirst findAny
        System.out.println("findFirst==>" + 2);
        Integer findFirst = List.of(1, 2, 3, 6, 7)
                .stream()
                .filter(num -> (num & 1) == 0)
                .findFirst()
                .orElse(0);
        System.out.println("findFirst==>" + findFirst);
        System.out.println("findFirst==>" + 2);
        Integer findAny = List.of(1, 4, 3, 6, 7)
                .stream()
                .filter(num -> (num & 1) == 0)
                .findAny()
                .orElse(0);
        System.out.println("findAny==>" + findAny);

        // 去重 distinct
        System.out.println("stream api distinct==>");
        List.of(1, 4, 7,4,5,7).stream().distinct()
                .forEach(System.out::println);
        // 排序 sorted
        System.out.println("stream api sorted==>");
        List.of(1, 4, 7, 4, 5, 7)
                .stream()
                .distinct()
                .sorted((a0, b0) -> (a0 < b0) ? -1 : ((a0 == b0) ? 0 : 1))
                .forEach(System.out::println);
        System.out.println("stream api sorted Comparator==>");
        List.of(1, 4, 7, 4, 5, 7)
                .stream()
                .distinct()
                .sorted(Comparator.comparingInt(s->s))
                .forEach(System.out::println);
        /**
         * <p>
         *     reduce 化简 两两合并 最大 最小 求和 求个数
         *     .reduce(p,x)->r   p上次合并结果 x 当前元素 ，r本次合并结果
         *     .reduce(init,(p,x) -> r) init 初始值 p上次合并结果 x 当前元素 ，r本次合并结果
         *     .reduce(init,(p,x) -> r,(r1,r2) ->r) 这个是并行流有关的
         * </p>
         */
        System.out.println("stream api reduce==>"+7);
        Integer reduce0 = List.of(1, 4, 7, 4, 5, 7)
                .stream()
                .reduce((num1, num2) -> (num1 > num2) ? num1 : num2)
                .orElse(-1);
        System.out.println("stream api reduce==>" + reduce0);

        System.out.println("stream api reduce 2==>"+7);
        Integer reduce2 = List.of(1, 4, 7, 4, 5, 7)
                .stream()
                .reduce(0, (num1, num2) -> (num1 > num2) ? num1 : num2);
        System.out.println("stream api reduce==>" + reduce2);

        /**
         * <p>
         *     .collect(
         *     ()->c, // 创建容器
         *     (c,x)->void, // 将x 加入c 容器
         *     ? // 如何将2个容器合并
         *     )
         * </p>
         */
        System.out.println("stream api collect==> 收集到List");
        Stream<String> stringStream = Stream.of("a12","b3","c5");
        // 收集到List
        stringStream.collect(
                () -> new ArrayList<String>(),
                (list, x) -> list.add(x),
                (a4, b4) -> {
                }
        ).forEach(System.out::println);
        System.out.println("stream api collect==> 官方的收集到List");
        Stream.of("a12","b3","c5")
                .collect(Collectors.toList())
                .forEach(System.out::println);

        // 收集到map
        Stream.of("c12","c3","c5")
                .collect(
                        HashMap::new,
                        (map,x) -> map.put(x,x),
                        (a4, b4) -> {
                        }
                )
                .entrySet()
                .stream()
                .forEach(System.out::println);
        System.out.println("stream api collect==> 官方的收集到Map");
        Stream.of("c12", "c36", "c56")
                .collect(Collectors.toMap(
                        (key) -> key,
                        (val) -> 1
                )).entrySet()
                .stream()
                .forEach(System.out::println);

        // 分组
        System.out.println("stream Collectors.groupingBy==> 奇偶分组");
        List<ProductVO> pList4 = new ArrayList<>(4);
        List<ProductVO> pList5 = new ArrayList<>(4);
        for (int ii = 0; ii < 5; ii++) {
            ProductVO vo = new ProductVO();
            vo.setNum(ii);
            vo.setProductName("apple-" + ii);
            pList4.add(vo);
            pList5.add(vo);
        }
        pList4.stream().collect(
                Collectors.groupingBy(
                        (pro) -> (pro.getNum() & 1) ==0? "0偶":"1奇",
                        Collectors.toList()
                )
        ).entrySet()
          .forEach(System.out::println);

        // 收集 奇偶分组取产品名称
        pList5.stream().collect(
                        Collectors.groupingBy(
                                // 分组逻辑 function
                                (pro) -> (pro.getNum() & 1) == 0 ? "0偶" : "1奇",
                                // 下游收集机
                                Collectors.mapping(
                                        ProductVO::getProductName,
                                        Collectors.joining(",")
                                )
                        )
                ).entrySet()
                .forEach(System.out::println);

        /**
         * <p>
         *     Stream 特性:
         *        1）一次使用
         *        2）两类操作
         *           中间操作 lazy  懒惰（map|filter|skip|distinct|sort|limit...）           可多次出现
         *           终结操作 eager 迫切 (forEach|findFirst|allMatch|reduce|collect...)     只能出现一次
         * </p>
         *
         */

        // 异步 函数对象
        System.out.println("CompletableFuture 异步" + Thread.currentThread().getName());
        CompletableFuture.runAsync(
                () -> System.out.println("异步操作-" + Thread.currentThread().getName())
        );
        // CompletableFuture 默认的线程池内的线程都是守护线程，如果主线程结束，守护线程也会结束 可能来不及执行异步操作
        System.out.println("CompletableFuture 异步 end" + Thread.currentThread().getName());
        /**
         * <p>
         *     CompletableFuture.supplyAsync 处理异步结果
         *     thenApply(Function) 转换结果
         *     thenApplyAsync(Function) 异步转换结果
         *     thenAccept(Consumer) 消费结果
         *     thenAcceptAsync(Consumer) 异步消费结果
         * </p>
         */
        CompletableFuture.supplyAsync(
                        () -> {
                            System.out.println("异步操作-" + Thread.currentThread().getName());
                            return 1;
                        }
                ).thenApply(x -> x + "Fun")
                .thenAccept(x -> System.out.println(x + "@@" + Thread.currentThread().getName()));

        System.out.println("CompletableFuture supplyAsync异步 end" + Thread.currentThread().getName());

       /* boolean aa = 1>2 & false;
        boolean ba = 1>2 && false;*/
        /**
         * <p>
         *     可序列化的函数对象 (Function & Serializable) 类名::方法名（函数引用 非lambda 表达式 有如下的逻辑）
         *     函数对象 <==> 字节码 会额外存储类和方法的信息，运行时就可以根据这些信息找到属性，从而进一步确定属性字段上的其它信息

         *         Type2<ProductVO, String> type4 = (Type2 & Serializable) ProductVO::getProductName;
         *         SerializedLambda invoke =  (SerializedLambda)type4.getClass().getDeclaredMethod("writeReplace").invoke(type4);
         *         // invoke 这个新对象， 它包含了原始函数对象的字节码 还包含了类和方法的额外信息
         *         System.out.println(invoke.getClass());

         *         // 哪个类使用了这个函数对象 ==》FunTest
         *         System.out.println(invoke.getCapturingClass());
         *         // 哪个类实现这个函数对象的逻辑 ==》ProductVO
         *         System.out.println(invoke.getImplClass());
         *         // 哪个方法实现这个函数对象的逻辑 ==》getProductName
         *         System.out.println(invoke.getImplMethodName());
         * </p>
         */

        /**
         * <p>
         *     其实现的原理
         *     lambda 表达式 是一种语法糖 ，它仍然被翻译成类，对象，方法
         *     eg:
         *       BinaryOperator<Integer> lam = (a,b) -> a+b;
         *      1） 方法从哪来
         *          类中出现lambda,就会在当前类中生成 private static(私有的静态方法)方法 ,方法内容就是lambda的逻辑
         *          可以用反射来验证
         *          for( Method method :当前类.class.getDeclaredMethods()){
         *              System.out.println(method);
         *          }
         *      2） 类和对象从哪来 (MethodHandles.lookup() => LambdaMetaFactory.metafactory(.....))
         *          在运行时根据lambda对象去实现这个接口中方法，方法中调用上私有的静态方法，从而完成lamdba解析
         *          BinaryOperator<Integer> lam = (a,b) -> a+b;
                    等价如下
         *          ==》BinaryOperator<Integer> lam = new TempLambda();

         *          会解析如下类似的代码：
         *          （加jdk 参数可以看见
         *            jdk 21:
         *            -Djdk.invoke.lambdaMetaFactory.dumpProxyClassesFiles
         *            早起：
         *            -Djdk.internal.lambda.dumpProxyClasses
         *          ）
         *          static final class TempLambda implements BinaryOperator<Integer>{
         *              @Override
         *              public Integer apply(Integer a, Integer b){
         *                  return lamdbad$main$0(a,b);
         *              }
         *          }

         *          private static Integer lamdbad$main$0(Integer a, Integer b){
         *              return a+b;
         *          }
         *
         * </p>
         */
        String str = new String("good");
        char[] ch = {'a', 'b', 'c'};
        FunTest funTest = new FunTest();
        funTest.change(str, ch);
        System.out.println(str + " and ");
        System.out.println(ch);
        int i=4,j=2;
        leftShift(i,j);
        System.out.println(i);
        // good and gbc 这里考的就是其实就java的方法是值传还是引用传递
    }

    public void change(String str, char[] ch) {
        str = "test ok";
        ch[0] = 'g';
    }

    public static void leftShift(int i, int j) {
        i <<= j;
        System.out.println("i <<= j val:" + i);
    }

    /**
     * 函数：是一种规则，只要输入一至，输出结果一至。
     * 静态方法 是一个合格的函数。
     *
     * @param num 入参
     * @return 结果
     */
    static int add(int num) {
        return num + num;
    }

    static int add(int num,int num1) {
        return num + num1;
    }

    /**
     *  函数规则定义
     */
    interface LambdaAdd {
        int add(int a, int b);
    }

    /**
     * <p>
     *     函数行为化原始逻辑
     * </p>
     * @param list 入参
     * @return 符合结果的list
     */
    static List<ProductVO> filter(List<ProductVO> list) {
        List<ProductVO> ret = new ArrayList<>();
        for (ProductVO vo : list) {
            // 商品数为偶数才反回 这里是一种规则 ,满足规则就返回boolean
            if (vo.getNum() % 2 == 0) {
                ret.add(vo);
            }
        }
        return ret;
    }

    /**
     *  <p>
     *      满足商品偶数据过滤的函数定义
     *  </p>
     */
    interface LambdaPro {
        boolean test(ProductVO vo);
    }
    /**
     * <p>
     *     函数行为化
     *     lambda定义(函数)： 参数-> 逻辑部分
     *     provo -> vo.getNum() % 2 == 0
     * </p>
     * @param list 入参
     * @return 符合结果的list
     */
    static List<ProductVO> filterLam(List<ProductVO> list,LambdaPro lambdaPro) {
        List<ProductVO> ret = new ArrayList<>();
        for (ProductVO vo : list) {
            // 商品数为偶数才反回 这里是一种规则,规则行为参数化，由外部传入，可变形增强
            if (lambdaPro.test(vo)) {
                // 调用test后才执行定定义的行为逻辑
                ret.add(vo);
            }
        }
        return ret;
    }

    /**
     * <p>
     *     函数接口类型定义
     * </p>
     * @param <T> 泛型 更通用
     */
    @FunctionalInterface
    interface Type1<T> {
        T test();
    }

    static List<Integer> filterNum (List<Integer> list) {
        List<Integer> ret = new ArrayList<>();
        for (Integer num1 : list) {
            // 商偶数才反回 这里是一种规则 ,满足规则就返回boolean
            if ((num1 & 1) == 0) {
                ret.add(num1);
            }
        }
        return ret;
    }

    static List<Integer> filterNumPredicate (List<Integer> list, Predicate<Integer> predicate) {
        List<Integer> ret = new ArrayList<>();
        for (Integer num1 : list) {
            // 商偶数才反回 这里是一种规则 ,满足规则就返回boolean
            if (predicate.test(num1)) {
                ret.add(num1);
            }
        }
        return ret;
    }

    static List<Integer> supply (int count) {
        List<Integer> ret = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            // 生成随机数据
            ret.add(ThreadLocalRandom.current().nextInt());
        }
        return ret;
    }

    static List<Integer> supply(int count, Supplier<Integer> integerSupplier) {
        List<Integer> ret = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            // 生成随机数据 形为参数化 （)-> ThreadLocalRandom.current().nextInt()
            ret.add(integerSupplier.get());
        }
        return ret;
    }
    // 类名::静态方法

    /**
     *
     * <p>
     *     类名::静态方法
     *     FunTest::printPO 方法引用来替换lambda函数表达式对象
     *     po -> System.out.println(po)
     *
     * </p>
     * @param vo vo
     */
    static void printPO(ProductVO vo){
        System.out.println(vo);
    }

    interface Type2<T, R> {
        R abc(T t);
    }
}

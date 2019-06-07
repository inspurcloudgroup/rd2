# Lambda #

Lambda 表达式是 Java SE 8 中一个重要的新特性。Lambda 表达式允许你通过表达式来代替功能接口。本节将重点讲解 Lambda 相关知识内容。

## 什么是函数式编程 ##
函数式编程（英语：functional programming）或称函数程序设计，又称泛函编程，是一种编程典范，它将计算机运算视为数学上的函数计算，并且避免使用程序状态以及易变对象。函数编程语言最重要的基础是λ演算（lambda calculus）。而且λ演算的函数可以接受函数当作输入（引数）和输出（传出值）。

## Lambda 表达式 ##
一个 Lambda 表达式具有下面这样的语法特征。它由三个部分组成：第一部分为一个括号内用逗号分隔的参数列表，参数即函数式接口里面方法的参数；第二部分为一个箭头符号：->；第三部分为方法体，可以是表达式和代码块。语法如下：

parameter -> expression body
下面列举了 Lambda 表达式的几个最重要的特征：

- 可选的类型声明：你不用去声明参数的类型。编译器可以从参数的值来推断它是什么类型。
- 可选的参数周围的括号：你可以不用在括号内声明单个参数。但是对于很多参数的情况，括号是必需的。
- 可选的大括号：如果表达式体里面只有一个语句，那么你不必用大括号括起来。
- 可选的返回关键字：如果表达式体只有单个表达式用于值的返回，那么编译器会自动完成这一步。若要指示表达式来返回某个值，则需要使用大括号。


## 编程实例 ##

在/home/project/新建一个类LamdbaTest.java
```
public class LamdbaTest {
    public static void main(String args[]){
        LamdbaTest tester = new LamdbaTest();

          // 带有类型声明的表达式
          MathOperation addition = (int a, int b) -> a + b;

          // 没有类型声明的表达式
          MathOperation subtraction = (a, b) -> a - b;

          // 带有大括号、带有返回语句的表达式
          MathOperation multiplication = (int a, int b) -> { return a * b; };

          // 没有大括号和return语句的表达式
          MathOperation division = (int a, int b) -> a / b;

          // 输出结果
          System.out.println("10 + 5 = " + tester.operate(10, 5, addition));
          System.out.println("10 - 5 = " + tester.operate(10, 5, subtraction));
          System.out.println("10 x 5 = " + tester.operate(10, 5, multiplication));
          System.out.println("10 / 5 = " + tester.operate(10, 5, division));

          // 没有括号的表达式            
          GreetingService greetService1 = message ->
          System.out.println("Hello " + message);

          // 有括号的表达式            
          GreetingService greetService2 = (message) ->
          System.out.println("Hello " + message);

          // 调用sayMessage方法输出结果
          greetService1.sayMessage("Shiyanlou");
          greetService2.sayMessage("Classmate");
       }

       // 下面是定义的一些接口和方法

       interface MathOperation {
          int operation(int a, int b);
       }

       interface GreetingService {
          void sayMessage(String message);
       }

       private int operate(int a, int b, MathOperation mathOperation){
          return mathOperation.operation(a, b);
       }
}
```

编译运行
```
$ javac LamdbaTest.java
$ java LamdbaTest
10 + 5 = 15
10 - 5 = 5
10 x 5 = 50
10 / 5 = 2 
Hello Shiyanlou
Hello Classmate
```
需要注意的是：

- Lambda 表达式优先用于定义功能接口在行内的实现，即单个方法只有一个接口。在上面的例子中，我们用了多个类型的 Lambda 表达式来定义 MathOperation 接口的操作方法。然后我们定义了 GreetingService 的 sayMessage 的实现。
- Lambda 表达式让匿名类不再需要，这为 Java 增添了简洁但实用的函数式编程能力。


## 作用域 ##

我们可以通过下面这段代码来学习 Lambda 的作用域。请将代码修改至如下这些：
```
public class LamdbaTest {
        final static String salutation = "Hello "; //正确，不可再次赋值
        //static String salutation = "Hello "; //正确，可再次赋值
        //String salutation = "Hello "; //报错
        //final String salutation = "Hello "; //报错

    public static void main(String args[]){
        //final String salutation = "Hello "; //正确，不可再次赋值
        //String salutation = "Hello "; //正确，隐性为 final , 不可再次赋值

        // salution = "welcome to "  
        GreetingService greetService1 = message -> 
        System.out.println(salutation + message);
        greetService1.sayMessage("Shiyanlou");
    }

    interface GreetingService {
       void sayMessage(String message);
    }
}
```

编译运行
```
$ javac LamdbaTest.java
$ java LamdbaTest
Hello Shiyanlou
```

可以得到以下结论：

- 可访问 static 修饰的成员变量，如果是 final static 修饰，不可再次赋值，只有 static 修饰可再次赋值；
- 可访问表达式外层的 final 局部变量（不用声明为 final，隐性具有 final 语义），不可再次赋值。

![图片描述](https://dn-simplecloud.shiyanlou.com/courses/uid1080089-20190607-1559916524864)

## 方法引用 ##

方法引用提供了一个很有用的语义来直接访问类或者实例的已经存在的方法或者构造方法。

方法引用可以通过方法的名字来引用其本身。方法引用是通过::符号（双冒号）来描述的。

它可以用来引用下列类型的方法：

- 构造器引用。语法是 Class::new，或者更一般的 Class< T >::new，要求构造器方法是没有参数；
- 静态方法引用。语法是 Class::static_method。
- 特定类的任意对象方法引用。它的语法是 Class::method。
- 特定对象的方法引用，它的语法是 instance::method。


下面是一个简单的方法引用的例子。
```
// LamdbaTest.java
import java.util.List;
import java.util.ArrayList;

public class LamdbaTest {

    public static void main(String args[]){
        List<String> names = new ArrayList<>();

        names.add("Peter");
        names.add("Linda");
        names.add("Smith");
        names.add("Zack");
        names.add("Bob");

        //     通过System.out::println引用了输出的方法
        names.forEach(System.out::println);
    }
}
```

编译并运行:
```
$ javac LamdbaTest.java
$ java LamdbaTest
Peter
Linda
Smith
Zack
Bob
```

## 函数式接口 ##
函数式接口是只包含一个方法的接口。例如，带有单个compareTo方法的比较接口，被用于比较的场合。Java 8 开始定义了大量的函数式接口来广泛地用于 lambda 表达式。

## java.util.function ##
java.util.function 包中包含了大量的函数式接口，基本可以满足我们的日常开发需求。


## 相关的接口及描述 ##

下面是部分函数式接口的列表。

接口	描述
BitConsumer<T,U>	该接口代表了接收两个输入参数 T、U，并且没有返回的操作
BiFunction<T,U,R>	该接口代表提供接收两个参数 T、U，并且产生一个结果 R 的方法
BinaryOperator<T>	代表了基于两个相同类型的操作数，产生仍然是相同类型结果的操作
BiPredicate<T,U>	代表了对两个参数的断言操作（基于 Boolean 值的方法）
BooleanSupplier	代表了一个给出 Boolean 值结果的方法
Consumer<T>	代表了接受单一输入参数并且没有返回值的操作
DoubleBinaryOperator	代表了基于两个 Double 类型操作数的操作，并且返回一个 Double 类型的返回值
DoubleConsumer	代表了一个接受单个 Double 类型的参数并且没有返回的操作
DoubleFunction<R>	代表了一个接受 Double 类型参数并且返回结果的方法
DoublePredicate	代表了对一个 Double 类型的参数的断言操作
DoubleSupplier	代表了一个给出 Double 类型值的方法
DoubleToIntFunction	代表了接受单个 Double 类型参数但返回 Int 类型结果的方法
DoubleToLongFunction	代表了接受单个 Double 类型参数但返回 Long 类型结果的方法
DoubleUnaryOperator	代表了基于单个 Double 类型操作数且产生 Double 类型结果的操作
Function<T,R>	代表了接受一个参数并且产生一个结果的方法
IntBinaryOperator	代表了对两个 Int 类型操作数的操作，并且产生一个 Int 类型的结果
IntConsumer	代表了接受单个 Int 类型参数的操作，没有返回结果
IntFunction<R>	代表了接受 Int 类型参数并且给出返回值的方法
IntPredicate	代表了对单个 Int 类型参数的断言操作
更多的接口可以参考 Java 官方 API 手册：java.lang.Annotation Type FunctionalInterface。在实际使用过程中，加有@FunctionalInterface注解的方法均是此类接口，位于java.util.Funtion包中。

## 编程实例 ##

下面我们通过一个例子学习如何使用这些函数式编程的接口。

在/home/project/目录下新建一个类FunctionTest.java
```
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class FunctionTest {
   public static void main(String args[]){
      List<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

      System.out.println("All of the numbers:");

      eval(list, n->true);

      System.out.println("Even numbers:");
      eval(list, n-> n%2 == 0 );

      System.out.println("Numbers that greater than  5:");
      eval(list, n -> n > 5 );
   }

   public static void eval(List<Integer> list, Predicate<Integer> predicate) {
      for(Integer n: list) {

         if(predicate.test(n)) {
            System.out.println(n);
         }
      }
   }
}
```

编辑完成后，编译运行。
```
$ javac FunctionTest.java  
$ java FunctionTest  
All of the numbers:
0
1
2
3
4
5
6
7
8
9
Even numbers:
0
2
4
6
8
Numbers that greater than  5:
6
7
8
9
```

## Predicate ##
Predicate 是 Java 提供的一个函数式接口，他接受一个参数 t，并执行断言操作返回一个 boolean。接口内容如下（这里没有列出接口中提供的默认方法）：
```
@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);
}
```

## 编程实例 ##

在/home/project/目录下创建一个源代码文件PredicateDemo.java。
```
import java.util.Random;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class PredicateDemo {
    public static void main(String[] args) {
        //简单使用  判断a是否大于5
        Predicate<Integer> predicate = a -> a > 50;
        System.out.println(predicate.test(52));
        //如果只断言int类型，可以直接使用 IntPredicate
        IntPredicate intPredicate = a -> a > 50;
//        System.out.println(intPredicate.test(50));
        IntStream.of(10,11,44,59,46,55,99,88,50)
                //结合filter过滤数字 小于或等于50的数字被过滤
                .filter(intPredicate)
                .peek(System.out::println).count();
    }
}
```

编译运行：
```
$ javac PredicateDemo.java
$ java PredicateDemo
true
59
55
99
88
```

## 练习题：lambda ##
在/home/project/目录下新建一个源代码文件LambdaTest.java，你需要完成以下要求：

- 建立一个数组1, 23, 4, 4, 22, 34, 45, 11, 33
- 使用 lambda 求出数组中的最小数
- 将数组去重，并将去重后数组的每个元素乘以 2，再求出乘以 2 后的数组的和，比如数组1,2,3,3，去重后为1,2,3，乘以 2 后为2,4,6，最后的和为12。

代码
```

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class LambdaTest {

	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(1, 23, 4, 4, 22, 34, 45, 11, 33);
		Comparator<Integer> comparator = (Integer i1, Integer i2) -> i1.compareTo(i2);
		list.sort(comparator);
		System.out.println(list.get(0));

		List<Integer> list2 = new ArrayList<>();
		min(list, list2, n -> list2.contains(n));
		
		IntSummaryStatistics stats = list2.stream().mapToInt((x) -> 2*x)
                .summaryStatistics();
		System.out.println(stats.getSum());
		

	}

	public static void min(List<Integer> list, List<Integer> list2, Predicate<Integer> predicate) {
		for (Integer n : list) {
			if (!predicate.test(n)) {
				list2.add(n);
			}
		}
	}



	

}


```




























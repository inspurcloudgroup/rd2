C++学习
1、在window系统上C++编辑环境安装VS,在控制台上成功运行第一个程序，了解VS环境各项的作用，包括源文件、头文件的创建
2、安装gcc，并配置环境变量，在命令空间成功运行CPP脚本（gcc main.cpp）
3、学习基本语法
（1）头文件：包含了程序中必需的或有用的信息。如#inclode<iostream>
（2）using namespace std; std:命名空间
（3）int main(){}定义主函数
（4）分号：语句结束符，表明一个逻辑实体的结束。
     如:x = y + 1
     语句块：一组使用大括号括起来的按逻辑连接的语句
     如：{x=0;y=1;}
（5）标识符
     用来表示变量、函数、类、模块、项目
	 区分大小写，字母、数字、下划线组成，但数字不能开头
（6）关键字
		asm
		else
		new
		this
		auto
		enum
		operator
		throw
		bool
		explicit
		private
		true
		break
		export
		protected
		try
		case
		extern
		public
		typedef
		catch
		false
		register
		typeid
		char
		float
		reinterpret_cast
		typename
		class
		for
		return
		union
		const
		friend
		short
		unsigned
		const_cast
		goto
		signed
		using
		continue
		if
		sizeof
		virtual
		default
		inline
		static
		void
		delete
		int
		static_cast
		volatile
		do
		long
		struct
		wchar_t
		double
		mutable
		switch
		while
		dynamic_cast
		namespace
		template
（7）数据类型
布尔型 bool
字符型 char
整型 int
浮点型 float
双浮点型 double
无类型 void
宽字符型 wchar_t    //相当于short int
typedef :为一个已有的类型取一个新的名字。
如：typedef int feet;
枚举类型：如果一个变量只有几种可能的值，可以定义为枚举类型
格式：enum 枚举名{标识符[=整形常数]，标识符[=整型常数],...}枚举变量；
（8）变量类型
定义：type variable1,variable2,variable3;
变量可以在声明的时候被初始化：type variable1 = value;type variable1 = funv();
(9) 变量的作用域
全局变量：在所有函数外部声明的变量
局部变量：在函数或一个代码块内部声明的变量
形式参数：在函数参数的定义中声明的变量
（10）定义常量
#define 常量名 常量值
const 常量类型 常量名 = 常量值；
（11）存储类型
auto:声明变量时根据初始化表达式自动推断该变量的类型、声明函数时函数返回值的占位符。
static:指示编译器在程序的生存周期内保持局部变量的存在，而不需要在每次进入和离开作用域时进行创建和销毁；修饰全局变量时，会使变量的作用域限制在声明它的文件内
extern: 用来在另一个文件中声明一个全局变量或函数。如：extern int write_extern();
//函数声明：返回值类型 函数名称（参数类型 参数名称，参数类型 参数名称）
（12）运算符
算术运算符：
+ 把两个操作数相加
- 第一个操作数中减去第二个操作数
* 两个操作数相乘
/ 子除以分母
% 模运算符，整除后的余数
++ 增运算符，整数值增加 1
-- 减运算符，整数值减少 1
关系运算符：
== 查两个操作数的值是否相等，如果相等则条件为真。
!= 查两个操作数的值是否相等，如果不相等则条件为真。
> 查左操作数的值是否大于右操作数的值，如果是则条件为真。
< 查左操作数的值是否小于右操作数的值，如果是则条件为真。
>= 查左操作数的值是否大于或等于右操作数的值，如果是则条件为真。
<= 查左操作数的值是否小于或等于右操作数的值，如果是则条件为真。
逻辑运算符：
&& 为逻辑与运算符。如果两个操作数都非零，则条件为真。
|| 为逻辑或运算符。如果两个操作数中有任意一个非零，则条件为真。
! 为逻辑非运算符。用来逆转操作数的逻辑状态。如果条件为真则逻辑非运算符将使其为假
位运算符：&、 | 、^异或、 左移（A<<2）、>>右移（A>>2）、~(翻转)
赋值运算符：
=简单的赋值运算符，把右边操作数的值赋给左边操作数C= A + B 将把 A + B 的值赋给 C
+=加且赋值运算符，把右边操作数加上左边操作数的结果赋值给左边操作数C += A 相当于 C = C + A
-=减且赋值运算符，把左边操作数减去右边操作数的结果赋值给左边操作数C -= A 相当于 C = C - A
*=乘且赋值运算符，把右边操作数乘以左边操作数的结果赋值给左边操作数C *= A 相当于 C = C * A
/=除且赋值运算符，把左边操作数除以右边操作数的结果赋值给左边操作数C /= A 相当于 C = C / A
%=求模且赋值运算符，求两个操作数的模赋值给左边操作数C %= A 相当于 C = C % A
<<=左移且赋值运算符C <<= 2 等同于 C = C << 2
>>=右移且赋值运算符C >>= 2 等同于 C = C >> 2
&=按位与且赋值运算符C &= 2 等同于 C = C & 2
^=按位异或且赋值运算符C ^= 2 等同于 C = C ^ 2
|=按位或且赋值运算符C |= 2 等同于 C = C | 2
杂项运算符：
sizeof:返回变量的大小 如：sizeof(a)
condition?x:Y 条件运算符 如果condition为真，则为X，否则为Y
, 逗号运算符 顺序执行一系列运算，最后逗号表达式的值是最后一个表达式的值
.和-> 成员运算符  用于引用类、结构体和共用体的成员
（）强制转换运算符 int(2.2)
&指针运算符 &a 获得变量a的实际地址
* 指针运算符,指向一个变量 *a 指向变量a
（13）基本的输入输出<iostream> 定义了cin、cout、cerr和clog对象，分别对应于标准输入流、标准输出流、非缓冲流和缓冲标准错误流
（14）循环
for循环：for ( init; condition; increment )
{
   statement(s);
}
while循环：while(condition)
{
   statement(s);
}
do...while循环：do
{
   statement(s);

}while( condition );
嵌套循环：一个循环内可以嵌套另一个循环。C++ 允许至少 256 个嵌套层次。
控制语句：break;continue
（15）判断语句
if语句：
if(boolean_expression)
{
   // 如果布尔表达式为真将执行的语句
}
if else语句：
if(boolean_expression)
{
   // 如果布尔表达式为真将执行的语句
}
else
{
   // 如果布尔表达式为假将执行的语句
}
switch语句：
switch(expression){
    case constant-expression  :
       statement(s);
       break; // 可选的
    case constant-expression  :
       statement(s);
       break; // 可选的
  
    // 您可以有任意数量的 case 语句
    default : // 可选的
       statement(s);
}
(16)数组
存储一个固定大小的相同类型元素的顺序集合。数组中的特定元素可以通过索引访问；所有的数组都是由连续的内存位置组成。
声明：type arrayname[arraysize];
初始化数组：double balance[5] = {1,2,3,4,5};
访问数组元素：balance[3]
多维数组：type name[size1][size2]...[sizeN];
初始化二维数组：int a[3][4] = {{1,2,3,4},{5,6,7,8},{2,3,4,5}}
访问二维数组：a[2][3]
数组名是一个指向数组中第一个元素的常量指针。
数组作为形参：void 函数名（int arr[]）;调用：函数名(数组名)
从函数返回数组：return 数组名；
（17）字符串：string str1 = "";
(18)指针
用来存放地址的变量
声明：type *ip   ,ip是一个指针变量，其内存放另一个变量的地址
赋值：ip = &var；ip = array
*ip等价于var
指向指针的指针：int **var;
指针可以传给函数：void getseconds(int *var) 传参数时可以传变量地址，也可以传数组 getseconds(数组名/&变量名)
NULL空指针：在变量声明的时候，如果没有确切的地址可以赋值，为指针变量赋一个 NULL 值是一个良好的编程习惯。赋为 NULL 值的指针被称为空指针。
NULL 指针是一个定义在标准库中的值为零的常量
（19）引用
引用变量是已存在变量的另一个名字，因此引用变量不存在空引用，创建时被初始化
如：int& r = i;
将引用变量传递给形参，可以通过改变形参改变实参的值；将变量的值传递给形参，改变形参不可以改变实参的值；将变量的地址传递给形参（形参是指针变量），通过改变形参可以改变实参的值
（20）数据结构
结构体声明：
struct type_name {
member_type1 member_name1;
member_type2 member_name2;
member_type3 member_name3;
.
.
} object_names;
定义结构体变量：type_name 实例名字；
对结构体变量赋值：实力名字.member_name1 = value；
将结构体作为函数参数传递：func(实例名字)     函数定义： void 函数名(struct typename 形参名字)
指向结构的指针:strucy typename *struct_pointer;    指向 struct_pointer = &实例名字 ；访问结构体的成员：struct_pointer->member_name1
4、nullptr:用来区分空指针、0；NULL既可以代表空指针又可以代表0，nullptr只代表空指针

#include <iostream>
void foo(char*);
void foo(int);
int main() {

	if (NULL == 0) std::cout << "NULL == 0" << std::endl;
	else std::cout << "NULL != 0" << std::endl;


	return 0;
}
运行结果：NULL==0
#include <iostream>
void foo(char*);
void foo(int);
int main() {

	if (NULL == (void*)0) std::cout << "NULL == 0" << std::endl;
	else std::cout << "NULL != 0" << std::endl;


	return 0;
}
运行结果：NULL==0
5、关键字 constexpr 用来明确的告诉编译器应该去验证函数len_foo是否是一个常数
constexpr int len_foo(){return 5;}
int main(){char arr[len_foo()+2]}
6、类型推导
auto:无法用于函数传参和推到数组，只能对变量进行推导，C++14之后，可以让普通函数具有返回推导
如：auto add(int x,int y){return x+y}
decltype:推导表达式的类型，decltype(x+y)会返回x+y的类型
7、区间迭代
基于范围的for循环：像python一样简洁的for循环
int main() {
	int array[] = { 1,2,3,4 };
	for (auto& x : array) {
		std::cout << x << std::endl;
	}
}
运行结果：1 2 3 4
8、初始化列表
（1）用于对象构造
列表初始化：int arr[3] = {1,2,3}
初始化列表:std::initializer_list<>
具有初始化列表的构造函数被称为初始化列表构造函数
如：class Magic{public:Magic(std::initializer_list<int> list){}};
（2）用于普通函数的形参
如：
void function(std::initializer_list<int> list){return;}
function({1,2,3,4})
（3）统一的语法来初始化任意对象
struct A {int a;float b;}
A c {1,1.1}
运行结果:std::cout<<c.a<<endl; 1
std::cout<<c.b<<endl; 1.1
9、模板增强
（1）外部模板,能够显示的告诉编译器何时进行模板的实例化：extern template class std::vector<double>;
（2）类型别名模板：模板是用来产生类型的。
template<typename T,typename U,int value>

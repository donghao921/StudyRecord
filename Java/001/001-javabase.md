### ***基本数据类型***
* bit 和 byte
    > 一个二进制的位叫做一个 bit，网络带宽中的单位，都是 bit;

    > 八个二进制位组成一个 byte，硬盘等存储的单位，都是 byte;

    > byte 是计算机中基本的衡量存储的单位，计算机对外使用时不会用 bit 作为划分存储的单位。
* 整数类型
    > byte 占用1个 byte；值域是 -128~127

    > short 占用2个 byte；值域是 -32768~32767

    > int 占用4个 byte；**Java中默认的整数类型是 int**

    > long 占用8个 byte； 数值以 L 结尾，例如：1000L；
* 浮点类型(小数)
    > float 占用4个 byte；有精度； 数值以 f/F 结尾，例如： 1.23f；

    > double 占用8个 byte；精度是 float 的两倍；**Java中默认的浮点类型是 double**
* 布尔类型和字符数据类型
    > booblean 占用1个 byte，值域是 true 或者 false

    > char 占用2个 byte，值域是所有字符
* 数据类型的自动转换
    > 低精度类型可自动向高精度类型转换

    > double > float > long > int > short > byte

    > char 可以转换成int，但是同样为两个byte，char因为是无符号类型，超出了short的取值范围，所以不能向short自动转换
* 强制数据类型转换
    > 高精度数值向低精度数值的转换，需要强制转换

    > 强制类型转换也是一种操作符

    > 语法是用小括号括起来的目标类型 放在被转换的值的前面

    > 强制转换会造成数据精度丢失

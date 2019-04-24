/*
 * Copyright (c) 2018  南京瑞玥科技有限公司. All Rights Reserved.
 */ 
package main.source.common.random;

import java.security.SecureRandom;
import java.util.Arrays;

/** 
 * @description 了解SecureRandom对象
 * @author 陈吕奖
 * @date 2018-08-29 
 */
public class SecureRandomDemo {

    public static void main(String[] args) throws Exception {
        //通过new来初始化，默认来说会使用NativePRNG算法生成随机数
        SecureRandom generater = new SecureRandom(); 
        System.out.println(generater.nextInt(37));//随机[0~37)的数字
        /*
         * 可以通过getInstance来初始化对象:
         * 其中需要传参的方法,则传算法名即可,如果不存在算法会抛出异常;
         * 另外需要传参,传两个参数的,第二个参数还可以指定算法程序包。
         */
        SecureRandom secureRandom3 = SecureRandom.getInstance("SHA1PRNG");
        SecureRandom secureRandom2 = SecureRandom.getInstance("SHA1PRNG", "SUN");
        
        /*
         * 方法介绍  nextBytes(byte[] bytes)
         * Typical callers of SecureRandom invoke the following methods to retrieve random bytes: 
         * 可以获取随机的一个byte数组,注意这里不是返回,这个方法是void返回类型,是直接随机改变了test
         */
        SecureRandom random = new SecureRandom();
        byte[] test = new byte[20];
        random.nextBytes(test);
        System.out.println(Arrays.toString(test));
        
        /*
         * generateSeed(int numBytes)
         * 通常,也可以使用generateSeed方法,来获取一个随机的byte数组,
         * 这个数组中的数通常可以用来做其他随机生成器的种子
         */
        byte seed[] = random.generateSeed(20);//数组的另一种定义方法
        System.out.println(Arrays.toString(seed));
    }
}
/*
 * 1.我们知道Random类中实现的随机算法是伪随机,也就是有规则的随机.在进行随机时,随机算法的起源数字称为种子数(seed),
 * 在种子数的基础上进行一定的变换,从而产生需要的随机数字.
 * 相同种子数的Random对象,相同次数生成的随机数字是完全相同的.也就是说,两个种子数相同的Random对象,生成的随机数字完全相同.
 * 所以在需要频繁生成随机数,或者安全要求较高的时候,不要使用Random,因为其生成的值其实是可以预测的.
 * 
 * 2.SecureRandom类提供加密的强随机数生成器 (RNG)
 * 当然,它的许多实现都是伪随机数生成器 (PRNG) 形式,这意味着它们将使用确定的算法根据实际的随机种子生成伪随机序列
 * 也有其他实现可以生成实际的随机数
 * 还有另一些实现则可能结合使用这两项技术
 * 
 * 3.SecureRandom和Random都是,也是如果种子一样,产生的随机数也一样:因为种子确定,随机数算法也确定,因此输出是确定的。
 * 只是说,SecureRandom类收集了一些随机事件,比如鼠标点击,键盘点击等等,SecureRandom 使用这些随机事件作为种子。
 * 这意味着,种子是不可预测的,而不像Random默认使用系统当前时间的毫秒数作为种子,有规律可寻。
 * 
 * 3、其他 关于种子seed获取思路
 * 
 * 4.产生高强度的随机数,有两个重要的因素:种子和算法.当然算法是可以有很多的,
 *   但是如何选择种子是非常关键的因素.
 *   如Random,它的种子是System.currentTimeMillis(),所以它的随机数都是可预测的.
 *   可预测有什么危险呢,可以看两个案例:
 *   《利用系统时间可预测破解java随机数》
 *   《当随机不够随机:一个在线扑克游戏的教训》
 *   那么如何得到一个近似随机的种子?这里有一个思路:
 *   收集计算机的各种信息,如键盘输入时间,CPU时钟,内存使用状态,硬盘空闲空间,IO延时,
 *   进程数量,线程数量等信息,来得到一个近似随机的种子
 *   这样的话,除了理论上有破解的可能,实际上基本没有被破解的可能.而事实上,
 *   现在的高强度的随机数生成器都是这样实现的
 */
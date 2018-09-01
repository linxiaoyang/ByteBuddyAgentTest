package bytebuddy.test.interceptor;


import com.alibaba.fastjson.JSON;
import net.bytebuddy.asm.Advice;

/**
 * @author olifer
 */
public class ToStringInterceptor2 {

    /**
     *
     * @param obj
     * @return
     *
     * @see Advice.This    对象
     */
    @Advice.OnMethodEnter
    public static String enter(
            @Advice.This Object obj
    ) {
        return JSON.toJSONString(obj);
    }

    /**
     * @param str * @see Argument
     * @Advice.Enter 是@Advice.OnMethodEnter的返回值
     * @see Advice.This    对象
     * @see Advice.Enter   使用这个注解注入的信息是@Advice.OnMethodEnter的返回值
     * @see Advice.Return  方法返回值
     * @see Advice.Thrown  抛出来的异常
     */
    @Advice.OnMethodExit(onThrowable = RuntimeException.class)
    public static void exit(
            @Advice.Enter String str,
            @Advice.This Object obj,
            @Advice.Return String returnString,
            @Advice.Thrown Throwable throwable
    ) {

        System.out.println("exit:" + str);
        System.out.println("exit:" + JSON.toJSONString(obj));
        System.out.println("exit:" + returnString);
        System.out.println("exit:" + throwable);
    }


}

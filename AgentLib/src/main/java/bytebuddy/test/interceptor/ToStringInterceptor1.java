package bytebuddy.test.interceptor;

import com.alibaba.fastjson.JSON;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;


/**
 * @author olifer
 */
public class ToStringInterceptor1 {

    /**
     * @param zuper  @This注解注入的是被拦截方法此时的对象
     * @param client @SuperCall 注入的是原来方法调用的结果，但是是被Callable封装的，一调用call方法，原来的方法就得以执行
     * @param args   @AllArguments 注入的是方法所有的参数
     * @param method @Origin注入的是被拦击的方法对象
     * @return
     * @throws Exception
     */
    public static String doToString(
            @This Object zuper,
            @SuperCall Callable<String> client,
            @AllArguments Object[] args,
            @Origin Method method
    ) throws Exception {
        System.out.println(client.call());
        System.out.println(method.getName());
        System.out.println(args.length);
        return JSON.toJSONString(zuper);
    }
}

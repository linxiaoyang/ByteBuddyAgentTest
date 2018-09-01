package bytebuddy.test.interceptor;


import bytebuddy.test.agent.ToStringAgent3;
import net.bytebuddy.asm.Advice;

/**
 * @author olifer
 */
public class ToStringInterceptor3 {


    @Advice.OnMethodEnter
    public static void enter(
            @ToStringAgent3.CountAdviceTransformer.CountSignature String sign
    ) {
        System.out.println(sign);
    }


}

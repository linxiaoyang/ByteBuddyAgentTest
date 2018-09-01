package bytebuddy.test.agent;

import bytebuddy.test.ToString;
import bytebuddy.test.interceptor.ToStringInterceptor1;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.pool.TypePool;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.*;


/**
 * @author olifer
 * <p>
 */
public class ToStringAgent1 {
    /**
     * @param args
     * @param instrumentation
     */
    public static void premain(String args, Instrumentation instrumentation) {
        new AgentBuilder.Default()
                //拦截类上打了ToString注解的类
                .type(isAnnotatedWith(ToString.class))
                .transform(new AgentBuilder.Transformer() {
                    @Override
                    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader) {
                        //拦击调用的方法名称是toString,拦截到之后使用ToStringInterceptor1进行处理
                        return builder.method(named("toString"))
                                .intercept(MethodDelegation.to(ToStringInterceptor1.class));

                    }
                }).installOn(instrumentation);
    }

    public static void agentmain(String args, Instrumentation instrumentation) {
        premain(args, instrumentation);
    }





}

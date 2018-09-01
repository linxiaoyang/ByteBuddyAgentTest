package bytebuddy.test.agent;

import bytebuddy.test.ToString;
import bytebuddy.test.interceptor.ToStringInterceptor2;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.*;


/**
 * @author olifer
 *
 *
 * 只能拦截get方法？ 返回值不为空的方法
 */
public class ToStringAgent2 {
    /**
     * @param args
     * @param instrumentation
     */
    public static void premain(String args, Instrumentation instrumentation) {
        new AgentBuilder.Default()
                .type(isAnnotatedWith(ToString.class))
                .transform(new AgentBuilder.Transformer() {
                    @Override
                    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader) {
                        return builder
                                .visit(Advice.to(ToStringInterceptor2.class).on(named("toString")));
                    }
                }).installOn(instrumentation);

        ;

    }


}

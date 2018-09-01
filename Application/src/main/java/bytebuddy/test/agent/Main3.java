package bytebuddy.test.agent;

import bytebuddy.test.ToString;
import bytebuddy.test.interceptor.ToStringInterceptor1;
import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;

import java.io.IOException;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class Main3 {
    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        ByteBuddyAgent.install();
        new AgentBuilder.Default()
                .type(isAnnotatedWith(ToString.class))
                .transform(new AgentBuilder.Transformer() {
                    @Override
                    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader) {
                        return builder.method(named("toString"))
                                .intercept(MethodDelegation.to(ToStringInterceptor1.class));
                    }
                }).installOnByteBuddyAgent();


        Foo foo = new Foo();
        foo.setAge("23");
        foo.setName("小太阳");
        System.out.println(foo);
    }
}

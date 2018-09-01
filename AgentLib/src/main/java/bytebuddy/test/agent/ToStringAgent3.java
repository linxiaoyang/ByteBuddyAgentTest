package bytebuddy.test.agent;

import bytebuddy.test.Count;
import bytebuddy.test.ToString;
import bytebuddy.test.interceptor.ToStringInterceptor2;
import bytebuddy.test.interceptor.ToStringInterceptor3;
import com.alibaba.fastjson.JSON;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.asm.AsmVisitorWrapper;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatcher;

import java.lang.annotation.*;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith;
import static net.bytebuddy.matcher.ElementMatchers.isPublic;
import static net.bytebuddy.matcher.ElementMatchers.named;


/**
 * @author olifer
 * <p>
 * <p>
 * 只能拦截get方法？ 返回值不为空的方法
 */
public class ToStringAgent3 {
    /**
     * @param args
     * @param instrumentation
     */
    public static void premain(String args, Instrumentation instrumentation) {
        new AgentBuilder.Default()
                .type(isAnnotatedWith(ToString.class))
                .transform(new CountAdviceTransformer()).installOn(instrumentation);

    }


    public static class CountAdviceTransformer implements AgentBuilder.Transformer {


        @Override
        public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader) {
            ClassFileLocator.Compound compound = new ClassFileLocator.Compound(
                    ClassFileLocator.ForClassLoader.of(classLoader),
                    ClassFileLocator.ForClassLoader.ofClassPath());

            AsmVisitorWrapper.ForDeclaredMethods advice = registerDynamicValues()
                    .to(ToStringInterceptor3.class).on(description(compound));

            return builder.visit(advice);
        }

        protected ElementMatcher<? super MethodDescription.InDefinedShape> description(
                ClassFileLocator.Compound compound) {
            return isPublic().and(isAnnotatedWith(Count.class));
        }

        public abstract class MyAgentDynamicValue<T extends Annotation> implements Advice.DynamicValue<T> {
            public abstract Class<T> getAnnotationClass();
        }

        private Advice.WithCustomMapping registerDynamicValues() {
            List<MyAgentDynamicValue<?>> dynamicValues = getDynamicValues();
            Advice.WithCustomMapping withCustomMapping = Advice.withCustomMapping();
            for (MyAgentDynamicValue dynamicValue : dynamicValues) {
                withCustomMapping = withCustomMapping.bind(dynamicValue.getAnnotationClass(), dynamicValue);
            }
            return withCustomMapping;
        }

        public List<MyAgentDynamicValue<?>> getDynamicValues() {
            return Collections.<MyAgentDynamicValue<?>>singletonList(new MetricsDynamicValue());
        }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.PARAMETER)
        public @interface CountSignature {
        }

        public class MetricsDynamicValue extends MyAgentDynamicValue<CountSignature> {

            @Override
            public Object resolve(MethodDescription.InDefinedShape instrumentedMethod,
                                  ParameterDescription.InDefinedShape target,
                                  AnnotationDescription.Loadable<CountSignature> annotation, boolean initialized) {

                Count count = instrumentedMethod.getDeclaredAnnotations().ofType(Count.class).loadSilent();
                InnerClass innerClass = new InnerClass();
                innerClass.setName(count.name());
                return JSON.toJSONString(innerClass);
            }

            @Override
            public Class<CountSignature> getAnnotationClass() {
                return CountSignature.class;
            }
        }


        public static class InnerClass {

            private String name;



            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }


        }


    }


}

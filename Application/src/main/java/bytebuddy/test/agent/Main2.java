package bytebuddy.test.agent;

import com.sun.tools.attach.*;

import java.io.IOException;
import java.util.List;

/**
 * @author olifer
 */
public class Main2 {
    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {


        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : list) {
            if (vmd.displayName().endsWith("Main2")) {
                VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
                virtualMachine.loadAgent("/Users/olifer/.m2/repository/ByteBuddyAgentTest/AgentLib/1.0-SNAPSHOT/AgentLib-1.0-SNAPSHOT.jar", "argument for agent");
                virtualMachine.detach();
            }
        }


        Foo foo = new Foo();
        foo.setAge("23");
        foo.setName("小太阳");
        System.out.println(foo.toString());
    }
}

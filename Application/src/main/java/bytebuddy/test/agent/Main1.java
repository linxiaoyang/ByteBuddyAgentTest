package bytebuddy.test.agent;


public class Main1 {

    public static void main(String[] args) {
        Foo foo = new Foo();
        foo.setAge("23");
        foo.setName("小太阳");
//        System.out.println(foo.getName());
        System.out.println(foo.toString());
    }
}

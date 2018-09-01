package bytebuddy.test.agent;


import bytebuddy.test.Count;
import bytebuddy.test.ToString;

@ToString
public class Foo {

    private String name;

    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    @Count(name = "toString")
    @Override
    public String toString() {
        return "Foo{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}

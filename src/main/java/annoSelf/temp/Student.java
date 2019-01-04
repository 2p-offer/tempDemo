package annoSelf.temp;

import annoSelf.anno.Info;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 2P on 18-12-7.
 */

class Persons<T> {
}

public class Student<T> extends Persons<Student> {

    private String name;

    @Info(sex = "man")
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public static void main(String[] args) throws IllegalAccessException {
        Student student1 = new Student();
        Student student2 = new Student();
        student1.setName("wy");
        student1.setSex("man");
        student2.setName("w");
        student2.setSex("woman");
        List<Student> list = new ArrayList<>();
        list.add(student1);
        list.add(student2);
        for (Student student : list) {
            //getGenericSuperclass()获得带有泛型的父类
            //Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
            //ParameterizedType参数化类型，即泛型
            ParameterizedType actualTypeArguments = (ParameterizedType) student.getClass().getGenericSuperclass();
            //getActualTypeArguments获取参数化类型的数组，泛型可能有多个
            Class c = (Class) actualTypeArguments.getActualTypeArguments()[0];
            for (Field f : c.getDeclaredFields()) {
                Info annotation = f.getAnnotation(Info.class);
                //获取所有注解字段的值
                if (annotation != null&&annotation.sex().equals("man")) {
                    String sex=(String)f.get(student);
                    System.out.println(sex+"-----------------");
                }
            }
        }
    }
}

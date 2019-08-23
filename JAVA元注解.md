# JAVA元注解

## @Target

@Target说明了Annotation所修饰的对象范围：Annotation可被用于 packages、types（类、接口、枚举、Annotation类型）、类型成员（方法、构造方法、成员变量、枚举值）、方法参数和本地变量（如循环变量、catch参数）。在Annotation类型的声明中使用了target可更加明晰其修饰的目标。
作用：用于描述注解的使用范围（即：被描述的注解可以用在什么地方）

取值:
```java
public enum ElementType {
    /**用于描述类、接口(包括注解类型) 或enum声明 Class, interface (including annotation type), or enum declaration */
    TYPE,
 
    /** 用于描述域 Field declaration (includes enum constants) */
    FIELD,
 
    /**用于描述方法 Method declaration */
    METHOD,
 
    /**用于描述参数 Formal parameter declaration */
    PARAMETER,
 
    /**用于描述构造器 Constructor declaration */
    CONSTRUCTOR,
 
    /**用于描述局部变量 Local variable declaration */
    LOCAL_VARIABLE,
 
    /** Annotation type declaration */
    ANNOTATION_TYPE,
 
    /**用于描述包 Package declaration */
    PACKAGE,
 
    /**
     * 用来标注类型参数 Type parameter declaration
     * @since 1.8
     */
    TYPE_PARAMETER,
 
    /**
     *能标注任何类型名称 Use of a type
     * @since 1.8
     */
    TYPE_USE
```
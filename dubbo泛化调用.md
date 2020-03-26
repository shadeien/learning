## dubbo泛化调用需要几个参数：
```java
interface, 方法类名---String
method, 方法名----String

parameterTypes, 参数类型--- String[]
args, 参数----Object[]
Object $invoke(String method, String[] parameterTypes, Object[] args) throws GenericException;
```
研究发现parameterTypes不是必须的，如果这个参数传空, 在provider侧，会通过类反射查找当前方法需要的参数类型。注意： 如果有重载的方法，会导致找到多个匹配方法，无法确定是哪个方法，会抛异常

```java
GenericFilter.invoke
Method method = ReflectUtils.findMethodByMethodSignature(invoker.getInterface(), name, types);


ReflectUtils.findMethodByMethodSignature:
List<Method> finded = new ArrayList<Method>();
for (Method m : clazz.getMethods()) {
    if (m.getName().equals(methodName)) {
        finded.add(m);
 }
}
if (finded.isEmpty()) {
    throw new NoSuchMethodException("No such method " + methodName + " in class " + clazz);
}
if(finded.size() > 1) {
    String msg = String.format("Not unique method for method name(%s) in class(%s), find %d methods.",
 methodName, clazz.getName(), finded.size());
 throw new IllegalStateException(msg);
}
```
其余三个参数为必须传参。interface 和 method 没啥问题，通过客户端参数上传。

## 关于args的问题：

- 多参数顺序问题
- 多参数类型问题
- 泛型嵌套问题
- 特殊参数（userId，clientIP，mac）问题
- 方法重载问题

前两个问题是相似的，我们来看个例子:
void normal(String param1, long param2);
这是一个接口定义的方法，里面有两个参数，一个String，一个long

客户端传参示例1：
```json
{
    "data": {
        "param1": "3333",
        "param2": "adsad"
    },
    "method": "normal",
    "interface": "com.wwwarehouse.dubbo.TestApi"
}
```

示例2:
```json
{
    "data": {
        "param1": "adsad",
        "param2": "3333"
    },
    "method": "normal",
    "interface": "com.wwwarehouse.dubbo.TestApi"
}
```
结果：

示例1：
```java
com.alibaba.dubbo.remoting.RemotingException: java.lang.NumberFormatException: For input string: "adsad"
```
示例2：

正常返回

复杂对象示例：
```java
public void normal3(Model<SubModel> param1, long param2);
传参1：

{
    "data": {
        "param1": {
            "collection": [
                "sdsa",
                "ssss"
            ],
            "name": "123",
            "map": {
                "key": {
                    "name": "subname",
                    "collection": [
                        "subcoll",
                        "subcollection"
                    ],
                    "map": {
                        "key": "value"
                    }
                }
            }
        },
        "param2": "1111"
    },
    "method": "normal3",
    "interface": "com.wwwarehouse.dubbo.TestApi"
}
```
param1内部数据顺序变换无影响，正常返回.



## 结论：

如果是多参数，顺序必须与服务提供者的顺序保持一致，数据类型能强转成功就没有问题。

建议：包装参数，像post接受参数一样，使用一个包装实体来接收参数，可以避免顺序问题。



泛型嵌套问题：
描述： 有嵌套的泛型类型会反序列化失败（如：Collection<Model<SubModel>>），如果只有一层（如：Model<SubModel>）可以正常序列化

示例1：
```java
public void normal5(Collection<Model<String>> param1, EnumNormal param2);

传参：

{
    "data": {
        "param1": [
            {
                "collection": [
                    "sdsa",
                    "ssss"
                ],
                "name": "123",
                "map": {
                    "key": "value",
                    "key2": "value3"
                }
            }
        ],
        "param2": "WED"
    },
    "method": "normal5",
    "interface": "com.wwwarehouse.dubbo.TestApi"
}
```
报错：
```java
java.lang.ArrayIndexOutOfBoundsException: 1
at com.alibaba.dubbo.common.utils.PojoUtils.getGenericClassByIndex(PojoUtils.java:503)
...
```
传参
```json
{
"data": {
"param1": [{

"class":"com.wwwarehouse.dubbo.Model",
"collection": ["sdsa", "ssss"],
"name": "123",
"map": {
"key": "value",
"key2": "value3"
}
}
],
"param2": "WED"
},
"method": "normal5",
"interface": "com.wwwarehouse.dubbo.TestApi"
}
```
结果：

正常返回，需要告诉PojoUtils里面是什么类型
```java
if (pojo instanceof Map<?, ?> && type != null) {
   Object className = ((Map<Object, Object>)pojo).get("class");
 if (className instanceof String) {
        try {
            type = ClassHelper.forName((String)className);
 } catch (ClassNotFoundException e) {
            // ignore
 }
}
  ```
非自定义实体泛型类不存在此问题
```java
public void normal9(Collection<Collection<String>> param1, EnumNormal param2);

public void normal4(Collection<HashMap<String, String>> param1, EnumNormal param2);
```
正常传参即可

## 特殊参数问题（userId，clientIP，mac）
特殊参数不建议放在参数中传递，一致性地放在RpcContext中，按需获取。

## 其他问题：
之前一些参数放在url中，如果参数很长会导致url长度过长被服务器截断。
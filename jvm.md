# JVM

## 内存区域

### 程序计数器
1. 一块较小的内存空间，可以看作是当前线程缩执行的字节码的行码指示器
2. 线程私有
3. 当执行方法计器记录的是正在执行的虚拟机字节码指定的地址如果执行的是native方法，计数器的值为空
4. 唯一一个在java虚拟机规范中没有规定任何OutOfMemoryError情况的区域

### 虚拟机栈
1. 线程私有，生命周期与线程相同
2. 描述的是方法执行的内存模型：每个方法在执行的同时会创建一个栈帧，用于存储局部变量表、操作数栈、动态链接、方法出口等信息。每一个方法的调用直至执行完成的过程，就对应一个栈帧在虚拟机栈中入栈到出栈的过程
3. 局部变量表保存的是各种基本数据类型、对象引用和returnAddress(执行了一条字节码指令的地址)
4. 64位的long和double会占用两个局部变量空间，其他的只占用一个，局部变量表所需的内存空间在 编译期间 完成分配。当进入一个方法时,这个方法需要在帧中分配多大的局部变量空间是完全确定的,在方法运行期间不会改变局部变量表的大小
5. 如果线程请求的栈深度大于虚拟机允许的深度，将抛出StackOverflowError。如果虚拟机无法申请到足够的内存将会抛出OutOfMemoryError

操作数栈：和局部变量区一样，操作数栈也是被组织成一个以字长为单位的数组。但是和前者不同的是，它不是通过索引来访问，而是通过标准的栈操作—压栈和出栈—来访问的。比如，如果某个指令把一个值压入到操作数栈中，稍后另一个指令就可以弹出这个值来使用。


### 本地方法栈
1. 为虚拟机使用native方法服务
2. 虚拟机规范中对本地方法栈中方法使用的语言，使用方式与数据结构没有强制规定，不同虚拟机可自由实现
3. 本地方法栈也会抛出StackOverflowError和OutOfMemoryError异常

### java堆
1. 所有线程共享的内存区域，在虚拟机启动时创建
2. 唯一目的为存放对象实例，几乎所有的对象实例都在这里分配内存
3. 垃圾收集器管理的主要区域
4. 通过-Xmx -Xms配置区域大小
5. 没有足够内存进行实例分配，并且无法扩展时，将抛出OutOfMemoryError

Default Xmx = MaxRAM / MaxRAMFraction

| 编译器         | MaxRAM  | 默认Xms |
| -----         | -----    | ------- |
| 32bit-client  | 1GB      | 1/4GB   |
| 32bit-server  | 4GB      | 1GB     |
| 64bit-server  | 128GB    | 32GB    |

```java
#默认值：4
-XX:MaxRAMFraction=N
#如果设置的限制值比Default Xmx小，使用该值
-XX:ErgoHeapSizeLimit=N
-XX:MinRAMFraction=2
if ((96MB*MinRAMFraction)>Physical Memory) {
	Default Xmx=Physical Memory/MinRAMFraction;
}

InitialRAMFraction=64
Default Xms = MaxRAM / InitialRAMFraction
-XX:OldSize=4
if (InitialRAMFraction<OldSize) {
	Default Xms=新生代+老年代
}
```

### 方法区
1. 各个线程共享的内存区域
2. 存储已被虚拟机加载的类的信息、常量、静态变量、即时编译器编译后的代码等数据
3. 当方法区无法满足内存分配需求时，将抛出OutOfMemoryError

### 运行时常量池
1. 用于存放编译期生成的各种字面量和符号引用，类加载后进入方法区的运行时常量池中存放
2. 运行期间可以放入新的常量

## JVM调优
[JVM调优](https://www.cnblogs.com/likehua/p/3369823.html "JVM调优").

-XX:+UseParallelGC     收集新生代空间
-XX:+UseParallelOldGC  收集老年代空间
-XX:+UseParNewGC       收集新生代空间
-XX:+UseG1GC           收集新生代空间

```java
#应用可承受的最大停顿时间，缺省情况下，我们不设定该参数
-XX:MaxGCPauseMillis=n  
#应用在垃圾回收上花费的时间百分比ThroughputGoal=1-1/(1+GCTimeRatio)，默认值:99
-XX:GCTimeRatio=n
```

### CMS
```java
#默认值：70；CMS会在老年代空间占用达到70%时启动并发收集周期
-XX:CMSInitialtingOccupancyFranction=N
#默认值：false
-XX:+UseCMSInitiatingOccupancyOnly
#设置并发后台GC线程数ConcGCThreads = (3+ParallelGCThreads)/4
-XX:ConcGCThreads=N
-XX:+CMSPermGenSweepingEnabled=false
#永久代参数，默认值：80
-XX:CMSInitialtingPermOccupancyFranction=N
-XX:CMSClassUniloadingEnabled=true
#增量式CMS,cpu资源紧张时使用，废弃
-XX:+CMSIncrementalMode
```

### G1
工作在堆内不同分区上的并发收集器,分区既可以归属于老年代，也可以归属于新生代，默认情况下，一个堆被划分为2048个分区，同一个代的分区不需要保持连续。
并不适用于新生代的分区，但是采用预定义的分区能够便于代的大小调整。

| 堆大小      | 默认G1分区的大小 |
| -----       | -----		 |
| <4GB        | 1MB|
| 4GB<>8GB    | 2MB|      
| 8GB<>16GB   | 4MB|
| 16GB<>32GB  | 8MB|
| 32GB<>64GB  | 16MB|
| >64GB    	  | 32MB|

```java
#分区大小=1<<log(初始堆的大小/2048), 2的幂等
-XX:G1HeapRegionSize=N
```
4个操作：
- 新生代垃圾收集(stop the world)
- 后台收集，并发周期
- 混合式垃圾收集 (stop the world)
- 必要时的Full GC

并发周期：
- 初始-标记(initial-mark) 暂停应用线程
- 扫描根分区(root region)
- 并发标记(concurrent-mark)
- 并发清理(concurrent-cleanup)

避免Full GC发生：
- 并发模式失效 [GC concurrent-mark-abort]---增加堆大小 OR 更早开始 OR 增加处理线程数
- 晋升失败 [to-space exhausted]-[full GC (Allocation Failure)]---需要更迅速地完成垃圾收集，每次新生代垃圾收集需要处理更多的老年代分区
- 疏散失败 [(to-space overflow)]---增加堆大小
- 巨型对象

```java
#默认值：200ms, 调整新生代和老年代的比例，调整堆大小，更早启动后台处理，改变晋升阈值，在混合式垃圾收集周期中处理更多或更少的老年代分区（最重要方式）
-XX:MaxGCPauseMillis=N
#调整G1的后台线程数ConcGCThreads=(ParallelGCThreads+2)/4
-XX:ParallelGCThreads=N
#调整运行频率, 默认值:45
-XX:InitiatingHeapOccupancyPercent=N
#调整混合式垃圾收集周期,如果分区的垃圾占比达到35%，就标记为X
-XX:G1MixdGCLiveThresholdPercent=N
#最大混合式GC周期数, 默认值:8
-XX:G1MixedGCCountTarget=N
```

## survivor
```java
#默认值:8,survivor_space_size=new_size/(initial_survivor_ratio+2)
-XX:InitialSurvivorRatio=N
#默认值：3，maximum_survivor_space_size=new_size/(min_survivor_ratop+2)
-XX:MinSurvivorRatio=N
#垃圾回收后空间空闲bil
-XX:TargetSurvivorRatio=N
#晋升阈值,Throughput & G1=7, CMS=6
-XX:InitialTenuringThreshold=N
#最大晋升阈值,Throughput & G1=15, CMS=6
-XX:MaxTenuringThreshold=N
#直接进入老年代
-XX:+AlwaysTenure
#只要Survivor空间有容量，就不会进入老年代
-XX:+NeverTenure
```

-XX:+PrintTenuringDistribution
Throughput收集器，
[new threshold 1(max 15)]

### TLAB (Thread Local Allocation Buffer)
TLAB的大小由三个因素决定：应用的线程数、Eden空间大小、线程的分配率. 默认开启
-XX:-UseTLAB
如果发现有大量对象分配发生在TLAB之外，可以减小分配对象的大小，或者调整TLAB的参数
-XX:TLABSize=N
-XX:-ResizeTLAB

## JIT(just-in-time) 即时编译器
- 解释型 （PHP） 跨平台，耗时久
- 编译型 （c++） 不同cpu指令集差异，不通用
java介于两者之间
字节码编译成汇编语言的过程，会做大量的优化，极大改善性能

### 编译器类型
-client
-server
-d64
分层编译：-XX:+TieredCompilation，默认开启.

### 调优代码缓存
缓存满了后会有警告
```java
Java HotSpot(TM) 64-Bit Server VM warning: CodeCache is full.
Compiler has been disabled
Java HotSpot(TM) 64-Bit Server VM warning: Try increasing the code cache size using -XX:ReservedCacheSize=???
```

|虚拟机版本|编译模式|默认大小|
|---------|-------|--------|
|64位 server|分层编译|JAVA 8 240MB|
|64位 server|分层编译|JAVA 7 96MB|

### 编译阈值
基于两种JVM计数器： 方法调用计数器 & 方法中的循环回边计数器
循环回边：包括达到循环自身的末尾，也包括执行了像continue这样的分支语句

栈上替换（On-Stack replacement, OSR）
OSR trigger = (ComileThreshold * ((OnStackReplacePercentage - InterpreterProfilePercentage)/100))
ComileThreshold -server= 10,000
-XX:InterpreterProfilePercentage=N=33

### 逃逸分析
编译器能做的最复杂的优化，常常会导致微基准测试失败

## 监控工具

### jcmd 
打印JAVA进程所涉及的基本类、线程和VM信息。
```java
% jcmd process_id command optional_arguments

% jcmd help


#查看JVM运行时长
jcmd process_id VM.uptime
#显示System.getProperties()的各个条目
jcmd process_id VM.system_properties
OR
jinfo -sysprops process_id
#JVM版本
jcmd process_id VM.version
#JVM命令行
jcmd process_id VM.command_line
#JVM调优标志
jcmd process_id VM.flags [-all]
#显示每个线程栈的输出
jcmd process_id Thread.print
#堆直方图
jcmd process_id GC.class_histogram
#堆转储
jcmd process_id GC.heap_dump /root/heap_dump.hprof
```

### jconsole
提供JVM活动的图形化视图，包括线程的使用、类的使用和GC活动
可以通过JMX查看远程JVM

### jhat
读取内存堆转储，并有助于分析。并运行一个小型的HTTP服务器，该服务允许你通过一系列网页链接查看堆转储信息.

### jmap
提供堆转储和其他JVM内存使用信息
```java
%  jmap -clstats process_id
#堆直方图,含死对象
jmap -histo process_id
#堆直方图,不含死对象
jmap -histo:live process_id
#堆转储,因为‘:live’会在转储之前强制一次Full GC
jmap -dump:live,file=/root/heap_dump.hprof process_id
```

### jinfo
查看JVM系统属性，可以动态设置一些系统属性

```java
jinfo -flags process_id
# 只对manageable标示有效
jinfo -flag PrintGCDetails process_id # turns off PrintGCDtails 
-XX:-PrintFCDetails

```

### jstack
转储JAVA进程的栈信息

```java
#显示每个线程的栈的输出
jstack process_id
```

### jstat
提供GC和类装载活动信息

```java
#间隔时间(1000ms)展示gc情况
% jstat -gcutil process_id 1000
```

### jvisualvm
监视JVM的GUI工具，分析JVM堆转储
监视(Monitor)选项卡可以从一个运行中的程序获得堆转储，也可以打开之前生成的堆转储文件，可以浏览堆，检查最大的保留对象，一级执行任意针对堆的查询.

## 内存分析

浅(shallow) & 深(deep) & 保留(retained).
一个对象的浅大小，指的是该对象本身的大小，如果该对象包含一个指向另一个对象的引用，4字节或8字节的引用会计算在内，但目标对象的大小不会包含进来.
深大小则包含那些对象的大小。深大小和保留大小的区别在于那些存在共享的对象

## 内存溢出错误
- JVM没有原生内存可用
- 永久代或元空间内存不足
- Java堆本身内存不足--对于给定的堆空间，应用中活跃对象太多
- JVM执行GC耗时太多

1. 原生内存不足
表现:
```java
Exception in thread "main" java.lang.OutOfMemoryError:
unable to create new native thread
```
如果OutOfMemoryError中提到了原生内存分配，那对堆进行调优解决不了问题，需要看下错误中提到的是何种原生内存问题.

2. 永久代或元空间内存不足
表现:
```java
Exception in thread "main" java.lang.OutOfMemoryError:
Metaspace
PermGen space
```
根源：
- 应用使用的类太多，超出永久代的默认容纳范围
- 类加载器的内存泄露. 重新部署时，会创建一个新的类加载器来加载新的类，老的类加载器就可以退出作用域了，这样该类的元数据就可以回收了，如果老的类加载器没有退出作用域，那么就会导致该问题

解决：
- 增加永久代或元空间大小
- 联系服务器厂商解决内存泄露, 在直方图中找到ClassLoader类的所有实例，然后跟踪他们的GC Root

3. 堆内存不足
表现:
```java
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
```

根源：
- 活跃对象过多
- 内存泄露

解决：
- 增加堆大小
- 堆转储分析, 间隔几分钟，获得连续的一些堆转储文件，进行比较
MAT内置功能，可以进行对比

自动堆转储
```java
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=<path>
-XX:+HeapDumpAfterFullGC
-XX:+HeapDumpBeforeFullGC
```

集合类是导致内存泄露的最常见原因：只插入，不释放
最好的办法是修改逻辑，主动删除不需要的条目，另外，可以使用弱引用或软引用的集合

4. 达到GC的开销限制
表现：
```java
Exception in thread "main" java.lang.OutOfMemoryError:GC overhead limit exceeded
```

需满足以下所有条件
- 花在Full GC上的时间超过-XX:GCTimeLimit=N标志所指定的值，默认值98.即98%的时间花在GC上，则满足该条件
- 一次Full GC回收的内存量少于-XX:GCHeapFreeLimit=N标志指定的值，默认值2，即Full GC期间释放的内存不足堆的2%，则满足该条件
- 上述两条连续5次Full GC均成立（无法调整该值）
- -XX:+UseGCOverhead-Limit(默认为true)

如果连续执行了5次以上的Full GC，并且应用将98%的时间花在Full GC上，有可能每次GC释放的堆空间可能会超过2%，这种情况下可以考虑增加GCHeapFreeLimit的值

如果前两个条件连续4次Full GC周期都成立，JVM会在第5次之前，释放所有的软引用，这样第5次的Full GC就可能会释放超过2%的堆内存

## 减少内存使用

减少对象大小

|类型|Java实例变量大小|
|----|--------------:|
|byte|1|
|char|2|
|short|2|
|int|4|
|float|4|
|long|8|
|double|8|
|refrence|4,32位&heap<32GB的64位JVM|
|refrence|8,heap>32GB的64位JVM|

隐藏对象头字段，对于普通对象，对象头在32位JVM占8字节，64位JVM占16字节，对于数组，对象头在32位及堆小于32GB的64位JVM上占16字节，其他情况占64字节
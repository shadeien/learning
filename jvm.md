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

```
#应用可承受的最大停顿时间，缺省情况下，我们不设定该参数
-XX:MaxGCPauseMillis=n  
#应用在垃圾回收上花费的时间百分比ThroughputGoal=1-1/(1+GCTimeRatio)，默认值:99
-XX:GCTimeRatio=n
```

### CMS
```
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

```
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

```
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
```
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
```
Java HotSpot(TM) 64-Bit Server VM warning: CodeCache is full.
Compiler has been disabled
Java HotSpot(TM) 64-Bit Server VM warning: Try increasing the code cache size using -XX:ReservedCacheSize=???
```

默认大小
```
64位 server, 分层编译, JAVA 8 240MB
64位 server, 分层编译, JAVA 7 96MB
```

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
% jcmd process_id command optional_arguments

% jcmd help

```
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
```

### jconsole
提供JVM活动的图形化视图，包括线程的使用、类的使用和GC活动
可以通过JMX查看远程JVM

### jhat
读取内存堆转储，并有助于分析。

### jamp
提供堆转储和其他JVM内存使用信息
```
%  jmap -clstats process_id
```

### jinfo
查看JVM系统属性，可以动态设置一些系统属性

```
jinfo -flags process_id
# 只对manageable标示有效
jinfo -flag PrintGCDetails process_id # turns off PrintGCDtails 
-XX:-PrintFCDetails

```

### jstack
转储JAVA进程的栈信息

```
#显示每个线程的栈的输出
jstack process_id
```

### jstat
提供GC和类装载活动信息

```
#间隔时间(1000ms)展示gc情况
% jstat -gcutil process_id 1000
```

### jvisualvm
监视JVM的GUI工具，分析JVM堆转储

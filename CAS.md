# lock free
常见的lock free编程一般是基于CAS(Compare And Swap)操作：CAS(void *ptr, Any oldValue, Any newValue);
即查看内存地址ptr处的值，如果为oldValue则将其改为newValue，并返回true，否则返回false。X86平台上的CAS操作一般是通过CPU的CMPXCHG指令来完成的。CPU在执行此指令时会首先锁住CPU总线，禁止其它核心对内存的访问，然后再查看或修改*ptr的值。简单的说CAS利用了CPU的硬件锁来实现对共享资源的串行使用。
优点：
- 1、开销较小：不需要进入内核，不需要切换线程；
- 2、没有死锁：总线锁最长持续为一次read+write的时间；
- 3、只有写操作需要使用CAS，读操作与串行代码完全相同，可实现读写不互斥。
缺点：
- 1、编程非常复杂，两行代码之间可能发生任何事，很多常识性的假设都不成立。
- 2、CAS模型覆盖的情况非常少，无法用CAS实现原子的复数操作。


而在性能层面上，CAS与mutex/readwrite lock各有千秋，简述如下：
- 1、单线程下CAS的开销大约为10次加法操作，mutex的上锁+解锁大约为20次加法操作，而readwrite lock的开销则更大一些。
- 2、CAS的性能为固定值，而mutex则可以通过改变临界区的大小来调节性能；
- 3、如果临界区中真正的修改操作只占一小部分，那么用CAS可以获得更大的并发度。
- 4、多核CPU中线程调度成本较高，此时更适合用CAS。
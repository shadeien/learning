## Executor

Executor 管理多个异步任务的执行，而无需程序员显式地管理线程的生命周期。这里的异步是指多个任务的执行互不干扰，不需要进行同步操作。

主要有三种 Executor：

- CachedThreadPool：一个任务创建一个线程；
- FixedThreadPool：所有任务只能使用固定大小的线程；
- SingleThreadExecutor：相当于大小为 1 的 FixedThreadPool。
- ScheduledThreadPool: 创建一个固定长度的线程池，并以延迟或定时的方法来执行任务, 类似Timer

```java
public static void main(String[] args) {
    ExecutorService executorService = Executors.newCachedThreadPool();
    for (int i = 0; i < 5; i++) {
        executorService.execute(new MyRunnable());
    }
    executorService.shutdown();
}
```

### ThreadPoolExecutor

```java
public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler) {
        if (corePoolSize < 0 ||
            maximumPoolSize <= 0 ||
            maximumPoolSize < corePoolSize ||
            keepAliveTime < 0)
            throw new IllegalArgumentException();
        if (workQueue == null || threadFactory == null || handler == null)
            throw new NullPointerException();
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.threadFactory = threadFactory;
        this.handler = handler;
    }
```

A、当提交新任务时，若线程池大小小于corePoolSize，将创建一个新的线程来执行任务，即使此时线程池中存在空闲线程；

B、当提交新任务时，若线程池达到corePoolSize大小，新提交的任务将被放入workQueue中，等待线程池调度执行；

C、当提交新任务时，若workQueue已满，且maximumPoolSize>corePoolSize，将创建新的线程来执行任务；

D、当提交新任务时，若任务总数超过maximumPoolSize，新提交的任务将由RejectedExecutionHandler来处理；

E、当线程池中的线程数超过corePoolSize时，若线程的空闲时间达到keepAliveTime，则关闭空闲线程

### BlockingQueue

基本的任务排序方法有3种：无界队列、有界队列、同步移交(Synchronous Handoff)

newFixedThreadPool & newSingleThreadExecutor: 无界的LinkedBlockingQueue
更稳妥的资源管理策略：使用有界队列，ArrayBlockingQueue、有界的LinkedBlockingQueue、PriorityBlockingQueue

### SynchronousQueue

对于非常大的或者无界的线程池，可以使用SychronousQueue来避免任务排队，以直接将任务从生产者移交给工作者线程。
SychronousQueue不是一个真正的队列，而是一种在线程之间进行移交的机制。
任务会直接移交给执行它的线程，而不是首先放到队列中，然后由工作者线程从队列中提取该任务。
只有当线程池是无界的或者可以拒绝任务时，SychronousQueue才有实际价值，newCahcedThreaPool工厂方法使用了SychronousQueue.

### 饱和策略

当有界队列被填满后(如果某个任务被提交到一个已被关闭的Executor时)，饱和策略开始发挥作用。ThreadPoolExecutor的饱和策略可以通过调用setRejectedExecutionHandler来修改
- AbortPolicy: 默认策略，抛出unchecked RejectedExecutionException
- CallerRunsPolicy：将某些任务回退到调用者，从而降低新任务的流量，所有线程和工作队列都被占用后，下一个任务会在调用execute时在主线程中执行，使得主线程不会调用accept，到达的请求会被保存在TCP层的队列中而不是应用程序的队列。如果持续过载，TCP层队列也会被占满，开始抛弃请求
- DiscardPolicy：悄悄抛弃该任务，无异常
- DiscardOldPolicy：抛弃下一个将被执行的任务，然后尝试重新提交新的任务(如果工作队列是一个优先队列，将抛弃优先级最高的任务，不建议搭配使用)

```java
ThreadPoolExecutor executor = new ThreadPoolExecutor(N_THREADS, N_THREADS, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(CAPACITY));
executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()));
```

### ThreadFactory

JDK线程池：Executors.newSingleThreadExecutor、Executors.newFixedThreadPool等由一个ThreadFactory来创建新的线程，默认情况下为Executors.defaultThreadFactory()，我们可以采用自定义的ThreadFactory工厂，增加对线程创建与销毁等更多的控制（比如上述代码中的内部类CustomThreadFactory即为新建线程的模板）

```java
public interface ThreadFactory {  
    Thread newThread(Runnable r);  
}
```

### ThreadPoolExecutor扩展

它提供了几个可以在子类化中改写的方法：beforeExecute、afterExecute、terminated
可以添加日志、计时、监视、统计信息收集等功能。
无论任务从run中正常返回，还是抛出一个异常（Error除外）返回，afterExecute都会被调用
如果beforeExecute抛出一个RuntimeException，任务将不被执行，afterExecute也不会调用
线程池完成关闭操作时调用terminated，用来释放Executor在其生命周期里分配的各种资源，还可以执行发送通知、记录日志、收集finalize统计信息等操作.

### Executors、ThreadPoolExecutor、ExecuteService、Executor

1) Executors
从Java5开始新增了Executors类，它有几个静态工厂方法用来创建线程池，这些静态工厂方法返回一个ExecutorService类型的值，此值即为线程池的引用。

2) Executor
Executor是一个接口，里面只有一个方法
```java
public interface Executor {
    void execute(Runnable command);
}
```

3) ExecuteService

```java
public interface ExecutorService extends Executor {...}
```

4) ThreadPoolExecutor
继承AbstractExecutorService，AbstractExecutorService实现ExecutorService接口
```java
public class ThreadPoolExecutor extends AbstractExecutorService {...}
public abstract class AbstractExecutorService implements ExecutorService {...}
```

### ExecutorService的生命周期
运行、关闭、已终止

shutdown方法将执行平缓关闭，不再执行新任务，同时等待已经提交的任务执行完成。
shutdownNow方法将粗暴关闭，取消运行中任务，不再处理已提交未处理任务

可以调用awaitTermination来等待ExecutorService到达终止状态，或者通过调用isTerminated来轮询ExecutorService是否已经终止，通常在调用awaitTermination之后会立即调用shutdown
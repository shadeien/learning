# command

## mpstat(Multiprocessor Statistics)
实时监控工具，报告与cpu的一些统计信息这些信息都存在/proc/stat文件中，在多CPU系统里，其不但能查看所有的CPU的平均状况的信息，而且能够有查看特定的cpu信息，mpstat最大的特点是:可以查看多核心的cpu中每个计算核心的统计数据；而且类似工具vmstat只能查看系统的整体cpu情况。
例：
	mpstat 2 5
Linux 3.10.0-327.el7.x86_64 (yankerp)   11/01/2017      _x86_64_        (1 CPU)

07:13:28 PM  CPU    %usr   %nice    %sys %iowait    %irq   %soft  %steal  %guest  %gnice   %idle
07:13:30 PM  all    0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.00  100.00
07:13:32 PM  all    0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.00  100.00
07:13:34 PM  all    0.50    0.00    0.50    0.00    0.00    0.00    0.00    0.00    0.00   99.00
07:13:36 PM  all    0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.00  100.00
07:13:38 PM  all    0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.00  100.00
Average:     all    0.10    0.00    0.10    0.00    0.00    0.00    0.00    0.00    0.00   99.80

- %user      在internal时间段里，用户态的CPU时间(%)，不包含nice值为负进程  (usr/total)*100
- %nice      在internal时间段里，nice值为负进程的CPU时间(%)   (nice/total)*100
- %sys       在internal时间段里，内核时间(%)       (system/total)*100
- %iowait    在internal时间段里，硬盘IO等待时间(%) (iowait/total)*100
- %irq       在internal时间段里，硬中断时间(%)     (irq/total)*100
- %soft      在internal时间段里，软中断时间(%)     (softirq/total)*100
- %idle      在internal时间段里，CPU除去等待磁盘IO操作外的因为任何原因而空闲的时间闲置时间(%) (idle/total)*100


## iostat

例： iostat -d -k 2 1
参数-d表示显示设备磁盘的使用状态；-k表示某些使用block为单位的列强制使用kilobytes为单位，2表示数据每隔2秒刷新一次 6表示一共刷新6次

Linux 3.10.0-327.el7.x86_64 (yankerp)   11/01/2017      _x86_64_        (1 CPU)

Device:            tps    kB_read/s    kB_wrtn/s    kB_read    kB_wrtn
sda              36.71      1375.40      1777.96    9519457   12305674
scd0              0.00         0.01         0.00         66          0
dm-0             48.01      1346.02      1708.81    9316107   11827069
dm-1             23.42        27.75        66.29     192056     458804

- tps：该设备每秒的传输次数（Indicate the number of transfers per second that were issued to the device.）。"一次传输"意思是"一次I/O请求"。多个逻辑请求可能会被合并为"一次I/O请求"。"一次传输"请求的大小是未知的。
- kB_read/s：每秒从设备（drive expressed）读取的数据量；
- kB_wrtn/s：每秒向设备（drive expressed）写入的数据量；
- kB_read：读取的总数据量；
- kB_wrtn：写入的总数量数据量；这些单位都为Kilobytes

使用iostat查看cpu统计信息使用-C参数 例： iostat -c 2 1
iostat还有一个比较常用的选项-x，该选项将用于显示和io相关的扩展数据。
iostat -d -x -k 1 6


## sar(System ActivityReporter)
系统活动情况报告 目前Linux上最为全面的系统性能分析工具之一，可以从多方面对系统的活动进行报告，包括：文件的读写情况、系统调用的使用情况、磁盘I/O、CPU效率、内存使用状况、进程活动及IPC有关的活动等，sar命令有sysstat安装包安装。

## vmstat
最常见的Linux/Unix监控工具，可以展现给定时间间隔的服务器的状态值,包括服务器的CPU使用率，内存使用，虚拟内存交换情况,IO读写情况。这个命令是我查看Linux/Unix最喜爱的命令，一个是Linux/Unix都支持，二是相比top，我可以看到整个机器的CPU,内存,IO的使用情况，而不是单单看到各个进程的CPU使用率和内存使用率(使用场景不一样)。

一般vmstat工具的使用是通过两个数字参数来完成的，第一个参数是采样的时间间隔数，单位是秒，第二个参数是采样的次数，如: vmstat  2 6 
procs -----------memory---------- ---swap-- -----io---- -system-- ------cpu-----
 r  b   swpd   free   buff  cache   si   so    bi    bo   in   cs us sy id wa st
 1  0      0 4480832    768 1462648  0    0     0     0    2    1  2  0 98  0  0

- r 表示运行队列(就是说多少个进程真的分配到CPU)，当这个值超过了CPU数目，就会出现CPU瓶颈了。这个也和top的负载有关系，一般负载超过了3就比较高，超过了5就高，超过了10就不正常了，服务器的状态很危险。top的负载类似每秒的运行队列。如果运行队列过大，表示你的CPU很繁忙，一般会造成CPU使用率很高。
- b 表示阻塞的进程
- swpd 虚拟内存已使用的大小，如果大于0，表示你的机器物理内存不足了，如果不是程序内存泄露的原因，那么你该升级内存了或者把耗内存的任务迁移到其他机器。
- free   空闲的物理内存的大小
- buff   Linux/Unix系统是用来存储，目录里面有什么内容，权限等的缓存
- cache cache直接用来记忆我们打开的文件,给文件做缓冲
- si  每秒从磁盘读入虚拟内存的大小，如果这个值大于0，表示物理内存不够用或者内存泄露了，要查找耗内存进程解决掉。
- so  每秒虚拟内存写入磁盘的大小，如果这个值大于0，同上
- bi  块设备每秒接收的块数量，这里的块设备是指系统上所有的磁盘和其他块设备，默认块大小是1024byte
- bo 块设备每秒发送的块数量，例如我们读取文件，bo就要大于0。bi和bo一般都要接近0，不然就是IO过于频繁，需要调整。
- in 每秒CPU的中断次数，包括时间中断
- cs 每秒上下文切换次数
- us 用户CPU时间
- sy 系统CPU时间，如果太高，表示系统调用时间长，例如是IO操作频繁。
- id  空闲 CPU时间
- wt 等待IO CPU时间

## top
能够实时监控系统的运行状态，并且可以按照cpu、内存和执行时间进行排序

## free
监控系统内存最常用的命令

- total：总计物理内存的大小。
- Used：已使用多大。
- Free：可用有多少。
- shared：多个进程共享的内存总额。
- buffers/cached:磁盘缓存的大小。
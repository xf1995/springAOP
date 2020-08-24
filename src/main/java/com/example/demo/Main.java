package com.example.demo;

import com.example.demo.anno.Front;
import com.example.demo.anno.Section;
import com.example.demo.aop.SectionAspect;
import com.example.demo.entity.Y;
import com.example.demo.interfaces.InterfaceY;
import com.example.demo.proxy.JdkDynamicProxy;
import com.example.demo.utils.RingBufferWheel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Unsafe;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: XF
 * @Date: 2020/5/24 10:16
 */
public class Main {
    private static final AtomicInteger value = new AtomicInteger(0);
    private static final AtomicInteger key = new AtomicInteger(1);
    private static Long valueOffset;
    public static Unsafe getUnsafeInstance() {
        try {
            Class<?> clazz = Unsafe.class;
            Field f = clazz.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            Unsafe unsafe = (Unsafe) f.get(clazz);
            return unsafe;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Logger logger = LoggerFactory.getLogger(Main.class);

        /*Unsafe UNSAFE = getUnsafeInstance();

        valueOffset = UNSAFE.staticFieldOffset(Main.class.getDeclaredField("value"));

        UNSAFE.compareAndSwapObject(Main.class,valueOffset,value,key);

        Object o = UNSAFE.getObjectVolatile(Main.class,valueOffset);
        //int i = UNSAFE.getInt(Main.class,valueOffset);
        System.out.println(o+"");
        System.out.println(valueOffset);*/

       /* ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:applocation.xml");
        System.out.println(applicationContext);
        X x = (X)applicationContext.getBean("x");
        System.out.println(x.self());*/

        //AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        /*byte[] bytes = new byte[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,28,30,31 };
        InputStream inputStream = new FileInputStream("C:\\Users\\17735\\Desktop\\license.glf");
        OutputStream outputStream = new FileOutputStream("C:\\Users\\17735\\Desktop\\2.glf");
        //String str = "+-/.?/';00!0@0#0$0%0^0&0*  0(";
        outputStream.write(bytes);*/

        /*ExecutorService executorService = Executors.newFixedThreadPool(3);
        RingBufferWheel ringBufferWheel = new RingBufferWheel(executorService,8);

        //Job job = new Job(0);//延迟1s
        Job job1 = new Job(3);//延迟3s
        Job job2 = new Job(5);//延时10s
        Job job3 = new Job(7);//延时20s
        Job job4 = new Job(9);//延时20s
        Job job5 = new Job(11);//延时20s
        Job job6 = new Job(13);
        Job job7 = new Job(15);
        Job job8 = new Job(17);
        Job job9 = new Job(19);
        Job job10 = new Job(21);

        ringBufferWheel.addTask(job1);
        ringBufferWheel.addTask(job2);
        ringBufferWheel.addTask(job3);
        ringBufferWheel.addTask(job4);
        ringBufferWheel.addTask(job5);
        ringBufferWheel.addTask(job6);
        ringBufferWheel.addTask(job7);
        ringBufferWheel.addTask(job8);
        ringBufferWheel.addTask(job9);
        ringBufferWheel.addTask(job10);


        ringBufferWheel.start();
        logger.error("...................");*/

        /*int i = 15 /1 % 64;
        int target = 5;
        int i1 = target % 2;
        int i2 = target & (target - 1);*/
        //logger.debug(i1+"");
        /*ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,2,1,TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>(2),Executors.defaultThreadFactory(),(r,executor)->{System.out.println(((Task)r).num+"拒绝策略");executor.execute(r);});
        for(int i=0;i<10;i++){
            Task task = new Task(i);
            threadPoolExecutor.execute(task);
            Task1 task1 = new Task1(i);
            Future submit = threadPoolExecutor.submit(task1);
            logger.info(submit.get()+"");
        }
        threadPoolExecutor.shutdown();

        File file = new File("C:\\Users\\17735\\Desktop\\总结.txt");
        TotalSum totalSum = new TotalSum();
        if(file.exists()){
            long start = System.currentTimeMillis();
            for(int i=0;i<10000;++i){
                Stream<String> stream = Files.lines(Paths.get("C:\\Users\\17735\\Desktop\\总结.txt"), StandardCharsets.UTF_8);
                List<String> list = stream.collect(Collectors.toList());
                for(String s : list){
                    totalSum.setSum(s.toCharArray().length);
                }
            }
            long l = System.currentTimeMillis() - start;
            System.out.println("耗时(ms):"+l);
            System.out.println("总字数:"+totalSum.getSum());
        }*/

        /*ExecutorService executorService = new ThreadPoolExecutor(4,5,1,TimeUnit.MILLISECONDS,new LinkedBlockingDeque<Runnable>());
        long start = System.currentTimeMillis();

        for(int i=0;i<10000;++i){
            ScanTask scanTask = new ScanTask(totalSum);
            executorService.execute(scanTask);
        }
        executorService.shutdown();
        executorService.isShutdown();
        while (!executorService.isTerminated()){
            //System.out.println("任务进行中");
        }
        long l = System.currentTimeMillis() - start;
        System.out.println("耗时(ms):"+l);
        System.out.println("总字数:"+totalSum.getSum());*/


        /*RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String,Object> valueMap = new LinkedMultiValueMap<String,Object>();
        valueMap.add("username","张三");
        //JSONObject jsonObject = new JSONObject();
        //jsonObject.put("username","张三");
        MediaType m = headers.getContentType();
        HttpEntity httpEntity = new HttpEntity(valueMap,headers);
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity("http://localhost:8080/indexController/index",httpEntity,Object.class);
        System.out.println(responseEntity.getBody());*/

        /*ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Integer> taskFuture = pool.submit(new MyForkJoinTask(1,1001));
        try{
            Integer reslut = taskFuture.get();
            System.out.println("reslut="+reslut);
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }*/

        /*Class clazz = X.class;
        Class superClazz = clazz.getSuperclass();
        Class[] clazzs = clazz.getInterfaces();
        superClazz = superClazz.getSuperclass();
        Method m = clazz.getMethod("");
        System.out.println(superClazz);*/

        /*CasRun casRun = new CasRun();

        for(int i =0;i<2;i++){
            Thread thread = new Thread(new TestCas(casRun));
            thread.setName("casRun"+i);
            thread.start();
        }

        TimeUnit.MICROSECONDS.sleep(1);
        casRun.releaseLock(1);*/



        /*Thread.currentThread().setName("maim主线程");
        Thread t = Thread.currentThread();
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        lock.lock();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("延时再次添加条件结束,开始wait");
                lock.lock();
                condition.await();
                System.out.println("延时唤醒.........");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });
        new Thread(()->{
            try {
                System.out.println("延时唤醒线程启动");
                TimeUnit.SECONDS.sleep(10);
                System.out.println("延时唤醒线程时间到");
                //Thread.currentThread().interrupt();
                lock.lock();
                //t.interrupt();
                condition.signal();
                //condition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }).start();
        condition.await();
        System.out.println(".........");

        lock.unlock();*/

        /*ThreadPoolExecutor executorService = new ThreadPoolExecutor(4,5,1,TimeUnit.MILLISECONDS,new LinkedBlockingDeque<Runnable>());

        executorService.execute(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();

        System.out.println(executorService.isShutdown());
        System.out.println(executorService.isTerminating());
        System.out.println(executorService.isTerminated());
        while (!executorService.awaitTermination(1,TimeUnit.SECONDS)){
            System.out.println(executorService.isTerminated());
        }
        System.out.println(executorService.isTerminated());*/
    }

    public static class Test2 implements Runnable{
        //Lock lock = new ReentrantLock(true);
        Lock lock;
        //volatile AtomicInteger i = new AtomicInteger(0);
        volatile int i =0;

        public Test2(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            //i.incrementAndGet();
            lock.lock();
            i++;
            String threadName = Thread.currentThread().getName();
            System.out.println("当前线程:"+threadName+"  i值为  "+i);
            lock.unlock();
        }

        public static void main(String[] args) throws InterruptedException {
            Lock lock = new ReentrantLock(true);
            Test2 test2 = new Test2(lock);
            for(int i =0;i<1000;i++){
                Thread thread = new Thread(test2);
                thread.setName("Test"+i);
                thread.start();
            }
            TimeUnit.SECONDS.sleep(3);
            System.out.println(lock);
            System.out.println("test2的最后i值为:" + test2.i);
        }
    }

    public static class MyForkJoinTask extends RecursiveTask<Integer>{

        private static final Integer MAX = 200;

        private Integer startValue;

        private Integer endValue;

        public MyForkJoinTask(Integer startValue,Integer endValue){
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        protected Integer compute() {
            if(endValue - startValue < MAX){
                System.out.println("开始计算的部分:startValue = "+startValue+";endValue ="+endValue);
                Integer totalValue = 0;
                for(int index = this.startValue;index <= this.endValue;index++){
                    totalValue += index;
                }
                return totalValue;
            }else{
                MyForkJoinTask subTask1 = new MyForkJoinTask(startValue,(startValue+endValue)/2);
                subTask1.fork();
                MyForkJoinTask subTask2 = new MyForkJoinTask((startValue+endValue)+1,endValue);
                subTask2.fork();
                return subTask1.join()+subTask2.join();
            }
        }
    }

    public static class TotalSum{
        volatile AtomicInteger sum = new AtomicInteger();
        //Object object = new Object();
        //int  sum;
        public int getSum() {
            return sum.get();
        }

        public void setSum(int sum) {
            this.sum.addAndGet(sum);
        }
    }

    public static class ScanTask implements Runnable{
        TotalSum totalSum;
        ScanTask(TotalSum totalSum){
            this.totalSum = totalSum;
        }

        @Override
        public void run() {
            Stream<String> stream = null;
            try {
                stream = Files.lines(Paths.get("C:\\Users\\17735\\Desktop\\总结.txt"), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<String> list = stream.collect(Collectors.toList());
            for(String s : list){
                totalSum.setSum(s.toCharArray().length);
            }
        }
    }

    public static class Job extends  RingBufferWheel.Task{
        Logger logger = LoggerFactory.getLogger(Job.class);
        public int num;

        public Job (int  num){
            this.num = num;
            this.key = num;
        }

        public void run() {
            logger.info("当前任务是"+num);
        }
    }

    public static class Task implements Runnable{
        Logger logger = LoggerFactory.getLogger(Task.class);
        int num;
        Task(int num){
            this.num = num;
        }
        @Override
        public void run() {
            logger.info(num+".......");
        }
    }

    public static class Task1 implements Callable<Integer>{
        int num;
        Task1(int num){
            this.num = num;
        }
        @Override
        public Integer call() throws Exception {
            return num+1;
        }
    }

    public static class Test implements Runnable{

        private static long demp = 0;

        private long demp_;
        public Test(long temp_){
            this.demp_ = temp_;
        }
        @Override
        public void run() {
            while (!Thread.interrupted()){
                demp = demp_;
            }
        }

        public static void main(String[] args) {
            Test test1 = new Test(0);
            Test test2 = new Test(-1);

            Thread thread1 = new Thread(test1);
            Thread thread2 = new Thread(test2);
            thread1.start();
            thread2.start();
            long val;
            while ((val = demp) == 0 || val == -1){
                System.out.println("正常....."+val);
            }
            System.out.println(pad("val："+Long.toBinaryString(val), 64));
            System.out.println("错误......"+val);

            thread1.interrupt();
            thread2.interrupt();
            //System.out.println("错误......"+demp);

        }
        private static String pad(String s, int targetLength) {
            int n = targetLength - s.length();
            for (int x = 0; x < n; x++) {
                s = "0" + s;
            }
            return s;
        }
    }


    public static class TwoThread{

        private volatile int start = 1;

        private volatile boolean flag = false;

        public static final Lock LOCK = new ReentrantLock();

        public static void main(String[] args) {
            TwoThread twoThread = new TwoThread();

            Thread thread1 = new Thread(new JiNum(twoThread));
            Thread thread2 = new Thread(new OuNum(twoThread));
            thread1.start();
            thread2.start();
        }

    }

    public static class JiNum implements Runnable{
        TwoThread twoThread;

        public JiNum(TwoThread twoThread){
            this.twoThread = twoThread;
        }

        @Override
        public void run() {
            while (twoThread.start<=100){
                if(!twoThread.flag){
                    if(twoThread.start<=100){
                        try{
                            //TwoThread.LOCK.lock();
                            System.out.println("当前start为奇数"+ twoThread.start);
                            twoThread.start++;
                            twoThread.flag = true;
                        }finally {
                            //TwoThread.LOCK.unlock();
                        }
                    }
                }
            }
        }
    }

    public static class OuNum implements Runnable{
        TwoThread twoThread;

        public OuNum(TwoThread twoThread){
            this.twoThread = twoThread;
        }

        @Override
        public void run() {
            while (twoThread.start<=100){
                if(twoThread.flag){
                    if(twoThread.start<=100){
                        try{
                            //TwoThread.LOCK.lock();
                            System.out.println("当前start为偶数"+ twoThread.start);
                            twoThread.start++;
                            twoThread.flag = false;
                        }finally {
                            //TwoThread.LOCK.unlock();
                        }
                    }
                }
            }
        }
    }

    public static class CasRun{

        private volatile AtomicInteger state = new AtomicInteger(0);

        public void cas(int arg){
            if(!tryCas(arg)){ //判断是否获取锁成功
                //获取锁失败  自旋当前线程
                if(selfCas(arg)){
                    System.out.println(Thread.currentThread().getName()+"线程获取锁成功 429行");
                }
            }
        }

        //尝试获取锁
        public boolean tryCas(int arg){
            if(state.compareAndSet(0,arg)){
                System.out.println(Thread.currentThread().getName()+"线程获取锁成功 437行");
                return true;
            }
            return false;
        }

        //自旋获取锁
        public boolean selfCas(int arg){
            System.out.println(Thread.currentThread().getName()+"线程进入自旋尝试获取锁 445行");
            int casNum = 0;
            for(;;){
                if(casNum >= 10){ //自旋10此挂起
                    System.out.println(Thread.currentThread().getName()+"线程自旋尝试获取锁失败10次,线程被挂起 451行");
                    LockSupport.park();
                    //Thread.interrupted();
                }
                if(tryCas(arg)){
                    return true;
                }
                casNum++;
                System.out.println(Thread.currentThread().getName()+"线程自旋尝试获取锁失败 456行");
            }
        }

        public boolean releaseLock(int arg){
            int i = state.get();
            if(i != 0){
                state.decrementAndGet();
            }
            return true;
        }
    }

    public static class TestCas implements Runnable{

        CasRun casRun;

        public TestCas(CasRun casRun){
            this.casRun = casRun;
        }

        @Override
        public void run() {
            casRun.cas(1);
            System.out.println(Thread.currentThread().getName()+"运行完成......................");

        }
    }

}


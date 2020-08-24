package com.example.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: XF
 * @Date: 2020/6/14 15:51
 */
public final class RingBufferWheel {
    Logger logger = LoggerFactory.getLogger(RingBufferWheel.class);

    private static final int STATIC_RING_SIZE = 8;//默认大小

    private Object[] ringBuffer;

    private int bufferSize;// 初始化大小

    private AtomicInteger taskSize = new AtomicInteger();

    private volatile boolean stop = false;

    private volatile AtomicBoolean start = new AtomicBoolean(false);

    private volatile AtomicLong tick = new AtomicLong();

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    private ExecutorService executorService;

    public RingBufferWheel(ExecutorService executorService){
        this.executorService = executorService;
        this.bufferSize = STATIC_RING_SIZE;
        this.ringBuffer = new Object[bufferSize];
    }

    public RingBufferWheel(int bufferSize){
       this(Executors.newFixedThreadPool(2),bufferSize);
    }

    public RingBufferWheel(ExecutorService executorService,int bufferSize){
        this(executorService);
        if(!powerOf2(bufferSize)){
            throw new RuntimeException("bufferSize=[" + bufferSize + "] must be a power of 2");
        }
        this.bufferSize = bufferSize;
        this.ringBuffer = new Object[bufferSize];
    }

    private boolean powerOf2(int target){
        if(target<=0){
            return false;
        }
        int value = target & (target - 1);
        if(value != 0){
            return false;
        }
        return true;
    }

    //添加task任务
    public void addTask(Task task){
        int key = task.getKey();
        int c = -1;
        try{
            lock.lock();
            int index = mod(key,bufferSize);
            logger.info("添加task 延时时间="+task.getKey()+"....index"+index);
            task.setIndex(index);
            LinkedHashSet<Task> tasks = get(index);
            int cycleNum = cycleNum(key); //得到task层级数
            task.setCycleNum(cycleNum);
            if (tasks != null) {
                tasks.add(task);
            }else{
                LinkedHashSet<Task> sets = new LinkedHashSet<>();
                sets.add(task);
                put(index,sets);
            }
            c = taskSize.getAndIncrement();
            if(c == 0){
                notEmpty.signal();
            }
        }finally {
            lock.unlock();
        }
        start();
    }


    public Set<Task> remove(int index){
        LinkedHashSet<Task> tempTask = new LinkedHashSet<>();
        LinkedHashSet<Task> reslutTask = new LinkedHashSet<>();

        LinkedHashSet<Task> sets = (LinkedHashSet<Task>) ringBuffer[index];
        if(sets == null){
            return reslutTask;
        }

        for(Task task : sets){
            if(task.getCycleNum() == 0){
                reslutTask.add(task);
                size2Notify();
            }else{
                task.setCycleNum(task.getCycleNum()-1);
                tempTask.add(task);
            }
        }
        //ringBuffer[index] = tempTask;
        put(index,tempTask);
        return reslutTask;
    }

    //启动时间轮
    public void start(){
        if(!start.get()){
            if(start.compareAndSet(start.get(),true)){
                logger.info("开始启动时间轮....");
                Thread thread = new Thread(new TriggerJob());
                thread.setName("consumer RingBuffer thread");
                thread.start();
            }
        }
    }

    //停止时间轮
    public void stop(){
        if(!stop){ //当前不是停止状态
            System.err.println("时间轮停止!!!");
            executorService.shutdown();
            stop = true;
        }
    }

    public void size2Notify(){
        try {
            lock.lock();
            taskSize.decrementAndGet();
        } finally {
            lock.unlock();
        }
    }


    //将下标为index的元素设置为set
    public void put(int index,LinkedHashSet<Task> tasks){
         ringBuffer[index] = tasks;
    }

    //计算task的层级数
    public int cycleNum(int key){
        return key / bufferSize;
    }

    //得到下标为index的set集合
    public LinkedHashSet<Task> get(int index){
        return (LinkedHashSet<Task>) ringBuffer[index];
    }

    //计算task在数组中的index下标 ,相当于取模,与hashmap中散列差不多
    public int mod(int key,int bufferSize){
        long key1 = key + tick.get();
        //(key1 & (bufferSize-1));
        return (int)key1 & (bufferSize-1); //(int)(key1 % bufferSize);
    }

    //继承Runable开启时间轮
    public class TriggerJob implements Runnable{
        @Override
        public void run() {
            int index =0 ;
            while (!stop){
                Set<Task> set = remove(index);
                logger.info("index="+index+",set.size="+set.size());
                for(Task task : set){
                    executorService.submit(task);
                }
                if(++index > bufferSize-1){
                    index = 0;
                }
                lock.lock();
                try {
                    while (taskSize.get() == 0){
                        notEmpty.await();
                    }
                    tick.incrementAndGet();
                    TimeUnit.SECONDS.sleep(1);
                }catch (InterruptedException e){
                    e.getStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }
    }

    public static class Task extends Thread{
        Logger logger = LoggerFactory.getLogger(Task.class);
        public int index; //数组中对应的下标位置
        public int key;//延时时间
        public int cycleNum;//任务对用的层级数


        @Override
        public void run() {
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getCycleNum() {
            return cycleNum;
        }

        public void setCycleNum(int cycleNum) {
            this.cycleNum = cycleNum;
        }
    }

}

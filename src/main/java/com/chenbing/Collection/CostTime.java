package com.chenbing.Collection;

public class CostTime {
    public long lastTime;

    public CostTime(){
        this.lastTime = System.currentTimeMillis();
    }

    public CostTime(long time){
        this.lastTime = time;
    }

    public void logCostTime(){
        System.out.println("--- cost ---"+(System.currentTimeMillis() - lastTime));
        this.lastTime = System.currentTimeMillis();
    }
}

package com.pravisolutions.zforInterviews.reentrantthread;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DataForOps {
    private ArrayList<Integer> buffer;
    private Lock lck;
    private int dataSize;

    DataForOps(ArrayList<Integer> list) {
        this.buffer = list;
        dataSize = this.buffer.size();
        lck = new ReentrantLock(true);
    }

    public int getDataSize() {
        return this.dataSize;
    }

    public int getSumOfElement(int accumulator, int index) {
        lck.lock();
        try {
            if(index == 0) {
                return (accumulator + buffer.get(index));
            }
            int currentVal = accumulator + buffer.get(index);
            return getSumOfElement(currentVal, (index-1));
        } finally {
            lck.unlock();
        }

    }

}

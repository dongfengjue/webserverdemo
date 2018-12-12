package com.chenbing.Collection;

import java.util.HashMap;

/*
* 固定的长度 扩不了容
* 数据都用链表扩展，当数据量大了之后，效率变低
* */
public class MyHashMap<K,V>  {
    private static int default_length=16;
    private MyEntry<K, V>[] entries;


    public MyHashMap() {
        super();
        entries = new MyEntry[default_length];
    }

    public V put(K key,V value) {
        int index = Math.abs(key.hashCode())%default_length;
//        System.out.println(key.hashCode()+"----"+entries.length+"-----"+index);
        MyEntry<K, V> previous = entries[index];
        for(MyEntry entry=entries[index];entry!=null;entry = entry.next) {
            if(entry.getKey().equals(key)) {
                V oldValue = (V) entry.getValue();
                entry.setValue(value);
                return oldValue;
            }
        }
        MyEntry<K, V> entry = new MyEntry<K,V>(key,value);
        entry.next=previous;
        entries[index] = entry;
        return null;
    }

    public V get(K key) {
        int index = Math.abs(key.hashCode())%default_length;
        for(MyEntry<K, V> entry=entries[index];entry!=null;entry=entry.next) {
            if(entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }


    private final class MyEntry<K,V> {
        private K key;
        private V value;
        private MyEntry next;
        public MyEntry(K key, V value) {
            super();
            this.key = key;
            this.value = value;
        }
        public MyEntry() {
            super();
        }
        public MyEntry(K key, V value, MyEntry next) {
            super();
            this.key = key;
            this.value = value;
            this.next = next;
        }
        public K getKey() {
            return key;
        }
        public void setKey(K key) {
            this.key = key;
        }
        public V getValue() {
            return value;
        }
        public void setValue(V value) {
            this.value = value;
        }
        public MyEntry getNext() {
            return next;
        }
        public void setNext(MyEntry next) {
            this.next = next;
        }
    }


    public static void main(String args[]){
        int loopNum = 100000;
        CostTime costTime = new CostTime(System.currentTimeMillis());

        MyHashMap<String,String> myHashMap = new MyHashMap<String, String>();
        for(int i = 0 ;i < loopNum ; i++){
            myHashMap.put("123"+i,"896"+i);
        }
        for(int i = 0 ;i < loopNum ; i++){
            myHashMap.get("123"+i);
//            System.out.println();
        }

        printCostTime(costTime);

        HashMap<String,String> hashMap = new HashMap<String, String>();
        for(int i = 0 ;i < loopNum ; i++){
            hashMap.put("123"+i,"896"+i);
        }
        for(int i = 0 ;i < loopNum ; i++){
            hashMap.get("123"+i);
//            System.out.println();
        }

        printCostTime(costTime);
    }

    public static void printCostTime(CostTime costTime){
//        System.out.println(costTime.lastTime);
        System.out.println("-----"+(System.currentTimeMillis() - costTime.lastTime));
        costTime.lastTime = System.currentTimeMillis();
    }
}

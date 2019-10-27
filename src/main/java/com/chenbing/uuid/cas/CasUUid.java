package com.chenbing.uuid.cas;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.SimpleFormatter;

public class CasUUid {
    private static AtomicInteger seq = new AtomicInteger(0);

    private static final int ROTATION = 99999;

    private static final String TIME_PATTERN = "yyyyMMddHHMMss";

    /**
     * 由于ROTATION的限制，每秒最多生成99999个不同的uid,否则会重复
     * @return
     */
    public static String next() {
        for (; ; ) {
            if (seq.intValue() < ROTATION || seq.compareAndSet(ROTATION, 0)) {
                break;
            }
        }
        for (; ; ) {
            int current = seq.intValue();
            if (current < ROTATION && seq.compareAndSet(current, ++current)) {
                return String.format("%s%04d", new SimpleDateFormat(TIME_PATTERN).format(new Date()), current);
            }
        }
    }

    public static void main(String[] args){
            System.out.println(CasUUid.next());
    }
}

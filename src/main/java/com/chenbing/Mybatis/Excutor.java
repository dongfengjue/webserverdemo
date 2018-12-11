package com.chenbing.Mybatis;

public interface Excutor {
    public <T> T query(String statement, Object parameter);
}

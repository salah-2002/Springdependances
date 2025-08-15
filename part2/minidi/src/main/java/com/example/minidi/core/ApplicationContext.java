package com.example.minidi.core;

public interface ApplicationContext {
    <T> T getBean(Class<T> type);
    Object getBean(String id);
  }

package com.example.minidi.core;

import java.lang.reflect.*;
import java.util.*;

public class SimpleContainer implements ApplicationContext {
  private final Map<String,Object> singletons = new HashMap<>();
  private final Map<String,BeanDefinition> defs = new HashMap<>();
  private final Map<Class<?>,Object> byType = new HashMap<>();

  public void register(BeanDefinition def){ defs.put(def.id, def); }
  public void initialize() {
    // instanciation + injection
    defs.values().forEach(d -> singletons.putIfAbsent(d.id, create(d.className)));
    // indexation par type
    singletons.values().forEach(b -> byType.putIfAbsent(b.getClass(), b));
  }

  private Object create(String className){
    try {
      Class<?> c = Class.forName(className);

      // 1) Constructor injection: ctor annoté @Inject ou ctor unique
      Constructor<?>[] ctors = c.getDeclaredConstructors();
      Constructor<?> target = Arrays.stream(ctors)
          .filter(ctor -> ctor.isAnnotationPresent(com.example.minidi.annotations.Inject.class))
          .findFirst().orElseGet(() -> ctors.length==1? ctors[0]: null);
      Object instance;
      if(target!=null && target.getParameterCount()>0){
        Object[] args = Arrays.stream(target.getParameterTypes()).map(this::resolveByType).toArray();
        target.setAccessible(true);
        instance = target.newInstance(args);
      } else {
        instance = c.getDeclaredConstructor().newInstance();
      }

      // 2) Field injection: @Inject sur attributs
      for(Field f: c.getDeclaredFields()){
        if(f.isAnnotationPresent(com.example.minidi.annotations.Inject.class)){
          f.setAccessible(true);
          f.set(instance, resolveByType(f.getType()));
        }
      }

      // 3) Setter injection: méthodes setX(@Inject Type)
      for(Method m: c.getDeclaredMethods()){
        if(m.getName().startsWith("set") && m.getParameterCount()==1 &&
           m.isAnnotationPresent(com.example.minidi.annotations.Inject.class)){
          m.setAccessible(true);
          m.invoke(instance, resolveByType(m.getParameterTypes()[0]));
        }
      }

      return instance;
    } catch(Exception e){ throw new RuntimeException(e); }
  }

  private Object resolveByType(Class<?> t){
    // existant ?
    for(Object b: singletons.values()){
      if(t.isAssignableFrom(b.getClass())) return b;
    }
    // sinon, tenter une définition correspondante
    for(BeanDefinition d: defs.values()){
      try {
        Class<?> c = Class.forName(d.className);
        if(t.isAssignableFrom(c)){
          return singletons.computeIfAbsent(d.id, k -> create(d.className));
        }
      } catch (Exception ignored) {}
    }
    throw new RuntimeException("No bean for type: "+t.getName());
  }

  @Override public <T> T getBean(Class<T> type){
    Object found = resolveByType(type);
    return type.cast(found);
  }
  @Override public Object getBean(String id){ return singletons.get(id); }
}
package com.example.minidi.annotations;

import com.example.minidi.core.*;
import java.io.IOException;
import java.net.*;
import java.util.*;

public class AnnotationApplicationContext extends SimpleContainer {
  public AnnotationApplicationContext(String basePackage){
    // scan très simple (classes du même classpath)
    for (String className: scan(basePackage)){
      try {
        Class<?> c = Class.forName(className);
        if(c.isAnnotationPresent(Component.class)){
          String id = c.getAnnotation(Component.class).value();
          if(id.isEmpty()) id = c.getSimpleName();
          register(new BeanDefinition(id, c.getName()));
        }
      } catch (ClassNotFoundException ignored){}
    }
    initialize();
  }
  private List<String> scan(String base){
    // impl minimale : suppose IDE/target/classes. Pour un TP c’est OK.
    String path = base.replace('.', '/');
    try {
      URL url = Thread.currentThread().getContextClassLoader().getResource(path);
      if(url==null) return List.of();
      var dir = new java.io.File(url.toURI());
      List<String> out = new ArrayList<>();
      for(var f: Objects.requireNonNull(dir.listFiles())){
        if(f.getName().endsWith(".class")){
          out.add(base+"."+f.getName().replace(".class",""));
        }
      }
      return out;
    } catch (URISyntaxException e){ return List.of(); }
  }
}
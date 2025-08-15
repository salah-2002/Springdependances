package com.example.minidi.xml;

import com.example.minidi.core.*;
import jakarta.xml.bind.JAXBContext;
import java.io.InputStream;

public class XmlApplicationContext extends SimpleContainer {
  public XmlApplicationContext(String xmlOnClasspath){
    try (InputStream is = Thread.currentThread().getContextClassLoader()
            .getResourceAsStream(xmlOnClasspath)) {
      var ctx = JAXBContext.newInstance(XmlConfig.class);
      var conf = (XmlConfig) ctx.createUnmarshaller().unmarshal(is);
      for (var b : conf.beans) register(new BeanDefinition(b.id, b.className));
      initialize();
    } catch (Exception e){ throw new RuntimeException(e); }
  }
}

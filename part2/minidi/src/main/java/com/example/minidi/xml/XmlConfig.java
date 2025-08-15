package com.example.minidi.xml;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name="beans")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlConfig {
  @XmlElement(name="bean") public List<XmlBean> beans;
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class XmlBean {
    @XmlAttribute public String id;
    @XmlAttribute(name="class") public String className;
  }
}

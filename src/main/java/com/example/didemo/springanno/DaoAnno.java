package com.example.didemo.springanno;

import com.example.didemo.dao.IDao;
import org.springframework.stereotype.Component;

@Component("daoAnno")
public class DaoAnno implements IDao {
    @Override public double getData() { return 50.0; }
}

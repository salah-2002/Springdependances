package com.example.didemo.springanno;

import com.example.didemo.dao.IDao;
import com.example.didemo.metier.IMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetierAnno implements IMetier {
    private final IDao dao;

    @Autowired
    public MetierAnno(IDao dao) { // injection par constructeur
        this.dao = dao;
    }

    @Override public double calcul() { return dao.getData() * 2; }
}

package com.example.didemo.metier;

import com.example.didemo.dao.IDao;

public class MetierImpl implements IMetier {
    // couplage faible: dépendance par l'interface
    private IDao dao;

    // injection par setter (classique)
    public void setDao(IDao dao) { this.dao = dao; }

    @Override
    public double calcul() {
        double data = dao.getData();
        return data * 1.5; // traitement métier
    }
}

package com.example.didemo.app;

import com.example.didemo.dao.DaoImpl;
import com.example.didemo.metier.MetierImpl;

public class AppStatic {
    public static void main(String[] args) {
        DaoImpl dao = new DaoImpl();          // new explicite
        MetierImpl metier = new MetierImpl();
        metier.setDao(dao);                   // injection manuelle

        System.out.println("Résultat = " + metier.calcul());
    }
}

package com.example.didemo.app;

import com.example.didemo.dao.IDao;
import com.example.didemo.metier.MetierImpl;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

public class AppDynamic {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        try (InputStream is = AppDynamic.class.getClassLoader()
                .getResourceAsStream("config.txt")) {
            props.load(is);
        }

        String daoClass = props.getProperty("dao.class");
        String metierClass = props.getProperty("metier.class");

        Class<?> cDao = Class.forName(daoClass);
        Class<?> cMetier = Class.forName(metierClass);

        Object dao = cDao.getDeclaredConstructor().newInstance();
        Object metier = cMetier.getDeclaredConstructor().newInstance();

        // appel du setter setDao via réflexion
        Method setDao = cMetier.getMethod("setDao", IDao.class);
        setDao.invoke(metier, dao);

        Method calcul = cMetier.getMethod("calcul");
        Object res = calcul.invoke(metier);
        System.out.println("Résultat = " + res);
    }
}

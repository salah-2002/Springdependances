package com.example.didemo.springanno;

import com.example.didemo.metier.IMetier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.example.didemo")
public class AnnoMain {
    public static void main(String[] args) {
        try (var ctx = new AnnotationConfigApplicationContext(AnnoMain.class)) {
            IMetier metier = ctx.getBean(IMetier.class);
            System.out.println("Résultat = " + metier.calcul());
        }
    }
}

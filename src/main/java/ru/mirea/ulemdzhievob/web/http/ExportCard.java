package ru.mirea.ulemdzhievob.web.http;

import ru.mirea.ulemdzhievob.Card;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ExportCard {

    public void saveCardToFile(Card card) {
        String fileName = card.getName() + ".crd";
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(card);
            System.out.println("Карта сохранена в " + fileName);
        } catch (IOException e) {
            System.err.println("Что-то не то в сохранении: " + e.getMessage());
        }
    }
}
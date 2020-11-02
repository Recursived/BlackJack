package controller;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.LinkedList;

import model.Card;
import model.Color;
import model.Value;

public class Deck {
    private LinkedList<Card> cardList = new LinkedList<Card>();

    public Deck(int nbBox){
        Value[] values = Value.class.getEnumConstants();
        Color[] colors = Color.class.getEnumConstants();
        for (int i = 0 ; i < nbBox; i++){
            for (Color c : colors){
                for (Value v : values){
                    cardList.add(new Card(v, c));
                }
            }
        }

        Collections.shuffle(cardList);
    }

    public Deck() {
        this(3);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Card c : cardList){
            sb.append(c.toString());
            sb.append(", ");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("]\n");
        return sb.toString();
    }

    public Card draw() throws EmptyDeckException {
        if (cardList.size() == 0){
            throw new EmptyDeckException("Impossible de retirer une carte lorsque le deck est vide !");
        }
        return cardList.poll();
    }
}

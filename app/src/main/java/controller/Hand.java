package controller;

import androidx.annotation.NonNull;

import java.util.LinkedList;

import model.Card;

public class Hand {
    private LinkedList<Card> cardList;

    public Hand() {
        this.cardList = new LinkedList<Card>();
    }

    public void clear(){
        this.cardList.clear();
    }

    public void add(Card card){
        this.cardList.add(card);
    }

    @Override
    public String toString() {
        // Affichage des cartes
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Card c : cardList){
            sb.append(c.toString());
            sb.append(", ");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("] : ");

        // TO-DO :  Affichage des scores possibles avec ces cartes
        return sb.toString();
    }
}

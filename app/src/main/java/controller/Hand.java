package controller;

import androidx.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

import model.Card;

public class Hand {
    private LinkedList<Card> cardList;

    public Hand() {
        this.cardList = new LinkedList<>();
    }

    public void clear(){
        this.cardList.clear();
    }

    public void add(Card card){
        this.cardList.add(card);
    }

    public List<Integer> count(){
        List<Integer> lst = new LinkedList<>();
        Integer total = 0;
        Integer total_2 = 0;

        for (Card c : cardList){
            if (c.getValueSymbole().equals("A")) {
                total += c.getPoints(); //
                total += c.getPoints() + 10; //
            }
            total += c.getPoints();
            total_2 += c.getPoints();
        }

        if (!total.equals(total_2)){
            lst.add(total);
            lst.add(total_2);
        } else {
            lst.add(total);
        }
        return lst;
    }

    public int best(){
        // On devrait installer jdk 1.8 pour les streams et les filters pour faire la fonction en une ligne
        List<Integer> points = count();
        Integer max = 0;
        for (Integer p: points){
            if (p <= 21 && max < p){
                max = p;
            }
        }
        return  max;
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
        sb.append("] : [");

        // Affichage des scores
        for (Integer i : count()){
            sb.append(i.toString());
            sb.append(", ");
        }

        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("]\n");

        // TO-DO :  Affichage des scores possibles avec ces cartes
        return sb.toString();
    }


}

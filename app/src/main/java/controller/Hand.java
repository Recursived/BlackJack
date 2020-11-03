package controller;

import androidx.annotation.NonNull;

import java.util.Arrays;
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
        int nbAs = 0;

        for (Card c : cardList){
            if (c.getValueSymbole().equals("A")) {
                nbAs++;
            }
            total += c.getPoints();
        }

        if(total>0) {
            lst.add(total);
        }

        for(int i=0;i<nbAs;i++){
            total+=10;
            lst.add(total);
        }

        return lst;
    }

    public int best(){
        // On devrait installer jdk 1.8 pour les streams et les filters pour faire la fonction en une ligne
        List<Integer> points = count();
        int max = 0;
        for (Integer p: points){
            if (p <= 21 && max < p){
                max = p;
            }
        }
        return max;
    }

    @Override
    public String toString() {
        // Affichage des cartes
        StringBuilder sb = new StringBuilder();
        sb.append(Arrays.toString(cardList.toArray()));
        sb.append(" : ");
        sb.append(Arrays.toString(count().toArray()));
        sb.append("\n");

        // TO-DO :  Affichage des scores possibles avec ces cartes
        return sb.toString();
    }


}
package view;

import android.util.Log;

import controller.Deck;
import controller.EmptyDeckException;
import controller.Hand;
import model.Card;
import model.Color;
import model.Value;

public class BlackJackConsole {

    public BlackJackConsole() {
        Card[] tab = {new Card(Value.TWO, Color.HEART), new Card(Value.JACK, Color.SPADE)};
        String TAG = "Card";

        for (Card c: tab){
            Log.e(TAG, "This card is a " + c + " worth " + c.getPoints() + " points" );
            Log.e(TAG, "It's a name " + c.getColorName());
            switch (c.getColorSymbole()){
                case "\u2665": Log.e(TAG, "symbole : heart"); break;
                case "\u2660": Log.e(TAG, "symbole : spade"); break;
                case "\u2663": Log.e(TAG, "symbole : club"); break;
                case "\u2666": Log.e(TAG, "symbole : diamond"); break;
            }

            if (c.getValueSymbole().matches("[JQK]")){
                Log.e(TAG, "It's a face");
            } else {
                Log.e(TAG, "It's not a face");
            }
        }

        Deck deck = new Deck(2);
        Hand hand = new Hand();
        Log.i(TAG, "Your hand is currently : "+ hand + "\n");
        for (int i = 0; i < 3; i++){
            try{
                hand.add(deck.draw());
            } catch (EmptyDeckException ex){
                System.err.println(ex.getMessage());
                System.exit(-1);
            }
        }
        Log.i(TAG, "Your hand is currently : "+ hand + "\n");
        hand.clear();
        Log.i(TAG, "Your hand is currently : "+ hand + "\n");

    }

}
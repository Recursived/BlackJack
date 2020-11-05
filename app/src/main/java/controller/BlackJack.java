package controller;

import java.util.LinkedList;
import java.util.List;

import model.Card;

public class BlackJack {

    private Deck deck;
    private Hand playerHand;
    private Hand bankHand;
    public int somme;
    public boolean gameFinished;
    private int nbDeck;

    public BlackJack() throws EmptyDeckException {
        this(3,3000);
    }

    public BlackJack(int nbDeck,int somme) throws EmptyDeckException {
        this.somme = somme;
        this.nbDeck=nbDeck;
        reset();
    }

    public void reset() throws EmptyDeckException {
        //Initialisation dans le reset pour réduire le nombre de ligne, et pas de reset dans le Deck, à revoir
        deck = new Deck(nbDeck);
        playerHand = new Hand();
        bankHand = new Hand();
        gameFinished = false;

        playerHand.add(deck.draw());
        playerHand.add(deck.draw());
        bankHand.add(deck.draw());
    }

    public String getPlayerHandString(){
        return playerHand.toString();
    }

    public String getBankHandString(){
        return bankHand.toString();
    }

    public int getPlayerBest(){
        return playerHand.best();
    }

    public int getBankBest(){
        return bankHand.best();
    }

    public List<Card> getPlayerCardList(){
        return new LinkedList<Card>(playerHand.getCardList());
    }

    public List<Card> getBankCardList(){
        return new LinkedList<Card>(bankHand.getCardList());
    }

    public boolean isPlayerWinner(){
        return getPlayerBest() > 0 && getPlayerBest() > getBankBest();
    }

    public boolean isBankWinner(){
        return getBankBest() > 0 && getPlayerBest() < getBankBest();
    }

    public boolean isGameFinished(){
        return gameFinished;
    }

    public void playerDrawAnotherCard() throws EmptyDeckException {
        if(!isGameFinished()){
            playerHand.add(deck.draw());
            if(playerHand.best()==0) gameFinished = true;
        }
    }

    public void bankLastTurn() throws EmptyDeckException {
        while(bankHand.best()<playerHand.best() && bankHand.best()!=0 && playerHand.best()!=0){
            bankHand.add(deck.draw());
        }
        gameFinished = true;
    }

    public void setNbDeck(int nbDeck){
        this.nbDeck=nbDeck;
    }

}

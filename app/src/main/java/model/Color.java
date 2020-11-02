package model;

public enum Color {
    HEART("\u2665"),
    SPADE("\u2660"),
    CLUB("\u2663"),
    DIAMOND("\u2666");

    private String symbole;

    private Color(String color){ this.symbole = color; }

    public String getSymbole(){ return this.symbole; }


}

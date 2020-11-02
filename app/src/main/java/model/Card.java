package model;

public class Card {
    private Value value;
    private Color color;

    public Card(Value value, Color color){
        this.value = value;
        this.color = color;
    }


    @Override
    public String toString() {
        return value.getSymbole() + color.getSymbole();
    }

    public String getColorSymbole(){
        return color.getSymbole();
    }

    public String getColorName(){
        return color.name();
    }

    public String getValueSymbole(){
        return value.getSymbole();
    }

    public int getPoints(){
        return value.getPoints();
    }

}

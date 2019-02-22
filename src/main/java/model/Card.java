package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Card {

    private SimpleIntegerProperty id;
    private SimpleStringProperty cardName;
    private SimpleIntegerProperty sell;
    private SimpleIntegerProperty buy;
    private SimpleIntegerProperty have;
    private SimpleIntegerProperty want;

    public Card(){

    }

    public Card(Integer s1, String s2, Integer s3, Integer s4, Integer s5, Integer s6){
        id = new SimpleIntegerProperty(s1);
        cardName = new SimpleStringProperty(s2);
        sell = new SimpleIntegerProperty(s3);
        buy = new SimpleIntegerProperty(s4);
        have = new SimpleIntegerProperty(s5);
        want = new SimpleIntegerProperty(s6);
    }
}

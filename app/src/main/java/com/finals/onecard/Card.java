package com.finals.onecard;

public class Card {
    public String cardName;
    public String cardType;

    public Card(String cardName, String cardType) {
        this.cardName = cardName;
        this.cardType = cardType;
    }

    public String getCardType() {
        return cardType;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}

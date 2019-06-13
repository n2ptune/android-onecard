package com.finals.onecard;

public class Card {
    public String cardName;
    public String cardType;
    public int number;
    public int imageId;

    public Card(String cardName, String cardType, int number) {
        this.cardName = cardName;
        this.cardType = cardType;
        this.number = number;
        try {
            this.imageId = R.drawable.class.getField(this.cardName).getInt(null);
        } catch(Exception e) {}
    }

    public String getCardType() {
        return cardType;
    }

    public String getCardName() {
        return cardName;
    }

    public int getCardNumber() {
        return number;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public int getImageId() {
        return imageId;
    }
}

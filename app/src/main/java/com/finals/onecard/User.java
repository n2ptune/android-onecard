package com.finals.onecard;

import java.util.ArrayList;

public class User {
    public ArrayList<Card> cards;
    public boolean turn;

    public User() {
        cards = new ArrayList<>();
        this.turn = false;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public boolean getTurn() { return this.turn; }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public int cardLength() {
        return this.cards.size();
    }

    public int getCardImageId(int index) {
        return this.cards.get(index).getImageId();
    }

    public Card getCard(int index) {
        return this.cards.get(index);
    }
}

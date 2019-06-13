package com.finals.onecard;

import java.util.ArrayList;
import java.util.Collections;

public class NotUser {
    public ArrayList<Card> cards;

    public NotUser() {
        this.cards = new ArrayList<>();

    }

    public void cardShuffle() {
        Collections.shuffle(this.cards);
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public int cardLength() { return this.cards.size(); }

    public Card pop() {
        return this.cards.remove(0);
    }
}

package com.finals.onecard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        User user1 = new User();
        User user2 = new User();
        NotUser notUser = new NotUser();

        ArrayList<String> sCard = new ArrayList<>();

        sCard.add("ace");
        sCard.add("jack");
        sCard.add("king");
        sCard.add("queen");

        for(int i = 0; i < 4; i++) {
            String cardType = (i == 0) ? "clubs" : (i == 1) ? "diamonds" : (i == 2) ? "hearts" : "spades";
            String cardName = "";
            for(int j = 0; j < 13; j++) {
                if(j >= 0 && 9 > j) {
                    cardName = "c" + (j + 2) + "_of_" + cardType;
                } else {
                    cardName = sCard.get(j - 9) + "_of_" + cardType;
                }
                notUser.addCard(new Card(cardName, cardType));
            }
        }

        notUser.addCard(new Card("red_joker", "joker"));
        notUser.addCard(new Card("black_joker", "joker"));

        notUser.cardShuffle();

        for(Card temp : notUser.cards)
            System.out.println(temp.getCardName());
    }
}

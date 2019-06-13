package com.finals.onecard;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    ImageView currentCard;
    LinearLayout layoutTop, layoutBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        User user1 = new User();
        User user2 = new User();
        NotUser notUser = new NotUser();

        currentCard = (ImageView) findViewById(R.id.currentCard);
        layoutTop = (LinearLayout) findViewById(R.id.layoutTop);
        layoutBottom = (LinearLayout) findViewById(R.id.layoutBottom);

        ArrayList<String> sCard = new ArrayList<>();

        sCard.add("ace");       /* Number = 1 */
        sCard.add("jack");      /* Number = 11 */
        sCard.add("king");      /* Number = 12 */
        sCard.add("queen");     /* Number = 13 */
                                /* Joker = 14 */

        for(int i = 0; i < 4; i++) {
            String cardType = (i == 0) ? "clubs" : (i == 1) ? "diamonds" : (i == 2) ? "hearts" : "spades";
            String cardName = "";
            int cardNumber;
            for(int j = 0; j < 13; j++) {
                if(j >= 0 && 9 > j) {
                    cardNumber = j + 2;
                    cardName = "c" + cardNumber + "_of_" + cardType;
                } else {
                    cardNumber = (sCard.get(j - 9).equals("ace")) ? 1 : (sCard.get(j - 9).equals("jack")) ? 11 : (sCard.get(j - 9).equals("king")) ? 12 : 13;
                    cardName = sCard.get(j - 9) + "_of_" + cardType;
                }
                notUser.addCard(new Card(cardName, cardType, cardNumber));
            }
        }

        notUser.addCard(new Card("red_joker", "joker", 14));
        notUser.addCard(new Card("black_joker", "joker", 14));

        notUser.cardShuffle();

        for(int i = 0; i < 7; i++) {
            user1.addCard(notUser.pop());
            user2.addCard(notUser.pop());
        }

        /* 시작 부분 */
        _init(user1, user2, notUser);
    }

    public void _init(User user1, User user2, NotUser notUser) {
        // 카드 한개 내려 놓기
        Card _currentCard = notUser.pop();
        // Drawable 저장
        Drawable drawable = getDrawable(_currentCard.getImageId());
        // ImageView 연결
        currentCard.setImageDrawable(drawable);

        // 이미지 버튼 만들어서 레이아웃과 연결
        for(int i = 0; i < user1.cardLength(); i++) {
            /* 카드 값 구해오기 */
            Card _card = user1.getCard(i);
            final String _type = _card.getCardType();
            final String _name = _card.getCardName();
            final int _number = _card.getCardNumber();
            /* 이미지 버튼 만들기 */
            final ImageButton btnImg = new ImageButton(this);
            /* 레이아웃과 연결 */
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(200, 300);
            btnImg.setLayoutParams(lp);
            btnImg.setTop(30);
            btnImg.setScaleType(ImageView.ScaleType.FIT_XY);
            btnImg.setImageResource(user1.getCardImageId(i));
            layoutTop.addView(btnImg);

            /* 버튼 이벤트 연결 */
            btnImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(_name + ", " + _type + ", " + _number);
                    layoutTop.removeView(v);
                }
            });
        }

        for(int i = 0; i < user2.cardLength(); i++) {
            /* 카드 값 구해오기 */
            Card _card = user2.getCard(i);
            final String _type = _card.getCardType();
            final String _name = _card.getCardName();
            /* 이미지 버튼 만들기 */
            ImageButton btnImg = new ImageButton(this);
            /* 레이아웃과 연결 */
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(200, 300);
            btnImg.setLayoutParams(lp);
            btnImg.setScaleType(ImageView.ScaleType.FIT_XY);
            btnImg.setImageResource(user2.getCardImageId(i));
            layoutBottom.addView(btnImg);

            /* 버튼 이벤트 연결 */
            btnImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(_name + ", " + _type);
                }
            });
        }
    }
}

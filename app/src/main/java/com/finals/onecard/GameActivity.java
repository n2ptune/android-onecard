package com.finals.onecard;

import android.content.Intent;
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
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    ImageView currentCard;
    ImageButton cardBack;
    LinearLayout layoutTop, layoutBottom;
    Card __currentCard;

    User user1 = new User();
    User user2 = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        final NotUser notUser = new NotUser();

        user1.setTurn(true);
        user2.setTurn(false);

        currentCard = (ImageView) findViewById(R.id.currentCard);
        layoutTop = (LinearLayout) findViewById(R.id.layoutTop);
        layoutBottom = (LinearLayout) findViewById(R.id.layoutBottom);
        cardBack = (ImageButton) findViewById(R.id.cardBack);

        layoutBottom.setVisibility(View.INVISIBLE);

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

        __currentCard = notUser.pop();

        cardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user1.getTurn()) {
                    user1.addCard(notUser.pop());
                    if(user1.cardLength() >= 20) { goEnd(); }
                    _init(user1, user2, notUser);
                    nextTurn();
                }
                else {
                    user2.addCard(notUser.pop());
                    if(user2.cardLength() >= 20) { goEnd(); }
                    _init(user1, user2, notUser);
                    nextTurn();
                }
            }
        });

        /* 시작 부분 */
        _init(user1, user2, notUser);
    }

    public void _init(final User user1, final User user2, NotUser notUser) {
        // Drawable 저장
        Drawable drawable = getDrawable(__currentCard.getImageId());
        // ImageView 연결
        currentCard.setImageDrawable(drawable);

        // 이미지 버튼 만들어서 레이아웃과 연결
        for(int i = 0; i < user1.cardLength(); i++) {
            /* 카드 값 구해오기 */
            final Card _card = user1.getCard(i);
            final String _type = _card.getCardType();
            final String _name = _card.getCardName();
            final int _number = _card.getCardNumber();
            /* 이미지 버튼 만들기 */
            final ImageButton btnImg = new ImageButton(this);
            /* 레이아웃과 연결 */
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(190, 250);
            btnImg.setLayoutParams(lp);
            btnImg.setTop(30);
            btnImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
            btnImg.setImageResource(user1.getCardImageId(i));
            btnImg.setVerticalScrollBarEnabled(true);
            layoutTop.addView(btnImg);

            /* 버튼 이벤트 연결 */
            btnImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* 현재 꺼내져 있는 카드와 적절성 판단 */
                    if(_number == __currentCard.getCardNumber() ||
                            _type == __currentCard.getCardType() ||
                            __currentCard.getCardNumber() == 14) {
                        layoutTop.removeView(v);
                        nextTurn();
                        __currentCard = _card;
                        drawCard(__currentCard);
                    } else if(_number == 14) {
                        /* 조커 처리 */
                        layoutTop.removeView(v);
                        nextTurn();
                        __currentCard = _card;
                    } else {
                        /* 다른걸 낼 때 */
                        Toast.makeText(GameActivity.this, "현재 카드와 숫자 혹은 문양이 같아야 합니다.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        for(int i = 0; i < user2.cardLength(); i++) {
            /* 카드 값 구해오기 */
            final Card _card = user2.getCard(i);
            final String _type = _card.getCardType();
            final String _name = _card.getCardName();
            final int _number = _card.getCardNumber();
            /* 이미지 버튼 만들기 */
            final ImageButton btnImg = new ImageButton(this);
            /* 레이아웃과 연결 */
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(190, 250);
            btnImg.setLayoutParams(lp);
            btnImg.setTop(30);
            btnImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
            btnImg.setImageResource(user2.getCardImageId(i));
            layoutBottom.addView(btnImg);

            /* 버튼 이벤트 연결 */
            btnImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* 현재 꺼내져 있는 카드와 적절성 판단 */
                    if(_number == __currentCard.getCardNumber() ||
                            _type == __currentCard.getCardType() ||
                            __currentCard.getCardNumber() == 14) {
                        layoutBottom.removeView(v);
                        nextTurn();
                        __currentCard = _card;
                        drawCard(__currentCard);
                    } else if(_number == 14) {
                        /* 조커 처리 */
                        layoutBottom.removeView(v);
                        nextTurn();
                        __currentCard = _card;
                    } else {
                        /* 다른걸 낼 때 */
                        Toast.makeText(GameActivity.this, "현재 카드와 숫자 혹은 문양이 같아야 합니다.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void drawCard(Card card) {
        currentCard.setImageResource(card.getImageId());
    }

    public void nextTurn() {
        if(user1.getTurn()) {
            layoutTop.setVisibility(View.INVISIBLE);
            user1.setTurn(false);

            layoutBottom.setVisibility(View.VISIBLE);
            user2.setTurn(true);
        } else {
            layoutTop.setVisibility(View.VISIBLE);
            user1.setTurn(true);

            layoutBottom.setVisibility(View.INVISIBLE);
            user2.setTurn(false);
        }
    }

    public void goEnd() {
        Intent intent = new Intent(GameActivity.this, EndActivity.class);
        startActivity(intent);
    }
}

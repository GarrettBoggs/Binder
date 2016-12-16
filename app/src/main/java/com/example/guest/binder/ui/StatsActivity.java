package com.example.guest.binder.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.binder.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StatsActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.winnerImage) ImageView mWinnerImage;
    @Bind(R.id.loserImage) ImageView mLoserImage;
    @Bind(R.id.nextButton) Button mNextButton;
    @Bind(R.id.contactClick) TextView mContactClick;
    @Bind(R.id.victorButton) Button mVictorButton;
    @Bind(R.id.characterButton) Button  mCharacterButton;

    Animation moveRight, moveLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String winner = intent.getStringExtra("winner");
        String loser = intent.getStringExtra("loser");

        mNextButton.setOnClickListener(this);
        mContactClick.setOnClickListener(this);
        mCharacterButton.setOnClickListener(this);
        mVictorButton.setOnClickListener(this);

        Picasso.with(this.getBaseContext()).load(winner).into(mWinnerImage);
        Picasso.with(this.getBaseContext()).load(loser).into(mLoserImage);

        moveRight = AnimationUtils.loadAnimation(this, R.anim.move_right);
        moveRight.setFillAfter(true);

        moveLeft = AnimationUtils.loadAnimation(this, R.anim.move_left);
        moveLeft.setFillAfter(true);

        mWinnerImage.startAnimation(moveRight);
        mLoserImage.startAnimation(moveLeft);
    }

    @Override
    public void onClick(View v) {

        if (v == mNextButton) {
            Intent intent = new Intent(StatsActivity.this, CoverActivity.class);
            startActivity(intent);
        }

        else if (v == mContactClick) {
            Intent intent = new Intent(StatsActivity.this, ContactActivity.class);
            startActivity(intent);
        }

        else if (v == mCharacterButton) {
            Intent intent = new Intent(StatsActivity.this, CharacterActivity.class);
            startActivity(intent);
        }

        else if (v == mVictorButton) {
            Intent intent = new Intent(StatsActivity.this, HistoryActivity.class);
            startActivity(intent);
        }


    }

}

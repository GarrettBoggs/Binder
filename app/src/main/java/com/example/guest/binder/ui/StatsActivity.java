package com.example.guest.binder.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.guest.binder.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StatsActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.winnerText) TextView mWinnerText;
    @Bind(R.id.loserText) TextView mLoserText;
    @Bind(R.id.nextButton) Button mNextButton;
    @Bind(R.id.contactClick) TextView mContactClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String winner = intent.getStringExtra("winner");
        String loser = intent.getStringExtra("loser");

        mWinnerText.setText(winner);
        mLoserText.setText(loser);

        mNextButton.setOnClickListener(this);
        mContactClick.setOnClickListener(this);

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


    }

}
package com.example.guest.binder.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.guest.binder.Constants;
import com.example.guest.binder.adapters.CharacterListAdapter;
import com.example.guest.binder.R;
import com.example.guest.binder.services.BombService;
import com.example.guest.binder.models.Character;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CoverActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.characterOneButton) Button mCharacterOneButton;
    @Bind(R.id.characterTwoButton) Button mCharacterTwoButton;

    private int mOrientation;

    private CharacterListAdapter mAdapter;

    private DatabaseReference mWinsReference;

    public Character mCharacter;

    public ArrayList<Character> mCharacters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mOrientation = this.getResources().getConfiguration().orientation;

        mWinsReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_WINS);

        mWinsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    String location = locationSnapshot.getValue().toString();
                    Log.d("Wins updated", "wins:");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);
        ButterKnife.bind(this);

        getCharacter();
        getCharacter();

        mCharacterOneButton.setOnClickListener(this);
        mCharacterTwoButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(CoverActivity.this, StatsActivity.class);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        DatabaseReference mWinsReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_CHARACTERS)
                .child(uid);

        DatabaseReference pushRef = mWinsReference.push();
        String pushId = pushRef.getKey();

        if(v == mCharacterOneButton){

            mCharacters.get(0).setPushId(pushId);
            pushRef.setValue( mCharacters.get(0));

            intent.putExtra("winner", mCharacters.get(0).getPicture());
            intent.putExtra("loser", mCharacters.get(1).getPicture());
            startActivity(intent);
        }

        if(v == mCharacterTwoButton){

            mCharacters.get(1).setPushId(pushId);
            pushRef.setValue( mCharacters.get(1));

            intent.putExtra("winner", mCharacters.get(1).getPicture());
            intent.putExtra("loser", mCharacters.get(0).getPicture());
            startActivity(intent);
        }
    }

    private void getCharacter() {
        final BombService bombService = new BombService();
        bombService.findCharacter(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mCharacters.add(bombService.proccessResults(response));

                CoverActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                            mAdapter = new CharacterListAdapter(getApplicationContext(), mCharacters);
                            mRecyclerView.setAdapter(mAdapter);

                        if(mOrientation == Configuration.ORIENTATION_LANDSCAPE){
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CoverActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            mRecyclerView.setLayoutManager(layoutManager);
                            mRecyclerView.setHasFixedSize(false);
                        }
                        else{
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CoverActivity.this);
                            mRecyclerView.setLayoutManager(layoutManager);
                            mRecyclerView.setHasFixedSize(false);
                        }

                        mCharacterOneButton.setText(mCharacters.get(0).getName());

                        if(mCharacters.size() > 1){

                            if(mCharacters.get(0).getName().equals(mCharacters.get(1).getName()) ) {
                                mCharacters.remove(1);
                                getCharacter();
                            }

                            if(mCharacters.size() > 1){
                                mCharacterTwoButton.setText(mCharacters.get(1).getName());
                            }

                        }

                    }

                });
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelable("characters", Parcels.wrap(mCharacters));
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mCharacters = Parcels.unwrap(savedInstanceState.getParcelable("characters"));
    }

}

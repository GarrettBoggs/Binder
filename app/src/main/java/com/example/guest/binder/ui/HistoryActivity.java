package com.example.guest.binder.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.guest.binder.Constants;
import com.example.guest.binder.R;
import com.example.guest.binder.adapters.FirebaseCharacterListAdapter;
import com.example.guest.binder.adapters.FirebaseCharacterViewHolder;
import com.example.guest.binder.models.Character;
import com.example.guest.binder.util.OnStartDragListener;
import com.example.guest.binder.util.SimpleItemTouchHelperCallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 12/8/16.
 */

    public class HistoryActivity extends AppCompatActivity implements OnStartDragListener {
        private DatabaseReference mCharacterReference;
        private FirebaseCharacterListAdapter mFirebaseAdapter;
        private ItemTouchHelper mItemTouchHelper;

        @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_character);
            ButterKnife.bind(this);

            setUpFirebaseAdapter();
        }

        private void setUpFirebaseAdapter() {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            Query query = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_CHARACTERS).child(uid).orderByChild(Constants.FIREBASE_QUERY_INDEX);

            mFirebaseAdapter = new FirebaseCharacterListAdapter(Character.class, R.layout.character_list_item_drag, FirebaseCharacterViewHolder.class,
                            query, this, this);

            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mFirebaseAdapter);

            ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
            mItemTouchHelper = new ItemTouchHelper(callback);
            mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            mFirebaseAdapter.cleanup();
        }

        @Override
        public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
            mItemTouchHelper.startDrag(viewHolder);
        }
    }


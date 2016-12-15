package com.example.guest.binder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.binder.R;
import com.example.guest.binder.models.Character;
import com.example.guest.binder.util.ItemTouchHelperViewHolder;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

/**
 * Created by Guest on 12/8/16.
 */
    public class FirebaseCharacterViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        private static final int MAX_WIDTH = 200;
        private static final int MAX_HEIGHT = 200;

        View mView;
        Context mContext;

        public ImageView mCharacterImageView;

        public FirebaseCharacterViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mContext = itemView.getContext();
        }

        public void bindCharacter(Character character) {
            TextView mCharacterTextView = (TextView) mView.findViewById(R.id.characterNameTextView);
            TextView mCharacterDescTextView = (TextView) mView.findViewById(R.id.descriptionTextView);
            mCharacterImageView = (ImageView) mView.findViewById(R.id.characterImageView);


            Picasso.with(mContext)
                    .load(character.getPicture())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(mCharacterImageView);

            mCharacterTextView.setText(character.getName());
            mCharacterDescTextView.setText(character.getDescription());

        }

    @Override
    public void onItemSelected() {
        Log.d("Animation", "onItemSelected");
        itemView.animate()
                .alpha(0.7f)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(500);
    }

    @Override
    public void onItemClear() {
        Log.d("Animation", "onItemClear");
        itemView.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f);
    }

}

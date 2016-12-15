package com.example.guest.binder.util;

/**
 * Created by Guest on 12/15/16.
 */
public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}

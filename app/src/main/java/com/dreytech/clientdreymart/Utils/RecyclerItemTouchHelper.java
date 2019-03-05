package com.dreytech.clientdreymart.Utils;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.dreytech.clientdreymart.Adapter.CartAdapter;
import com.dreytech.clientdreymart.Adapter.FavoriteAdapter;
import com.dreytech.clientdreymart.Database.ModelDB.Favorite;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    RecyclerItemTouchHelperListener listener;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (listener != null)
            listener.onSwiped(viewHolder,direction,viewHolder.getAdapterPosition());
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof FavoriteAdapter.FavoriteViewHolder)
        {
            View foregroundView = ((FavoriteAdapter.FavoriteViewHolder)viewHolder).view_foreground;
            getDefaultUIUtil().clearView(foregroundView);
        }
        else  if (viewHolder instanceof CartAdapter.CartViewHolder)
        {
            View foregroundView = ((CartAdapter.CartViewHolder)viewHolder).view_foreground;
            getDefaultUIUtil().clearView(foregroundView);
        }

    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null)
        {
            if (viewHolder instanceof FavoriteAdapter.FavoriteViewHolder)
            {
                View foregroundView = ((FavoriteAdapter.FavoriteViewHolder)viewHolder).view_foreground;
                getDefaultUIUtil().onSelected(foregroundView);
            }
            else if (viewHolder instanceof CartAdapter.CartViewHolder)
            {
                View foregroundView = ((CartAdapter.CartViewHolder)viewHolder).view_foreground;
                getDefaultUIUtil().onSelected(foregroundView);
            }
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (viewHolder instanceof FavoriteAdapter.FavoriteViewHolder)
        {
            View foregroundView = ((FavoriteAdapter.FavoriteViewHolder)viewHolder).view_foreground;
            getDefaultUIUtil().onSelected(foregroundView);
        }
        else if (viewHolder instanceof CartAdapter.CartViewHolder)
        {
            View foregroundView = ((CartAdapter.CartViewHolder)viewHolder).view_foreground;
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (viewHolder instanceof FavoriteAdapter.FavoriteViewHolder)
        {
            View foregroundView = ((FavoriteAdapter.FavoriteViewHolder)viewHolder).view_foreground;
            getDefaultUIUtil().clearView(foregroundView);
        }
        else if (viewHolder instanceof CartAdapter.CartViewHolder)
        {
            View foregroundView = ((CartAdapter.CartViewHolder)viewHolder).view_foreground;
            getDefaultUIUtil().clearView(foregroundView);
        }

    }
}

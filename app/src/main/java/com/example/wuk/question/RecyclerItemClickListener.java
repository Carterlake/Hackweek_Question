package com.example.wuk.question;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import static java.lang.Math.abs;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;
    private GestureDetector mGestureDetector;
    private View childView;
    private RecyclerView touchView;
    public static boolean isDeleteShown;
    private static View lastChildView = null;

    public RecyclerItemClickListener(Context context, RecyclerItemClickListener.OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (childView != null && mListener != null) {
                    mListener.onItemClick(childView, touchView.getChildAdapterPosition(childView));
                }
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                if (childView != null && mListener != null) {
                    mListener.onLongClick(childView, touchView.getChildAdapterPosition(childView));
                }
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (abs(distanceY) > abs(distanceX)) {
                    return false;
                }
                if (childView != null && mListener != null && distanceX > 30) {
                    if (lastChildView == null) {
                        lastChildView = childView;
                    } else {
                        mListener.onCancel(lastChildView, touchView.getChildAdapterPosition(lastChildView));
                        lastChildView = childView;
                    }
                    mListener.onScroll(childView, touchView.getChildAdapterPosition(childView));
                }
                if (childView != null && mListener != null && distanceX < -20) {
                    mListener.onCancel(childView, touchView.getChildAdapterPosition(childView));
                }
                return true;
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onLongClick(View view, int position);

        void onScroll(View view, int position);

        void onCancel(View view, int position);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        touchView = rv;
        childView = rv.findChildViewUnder(e.getX(), e.getY());
        return mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
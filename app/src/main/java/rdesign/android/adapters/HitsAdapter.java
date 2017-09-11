package rdesign.android.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;

import rdesign.android.R;
import rdesign.android.bean.Hit;
import rdesign.android.util.DateUtil;

/**
 * Created by saravillarreal on 9/6/17.
 */

public class HitsAdapter extends RecyclerView.Adapter<HitsAdapter.ViewHolder> {
    private ArrayList<Hit> values;
    Context mContex;

    // Provide a suitable constructor (depends on the kind of dataset)
    public HitsAdapter(ArrayList<Hit> myDataset, Context context) {
        values = myDataset;
        mContex = context;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader, txtTitle, txtAuthor, txtLink, txtDescription, txtDate, txtTags;
        public ImageView mImage;
        public View layout;
        public LinearLayout linearLayoutInfo, linearWeb;
        public WebView mWebView;
        public CardView mCardView;
        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtTitle = (TextView) v.findViewById(R.id.title_info);
            txtAuthor = (TextView) v.findViewById(R.id.author_info);
            txtDate = (TextView) v.findViewById(R.id.date_taken_info);
            linearLayoutInfo = (LinearLayout) v.findViewById(R.id.linear_info);
            linearWeb = (LinearLayout) v.findViewById(R.id.linear_web);
            mWebView = (WebView) v.findViewById(R.id.web_view);
            mImage = (ImageView) v.findViewById(R.id.icon);
            mCardView = (CardView) v.findViewById(R.id.card_view);
        }
    }

    public void add(int position, String item) {
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }



    // Create new views (invoked by the layout manager)
    @Override
    public HitsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = values.get(position).getTitle();
        holder.linearLayoutInfo.setVisibility(View.VISIBLE);
        holder.txtTitle.setText(values.get(position).getStory_title());
        holder.txtAuthor.setText(values.get(position).getAuthor());
        try {
            holder.txtDate.setText(DateUtil.getTimeAgo(DateUtil.getDateAtMilliSec(values.get(position).getCreated_at())));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.linearLayoutInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //web view
                holder.linearWeb.setVisibility(View.VISIBLE);
                holder.mWebView.loadUrl(values.get(position).getStory_url());




            }
        });


        holder.linearLayoutInfo.setOnTouchListener(new OnSwipeTouchListener(mContex) {
            @Override
            public void onSwipeLeft() {
                remove(position);
                Toast.makeText(mContex, "onSwipeLeft", Toast.LENGTH_SHORT).show();
            }

        });

        

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

    public class OnSwipeTouchListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener(Context context) {
            gestureDetector = new GestureDetector(context, new GestureListener());
        }

        public void onSwipeLeft() {
        }

        public void onSwipeRight() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_DISTANCE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float distanceX = e2.getX() - e1.getX();
                float distanceY = e2.getY() - e1.getY();
                if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (distanceX > 0)
                        onSwipeRight();
                    else
                        onSwipeLeft();
                    return true;
                }
                return false;
            }
        }
    }
}

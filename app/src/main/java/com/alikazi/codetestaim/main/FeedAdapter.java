package com.alikazi.codetestaim.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alikazi.codetestaim.R;
import com.alikazi.codetestaim.models.PlayoutItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapter extends ListAdapter<PlayoutItem, FeedAdapter.ItemViewHolder> {

    private static final DiffUtil.ItemCallback<PlayoutItem> ITEM_COMPARATOR =
            new DiffUtil.ItemCallback<PlayoutItem>() {
                @Override
                public boolean areItemsTheSame(@NonNull PlayoutItem oldItem, @NonNull PlayoutItem newItem) {
                    return oldItem.id.equalsIgnoreCase(newItem.id);
                }

                @Override
                public boolean areContentsTheSame(@NonNull PlayoutItem oldItem, @NonNull PlayoutItem newItem) {
                    return oldItem == newItem;
                }
            };

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ItemSelectionListener mItemSelectionListener;

    public FeedAdapter(Context context, ItemSelectionListener itemSelectionListener) {
        super(ITEM_COMPARATOR);
        mContext = context;
        mItemSelectionListener = itemSelectionListener;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_playout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemSelectionListener != null) {
                    mItemSelectionListener.onItemSelected(position);
                }
            }
        });

        PlayoutItem item = getItem(position);
        holder.artistTextView.setText(item.artist);
    }

    protected class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView heroImageView;
        private TextView titleTextView;
        private TextView artistTextView;
        private TextView albumTextView;
        private ImageView cartImageView;

        private ItemViewHolder(View view) {
            super(view);
            heroImageView = view.findViewById(R.id.hero_item_image);
            titleTextView = view.findViewById(R.id.item_title);
            artistTextView = view.findViewById(R.id.item_artist);
            albumTextView = view.findViewById(R.id.item_album);
            cartImageView = view.findViewById(R.id.item_cart);
        }
    }

    public interface ItemSelectionListener {
        void onItemSelected(int itemPosition);
    }
}

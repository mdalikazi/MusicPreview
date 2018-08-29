package com.alikazi.codetestaim.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alikazi.codetestaim.R;
import com.alikazi.codetestaim.models.PlayoutItem;
import com.alikazi.codetestaim.utils.AimViewUtils;
import com.alikazi.codetestaim.utils.NetConstants;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapter extends ListAdapter<PlayoutItem, FeedAdapter.ItemViewHolder> {

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

        final PlayoutItem item = getItem(position);
        AimViewUtils.showImageWithGlide(mContext, item.imageUrl, holder.heroImageView);
        holder.titleTextView.setText(item.title);
        holder.artistTextView.setText(item.artist);
        holder.albumTextView.setText(item.album);
        holder.cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.customFields != null) {
                    PlayoutItem.CustomField customField = item.customFields.get(0);
                    if (customField.name.equalsIgnoreCase(NetConstants.CUSTOM_FIELDS_KEY_ITUNES_BUY)) {
                        Toast.makeText(mContext, R.string.toast_message_itunes, Toast.LENGTH_LONG).show();
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(customField.value));
                        mContext.startActivity(browserIntent);
                    }
                }
            }
        });
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

    public interface ItemSelectionListener {
        void onItemSelected(int itemPosition);
    }
}

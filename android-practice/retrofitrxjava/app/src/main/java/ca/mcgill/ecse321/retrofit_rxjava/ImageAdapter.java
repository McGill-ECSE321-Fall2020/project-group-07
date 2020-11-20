package ca.mcgill.ecse321.retrofit_rxjava;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private Bitmap[] localBitmapSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.artworkImage);
        }

        public ImageView getImageView() {
            return imageView;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param bitmapSet Bitmap[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public ImageAdapter(Bitmap[] bitmapSet) {
        localBitmapSet = bitmapSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_row, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getImageView().setImageBitmap(localBitmapSet[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localBitmapSet.length;
    }
}

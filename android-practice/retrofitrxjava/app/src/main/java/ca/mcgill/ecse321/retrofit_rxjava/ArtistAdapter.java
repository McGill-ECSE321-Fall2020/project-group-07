package ca.mcgill.ecse321.retrofit_rxjava;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ca.mcgill.ecse321.retrofit_rxjava.dto.ArtistDto;
import ca.mcgill.ecse321.retrofit_rxjava.dto.ArtworkDto;
import de.hdodenhof.circleimageview.CircleImageView;


public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {

    private static final String TAG = "ImageAdapter";

    private Bitmap[] localBitmapSet;
    private String [] firstLastName;
    private String [] username;
    private Long [] artistId;


    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView imageView;
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.profile_image);
            textView=view.findViewById(R.id.artist_name);
        }

        public ImageView getImageView() {
            return imageView;
        }
        public TextView getTextView(){return textView;}
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param bitmapSet Bitmap[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public ArtistAdapter(Context context, Bitmap[] bitmapSet, List<ArtistDto> dtos) {
        localBitmapSet = bitmapSet;
        setAttributes(dtos);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.artist_recycler_row, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.getImageView().setImageBitmap(localBitmapSet[position]);
        viewHolder.getTextView().setText(firstLastName[position]);

//        viewHolder.itemView.setOnClickListener(view -> {
//            Intent intent = new Intent(view.getContext(),ArtworkDetailActivity.class);
//            intent.putExtra("TITLE",title[position]);
//            intent.putExtra("DESC",desc[position]);
//            intent.putExtra("MEDIUM",medium[position]);
//            intent.putExtra("DIMENSION",dimension[position]);
//            intent.putExtra("PRICE",price[position]);
//            intent.putExtra("ARTIST",artist[position]);
//            intent.putExtra("ID",((Integer)artId[position]).toString());
//            intent.putExtra("WEIGHT",((Double) weight[position]).toString());
//
//            view.getContext().startActivity(intent);
//            Log.e(TAG, "onClick: "+artId[position] );
//        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localBitmapSet.length;
    }

    private void setAttributes(List<ArtistDto> dtos){
        int n=dtos.size();

        firstLastName=new String[n];
        username=new String[n];
        artistId=new Long[n];

        for (int i=0;i<n;i++){

            ArtistDto d = dtos.get(i);
            firstLastName[i]=d.getFirstname()+" "+d.getLastname();
            username[i]=d.getLastname();
            artistId[i]=d.getArtistId();
        }
    }
}

package ca.mcgill.ecse321.android_frontend;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import ca.mcgill.ecse321.android_frontend.dto.ArtworkDto;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private static final String TAG = "ImageAdapter";

    private Bitmap[] localBitmapSet;
    private String [] title;
    private String [] artist;
    private String [] desc;
    private String [] medium;
    private String [] dimension;
    private double [] weight;
    private double [] price;
    private int [] artId;


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


    public ImageAdapter(Context context, List<ArtworkDto> dtos, boolean sortById) {
        setAttributes(dtos,sortById);
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

        viewHolder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(),ArtworkDetailActivity.class);
            intent.putExtra("TITLE",title[position]);
            intent.putExtra("DESC",desc[position]);
            intent.putExtra("MEDIUM",medium[position]);
            intent.putExtra("DIMENSION",dimension[position]);
            intent.putExtra("PRICE",price[position]);
            intent.putExtra("ARTIST",artist[position]);
            intent.putExtra("ID",((Integer)artId[position]).toString());
            intent.putExtra("WEIGHT",((Double) weight[position]).toString());

            view.getContext().startActivity(intent);
            Log.e(TAG, "onClick: "+artId[position] );
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localBitmapSet.length;
    }


    private void setAttributes(List<ArtworkDto> dtos, boolean sort){

        if (sort){
            Collections.sort(dtos);
        }
        int n=dtos.size();

        localBitmapSet=new Bitmap[n];
        title=new String[n];
        artist=new String[n];
        desc=new String[n];
        medium=new String[n];
        dimension=new String[n];
        price=new double[n];
        artId=new int[n];
        weight=new double[n];

        for (int i=0;i<n;i++){
            ArtworkDto d = dtos.get(i);

            localBitmapSet[i]=d.getArtBitmap();
            title[i]=d.getName();
            artist[i]=d.getUsername();
            desc[i]=d.getDescription();
            medium[i]=d.getMedium();
            dimension[i]=d.getDimension();
            price[i]=d.getPrice();
            artId[i]=d.getArtworkId();
            weight[i]=d.getWeight();

            Log.e(TAG, "setAttributes: "+d.getArtworkId() );
        }
    }
}
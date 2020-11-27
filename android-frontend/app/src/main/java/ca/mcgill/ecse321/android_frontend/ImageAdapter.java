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

    /**
     * Creates a Custom ViewHolder class that extends the RecyclerView.ViewHolder
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
     * initializes the data of the Adapter to populate the Recylcer view
     * @param context
     * @param dtos  List<ArtworkDto> containing the text and Bitmap information of the artworks that
     *              will eventually populate the RecyclerView
     * @param sortById boolean, whether to sort the list of ArtworkDto's by most recently uploaded
     *                 or not. True when used for display individual artist's pages, false for the
     *                 Discovery page
     */
    public ImageAdapter(Context context, List<ArtworkDto> dtos, boolean sortById) {
        setAttributes(dtos,sortById);
    }

    /**
     * boiler plate of RecyclerView, essentially says that upon creation, associate the RecyclerView
     * with the R.layout.recycler_row elements as rows of the RecyclerView
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_row, viewGroup, false);

        return new ViewHolder(view);
    }

    /**
     * boilder plate of the RecyclerView, binds each row of the RecyclerView to an actual bitmap
     * of each ArtworkDto
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.getImageView().setImageBitmap(localBitmapSet[position]);

        /**
         * a custom onClickListener to each artwork displayed in the recyclerview, takes you to
         * the detailed description of the artwok and the subsequent checkout options
         */
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

    /**
     * boiler plate RecyclerView
     * @return
     */
    @Override
    public int getItemCount() {
        return localBitmapSet.length;
    }


    /**
     * for parsing the List<ArtworkDto> so that the RecyclerView can get the right information in the
     * right plates
     * @param dtos List<ArtworkDto>
     * @param sort boolean
     */
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

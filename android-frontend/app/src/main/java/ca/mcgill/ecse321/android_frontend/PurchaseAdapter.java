package ca.mcgill.ecse321.android_frontend;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import ca.mcgill.ecse321.android_frontend.dto.PurchaseSummaryDto;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ViewHolder>{

    // All information to be retrieved from the purchase Dtos
    private Bitmap[] localBitmapSet;
    private String [] artwork;
    private String [] artist;
    private String [] date;
    private String [] shipment;
    private String [] price;
    private String [] commission;

    /**
     * Associates the RecyclerView with the R.layout.purchase_recycler_row elements as
     * rows of the RecyclerView, so that one can make multiple copies of a specific layout
     * with different data in each.
     *
     * @param parent ViewGroup
     * @param viewType int
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.purchase_recycler_row, parent, false);

        return new PurchaseAdapter.ViewHolder(view);
    }

    /**
     * Binds each row of the RecyclerView to a bitmap and associated text information
     * within the data supplied.
     *
     * @param holder ViewHolder
     * @param position int
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //Dynamically change front end information so that user can see their respective purchases
        holder.getImageView().setImageBitmap(localBitmapSet[position]);
        holder.artist.setText(artist[position]);
        holder.artwork.setText(artwork[position]);
        holder.date.setText(date[position]);
        holder.commission.setText(commission[position]);
        holder.price.setText(price[position]);
        holder.shipment.setText(shipment[position]);

    }

    @Override
    public int getItemCount() {
        return localBitmapSet.length;
    }

    /**
     * Hold the necessary elements that will hold the user's specific
     * purchase information.
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imageView;
        private final TextView price;
        private final TextView commission;
        private final TextView artwork;
        private final TextView artist;
        private final TextView date;
        private final TextView shipment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Find individual TextView element in order to change their information dynamically
            imageView = itemView.findViewById(R.id.imageView);
            artwork = itemView.findViewById(R.id.purchase_artwork_name);
            artist = itemView.findViewById(R.id.purchase_artist_name);
            date = itemView.findViewById(R.id.purchase_date_purchased);
            price = itemView.findViewById(R.id.purchase_price_value);
            commission = itemView.findViewById(R.id.purchase_commission_value);
            shipment = itemView.findViewById(R.id.purchase_shipment_type);
        }

        public ImageView getImageView() {
            return imageView;
        }
        public TextView getPrice(){return price;}
        public TextView getCommission(){return commission;}
        public TextView getArtwork(){return artwork;}
        public TextView getArtist(){return artist;}
        public TextView getDate(){return date;}
        public TextView getShipment(){return shipment;}

    }

    public PurchaseAdapter(Context context, Bitmap[] bitmapSet, List<PurchaseSummaryDto> dtos){
        localBitmapSet = bitmapSet;
        setAttributes(dtos);
    }

    /**
     * Populates this class's attributes with the necessary information
     * from the dtos, so that the RecyclerView can get the appropriate
     * information.
     *
     * @param dtos List<PurchaseSummaryDto>
     */
    private void setAttributes(List<PurchaseSummaryDto> dtos){

        int size = dtos.size();

        artwork = new String[size];
        artist = new String[size];
        date = new String[size];
        commission = new String[size];
        shipment = new String[size];
        price =new String[size];

        for(int i =0; i<size; i++){

            PurchaseSummaryDto dto = dtos.get(i);

            // Add each dto information into the list in order to add this information onto the front end
            // using the recyclerView
            artwork[i] = dto.getName();
            artist[i] = dto.getArtistName();
            date[i] = dto.getDatePurchased();
            commission[i] = dto.getCommission() +"";
            shipment[i] = dto.getShipmentType();
            price[i] = dto.getPrice()+"";

        }

    }

}
package ca.mcgill.ecse321.android_frontend;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import ca.mcgill.ecse321.android_frontend.dto.ArtistDto;
import de.hdodenhof.circleimageview.CircleImageView;


public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {

    private static final String TAG = "ImageAdapter";

    private Bitmap[] localBitmapSet;
    private String [] firstLastName;
    private String [] username;
    private Long [] artistId;

    /**
     * Creates a Custom ViewHolder class that extends the RecyclerView.ViewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView imageView;
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.page_profile_image);
            textView=view.findViewById(R.id.page_artist_name);
        }

        public ImageView getImageView() {
            return imageView;
        }
        public TextView getTextView(){return textView;}
    }

    /**
     * initializes the data of the Adapter to populate the Recylcer view
     * @param context
     * @param dtos  List<ArtistDto> containing the text information of the artist that
     *              will eventually populate the RecyclerView
     * @param bitmapSet Bitmap[], containing the profile image Bitmap of each ArtistDto
     */
    public ArtistAdapter(Context context, Bitmap[] bitmapSet, List<ArtistDto> dtos) {
        localBitmapSet = bitmapSet;
        setAttributes(dtos);
    }

    /**
     * boiler plate of RecyclerView, essentially says that upon creation, associate the RecyclerView
     * with the R.layout.artist_recycler_row elements as rows of the RecyclerView
     * @param viewGroup
     * @param viewType
     * @return
     */    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.artist_recycler_row, viewGroup, false);

        return new ViewHolder(view);
    }

    /**
     * boilder plate of the RecyclerView, binds each row of the RecyclerView to an actual bitmap and
     * the associated text information within the data supplied
     *
     * @param viewHolder
     * @param position
     */    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.getImageView().setImageBitmap(localBitmapSet[position]);
        viewHolder.getTextView().setText(firstLastName[position]);

        /**
         * a custom onClickListener to each artist displayed in the recyclerview, takes you to
         * the artist's page with his/her artwoks and the subsequent checkout options
         */
        viewHolder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(),ArtistPageActivity.class);
            intent.putExtra("USERNAME",username[position]);
            intent.putExtra("ARTISTID",artistId[position]);
            intent.putExtra("FIRSTLAST",firstLastName[position]);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            (localBitmapSet[position]).compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            intent.putExtra("PROFILEIMG",byteArray);
            view.getContext().startActivity(intent);
        });
    }

    /**
     * boiler plate RecyclerView
     * @return
     */    @Override
    public int getItemCount() {
        return localBitmapSet.length;
    }


    /**
     * for parsing the List<ArtistDtos> so that the RecyclerView can get the right information in the
     * right plates
     * @param dtos List<ArtistDto>
     */
    private void setAttributes(List<ArtistDto> dtos){
        int n=dtos.size();

        firstLastName=new String[n];
        username=new String[n];
        artistId=new Long[n];

        for (int i=0;i<n;i++){

            ArtistDto d = dtos.get(i);
            firstLastName[i]=d.getFirstname()+" "+d.getLastname();
            username[i]=d.getUsername();
            artistId[i]=d.getArtistId();
        }
    }
}

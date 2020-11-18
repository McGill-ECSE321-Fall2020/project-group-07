//package ca.mcgill.ecse321.discoverpage;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.List;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
//    private static final String TAG = "RecyclerViewAdapter";
//
//    private List<String> allArtistInfo = new ArrayList<>();
//    private List<String> allArtistHeadshots = new ArrayList<>();
//    private Context mContext;
//
//    public RecyclerViewAdapter(List<String> allArtistInfo, List<String> allArtistHeadshots, Context mContext) {
//        this.allArtistInfo = allArtistInfo;
//        this.allArtistHeadshots = allArtistHeadshots;
//        this.mContext = mContext;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent,false);
//        ViewHolder viewHolder = new ViewHolder(view);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Log.d(TAG, "onBindViewHolder: called.");
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//
//        CircleImageView artistHeadshot;
//        TextView artistInfo;
//        ConstraintLayout parentLayout;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            artistHeadshot=itemView.findViewById(R.id.artistheadshot);
//            artistInfo=itemView.findViewById(R.id.artistinfo);
//            parentLayout=itemView.findViewById(R.id.artistentry);
//        }
//    }
//}

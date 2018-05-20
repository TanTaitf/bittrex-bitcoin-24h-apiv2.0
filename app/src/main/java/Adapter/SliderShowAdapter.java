package Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.media.tf.appbitcoinbittrex.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;

import Custom.AnimationUtils_Cus;
import Model.Video;

import static Model.Config.setFont;

/**
 * SliderShowAdapter show view on top
 */

public class SliderShowAdapter extends RecyclerView.Adapter<SliderShowAdapter.ViewHolder> {
    private ArrayList<Video> truyenTranhArrayList;
    private Context context1;
    private int preViousPositon = 0;
    private Typeface typeFace;

    public SliderShowAdapter(ArrayList<Video> truyenTranhArrayList, Context context) {
        this.truyenTranhArrayList = truyenTranhArrayList;
        this.context1 = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.cumstom_slider_show, parent, false);

        return new ViewHolder(itemView, context1, truyenTranhArrayList);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mTenTruyen.setText(truyenTranhArrayList.get(position).getTitle());
        holder.mChapter.setText(truyenTranhArrayList.get(position).getChanelTitle() + "    ");
        holder.mLuotXem.setText("IDC.IO News");
        String url = truyenTranhArrayList.get(position).getThumnail();
        Picasso.get()
                .load(url)
//                .placeholder(R.drawable.avata_bay_mau)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .noFade()
//                .error(R.drawable.avata_bay_mau)
//                .config(Bitmap.Config.RGB_565)
                .into(holder.imgHinhTruyen);
        try {
            if (position > preViousPositon) {
                AnimationUtils_Cus.animate(holder, true);
            } else {
                AnimationUtils_Cus.animate(holder, false);
            }
            preViousPositon = position;
        } catch (Exception e) {
            return;
        }

    }

    @Override
    public int getItemCount() {
        return truyenTranhArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTenTruyen, mTheLoai, mLuotXem, mChapter;
        public ImageView imgHinhTruyen;
        ArrayList<Video> arrayListItem = new ArrayList<>();
        Context context;

        public ViewHolder(View itemView, Context context, ArrayList<Video> arrayListTruyen) {
            super(itemView);
            this.arrayListItem = arrayListTruyen;
            this.context = context;
            itemView.setOnClickListener(this);
            typeFace = setFont(context, typeFace);
            mTenTruyen = (TextView) itemView.findViewById(R.id.txtTenTruyen);
            mTenTruyen.setTypeface(typeFace);
            mLuotXem = (TextView) itemView.findViewById(R.id.txtLuotXem);
            mLuotXem.setTypeface(typeFace);
            mChapter = (TextView) itemView.findViewById(R.id.txtChapter);
            mChapter.setTypeface(typeFace);
            imgHinhTruyen = (ImageView) itemView.findViewById(R.id.imgTruyen);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Video data = this.arrayListItem.get(pos);
            String url = data.getId();

            new FinestWebView.Builder(context)
                    .titleDefault(data.getTitle())
                    .webViewBuiltInZoomControls(true)
                    .webViewDisplayZoomControls(true)
                    .dividerHeight(0)
                    .gradientDivider(false)
                    .show(url);

        }

    }
}

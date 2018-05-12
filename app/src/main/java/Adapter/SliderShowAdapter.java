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
 * Created by Nguyen Sang on 09/14/2017.
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
//            imgHinhTruyen.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Video data = this.arrayListItem.get(pos);
//            context.startActivity(new Intent(context, NewsTopActivity.class)
//                    .putExtra("id", data.getId()).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            String url = data.getId();

            new FinestWebView.Builder(context)
                    .titleDefault(data.getTitle())
                    .webViewBuiltInZoomControls(true)
                    .webViewDisplayZoomControls(true)
                    .dividerHeight(0)
                    .gradientDivider(false)
//                    .setCustomAnimations(R.anim.activity_open_enter, R.anim.activity_open_exit,
//                            R.anim.activity_close_enter, R.anim.activity_close_exit)

                    .show(url);

            ////                    .toolbarScrollFlags(0)
            //                    .webViewJavaScriptEnabled(true)
            //                    .webViewUseWideViewPort(false)


// .injectJavaScript("javascript: document.getElementById('msg').innerHTML='Hello "
//                    + "TheFinestArtist"
//                    + "!';")






//            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//            CustomTabsIntent customTabsIntent = builder.build();
//            customTabsIntent.launchUrl((Activity) context, Uri.parse(url));


//            Intent intent= new Intent(context,TruyenTranhChiTiet.class);
//            intent.putExtra("linkTruyen",truyenTranh.getId());
//            context.startActivity(intent);
//            Intent intent = new Intent(context, PlayVideoAcivity.class);
//            intent.putExtra("crash", true);
//            intent.putExtra("id",truyenTranh.getId());
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                    | Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    | Intent.FLAG_ACTIVITY_NEW_TASK);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//            AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);

//            try {
//                pendingIntent.send();
//            } catch (PendingIntent.CanceledException e) {
//                e.printStackTrace();
//            }
            //context.startActivity(new Intent(context,PlayVideoAcivity.class).putExtra("id", truyenTranh.getId()));

//            context1.startActivity(new Intent(context1,PlayVideoAcivity.class)
//                    .putExtra("id",truyenTranh.getId()));

        }

    }
}

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
import com.squareup.picasso.Picasso;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;

import Model.ThongTinBitcoin;

import static Model.Config.setFont;

/**
 * Created by Windows 8.1 Ultimate on 31/01/2018.
 */

public class Adapter_TabNews extends RecyclerView.Adapter<Adapter_TabNews.ViewHolder> {
    private ArrayList<ThongTinBitcoin> arrayList;
    public ArrayList<ThongTinBitcoin> players;
    private Context context;
    private int preViousPositon=0;

    private Typeface typeFace;
    public Adapter_TabNews(ArrayList<ThongTinBitcoin> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        this.players = arrayList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemView=inflater.inflate(R.layout.custom_list_news,parent,false);

        return new ViewHolder(itemView,context,arrayList);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTen.setText(arrayList.get(position).getTieuDe());
        holder.mThoiGian.setText(" " + arrayList.get(position).getThoiGian());

        String url=arrayList.get(position).getHinhAnh();
        if (url.contains("(")){
            int v1 = url.indexOf("(");
            int v2 = url.lastIndexOf(")");
            url = url.substring(v1 + 1, v2);
        }

        Picasso.get()
                .load(url)
//                .placeholder(R.drawable.avata_bay_mau)
//                .memoryPolicy(MemoryPolicy.NO_CACHE)
                //.noFade()
//                .error(R.drawable.avata_bay_mau)
                .into(holder.imgHinh);
        if(position>preViousPositon){
            //animate_down(holder);
            //AnimationUtils_Cus.animeteHorizontal(holder,true);

        }else{
            //AnimationUtils_Cus.animeteHorizontal(holder,false);
            //animate_down(holder);
        }
        preViousPositon=position;
    }
//    public void animate_down(RecyclerView.ViewHolder viewHolder) {
//        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down);
//        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
//    }
//    public void animate_up(RecyclerView.ViewHolder viewHolder) {
//        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.slide_out_right);
//        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
//    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTen,mThoiGian;
        public ImageView imgHinh;
        ArrayList<ThongTinBitcoin> arrayItem = new ArrayList<>();
        Context context;

        public ViewHolder(View itemView, Context context,ArrayList<ThongTinBitcoin> arrayList) {
            super(itemView);
            this.arrayItem = arrayList;
            this.context=context;
            itemView.setOnClickListener(this);
            typeFace= setFont(context, typeFace);
            mTen= (TextView) itemView.findViewById(R.id.txtTenTruyen);
            mTen.setTypeface(typeFace);
            mThoiGian= (TextView) itemView.findViewById(R.id.txtTheLoai);
            mThoiGian.setTypeface(typeFace);
            imgHinh= (ImageView) itemView.findViewById(R.id.imgTruyen);

        }
        @Override
        public void onClick(View v) {
            int pos=getAdapterPosition();
            ThongTinBitcoin bitcoin = this.arrayItem.get(pos);

            new FinestWebView.Builder(context)
                    .theme(R.style.ThemWebView)
                    .titleDefault(bitcoin.getTieuDe())
                    .webViewBuiltInZoomControls(true)
                    .webViewDisplayZoomControls(true)
                    .iconDefaultColorRes(R.color.accent)
                    .progressBarColorRes(R.color.accent)
                    .stringResCopiedToClipboard(R.string.copied_to_clipboard)
                    .dividerHeight(0)
                    .gradientDivider(false)
                    .setCustomAnimations(R.anim.activity_open_enter, R.anim.activity_open_exit,
                            R.anim.activity_close_enter, R.anim.activity_close_exit)
                    .show(bitcoin.getLink());

        }
    }
}

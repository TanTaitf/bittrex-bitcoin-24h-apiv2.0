package Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.media.tf.appbitcoinbittrex.ListSumaryActivity;
import com.media.tf.appbitcoinbittrex.R;
import com.ramotion.foldingcell.FoldingCell;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Model.MarketSumary;

import static Model.Config.setFont;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

    private ArrayList<MarketSumary> arrayList;
    private Context context;
    private int preViousPositon = 0;
    private Typeface typeFace;

    // Matching each View Type against a Integer
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    // Items List varaiable
    //private List<MarketSumary> mDataset;

    // Boolean to track loading status
    private boolean loading = false;

    // Listener or the Interface defined in STEP 4
    private OnLoadMoreListener mOnLoadMoreListener;

    // Constructor for the Adapter
    public RecycleAdapter(ArrayList<MarketSumary> Dataset, Context context, OnLoadMoreListener onLoadMoreListener) {
        arrayList = Dataset;
        mOnLoadMoreListener = onLoadMoreListener;
        this.context = context;
        typeFace = setFont(context, typeFace);
    }

    public RecycleAdapter(ArrayList<MarketSumary> ArrayList, Context context) {
        this.arrayList = ArrayList;
        this.context = context;
        typeFace = setFont(context, typeFace);
    }

    // ViewHolder for ProgressBar
    // Copy it as it is
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {

        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progress_bar_bottom);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
            vh = new ViewHolder(view, context, arrayList);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_bar_bottom, parent, false);
            vh = new ProgressViewHolder(v);
        }
        return (ViewHolder) vh;
        //return new ViewHolder(view,context,arrayList);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder) {
            DecimalFormat df = new DecimalFormat("#.######");
            DecimalFormat dfVolume = new DecimalFormat("######.##");
            holder.txt_MarketCurrency.setText(arrayList.get(position).getMarketName());
            holder.txt_Volume.setText("$" + dfVolume.format(arrayList.get(position).getVolume()));
            holder.txt_High.setText("+" + df.format(arrayList.get(position).getHigh()));
            holder.txt_Low.setText("-" + df.format(arrayList.get(position).getLow()));
            String url = arrayList.get(position).getLogoUrl();
            Picasso.get()
                    .load(url)
//                    .placeholder(R.drawable.avata_bay_mau)
//                .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .noFade()
//                    .error(R.drawable.)
                    .into(holder.img_logoBitCon);

            DecimalFormat df1 = new DecimalFormat("#######.######");
            DecimalFormat df2 = new DecimalFormat("##.#########");
            SimpleDateFormat spd = new SimpleDateFormat("dd/mm/yyyy");
            DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
//        Date date = (Date)formatter.parse(start_dt);
            // set Text
            // header
            holder.txt_nameCoinsContent.setText(arrayList.get(position).getMarketName());
            // body 1
            holder.content_baseVolume_values.setText("$" + df1.format(arrayList.get(position).getBaseVolume()));
            holder.content_bitCoinsType.setText(arrayList.get(position).getBaseCurrencyLong());
            Picasso.get()
                    .load(url)
//                    .placeholder(R.drawable.avata_bay_mau)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .noFade()
//                    .error(R.drawable.)
                    .into(holder.content_avatar);
            holder.content_minTrade_title4.setText("$" + arrayList.get(position).getMinTradeSize());
            //body 2
            holder.txtLastCurrContent3.setText(df2.format(arrayList.get(position).getLast()));
            holder.txt_BidContent2.setText(df2.format(arrayList.get(position).getBid()));
            holder.txt_AskContent2.setText(df2.format(arrayList.get(position).getAsk()));

            try {
                String cr = new SimpleDateFormat("dd-mm-yyyy").format(new SimpleDateFormat("yyyy-mm-dd").parse(arrayList.get(position).getCreated().toString()));
//            holder.content_CreateDate_badge2.setText(""+(Date) formatter.parse((arrayList.get(position).getCreated().toString())));
                holder.content_CreateDate_badge2.setText(cr);
                cr = new SimpleDateFormat("dd-mm-yyyy").format(new SimpleDateFormat("yyyy-mm-dd").parse(arrayList.get(position).getTimeStam().toString()));
                holder.content_to_TimeStamp_2.setText(cr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // body 3
            holder.content_valuesBuy_time.setText("$" + df1.format(arrayList.get(position).getOpenBuyOrders()));
            holder.content_valuesSell_time.setText("$" + df1.format(arrayList.get(position).getOpenSellOrders()));

            holder.img_logoBitCon.setTag(position);
            holder.content_avatar.setTag(position);

        } else {

            ProgressViewHolder holderPrg = null;
            holderPrg.progressBar.setIndeterminate(true);
            if (!loading) {
                // End has been reached
                // Do something
                if (mOnLoadMoreListener != null) {
                    mOnLoadMoreListener.onLoadMore(position);
                }
                loading = true;
            }

        }


    }

    // Method to set value of boolean variable "loading" to false
    // this method is called when data is loaded in the Activity class
    public void setLoaded() {
        loading = false;
    }

    // This method contains the main logic for showing ProgressBar at the end of RecyclerView
    // In the Activity class, we insert a null item at the end of the list
    // this null item produces a ProgressViewHolder by the logic given below
    @Override
    public int getItemViewType(int position) {
        return arrayList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }


    // Method to add more items to the list
    public void update(List<MarketSumary> newItems) {
        arrayList.addAll(newItems);
        notifyDataSetChanged();
    }

    // This method is used to remove ProgressBar when data is loaded
    public void removeLastItem() {
        arrayList.remove(arrayList.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ArrayList<MarketSumary> arrayListSumary = new ArrayList<>();
        Context context;

        public TextView txt_MarketCurrency, txt_Volume, txt_High, txt_Low;
        public ImageView img_logoBitCon;
        public FoldingCell fc;

        // get Content
        // header
        public TextView txt_nameCoinsContent, txt_CurrencyActiveContent;
        public ImageView img_iconContent;
        // body 1
        public TextView content_baseVoluem_view, content_baseVolume_values;
        public TextView content_bitCoinsType;
        public ImageView content_avatar;
        public TextView content_MinTrade_view, content_minTrade_title4;
        public ShimmerTextView txt_Analytic;
        // body 2
        public TextView txtLastCurrContent2, txtLastCurrContent3, txt_BidContent, txt_BidContent2,
                txt_AskContent, txt_AskContent2;
        public TextView content_CreateDate_badge, content_CreateDate_badge2, content_toTimeStamp_1, content_to_TimeStamp_2;
        // body 3
        public TextView content_openBuy_date_badge, content_valuesBuy_time, content_txtBuy_btn2;
        public TextView content_OpenSellContent_badge, content_valuesSell_time, content_txtSell_btn3;

        // footer
        public TextView txtFooter;

        void initView() {
            // header
            txt_nameCoinsContent = itemView.findViewById(R.id.txt_nameCoinsContent);
            txt_CurrencyActiveContent = (TextView) itemView.findViewById(R.id.txt_CurrencyActiveContent);
            img_iconContent = itemView.findViewById(R.id.img_iconContent);
            // set typeface header 1
            txt_nameCoinsContent.setTypeface(typeFace);
            txt_CurrencyActiveContent.setTypeface(typeFace);


            // body 1 set typeface
            content_baseVoluem_view = (TextView) itemView.findViewById(R.id.content_baseVoluem_view);
            content_baseVolume_values = (TextView) itemView.findViewById(R.id.content_baseVolume_values);
            content_bitCoinsType = itemView.findViewById(R.id.content_bitCoinsType);
            content_avatar = itemView.findViewById(R.id.content_avatar);
            content_MinTrade_view = itemView.findViewById(R.id.content_MinTrade_view);
            content_minTrade_title4 = itemView.findViewById(R.id.content_minTrade_title4);


            content_baseVoluem_view.setTypeface(typeFace);
            content_baseVolume_values.setTypeface(typeFace);
            content_MinTrade_view.setTypeface(typeFace);
            content_minTrade_title4.setTypeface(typeFace);

            // body 2
            txtLastCurrContent2 = itemView.findViewById(R.id.txtLastCurrContent2);
            txtLastCurrContent3 = itemView.findViewById(R.id.txtLastCurrContent3);
            txt_BidContent = itemView.findViewById(R.id.txt_BidContent);
            txt_BidContent2 = itemView.findViewById(R.id.txt_BidContent2);
            txt_AskContent = itemView.findViewById(R.id.txt_AskContent);
            txt_AskContent2 = itemView.findViewById(R.id.txt_AskContent2);

            content_CreateDate_badge = itemView.findViewById(R.id.content_CreateDate_badge);
            content_CreateDate_badge2 = itemView.findViewById(R.id.content_CreateDate_badge2);
            content_toTimeStamp_1 = itemView.findViewById(R.id.content_toTimeStamp_1);
            content_to_TimeStamp_2 = itemView.findViewById(R.id.content_to_TimeStamp_2);

            // set typeface
            txtLastCurrContent2.setTypeface(typeFace);
            txtLastCurrContent3.setTypeface(typeFace);
            txt_BidContent.setTypeface(typeFace);
            txt_BidContent2.setTypeface(typeFace);
            txt_AskContent.setTypeface(typeFace);
            txt_AskContent2.setTypeface(typeFace);
            content_CreateDate_badge.setTypeface(typeFace);
            content_CreateDate_badge2.setTypeface(typeFace);
            content_toTimeStamp_1.setTypeface(typeFace);
            content_to_TimeStamp_2.setTypeface(typeFace);

            // body 3
            content_openBuy_date_badge = itemView.findViewById(R.id.content_openBuy_date_badge);
            content_valuesBuy_time = itemView.findViewById(R.id.content_valuesBuy_time);
            content_txtBuy_btn2 = itemView.findViewById(R.id.content_txtBuy_btn2);
            content_OpenSellContent_badge = itemView.findViewById(R.id.content_OpenSellContent_badge);
            content_valuesSell_time = itemView.findViewById(R.id.content_valuesSell_time);
            content_txtSell_btn3 = itemView.findViewById(R.id.content_txtSell_btn3);
            // set typeface
            content_openBuy_date_badge.setTypeface(typeFace);
            content_valuesBuy_time.setTypeface(typeFace);
            content_txtBuy_btn2.setTypeface(typeFace);
            content_OpenSellContent_badge.setTypeface(typeFace);
            content_valuesSell_time.setTypeface(typeFace);
            content_txtSell_btn3.setTypeface(typeFace);

            // footer
            txtFooter = itemView.findViewById(R.id.txtFooter);
            txtFooter.setTypeface(typeFace);
        }

        public ViewHolder(final View itemView, final Context context, ArrayList<MarketSumary> arrayList) {
            super(itemView);
            this.arrayListSumary = arrayList;
            this.context = context;

            initView();
            img_logoBitCon = itemView.findViewById(R.id.img_iconCurrency);
            txt_MarketCurrency = (TextView) itemView.findViewById(R.id.txt_vp_item_list);
            txt_Volume = (TextView) itemView.findViewById(R.id.txt_Volume);
            txt_High = (TextView) itemView.findViewById(R.id.txt_High);
            txt_Low = (TextView) itemView.findViewById(R.id.txt_Low);
            fc = (FoldingCell) itemView.findViewById(R.id.folding_cell);

            txt_MarketCurrency.setTypeface(typeFace);
            txt_Volume.setTypeface(typeFace);
            txt_High.setTypeface(typeFace);
            txt_Low.setTypeface(typeFace);
            Shimmer shimmer = new Shimmer();
            txt_Analytic = itemView.findViewById(R.id.shimmer_tv);
            txt_Analytic.setTypeface(typeFace);
            shimmer.start(txt_Analytic);

            content_txtBuy_btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context,"Click text",Toast.LENGTH_LONG).show();
                }
            });
            itemView.setOnClickListener(this);

//            // attach click listener to folding cell
            fc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fc.toggle(false);
                    //Toast.makeText(context,"Mơ rong Fooolee",Toast.LENGTH_LONG).show();

                }
            });
            content_avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendIntent();
                }
            });
            txt_CurrencyActiveContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendIntent();
                }
            });
            txt_Analytic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendIntent();
                }
            });
        }

        void sendIntent() {
            int pos = getAdapterPosition();
            final MarketSumary a = arrayListSumary.get(pos);
            context.startActivity(new Intent(context, ListSumaryActivity.class).putExtra("currency",
                    a.getMarketName()).putExtra("icon",a.getLogoUrl()));

        }

        @Override
        public void onClick(View v) {
            //int pos = getAdapterPosition();
            //MarketSumary arrayListSumary = this.arrayListSumary.get(pos);
//            try {
//                fc.toggle(false);
//            }catch (Exception e){
//                return;
//            }

            //Toast.makeText(context,"Mơ rong",Toast.LENGTH_LONG).show();
            // get our folding cell
            //context.startActivity(new Intent(context,ListSumaryActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        }
    }
}
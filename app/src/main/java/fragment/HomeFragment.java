package fragment;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.widget.PullRefreshLayout;
import com.media.tf.appbitcoinbittrex.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import Adapter.OnLoadMoreListener;
import Adapter.RecycleAdapter;
import Model.MarketSumary;

import static Model.Config.setFont;

//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//import com.azoft.carousellayoutmanager.CarouselLayoutManager;
//import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
//import com.azoft.carousellayoutmanager.CenterScrollListener;
//import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.media.tf.app_dj_online.Model.KeyWord;
//import com.media.tf.app_dj_online.Model.Video;
//import com.media.tf.app_dj_online.R;
//import com.media.tf.app_dj_online.activity.ShowMoreList;
//import com.media.tf.app_dj_online.adapter.TruyenTranhAdapter;
//import com.romainpiel.shimmer.Shimmer;
//import com.romainpiel.shimmer.ShimmerTextView;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
//import static com.media.tf.app_dj_online.Model.Config.api_search;
//import static com.media.tf.app_dj_online.Model.Config.setFont;

/**
 * Created by Nguyen Sang on 09/13/2017.
 */

public class HomeFragment extends Fragment {

    private TextView txt_icon_bitcoins, txt_title_market, txt_title_Volume, txt_title_High, txt_title_Low;
    private Typeface typeface;
    private View view;
    private ArrayList<MarketSumary> arrayList;
    private RecycleAdapter adapter;
    private RecyclerView recyclerView;
    final String url = "https://bittrex.com/api/v2.0/pub/markets/GetMarketSummaries";
    private boolean checkSort = false;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.item_vp_list,container,false);
        typeface = setFont(getContext(), typeface);

        initViewPager(view);
        final RecyclerView recyclerView = view.findViewById(R.id.rv);
                initViewPager(view);
                final PullRefreshLayout layout = view.findViewById(R.id.pull_to_refresh);

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(
                                getContext(), LinearLayoutManager.VERTICAL, false
                        )
                );

                getJsonApiFromBittrex(url, recyclerView);

                layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // start refresh
                        getJsonApiFromBittrex(url, recyclerView);
                        layout.setRefreshing(false);
                    }
                });

        txt_title_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.reverse(arrayList);
                adapter.notifyDataSetChanged();
            }
        });
        txt_title_Volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSort == false){
                    arrayList = sortVolume(arrayList, checkSort);
                    adapter.notifyDataSetChanged();
                    checkSort = true;
                }else {
                    arrayList = sortVolume(arrayList, checkSort);
                    adapter.notifyDataSetChanged();
                    checkSort = false;
                }

            }
        });
        return view;
    }
    public ArrayList sortVolume(ArrayList<MarketSumary> sumaries, boolean sort){

        for(int i = 0; i < sumaries.size(); i++){
            for (int j = i + 1; j < sumaries.size(); j++) {
                if(checkSort == false){
                    if ( sumaries.get(i).getVolume()
                            < sumaries.get(j).getVolume()) {
                        MarketSumary a = sumaries.set(i, sumaries.get(j));
                        sumaries.set(j, a);
                    }
                }
                if(checkSort == true){
                    if ( sumaries.get(i).getVolume()
                            > sumaries.get(j).getVolume()) {
                        MarketSumary a = sumaries.set(i, sumaries.get(j));
                        sumaries.set(j, a);
                    }
                }

            }
        }
        return sumaries;
    }
    private void initViewPager(View view) {
        txt_icon_bitcoins = view.findViewById(R.id.txt_icon_bitcoins);
        txt_title_market = view.findViewById(R.id.txt_title_market);
        txt_title_High = view.findViewById(R.id.txt_title_High);
        txt_title_Volume = view.findViewById(R.id.txt_title_Volume);
        txt_title_Low = view.findViewById(R.id.txt_title_Low);
        //img_viewCurrecy = view.findViewById(R.id.img_viewCurrecy);


        txt_title_market.setTypeface(typeface);
        txt_title_High.setTypeface(typeface);
        txt_icon_bitcoins.setTypeface(typeface);
        txt_title_Volume.setTypeface(typeface);
        txt_title_Low.setTypeface(typeface);
        txt_title_market.setPaintFlags(txt_title_market.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
    void getJsonApiFromBittrex(String url, final RecyclerView recyclerView) {
        //Toast.makeText(MainActivity.this, url.toString(), Toast.LENGTH_LONG).show();
        arrayList = new ArrayList();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        //url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=android&type=video&maxResults=20&key=AIzaSyBmTVXZn7dsnLL__gLeK2EPL_5_z-igqCw";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //txt_JsonApi.setText("ffjfjfh" + response.toString());
                //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                JSONObject node;
                JSONObject nodeMarket;
                JSONObject nodeSummary;

                // init Market
                String MarketCurrency = "";
                String baseCurrency = "";
                String MarketCurrencyLong = "";
                String BaseCurrencyLong = "";
                String MinTradeSize = "";
                String MarketName = "";
                String Created = "";
                String LogoUrl = "";
                String TimeStam = "";

                // init Summary
                Double High = 0.0;
                Double Low = 0.0;
                Double Volume = 0.0;
                Double Last = 0.0;
                Double BaseVolume = 0.0;
                Double Bid = 0.0;
                Double Ask = 0.0;
                Double OpenBuyOrders = 0.0;
                Double OpenSellOrders = 0.0;
                Double PrevDay = 0.0;

                StringBuilder t = new StringBuilder();
                JSONArray jsonitem = response.optJSONArray("result");
                for (int j = 0; j < jsonitem.length(); j++) {

                    node = jsonitem.optJSONObject(j);
                    nodeMarket = node.optJSONObject("Market");
                    nodeSummary = node.optJSONObject("Summary");
                    // get Market
                    MarketCurrency = nodeMarket.optString("MarketCurrency");
                    baseCurrency = nodeMarket.optString("BaseCurrency");
                    MarketCurrencyLong = nodeMarket.optString("MarketCurrencyLong");
                    BaseCurrencyLong = nodeMarket.optString("BaseCurrencyLong");
                    MinTradeSize = nodeMarket.optString("MinTradeSize");
                    MarketName = nodeMarket.optString("MarketName");
                    Created = nodeMarket.optString("Created");
                    LogoUrl = nodeMarket.optString("LogoUrl");

                    // get Summary
                    High = nodeSummary.optDouble("High");
                    Low = nodeSummary.optDouble("Low");
                    Volume = nodeSummary.optDouble("Volume");
                    Last = nodeSummary.optDouble("Last");
                    BaseVolume = nodeSummary.optDouble("BaseVolume");
                    Bid = nodeSummary.optDouble("Bid");
                    Ask = nodeSummary.optDouble("Ask");
                    OpenBuyOrders = nodeSummary.optDouble("OpenBuyOrders");
                    OpenSellOrders = nodeSummary.optDouble("OpenSellOrders");
                    PrevDay = nodeSummary.optDouble("PrevDay");
                    TimeStam = nodeSummary.optString("TimeStamp");

                    //getCountVideo(idvideo);
                    arrayList.add(new MarketSumary(MarketCurrency, baseCurrency, MarketCurrencyLong, BaseCurrencyLong,
                            MinTradeSize, MarketName, Created, LogoUrl, High, Low, Volume, Last, BaseVolume, Bid, Ask, OpenBuyOrders, OpenSellOrders, PrevDay, TimeStam));
                }
//                adapter = new RecycleAdapter(arrayList, getBaseContext());
//                recyclerView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();

                adapter = new RecycleAdapter(arrayList,getContext(), new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore(int position) {
//                        loadMoreData(position);

                        //arrayList.clear();
//                        for (int i = 0; i < limit; i++) {
//                            mDataSet.add(new Comment("Tutorial on Load More Data in RecyclerView with ProgressBar by Know My Work", "Know My Work"));
//                        }
//                        arrayList.add(null);

                        adapter.removeLastItem();
                        adapter.setLoaded();
                        adapter.update(arrayList);
                    }
                });
//                mProgressBar.setVisibility(View.GONE);

                recyclerView.setAdapter(adapter);
                //adapter.onAttachedToRecyclerView(recyclerView);
                adapter.notifyDataSetChanged();

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getContext(), "Da huy" + error.toString(), Toast.LENGTH_LONG).show();
                return;
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    @Override
    public void onPause() {
        super.onPause();
        if (getActivity().getSupportFragmentManager().findFragmentByTag("HomeFragment") != null)
            getActivity().getSupportFragmentManager().findFragmentByTag("HomeFragment").setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity().getSupportFragmentManager().findFragmentByTag("HomeFragment") != null)
            getActivity().getSupportFragmentManager().findFragmentByTag("HomeFragment").getRetainInstance();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    private void setTruyenTranh(final RecyclerView mRecyclerView , String key, int max, final int index, final boolean action, boolean layout) {
//        key = key.replace(" ","%20");
//        //LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
//        if(layout==true){
//            CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL,true);
//            layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
//            mRecyclerView.setLayoutManager(layoutManager);
//            mRecyclerView.addOnScrollListener(new CenterScrollListener());
//        }else {
//            mRecyclerView.setLayoutManager(new CustomLinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
//        }
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setNestedScrollingEnabled(false);
//        final ArrayList<Video> arrayList= new ArrayList<>();
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//
//        final String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q="+key+"&type=video&maxResults="+max+"&key="+api_search;
//        if (index == 1){
//            key_1 = key;
//        }
//        if (index == 2){
//            key_2 = key;
//        }
//        if (index == 5){
//            key_3 = key;
//        }
//        if (index == 4){
//            key_4 = key;
//        }
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                arrayList.clear();
//                JSONObject item ;
//                JSONObject id ;
//                String idvideo ;
//                JSONObject jsonSpinet ;
//                String title = "";
//                String dertion = "";
//                JSONObject jsonthumnail ;
//                JSONObject jsondefault ;
//                String imaview = "";
//                String chanelTitle = "";
//                String dayupl = "";
//                JSONArray jsonitem = response.optJSONArray("items");
//                for (int j = 0; j < jsonitem.length();j++)
//                {
//                     item = jsonitem.optJSONObject(j);
//                     id = item.optJSONObject("id");
//                     idvideo = id.optString("videoId");
//
//
//                     jsonSpinet = item.optJSONObject("snippet");
//                     title = jsonSpinet.optString("title");
//                     dertion = jsonSpinet.optString("description");
//
//                     jsonthumnail = jsonSpinet.optJSONObject("thumbnails");
//                     jsondefault = jsonthumnail.optJSONObject("medium");
//                     imaview = jsondefault.optString("url");
//                     chanelTitle = jsonSpinet.optString("channelTitle");
//                     dayupl = jsonSpinet.optString("publishedAt");
//                    arrayList.add(new Video(idvideo,title,dertion,imaview,chanelTitle, dayupl));
//
//                }
//                //adapterVideo.notifyDataSetChanged();
//                final TruyenTranhAdapter adapter = new TruyenTranhAdapter(arrayList,getActivity());
//                //final Adapter_Tab_3 adapter = new Adapter_Tab_3(arrayList,getActivity());
//                mRecyclerView.setAdapter(adapter);
//                count ++;
//                if (action==true){
//                    setAutoScroll(mRecyclerView,adapter);
//                }
//                if (count == 5){
//                    ln_gif_loadhome.setVisibility(View.GONE);
////                    MainActivity.setWellcome();
//                    //MainActivity.rippleBackground.stopRippleAnimation();
////                    layout_Wellcom.setVisibility(View.GONE);
////                    mView.dismiss();
//                    //pDialog.dismiss();
////                    Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
////                    intent.putExtra("message", "Home");
////                    getActivity().startActivity(intent);
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        requestQueue.add(jsonObjectRequest);
//        SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.END);
//        snapHelperStart.attachToRecyclerView(mRecyclerView);

    }
//    private void setAutoScroll(final RecyclerView recyclerView , final TruyenTranhAdapter adapter){
//        final int speedScroll = 2500;
//        final Handler handler = new Handler();
//        final Runnable runnable = new Runnable() {
//            int count = 0;
//            boolean flag = true;
//            @Override
//            public void run() {
//                if(count < adapter.getItemCount()){
//                    if(count==adapter.getItemCount()-1){
//                        flag = false;
//                    }else if(count == 0){
//                        flag = true;
//                    }
//                    if(flag) count++;
//                    else count--;
//
//                    recyclerView.smoothScrollToPosition(count);
//                    handler.postDelayed(this,speedScroll);
//                }
//            }
//        };
//        handler.postDelayed(runnable,speedScroll);

//    }

    public class CustomLinearLayoutManager extends LinearLayoutManager {
        public CustomLinearLayoutManager(Context context) {
            super(context);
        }

        public CustomLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public CustomLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {

            final LinearSmoothScroller linearSmoothScroller =
                    new LinearSmoothScroller(recyclerView.getContext()) {
                        private static final float MILLISECONDS_PER_INCH = 200f;

                        @Override
                        public PointF computeScrollVectorForPosition(int targetPosition) {
                            return CustomLinearLayoutManager.this
                                    .computeScrollVectorForPosition(targetPosition);
                        }

                        @Override
                        protected float calculateSpeedPerPixel
                                (DisplayMetrics displayMetrics) {
                            return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
                        }
                    };
            linearSmoothScroller.setTargetPosition(position);
            startSmoothScroll(linearSmoothScroller);
        }
    }


}

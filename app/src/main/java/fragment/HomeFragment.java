package fragment;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.developers.coolprogressviews.SimpleArcProgress;
import com.media.tf.appbitcoinbittrex.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import Adapter.OnLoadMoreListener;
import Adapter.RecycleAdapter;
import Model.MarketSumary;

import static Model.Config.setFont;


/**
 * Created by tfmedia on 09/13/2017.
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
    private SimpleArcProgress progress;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.item_vp_list,container,false);
        typeface = setFont(getContext(), typeface);
        progress = (SimpleArcProgress)view.findViewById(R.id.progress);
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
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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

                if(arrayList.size()>0) {
                    adapter = new RecycleAdapter(arrayList, getContext(), new OnLoadMoreListener() {
                        @Override
                        public void onLoadMore(int position) {
                            adapter.removeLastItem();
                            adapter.setLoaded();
                            adapter.update(arrayList);
                        }
                    });

                    recyclerView.setAdapter(adapter);
                    //adapter.onAttachedToRecyclerView(recyclerView);
                    adapter.notifyDataSetChanged();
                    progress.setVisibility(View.GONE);
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getContext(), "No connect to Server !\nPlease check Internet !", Toast.LENGTH_LONG).show();
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

}

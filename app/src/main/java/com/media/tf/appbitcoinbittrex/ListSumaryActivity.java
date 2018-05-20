package com.media.tf.appbitcoinbittrex;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.developers.coolprogressviews.SimpleArcProgress;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Model.DataJsonGetTicks;
import cn.refactor.lib.colordialog.PromptDialog;

import static Model.Config.isNetworkAvailable;
import static Model.Config.setFont;

public class ListSumaryActivity extends AppCompatActivity {

    private CandleStickChart mChart;
    private Typeface typeface;
    private String currency = "BTC-CVC";
    private String timeoneMin = "oneMin";
    private String timethirtyMin = "thirtyMin";
    private String timeFiveMin = "fiveMin";
    private String timeHour = "hour";
    private String timeDay = "day";
    private String urlLogo = "";
    private String url = "https://bittrex.com/Api/v2.0/pub/market/GetTicks?marketName=" + currency + "&tickInterval=" + timeFiveMin + "&_=1500915289433";
    private ArrayList<DataJsonGetTicks> arrayList;
    // init layout
    // header
    private  TextView tvXMa,tvChart;
    private ImageView img_Currency;
    // body 1
    private Button btn_one,btn_five,btn_1hour,btn_1day,btn_1week,btn_1month;
    // body 2
    private TextView txt_titleVolume,txt_Volume,txt_ActiveTime,txt_Day;
    private TextView txt_High,txt_Low;
    // body 3
    private TextView txt_titleOpen,txt_Open,txt_titleClose,txt_Close,txt_titleBaseVl,txt_BaseVl;
    private SimpleArcProgress progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean checkInternet = isNetworkAvailable(this);
        if (checkInternet == false) {
            new PromptDialog(ListSumaryActivity.this)
                    .setDialogType(PromptDialog.DIALOG_TYPE_WARNING)
                    .setAnimationEnable(true)
                    .setTitleText("ERROR")
                    .setContentText("No connect to Server !\nPlease check Internet !")
                    .setPositiveListener(getString(R.string.ok), new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                            finish();
                        }
                    }).show();
        } else {
            setContentView(R.layout.activity_candlechart);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            }
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.getsuportactionbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Intent intent = getIntent();
            currency = intent.getStringExtra("currency").toString().trim();
            urlLogo = intent.getStringExtra("icon");
            //        TextView title=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
            //        title.setText(currency);
            typeface = setFont(this, typeface);
            initView();
            arrayList = new ArrayList();
            url = "https://bittrex.com/Api/v2.0/pub/market/GetTicks?marketName=" + currency + "&tickInterval=thirtyMin&_=1500915289433";
            getJsonTicksFromBittrex(url);


            mChart.getDescription().setEnabled(false);

            // if more than 60 entries are displayed in the chart, no values will be
            // drawn
            mChart.setMaxVisibleValueCount(60);

            // scaling can now only be done on x- and y-axis separately
            mChart.setPinchZoom(false);

            mChart.setDrawGridBackground(false);

            XAxis xAxis = mChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setTextColor(Color.WHITE);

            YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.setEnabled(false);
//        leftAxis.setLabelCount(7, false);
            leftAxis.setDrawGridLines(false);
            leftAxis.setDrawAxisLine(false);
            leftAxis.setTextColor(Color.WHITE);

            YAxis rightAxis = mChart.getAxisRight();
            rightAxis.setEnabled(false);
            rightAxis.setStartAtZero(false);

            mChart.resetTracking();
            btn_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int num = arrayList.size() / 1500;
                    setDataChart(num);
                }
            });
            btn_five.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int num = arrayList.size() / 1200;
                    setDataChart(num);
                }
            });
            btn_1hour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int num = arrayList.size() / 700;
                    setDataChart(num);
                }
            });
            btn_1day.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int num = arrayList.size() / 500;
                    setDataChart(num);
                }
            });
            btn_1week.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int num = arrayList.size() / 30;
                    setDataChart(num);
                }
            });
            btn_1month.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int num = arrayList.size() / 10;
                    setDataChart(num);
                }
            });
        }

    }

    void setDataChart(int numCount) {

        mChart.resetTracking();
        ArrayList<CandleEntry> entries = new ArrayList<>();
        if (arrayList != null && arrayList.size() > 0) {
            //int numCount = arrayList.size() / 5;
            //Toast.makeText(this, arrayList.size() + "set Data " + numCount, Toast.LENGTH_LONG).show();

            int col = 0;
            for (int i = numCount; i < arrayList.size(); i++) {
                entries.add(new CandleEntry(col, (float) arrayList.get(i).getH(), (float) arrayList.get(i).getL(), (float) arrayList.get(i).getO(), (float) arrayList.get(i).getC()));
                col++;
            }
            // Entries
            CandleDataSet set1 = new CandleDataSet(entries, "Data Set");                // setting data
            set1.setDrawIcons(false);
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(Color.YELLOW);
            set1.setShadowColor(Color.DKGRAY);
            set1.setShadowWidth(0.2f);
            set1.setDecreasingColor(Color.RED);
            set1.setDecreasingPaintStyle(Paint.Style.FILL);
            set1.setIncreasingColor(Color.rgb(122, 242, 84));
            set1.setIncreasingPaintStyle(Paint.Style.STROKE);
            set1.setNeutralColor(Color.BLUE);
            set1.setValueTextColor(Color.WHITE);
            set1.setHighlightLineWidth(1f);

            CandleData data = new CandleData(set1);
            mChart.setData(data);
            mChart.invalidate();
            mChart.animateY(950);
            mChart.getLegend().setEnabled(false);
            progress.setVisibility(View.GONE);
        }




    }

    void initView() {
        // header
        img_Currency = (ImageView) findViewById(R.id.img_Currency);
//        tvXMa = (TextView)findViewById(R.id.tvXMa);
//        tvXMa.setTypeface(typeface);
//        tvChart = (TextView)findViewById(R.id.tvChart);
//        tvChart.setTypeface(typeface);
        mChart = (CandleStickChart) findViewById(R.id.chart1);
        // body 1
        btn_one = (Button)findViewById(R.id.btn_one);
        btn_five = (Button)findViewById(R.id.btn_five);
        btn_1hour = (Button)findViewById(R.id.btn_1hour);
        btn_1day = (Button)findViewById(R.id.btn_1day);
        btn_1week = (Button)findViewById(R.id.btn_1week);
        btn_1month = (Button)findViewById(R.id.btn_1month);
        btn_one.setTypeface(typeface);
        btn_five.setTypeface(typeface);
        btn_1hour.setTypeface(typeface);
        btn_1day.setTypeface(typeface);
        btn_1week.setTypeface(typeface);
        btn_1month.setTypeface(typeface);
        // body 2
        txt_titleVolume = (TextView)findViewById(R.id.txt_titleVolume);
        txt_Volume = (TextView)findViewById(R.id.txt_Volume);
        txt_ActiveTime = (TextView) findViewById(R.id.txt_ActiveTime);
        txt_Day = (TextView)findViewById(R.id.txt_Day);
        txt_High = (TextView)findViewById(R.id.txt_High);
        txt_Low = (TextView)findViewById(R.id.txt_Low);
        txt_titleVolume.setTypeface(typeface);
        txt_Volume.setTypeface(typeface);
        txt_ActiveTime.setTypeface(typeface);
        txt_Day.setTypeface(typeface);
        txt_High.setTypeface(typeface);
        txt_Low.setTypeface(typeface);

        // body 3
        txt_titleOpen = (TextView)findViewById(R.id.txt_titleOpen);
        txt_Open = (TextView)findViewById(R.id.txt_Open);
        txt_titleClose = (TextView)findViewById(R.id.txt_titleClose);
        txt_Close = (TextView)findViewById(R.id.txt_Close);
        txt_titleBaseVl = (TextView)findViewById(R.id.txt_titleBaseVl);
        txt_BaseVl = (TextView)findViewById(R.id.txt_BaseVl);
        txt_titleOpen.setTypeface(typeface);
        txt_Open.setTypeface(typeface);
        txt_titleClose.setTypeface(typeface);
        txt_Close.setTypeface(typeface);
        txt_BaseVl.setTypeface(typeface);
        txt_titleBaseVl.setTypeface(typeface);

        // set Typeface
        progress = (SimpleArcProgress)findViewById(R.id.progress);

        Picasso.get()
                .load(urlLogo)
//                    .placeholder(R.drawable.avata_bay_mau)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .noFade()
//                    .error(R.drawable.)
                .into(img_Currency);
    }
    @Override
    public void onBackPressed() {
        finish();
//        onDestroy();
//        return;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
    void getJsonTicksFromBittrex(String url) {
        //Toast.makeText(this, url.toString(), Toast.LENGTH_LONG).show();
        DecimalFormat df = new DecimalFormat("#.######");
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject node;
                JSONObject nodeTicks;

                // init ticks
                float O, H, L, C, V, BV;
                String T;

                JSONArray jsonitem = response.optJSONArray("result");

                for (int j = 0; j < jsonitem.length(); j++) {

                    node = jsonitem.optJSONObject(j);

                    // get Summary
                    O = (float) node.optDouble("O");
                    H = (float) node.optDouble("H");
                    L = (float) node.optDouble("L");
                    C = (float) node.optDouble("C");
                    V = (float) node.optDouble("V");
                    BV = (float) node.optDouble("BV");
                    T = node.optString("T");

                    arrayList.add(new DataJsonGetTicks(O, H, L, C, V, BV, T));
                }


                if(arrayList.size()>0) {
                    int numCount = arrayList.size() / 5;
                    setDataChart(numCount);
                    setDataView();
                }else {
                    new PromptDialog(ListSumaryActivity.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                            .setAnimationEnable(true)
                            .setTitleText("ERROR")
                            .setContentText("No connect to Server !\nPlease check Internet !")
                            .setPositiveListener(getString(R.string.ok), new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    finish();
                                }
                            }).show();
                }

//                adapter = new RecycleAdapter(arrayList, getBaseContext());
//                recyclerView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // error.printStackTrace();
                showDialog();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    void showDialog(){
        PromptDialog promptDialog  = new PromptDialog(ListSumaryActivity.this);
        promptDialog.setCancelable(false);
        promptDialog.setDialogType(PromptDialog.DIALOG_TYPE_WRONG);
        promptDialog.setAnimationEnable(true);
        promptDialog.setTitleText("INTERNET CONNECT !");
        promptDialog.setCanceledOnTouchOutside(false);
        //promptDialog.setContentText("Please input User name and \n"+"Password before login to Server !");
        promptDialog.setPositiveListener(this.getString(R.string.ok), new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        finish();
                        dialog.dismiss();
                    }
                });
        promptDialog.show();
    }
    void setDataView(){
        DecimalFormat df = new DecimalFormat("#.######");
        DecimalFormat dfVolume = new DecimalFormat("######.###");
        txt_Volume.setText("$"+dfVolume.format(arrayList.get(arrayList.size()-1).getV()));
        try {
            String cr = new SimpleDateFormat("dd-mm-yyyy").format(new SimpleDateFormat("yyyy-mm-dd").parse(arrayList.get(arrayList.size()-1).getT()));
            txt_Day.setText(cr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txt_High.setText("+"+df.format(arrayList.get(arrayList.size()-1).getH())+"%");
        txt_Low.setText("-"+df.format(arrayList.get(arrayList.size()-1).getL())+"%");

        // body 3
        txt_Open.setText(""+df.format(arrayList.get(arrayList.size()-1).getO()) + "%");
        txt_Close.setText(""+df.format(arrayList.get(arrayList.size()-1).getC()) +"%");
        txt_BaseVl.setText(""+df.format(arrayList.get(arrayList.size()-1).getBV())+"%");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.candle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bitmap bm= null;
        if (img_Currency != null)
        {
            bm = ((BitmapDrawable)img_Currency.getDrawable()).getBitmap();
        }
        switch (item.getItemId()) {
            case android.R.id.home: {
                this.finish();
                break;
            }
            case R.id.actionToggleHighlight: {
                Alerter.create(ListSumaryActivity.this)
                        .setTitle(currency)
                        .setIcon(bm)
                        .setIconColorFilter(0) // Optional - Removes white tint
                        .setText("Added " + currency + " to Favorites your !")
                        .setBackgroundColorInt(getResources().getColor(R.color.pocket_color_4))
                        .enableProgress(true)
                        .setProgressColorRes(R.color.white)
                        .show();
                break;
            }
            case R.id.animateX: {
                mChart.animateX(3000);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(3000);
                break;
            }
            case R.id.animateXY: {

                mChart.animateXY(3000, 3000);
                break;
            }
        }
        return true;
    }

}

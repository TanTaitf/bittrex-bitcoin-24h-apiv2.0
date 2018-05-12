package com.media.tf.appbitcoinbittrex;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import Adapter.AdapterPagerFragment;
import Adapter.RecycleAdapter;
import Adapter.SliderShowAdapter;
import Model.MarketSumary;
import Model.Video;
import devlight.io.library.ntb.NavigationTabBar;
import fragment.CommentFragment;
import fragment.FlipPageViewTransformer;
import fragment.FragmentUser;
import fragment.HomeFragment;
import fragment.NewspaperFragment;

import static Model.Config.setFont;
import static org.jsoup.Jsoup.parse;

public class MainActivity extends AppCompatActivity {

    private int limitNumberOfPages = 4;
    private ArrayList<MarketSumary> arrayBitCoin;
    private Typeface typeface;
    private RecycleAdapter adapter;
    private RecyclerView recyclerView;
    private TextView txt_icon_bitcoins, txt_title_market, txt_title_Volume, txt_title_High, txt_title_Low;
    private ImageView img_viewCurrecy;
    private RecyclerView rycSliderShow;
    private TextView txtTextSilderShow;
    private Window window;
    private Toolbar topToolBar;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<Video> arrayItem;
    public static Handler handler = new Handler();
    public  NavigationTabBar navigationTabBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        typeface = setFont(this, typeface);
//        if (Build.VERSION.SDK_INT >= 21) {
//            window = this.getWindow();
        // window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS|WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//            View decorView = getWindow().getDecorView();
//            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
//            decorView.setSystemUiVisibility(uiOptions);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//            window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
//        }
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.setReverseLayout(true);
        initView();
        boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        boolean PermanentMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey(); // true if physical, false if virtual

        //Toast.makeText(this,PermanentMenuKey+""+ hasBackKey+""+hasHomeKey +"",Toast.LENGTH_LONG).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

//            WindowManager.LayoutParams attributes = getWindow().getAttributes();
//            attributes.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
//            getWindow().setAttributes(attributes);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//
//            }
           // w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
//        if(!PermanentMenuKey && hasBackKey == true) {
//            Resources resources = MainActivity.this.getResources();
//            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
//            int hegiht_Bar = 0;
//            if (resourceId > 0) {
//                hegiht_Bar = resources.getDimensionPixelSize(resourceId);
//               Toast.makeText(this, hegiht_Bar +"",Toast.LENGTH_LONG).show();
//                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) navigationTabBar.getLayoutParams();
//                params.bottomMargin = hegiht_Bar;
//                navigationTabBar.setLayoutParams(params);
//            }
//        }


        txtTextSilderShow.setSelected(true);
        arrayBitCoin = new ArrayList<>();
        //setSupportActionBar(topToolBar);
//        topToolBar.setTitle("Bitcoin 24HR");
////        topToolBar.setLogo(R.mipmap.ic_launcher);
//        topToolBar.setLogoDescription(getResources().getString(R.string.logo_desc));
        initUI();
        setSliderShow(rycSliderShow, "", 20, 1, true, false);
        txtTextSilderShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListSumaryActivity.class));
            }
        });
        String url_Html = "https://bittrex.com/home/markets";
        url_Html = "https://news.bitcoin.com/";
        Random rand = new Random();
        messageLoop(rand);


//        setUpViewPagerTab();
    }

    private static String docNoiDung_Tu_URL(String theUrl) {
        StringBuilder content = new StringBuilder();

        try {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    class ReadHmtl extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... Params) {
            return docNoiDung_Tu_URL(Params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    private ArrayList getHtml1() {
        String linkImg = "";
        String anhurl = "";
        String title = "";
        String date = "";
        String url_web = "";
        arrayItem = new ArrayList<>();
        ArrayList arrtemp = new ArrayList();
        Random rand = new Random();
        String rss_url = null;
        try {

            rss_url = new ReadHmtl().execute("https://news.bitcoin.com/").get();
            Document doc = (Document) Jsoup.parse(rss_url);
            //doc = Jsoup.connect("https://news.bitcoin.com/").get();

            //Elements oDd = doc.getElementsByClass("td_module_mx25 td_module_wrap td-animation-stack td-big-grid-post-3 td-big-grid-post td-mx-15");
            Elements ele = doc.getElementsByClass("td-module-image");
            Elements di = doc.getElementsByClass("td-post-category");

            for (org.jsoup.nodes.Element du : di) {
                System.out.println("---od " + du.text().toString());
                arrtemp.add(du.text());
            }
            for (org.jsoup.nodes.Element el : ele) {

                Elements link = el.getElementsByClass("td-module-thumb");
                for (org.jsoup.nodes.Element lq : link) {
                    Elements a = link.select("a");
                    title = a.attr("title").toString();
                    url_web = a.attr("href");
                    Elements url = null;
                    if (a.select("span").isEmpty() == false) {
                        url = (a.select("span"));
                        if (url != null) {
                            anhurl = url.attr("style");
                            if (anhurl != null) {
                                int v1 = anhurl.indexOf("(");
                                int v2 = anhurl.lastIndexOf(")");
                                anhurl = anhurl.substring(v1 + 1, v2);
                            }
                        }
                    } else {
                        url = a.select("img");
                        if (url != null) {
                            anhurl = url.attr("src").toString();
                        }
                    }
                    int n = rand.nextInt(arrtemp.size());
                    arrayItem.add(new Video(title, anhurl, arrtemp.get(n).toString(),url_web));
                }


            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return arrayItem;
    }

    private void getHtlmBittrex(String url, final Context context) {
//        Jsoup jsoup = new Jsoup();
//        jsoup.getStringVolley(url,context);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();

                        // Log.d("AAA",abc.toString());
                        org.jsoup.nodes.Document document = null;
                        String src = "";
                        String biggest_volume = "";
                        String namCunrrecy = "";
                        String baseCyrrecy = "";
                        String changeCurrency = "";

                        document = parse(response);
                        if (document != null) {
                            Toast.makeText(context, "Not Nulll----", Toast.LENGTH_SHORT).show();
                            // carousel carousel-navigation market-info-list hidden-xs clearfix
                            Elements subjectElements = document.select("div.navigation > div.carousel carousel-navigation market-info-list hidden-xs clearfix > ul");
                            for (org.jsoup.nodes.Element element : subjectElements) {


//                                String html = "<h6 class='uiStreamMessage' data-ft=''><span class='messageBody' data-ft=''>Twisted<a href='http://'><span>http://</span><span class='word_break'></span>www.tb.net/</a> Balloons</span></h6>";
//                                Document doc = Jsoup.parse(html);
//                                Elements elements = doc.select("h6.uiStreamMessage > span.messageBody");


                                Elements ulSubject = element.select("li.market-list>div.item");
                                Element imgSubject = ulSubject.select("div.col-md-6 col-xs-6 market-icon>img").first();
//                                for (Element e : elements) {
//                                    System.out.println("All text:" + e.text());
//                                    System.out.println("Only messageBody text:" + e.ownText());
//                                }

                                if (imgSubject != null) {
                                    src = imgSubject.attr("src");
                                }

                                Elements div2 = ulSubject.select("div.col-md-6 col-xs-6 market-price-detail");
                                Elements biggest = div2.select("div.title market-gain");
                                biggest_volume = biggest.text();
                                Elements name_title = div2.select("div.name market-name");
                                namCunrrecy = name_title.text();
                                Elements base = div2.select("div.name volume market-price");
                                baseCyrrecy = base.text();

                                Element up = div2.select("div.changed market-percent-up > span").first();
                                if (up != null) {
                                    changeCurrency = up.text();
                                }
//                                for(org.jsoup.nodes.Element span : subjectElements){
//                                    Element up_value = span.getElementsByTag("span").first();
//                                    if (up_value != null) {
//                                        changeCurrency = up_value.text();
//                                    }
//                                }
                                Toast.makeText(context, "Jsoup _ " + src + baseCyrrecy + biggest_volume + namCunrrecy + changeCurrency + up, Toast.LENGTH_LONG).show();

                            }


                        } else {
                            Toast.makeText(context, "Nulll----", Toast.LENGTH_SHORT).show();
                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("AAA", error.toString());
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating a Request Queue
        requestQueue.add(stringRequest);
    }

    private void initView() {
        navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        rycSliderShow = (RecyclerView) findViewById(R.id.myRecyclerViewDangTienHanh);
        //findViewById(R.id.txtSliderShow).setSelected(true);
        txtTextSilderShow = (TextView) findViewById(R.id.txtSliderShow);
        txtTextSilderShow.setTypeface(typeface);

        //topToolBar = (Toolbar)findViewById(R.id.myToolBar);
    }

//    private void initViewPager(View view) {
//        txt_icon_bitcoins = view.findViewById(R.id.txt_icon_bitcoins);
//        txt_title_market = view.findViewById(R.id.txt_title_market);
//        txt_title_High = view.findViewById(R.id.txt_title_High);
//        txt_title_Volume = view.findViewById(R.id.txt_title_Volume);
//        txt_title_Low = view.findViewById(R.id.txt_title_Low);
//        //img_viewCurrecy = view.findViewById(R.id.img_viewCurrecy);
//
//
//        txt_title_market.setTypeface(typeface);
//        txt_title_High.setTypeface(typeface);
//        txt_icon_bitcoins.setTypeface(typeface);
//        txt_title_Volume.setTypeface(typeface);
//        txt_title_Low.setTypeface(typeface);
//        txt_title_market.setPaintFlags(txt_title_market.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//    }

    public void messageLoop(final Random rand) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int n = rand.nextInt(arrayItem.size());
                        txtTextSilderShow.setText(arrayItem.get(n).getTitle().toString());
                        handler.postDelayed(this, 7000);

                    }
                });
            }
        }, 7000);
    }

    private void setUpViewPagerTab(ViewPager viewpager) {
        AdapterPagerFragment adapter= new AdapterPagerFragment(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(),"");

        adapter.addFragment(new NewspaperFragment(),"");
        adapter.addFragment(new FragmentUser(),"");
        adapter.addFragment(new CommentFragment(),"");

        viewpager.setOffscreenPageLimit(limitNumberOfPages);
        viewpager.setAdapter(adapter);
        viewpager.setPageTransformer(false,new FlipPageViewTransformer());


//        tabLayout.setupWithViewPager(viewpager);
//        setIconTabLayout();
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            public void onTabSelected(TabLayout.Tab tab) {
//                int colorFrom = ((ColorDrawable) toolbar.getBackground()).getColor();
//                int colorTo = getColorForTab(tab.getPosition());
//                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
//                colorAnimation.setDuration(900);
//                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animator) {
//                        int color = (int) animator.getAnimatedValue();
//                        toolbar.setBackgroundColor(color);
//                        tabLayout.setBackgroundColor(color);
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            getWindow().setStatusBarColor(color);
//                        }
//                    }
//                });
//                colorAnimation.start();
//                txtView.setTypeface(typeface);
//
//                //SET title cho Toolbar
//                if (tab.getPosition()==1){
////                    txtView.setText("TOP EDM");
////                    YoYo.with(Techniques.FadeIn)
////                            .duration(350)
////                            .withListener(new Animator.AnimatorListener() {
////                                @Override
////                                public void onAnimationStart(Animator animation) {
////
////                                }
////
////                                @Override
////                                public void onAnimationEnd(Animator animation) {
////
////                                }
////
////                                @Override
////                                public void onAnimationCancel(Animator animation) {
////                                }
////
////                                @Override
////                                public void onAnimationRepeat(Animator animation) {
////
////                                }
////                            })
////                            .playOn(txtView);
//                    /*if (android.os.Build.VERSION.SDK_INT >= 21){
//                        setStatusColorBlueGray900();
//                    }*/
//                }else if(tab.getPosition()==2){
////                    txtView.setText("TOP 20 DJ");
////                    YoYo.with(Techniques.FadeIn)
////                            .duration(350)
////                            .withListener(new Animator.AnimatorListener() {
////                                @Override
////                                public void onAnimationStart(Animator animation) {
////
////                                }
////
////                                @Override
////                                public void onAnimationEnd(Animator animation) {
////
////                                }
////
////                                @Override
////                                public void onAnimationCancel(Animator animation) {
////                                }
////
////                                @Override
////                                public void onAnimationRepeat(Animator animation) {
////
////                                }
////                            })
////                            .playOn(txtView);
//
//                    /*if (android.os.Build.VERSION.SDK_INT >= 21){
//                        setStatusColorTeal900();
//                    }*/
//                }else if (tab.getPosition()==3){
////                    txtView.setText("TOP INTRO");
////                    YoYo.with(Techniques.FadeIn)
////                            .duration(350)
////                            .withListener(new Animator.AnimatorListener() {
////                                @Override
////                                public void onAnimationStart(Animator animation) {
////
////                                }
////
////                                @Override
////                                public void onAnimationEnd(Animator animation) {
////
////                                }
////
////                                @Override
////                                public void onAnimationCancel(Animator animation) {
////                                }
////
////                                @Override
////                                public void onAnimationRepeat(Animator animation) {
////
////                                }
////                            })
////                            .playOn(txtView);
//
//                }
//                else if(tab.getPosition()==4){
////                    txtView.setText("ABOUT APP");
////                    YoYo.with(Techniques.FadeIn)
////                            .duration(350)
////                            .withListener(new Animator.AnimatorListener() {
////                                @Override
////                                public void onAnimationStart(Animator animation) {
////
////                                }
////
////                                @Override
////                                public void onAnimationEnd(Animator animation) {
////
////                                }
////
////                                @Override
////                                public void onAnimationCancel(Animator animation) {
////                                }
////
////                                @Override
////                                public void onAnimationRepeat(Animator animation) {
////
////                                }
////                            })
////                            .playOn(txtView);
//
//                }
//                else if (tab.getPosition()==5) {
////                    txtView.setText("TOP DANCE");
////                    YoYo.with(Techniques.FadeIn)
////                            .duration(350)
////                            .withListener(new Animator.AnimatorListener() {
////                                @Override
////                                public void onAnimationStart(Animator animation) {
////
////                                }
////
////                                @Override
////                                public void onAnimationEnd(Animator animation) {
////
////                                }
////
////                                @Override
////                                public void onAnimationCancel(Animator animation) {
////                                }
////
////                                @Override
////                                public void onAnimationRepeat(Animator animation) {
////
////                                }
////                            })
////                            .playOn(txtView);
//
//                    /*if (android.os.Build.VERSION.SDK_INT >= 21) {
//                        setStatusColorPurple900();
//                    }*/
//                }else {
////                    txtView.setText("HOME");
////                    YoYo.with(Techniques.FadeIn)
////                            .duration(350)
////                            .withListener(new Animator.AnimatorListener() {
////                                @Override
////                                public void onAnimationStart(Animator animation) {
////
////                                }
////
////                                @Override
////                                public void onAnimationEnd(Animator animation) {
////
////                                }
////
////                                @Override
////                                public void onAnimationCancel(Animator animation) {
////                                }
////
////                                @Override
////                                public void onAnimationRepeat(Animator animation) {
////
////                                }
////                            })
////                            .playOn(txtView);
//
//                    /*if (android.os.Build.VERSION.SDK_INT >= 21){
//                        setStatusColorLightBlue900();
//                    }*/
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }
    private void initUI() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);


        setUpViewPagerTab(viewPager);
//        viewPager.setAdapter(new PagerAdapter() {
//            @Override
//            public int getCount() {
//                return 4;
//            }
//
//            @Override
//            public boolean isViewFromObject(final View view, final Object object) {
//                return view.equals(object);
//            }
//
//            @Override
//            public void destroyItem(final View container, final int position, final Object object) {
//                ((ViewPager) container).removeView((View) object);
//            }
//
//            @Override
//            public Object instantiateItem(final ViewGroup container, final int position) {
//                final View view = LayoutInflater.from(
//                        getBaseContext()).inflate(R.layout.item_vp_list, null, false);
//                final RecyclerView recyclerView = view.findViewById(R.id.rv);
//                initViewPager(view);
//                final PullRefreshLayout layout = view.findViewById(R.id.pull_to_refresh);
//
//                recyclerView.setHasFixedSize(true);
//                recyclerView.setLayoutManager(new LinearLayoutManager(
//                                getBaseContext(), LinearLayoutManager.VERTICAL, false
//                        )
//                );
//
//                getJsonApiFromBittrex(url, recyclerView);
//
//                layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
//                        // start refresh
//                        getJsonApiFromBittrex(url, recyclerView);
//                        layout.setRefreshing(false);
//                    }
//                });
//
//                container.addView(view);
//                return view;
//            }
//        });



        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_dollarblack),
                        Color.parseColor(colors[0]))
                        .title("Heart")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_third),
                        Color.parseColor(colors[1]))
                        .title("Cup")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_userblack),
                        Color.parseColor(colors[2]))
                        .title("Diploma")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fourth),
                        Color.parseColor(colors[3]))
                        .title("Flag")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);

        //IMPORTANT: ENABLE SCROLL BEHAVIOUR IN COORDINATOR LAYOUT
        navigationTabBar.setBehaviorEnabled(true);

        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {

            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
                model.hideBadge();
            }
        });
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {

            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.parent);
//        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
//                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
//                    navigationTabBar.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            final String title = String.valueOf(new Random().nextInt(15));
//                            if (!model.isBadgeShowed()) {
//                                model.setBadgeTitle(title);
//                                model.showBadge();
//                            } else model.updateBadgeTitle(title);
//                        }
//                    }, i * 100);
//                }
//
//                coordinatorLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        final Snackbar snackbar = Snackbar.make(navigationTabBar, "Coordinator NTB", Snackbar.LENGTH_SHORT);
//                        snackbar.getView().setBackgroundColor(Color.parseColor("#9b92b3"));
//                        ((TextView) snackbar.getView().findViewById(R.id.snackbar_text))
//                                .setTextColor(Color.parseColor("#423752"));
//                        snackbar.show();
//                    }
//                }, 1000);
//            }
//        });

        final CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#ffffff"));
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#ffffff"));
    }

    private void setSliderShow(final RecyclerView mRecyclerView, String key, int max, final int index, final boolean action, boolean layout) {
        key = key.replace(" ", "%20");
        //LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        if (layout == true) {
            CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
            layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.addOnScrollListener(new CenterScrollListener());
        } else {
            mRecyclerView.setLayoutManager(new CustomLinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        }
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        final ArrayList<Video> arrayList = getHtml1();


        final SliderShowAdapter adapter = new SliderShowAdapter(arrayList, getApplicationContext());
        //final Adapter_Tab_3 adapter = new Adapter_Tab_3(arrayList,getActivity());
        mRecyclerView.setAdapter(adapter);

        if (action == true) {
            setAutoScroll(mRecyclerView, adapter);
        }


//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        Toast.makeText(this,"load anh", Toast.LENGTH_LONG).show();
//        final String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=android&type=video&maxResults=20&key=AIzaSyBmTVXZn7dsnLL__gLeK2EPL_5_z-igqCw";
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
//                    item = jsonitem.optJSONObject(j);
//                    id = item.optJSONObject("id");
//                    idvideo = id.optString("videoId");
//
//
//                    jsonSpinet = item.optJSONObject("snippet");
//                    title = jsonSpinet.optString("title");
//                    dertion = jsonSpinet.optString("description");
//
//                    jsonthumnail = jsonSpinet.optJSONObject("thumbnails");
//                    jsondefault = jsonthumnail.optJSONObject("medium");
//                    imaview = jsondefault.optString("url");
//                    chanelTitle = jsonSpinet.optString("channelTitle");
//                    dayupl = jsonSpinet.optString("publishedAt");
//                    arrayList.add(new Video(idvideo,title,dertion,imaview,chanelTitle, dayupl));
//
//                }
//                //adapterVideo.notifyDataSetChanged();
//                final SliderShowAdapter adapter = new SliderShowAdapter(arrayList,getApplicationContext());
//                //final Adapter_Tab_3 adapter = new Adapter_Tab_3(arrayList,getActivity());
//                mRecyclerView.setAdapter(adapter);
//
//                if (action==true){
//                    setAutoScroll(mRecyclerView,adapter);
//                }
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

    private void setAutoScroll(final RecyclerView recyclerView, final SliderShowAdapter adapter) {
        final int speedScroll = 2500;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;
            boolean flag = true;

            @Override
            public void run() {
                if (count < adapter.getItemCount()) {
                    if (count == adapter.getItemCount() - 1) {
                        flag = false;
                    } else if (count == 0) {
                        flag = true;
                    }
                    if (flag) count++;
                    else count--;

                    recyclerView.smoothScrollToPosition(count);
                    handler.postDelayed(this, speedScroll);
                }
            }
        };
        handler.postDelayed(runnable, speedScroll);

    }

    public static class CustomLinearLayoutManager extends LinearLayoutManager {
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

    private int randomColor() {
        float[] TEMP_HSL = new float[]{0, 0, 0};
        float[] hsl = TEMP_HSL;
        hsl[0] = (float) (Math.random() * 360);
        hsl[1] = (float) (40 + (Math.random() * 60));
        hsl[2] = (float) (40 + (Math.random() * 60));
        return ColorUtils.HSLToColor(hsl);
    }
}

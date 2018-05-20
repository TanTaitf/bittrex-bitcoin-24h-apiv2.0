package fragment;


import android.content.Context;
import android.graphics.PointF;
import android.os.AsyncTask;
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

import com.developers.coolprogressviews.SimpleArcProgress;
import com.media.tf.appbitcoinbittrex.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Adapter.Adapter_TabNews;
import Model.ThongTinBitcoin;
import Model.Video;

public class NewspaperFragment extends Fragment {

    private View view;
    ArrayList<Video> arrayList;
    private Adapter_TabNews adapter;
    private RecyclerView myRecyclerView;
    private SimpleArcProgress progress;
    private ArrayList<ThongTinBitcoin> articlesBitCoin;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_newspaper,container,false);
        myRecyclerView = view.findViewById(R.id.listFeed);
        myRecyclerView.setLayoutManager(new CustomLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        progress = (SimpleArcProgress)view.findViewById(R.id.progress);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setNestedScrollingEnabled(false);
        articlesBitCoin = new ArrayList<>();

        try {
            articlesBitCoin = new Download().execute("https://news.bitcoin.com/").get();

//            for(ThongTinBitcoin a : articlesBitCoin) {
//                String hinhAnh = a.getHinhAnh();
//                int indexbd = hinhAnh.indexOf("http");
//                int indexkt = hinhAnh.lastIndexOf("g");
//                hinhAnh = hinhAnh.substring(indexbd, indexkt+1);
//                Log.d("AAA", hinhAnh);
//
//                String link = a.getLink();
//                Log.d("AAA", link);
//
//                String thoiGian = a.getThoiGian();
//                Log.d("AAA", thoiGian);
//
//                String title = a.getTieuDe();
//                Log.d("AAA", title);
//                //Toast.makeText(getActivity(), link+thoiGian, Toast.LENGTH_SHORT).show();
//            }
            setItemList(articlesBitCoin);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private class Download extends AsyncTask<String, String, ArrayList<ThongTinBitcoin>> {

        @Override
        protected ArrayList<ThongTinBitcoin> doInBackground(String... strings) {
            Document doc = null;
            ArrayList<ThongTinBitcoin> list = new ArrayList<>();
            try {
                doc = Jsoup.connect(strings[0]).get();
                Elements div = doc.getElementsByClass("td-big-grid-wrapper td-posts-5").get(0).children();
                if (div != null) {
                    int length = div.get(0).getElementsByClass("td-module-thumb").size();
                    for (int i = 0; i < length; i++) {
                        ThongTinBitcoin a = new ThongTinBitcoin();
                        String hinhAnh = div.get(0).getElementsByClass("td-module-thumb").get(i).getElementsByTag("span").attr("style");
                        a.setHinhAnh(hinhAnh);

                        String link = div.get(0).getElementsByClass("td-module-thumb").get(i).getElementsByTag("a").attr("href");
                        a.setLink(link);

                        String thoiGian = div.get(0).getElementsByClass("td-module-meta-info").get(i).text();
                        a.setThoiGian(thoiGian);


                        String title = div.get(0).getElementsByClass("td-big-grid-meta").get(i).text();
                        a.setTieuDe(title);

                        list.add(a);
                    }


                    if (div != null) {
                        length = div.get(1).getElementsByClass("td-module-thumb").size();
                        for (int i = 0; i < length; i++) {
                            ThongTinBitcoin a = new ThongTinBitcoin();
                            String hinhAnh = div.get(1).getElementsByClass("td-module-thumb").get(i).getElementsByTag("span").attr("style");
                            a.setHinhAnh(hinhAnh);

                            String link = div.get(1).getElementsByClass("td-module-thumb").get(i).getElementsByTag("a").attr("href");
                            a.setLink(link);

                            String thoiGian = div.get(1).getElementsByClass("td-module-meta-info").get(i).text();
                            a.setThoiGian(thoiGian);


                            String title = div.get(1).getElementsByClass("td-big-grid-meta").get(i).text();
                            a.setTieuDe(title);

                            list.add(a);
                        }
                    }



                    div = doc.getElementsByClass("vc_column-inner vc_custom_1509989831623").get(0).getElementsByClass("wpb_wrapper").get(0).children();
                    if(div != null) {
                        Elements li = div.get(0).getElementsByClass("td_module_mx16 td_module_wrap td-animation-stack");
                        length = li.size();
                        for (int i = 0; i < length; i++) {
                            ThongTinBitcoin a = new ThongTinBitcoin();
                            String thoiGian = li.get(i).getElementsByClass("latest-left").text();
                            a.setThoiGian(thoiGian);

                            String title = li.get(i).getElementsByClass("latest-right").text();
                            a.setTieuDe(title);

                            String hinhAnh = li.get(i).getElementsByClass("td-module-thumb").get(0).getElementsByTag("img").attr("src");
                            a.setHinhAnh(hinhAnh);

                            String link = li.get(i).getElementsByClass("td-module-thumb").get(0).getElementsByTag("a").attr("href");
                            a.setLink(link);

                            list.add(a);
                        }
                    }


                    if(div != null)
                    {
                        for (int i = 1; i < 4; i++) {
                            ThongTinBitcoin a = new ThongTinBitcoin();
                            String hinhAnh = div.get(i).getElementsByClass("td-module-thumb").get(0).getElementsByTag("img").attr("src");
                            a.setHinhAnh(hinhAnh);

                            String link = div.get(i).getElementsByClass("td-module-thumb").get(0).getElementsByTag("a").attr("href");
                            a.setLink(link);

                            String thoiGian = div.get(i).getElementsByClass("td-module-meta-info").get(0).getElementsByClass("ago-date-small").text();
                            a.setThoiGian(thoiGian);


                            String title = div.get(i).getElementsByClass("td-module-meta-info").get(0).getElementsByClass("entry-title td-module-title").text();
                            a.setTieuDe(title);

                            list.add(a);
                        }
                    }


                    if(div != null) {
                        Elements li = div.get(4).getElementsByClass("td_module_mx16 td_module_wrap td-animation-stack");
                        for (int i = 0; i < 3; i++) {
                            ThongTinBitcoin a = new ThongTinBitcoin();
                            String thoiGian = li.get(i).getElementsByClass("latest-left").text();
                            a.setThoiGian(thoiGian);

                            String title = li.get(i).getElementsByClass("latest-right").text();
                            a.setTieuDe(title);

                            String hinhAnh = li.get(i).getElementsByClass("td-module-thumb").get(0).getElementsByTag("img").attr("src");
                            a.setHinhAnh(hinhAnh);

                            String link = li.get(i).getElementsByClass("td-module-thumb").get(0).getElementsByTag("a").attr("href");
                            a.setLink(link);

                            list.add(a);
                        }
                    }



                    if(div != null)
                    {
                        for (int i = 5; i < 8; i++) {
                            ThongTinBitcoin a = new ThongTinBitcoin();
                            String hinhAnh = div.get(i).getElementsByClass("td-module-thumb").get(0).getElementsByTag("img").attr("src");
                            a.setHinhAnh(hinhAnh);

                            String link = div.get(i).getElementsByClass("td-module-thumb").get(0).getElementsByTag("a").attr("href");
                            a.setLink(link);

                            String thoiGian = div.get(i).getElementsByClass("td-module-meta-info").get(0).getElementsByClass("ago-date-small").text();
                            a.setThoiGian(thoiGian);


                            String title = div.get(i).getElementsByClass("td-module-meta-info").get(0).getElementsByClass("entry-title td-module-title").text();
                            a.setTieuDe(title);

                            list.add(a);
                        }
                    }

                    if(div != null) {
                        Elements li = div.get(8).getElementsByClass("td_module_mx16 td_module_wrap td-animation-stack");
                        length = li.size();
                        for (int i = 0; i < length; i++) {
                            ThongTinBitcoin a = new ThongTinBitcoin();
                            String thoiGian = li.get(i).getElementsByClass("latest-left").text();
                            a.setThoiGian(thoiGian);

                            String title = li.get(i).getElementsByClass("latest-right").text();
                            a.setTieuDe(title);

                            String hinhAnh = li.get(i).getElementsByClass("td-module-thumb").get(0).getElementsByTag("img").attr("src");
                            a.setHinhAnh(hinhAnh);

                            String link = li.get(i).getElementsByClass("td-module-thumb").get(0).getElementsByTag("a").attr("href");
                            a.setLink(link);

                            list.add(a);
                        }
                    }


                    if(div != null)
                    {
                        for (int i = 9; i < 10; i++) {
                            ThongTinBitcoin a = new ThongTinBitcoin();
                            String hinhAnh = div.get(i).getElementsByClass("td-module-thumb").get(0).getElementsByTag("img").attr("src");
                            a.setHinhAnh(hinhAnh);

                            String link = div.get(i).getElementsByClass("td-module-thumb").get(0).getElementsByTag("a").attr("href");
                            a.setLink(link);

                            String thoiGian = div.get(i).getElementsByClass("td-module-meta-info").get(0).getElementsByClass("ago-date-small").text();
                            a.setThoiGian(thoiGian);


                            String title = div.get(i).getElementsByClass("td-module-meta-info").get(0).getElementsByClass("entry-title td-module-title").text();
                            a.setTieuDe(title);

                            list.add(a);
                        }
                    }


                    div = doc.getElementsByClass("td-block-row");
                    if(div != null)
                    {
                        length = div.size();
                        for(int i=0 ;i<length; i++)
                        {
                            Elements div1 = div.get(i).getElementsByClass("td-block-span4");
                            int length1 = div1.size();
                            for(int j =0;j<length1;j++)
                            {
                                ThongTinBitcoin a = new ThongTinBitcoin();
                                String hinhAnh = div1.get(j).getElementsByClass("td-module-thumb").get(0).getElementsByTag("img").attr("src");
                                a.setHinhAnh(hinhAnh);

                                String link = div1.get(j).getElementsByClass("td-module-thumb").get(0).getElementsByTag("a").attr("href");
                                a.setLink(link);

                                String title = div1.get(j).getElementsByClass("entry-title td-module-title").text();
                                a.setTieuDe(title);

                                String thoiGian = div1.get(j).getElementsByClass("td-module-meta-info").text();
                                a.setThoiGian(thoiGian);

                                list.add(a);
                            }
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<ThongTinBitcoin> articles) {
            super.onPostExecute(articles);

        }
    }
    void setItemList(ArrayList<ThongTinBitcoin> thongTinBitcoinArrayList){
        if(thongTinBitcoinArrayList.size() > 0){
            adapter = new Adapter_TabNews(thongTinBitcoinArrayList,getActivity());
            myRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            progress.setVisibility(View.GONE);
        }

    }

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

package Controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static org.jsoup.Jsoup.parse;

public class Jsoup {
    public String getRSSLinkFromURL(String url) {
        // RSS url
        String rss_url = null;
        String jpg = "";

//        try {
//            // Using JSoup library to parse the html source code
//            org.jsoup.nodes.Document doc = Jsoup.(url).get();
////            Toast.makeText(getActivity(),  doc.toString(),Toast.LENGTH_SHORT).show();
//
//            // finding rss links which are having link[type=application/rss+xml]
//            org.jsoup.select.Elements links = doc.select("link[type=application/rss+xml]");
//
//
////            org.jsoup.nodes.Document document = Jsoup.connect(url).get();
//            String title = doc.title();
//
//            Elements pngs = doc.select("img[src$=.jpg]");
//            jpg = pngs.get(0).attr("href").toString();
//            Log.d("LinkAnh", "linkanh " +jpg);
//            Log.d("No of RSS links found", " " + links.size());
//
//            // check if urls found or not
//            if (links.size() > 0) {
//                rss_url = links.get(0).attr("href").toString();
//            } else {
//                // finding rss links which are having link[type=application/rss+xml]
//                org.jsoup.select.Elements links1 = doc
//                        .select("link[type=application/atom+xml]");
//                if(links1.size() > 0){
//                    rss_url = links1.get(0).attr("href").toString();
//                }
//            }
//
//        } catch (IOException e) {
//            Log.d("TTT", "getRSSLinkFromURL: "+e.toString());
//            e.printStackTrace();
//        }

        // returing RSS url
        return rss_url;
    }

    public void getStringVolley(String url, final Context context) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();

                        // Log.d("AAA",abc.toString());
                        org.jsoup.nodes.Document document = null;
                        String src = "";
                        String biggest_volume = "";
                        String namCunrrecy = "";
                        String baseCyrrecy = "";
                        String changeCurrency = "";

                        document = parse(response.toString());
                        if (document != null) {
                            // carousel carousel-navigation market-info-list hidden-xs clearfix
                            Elements subjectElements = document.select("div.navigation>div.carousel carousel-navigation market-info-list hidden-xs clearfix>ul");
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
                                Toast.makeText(context, "Jsoup _ " + src + baseCyrrecy + biggest_volume + namCunrrecy+changeCurrency + up, Toast.LENGTH_LONG).show();

                            }


                        }
                        else {
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

//    public void getHtml(String url) {
//        String rss_url = null;
//        try {
//            rss_url = new ReadHmtl().execute(url).get();
//            Document document = (Document)Jsoup.Parse(rss_url);
//            Document doc = Jsoup.connect("https://news.bitcoin.com/").get();
//            Elements ele = doc.getElementsByClass("td-module-image");
//            System.out.println(ele.toString());
//            for (Element el : ele) {
//                System.out.println(el.toString());
//            }
//            if (document != null) {
//                Elements subjectElements = document.select("div.ctDetatilS");
//                for (Element element : subjectElements) {
//                    Element imgSubject = element.getElementsByTag("img").first();
//                    if (imgSubject != null) {
//                        String src1 = imgSubject.attr("src");
////                        dem = 1;
////                        if (dem == 1){
////                            jpg.add(src1);
////                            dem = 0;
////                            break;
////                        }
//                    }
//                }
//
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//
//    private static String docNoiDung_Tu_URL(String theUrl) {
//        StringBuilder content = new StringBuilder();
//
//        try {
//            // create a url object
//            URL url = new URL(theUrl);
//
//            // create a urlconnection object
//            URLConnection urlConnection = url.openConnection();
//
//            // wrap the urlconnection in a bufferedreader
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//
//            String line;
//
//            // read from the urlconnection via the bufferedreader
//            while ((line = bufferedReader.readLine()) != null) {
//                content.append(line + "\n");
//            }
//            bufferedReader.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return content.toString();
//    }
//
//    class ReadHmtl extends AsyncTask<String, Integer, String> {
//        @Override
//        protected String doInBackground(String... Params) {
//            return docNoiDung_Tu_URL(Params[0]);
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//        }
//    }
}

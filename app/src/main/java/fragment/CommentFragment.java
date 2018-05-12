package fragment;

import android.animation.Animator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.media.tf.appbitcoinbittrex.R;

import static Model.Config.setFont;
import static Model.Config.setFont_Logo;

//import com.daimajia.androidanimations.library.Techniques;
//import com.daimajia.androidanimations.library.YoYo;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.reward.RewardItem;
//import com.google.android.gms.ads.reward.RewardedVideoAd;
//import com.google.android.gms.ads.reward.RewardedVideoAdListener;
//import com.media.tf.app_dj_online.R;
//
//import info.hoang8f.widget.FButton;
//
//import static com.media.tf.app_dj_online.Model.Config.ID_donViQuangNative;
//import static com.media.tf.app_dj_online.Model.Config.ID_ungDung;
//import static com.media.tf.app_dj_online.Model.Config.setFont;
//import static com.media.tf.app_dj_online.Model.Config.setFont_Logo;

/**
 * Created by Nguyen Sang on 10/25/2017.
 */

public class CommentFragment extends Fragment {

    private View view;
   // private AVLoadingIndicatorView avi, avi1, avi2;
    LinearLayout linear1,showless; //review;
    LinearLayout linear2;
     //ln, layout_view_Other, layout_info_count, layout_title_view;
//    private Dialog dialog;
//    ImageView img_heart, img_showfull;
//    ImageButton img_share, img_comment;
//    private LinearLayout mainLayout;
    TextView txttitle, txtCountView, txtdicription, txtShowMore, txtShowLess;
    Typeface typeface;
    Typeface typeface3;
//    private AdView mAdView;
//    FButton fButton_Donate ;
//    private RewardedVideoAd mRewardedVideoAd;
//    RotatingTextWrapper rotatingTextWrapper;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_comment,container,false);
        typeface= setFont(getActivity(),typeface);
        typeface3 = setFont_Logo(getActivity(), typeface3);
        InitView();
//        MobileAds.initialize(getActivity(), ID_ungDung);
//        mAdView = (AdView)view.findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

//        MobileAds.initialize(getActivity(),
//                "ca-app-pub-3940256099942544~3347511713");
        // Use an activity context to get the rewarded video instance.
//        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getActivity());
//        mRewardedVideoAd.loadAd(ID_donViQuangNative,
//                new AdRequest.Builder().build());
////        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
////                new AdRequest.Builder().build());
//        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
//            @Override
//            public void onRewarded(RewardItem rewardItem) {
//                //Toast.makeText(getActivity(), "Ad triggered reward.", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onRewardedVideoAdLoaded() {
//               // Toast.makeText(getActivity(), "Ad loaded.", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onRewardedVideoAdOpened() {
//                //Toast.makeText(getActivity(), "Ad opened.", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onRewardedVideoStarted() {
//                //Toast.makeText(getActivity(), "Ad started.", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onRewardedVideoAdClosed() {
//                Toast.makeText(getActivity(), "Thank you Donate for App !", Toast.LENGTH_SHORT).show();
//                ReloadRewardedVideoAd();
//            }
//
//            @Override
//            public void onRewardedVideoAdLeftApplication() {
//                //Toast.makeText(getActivity(), "Ad left application.", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onRewardedVideoAdFailedToLoad(int i) {
//                //Toast.makeText(getActivity(), "Ad failed to load.", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onRewardedVideoCompleted() {
//
//            }
//        });



        linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear1.setVisibility(View.GONE);
                linear2.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeInDown)
                        .duration(500)
                        .playOn(linear2);


            }
        });

        showless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.FadeOutUp)
                        .duration(500)
                        .withListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                linear1.setVisibility(View.VISIBLE);
                                linear2.setVisibility(View.GONE);

                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        })
                        .playOn(linear2);

            }
        });

//        fButton_Donate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //loadRewardedVideoAd();
//                //startActivity(new Intent(getActivity(),DonateViewActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//            }
//        });

//        rotatingTextWrapper.setSize(35);
//
//        Rotatable rotatable = new Rotatable(Color.parseColor("#FFA036"), 1000, "Word", "Word01", "Word02");
//        rotatable.setSize(35);
//        rotatable.setAnimationDuration(500);
//
//        rotatingTextWrapper.setContent("Best of ?", rotatable);




//        Typeface typeface1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Light.ttf");
//        Typeface typeface2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Reckoner_Bold.ttf");
//
//        rotatingTextWrapper.setSize(35);
//        rotatingTextWrapper.setTypeface(typeface2);
//
//        Rotatable rotatable = new Rotatable(Color.parseColor("#FFA036"), 1000, "DJ", "EDM", "CNS");
//        rotatable.setSize(35);
//        rotatable.setTypeface(typeface1);
//        rotatable.setInterpolator(new AccelerateInterpolator());
//        rotatable.setAnimationDuration(500);
//
//        Rotatable rotatable2 = new Rotatable(Color.parseColor("#123456"), 1000, "Music Mix", "Dance", "Bass Boos");
//        rotatable2.setSize(25);
//        rotatable2.setTypeface(typeface1);
//        rotatable2.setInterpolator(new DecelerateInterpolator());
//        rotatable2.setAnimationDuration(500);
//
//        rotatingTextWrapper.setContent("This is ? and ?", rotatable, rotatable2);


        return view;
    }
//    private void ReloadRewardedVideoAd(){
//        mRewardedVideoAd.destroy(getContext());
//        mRewardedVideoAd.loadAd(ID_donViQuangNative,
//                new AdRequest.Builder().build());
//    }
//    private void loadRewardedVideoAd() {
//
//        if (mRewardedVideoAd.isLoaded()) {
//            mRewardedVideoAd.show();
//        }
//    }
    private void InitView(){

        txttitle = view.findViewById(R.id.txtTenVideo);
        txtCountView = view.findViewById(R.id.txtLuotXem);
        txtdicription = view.findViewById(R.id.txtdicription);
//        fButton_Donate = view.findViewById(R.id.FButtonDonateView);
        linear1 = view.findViewById(R.id.linear1);
        showless = view.findViewById(R.id.showless);
        linear2 = view.findViewById(R.id.linear2);
//
////        avi = view.findViewById(R.id.avi);
////        avi1 = view.findViewById(R.id.avi1);
////        avi2 = view.findViewById(R.id.avi2);
        txtShowMore = view.findViewById(R.id.show1);
        txtShowLess = view.findViewById(R.id.show2);
//        //rotatingTextWrapper = view.findViewById(R.id.custom_switcher);
//

        txtShowMore.setTypeface(typeface);
        txtShowLess.setTypeface(typeface);
        txtdicription.setTypeface(typeface);
        txtCountView.setTypeface(typeface);
        txttitle.setTypeface(typeface3);





    }

}

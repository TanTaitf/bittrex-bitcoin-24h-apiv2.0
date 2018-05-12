package Custom;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Nguyen Sang on 09/17/2017.
 */

public class AnimationUtils_Cus {
    public static void animate(RecyclerView.ViewHolder holder, Boolean goesDown){
        ObjectAnimator animatorTranY=ObjectAnimator.ofFloat(holder.itemView,"translationX",goesDown==true?500:-500,0);
        animatorTranY.setDuration(1000);
        animatorTranY.start();


    }
//    public static  void animeteHorizontal(RecyclerView.ViewHolder holder,Boolean goesDown){
//        AnimatorSet animatorSet= new AnimatorSet();
//        ObjectAnimator animatorTranslateY=ObjectAnimator.ofFloat(holder.itemView,"translationY",goesDown==true?500:-500,0);
//        animatorTranslateY.setDuration(2000);
//        animatorSet.playTogether(animatorTranslateY);
//        animatorSet.start();
//       /* AnimatorSet animatorSet= new AnimatorSet();
//        ObjectAnimator animatorTransX=ObjectAnimator.ofFloat(holder.itemView,"translationX",goesDown==true?100:-100,0);
//        ObjectAnimator animatorTransY=ObjectAnimator.ofFloat(holder.itemView,"translationY",-50,50,-30,30,-20,20,-10,10,-5,5,0);
//        animatorTransY.setDuration(1000);
//        animatorTransX.setDuration(1000);
//        animatorSet.playTogether(animatorTransX,animatorTransY);
//        animatorSet.start();*/
//    }
//    public static void slideAnimation(RecyclerView.ViewHolder holder, boolean animate) {
//        ObjectAnimator animatorY = ObjectAnimator.ofFloat(holder.itemView, "translationY", animate ? 40 : -40, 1f);
//        ObjectAnimator animatorX = ObjectAnimator.ofFloat(holder.itemView, "translationX", animate ? 40 : -40, 1f);
//        AnimatorSet set = new AnimatorSet();
//        set.playTogether(animatorX , animatorY);
//        set.setDuration(1000);
//        set.start();
//
//    }
}

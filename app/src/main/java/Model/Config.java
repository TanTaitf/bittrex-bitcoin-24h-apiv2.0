package Model;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;

/**
 * Created by Windows 8.1 Ultimate on 10/03/2018.
 */

public class Config {

    public static String ID_donViQuangNative = "ca-app-pub-6352050986417104/2430514342";
    public static String ID_donViQuangCaoVideo = "ca-app-pub-6352050986417104/9059749290";
    public static String ID_ungDung = "ca-app-pub-6352050986417104~9357254466";
    public static String ID_donViQuangCao = "ca-app-pub-6352050986417104/5669290894";
    public static Typeface setFont(Context context,Typeface typeface ){
        typeface=Typeface.createFromAsset(context.getAssets(),"fonts/Lato-Regular.ttf");
        return typeface;
    }
    public static Typeface setFont_Logo(Context context,Typeface typeface ){
        typeface= Typeface.createFromAsset(context.getAssets(),"fonts/Capture_it.ttf");
        return typeface;
    }

    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}

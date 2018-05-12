package fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.media.tf.appbitcoinbittrex.R;

import Custom.MyTextView;


public class UserLogin extends Fragment {
    MyTextView create;
    TextView zoo;
    private View view;
    FragmentTransaction fragmentTransaction;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.login,container,false);
        anhXa();
        final FragmentManager fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        Typeface custom_fonts = Typeface.createFromAsset(getContext().getAssets(), "fonts/Capture_it.ttf");
        zoo = (TextView)view.findViewById(R.id.zoo);
        zoo.setTypeface(custom_fonts);
        create = (MyTextView)view.findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSignUp signUp = new UserSignUp();
                //fragmentTransaction.add(R.id.frament,signUp);
                fragmentTransaction.replace(R.id.frament,signUp);

                fragmentTransaction.commit();

            }
        });

        return view;
    }



    private void anhXa() {

    }
}

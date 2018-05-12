package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.media.tf.appbitcoinbittrex.R;

public class FragmentUser extends android.support.v4.app.Fragment{

    private View view;
    FragmentTransaction fragmentTransaction;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_layout_user,container,false);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        fragment = new UserLogin();
        //fragmentTransaction.add(R.id.frament,fragment);
        fragmentTransaction.replace(R.id.frament,fragment);
        fragmentTransaction.commit();

        return view;
    }
}

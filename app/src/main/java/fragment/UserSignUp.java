package fragment;

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
import cn.refactor.lib.colordialog.PromptDialog;


/**
 * Created by tfmedia on 09/13/2017.
 */

public class UserSignUp extends Fragment {

    TextView zoo;
    MyTextView create;
    private View view;
    FragmentTransaction fragmentTransaction;
    MyTextView signin1, login;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.signup,container,false);
        anhXa();
        signin1 = (MyTextView)view.findViewById(R.id.signin1);
        login = (MyTextView)view.findViewById(R.id.login);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        // check user signup to Server
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PromptDialog(getActivity())
                        .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                        .setAnimationEnable(true)
                        .setTitleText("SIGNUP SUCCESS")
                        .setContentText("Your wallet system is being upgraded\n" +
                                "Please come back later !")
                        .setPositiveListener(getActivity().getString(R.string.ok), new PromptDialog.OnPositiveListener() {
                            @Override
                            public void onClick(PromptDialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
        signin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLogin login = new UserLogin();
                fragmentTransaction.replace(R.id.frament,login);

                fragmentTransaction.commit();
            }
        });
        return view;
    }



    private void anhXa() {

    }
}

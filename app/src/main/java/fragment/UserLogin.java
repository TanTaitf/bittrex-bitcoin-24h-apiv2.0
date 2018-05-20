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
import android.widget.EditText;
import android.widget.TextView;

import com.media.tf.appbitcoinbittrex.R;

import Custom.MyTextView;
import cn.refactor.lib.colordialog.PromptDialog;


public class UserLogin extends Fragment {
    MyTextView create;
    TextView zoo, login;
    EditText edt_user,edt_pass;
    private View view;
    FragmentTransaction fragmentTransaction;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.login,container,false);
        anhXa();
        final FragmentManager fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        Typeface custom_fonts = Typeface.createFromAsset(getContext().getAssets(), "fonts/Capture_it.ttf");

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
        // check user login to Server
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAccount();

            }
        });
        return view;
    }
    void checkAccount(){
        if(edt_user.length() > 0 && edt_pass.length() > 0)
        {
            new PromptDialog(getActivity())
                    .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                    .setAnimationEnable(true)
                    .setTitleText("LOGIN SUCCESS")
                    .setContentText("Your wallet system is being upgraded\n" +
                            "Please come back later !")
                    .setPositiveListener(getActivity().getString(R.string.ok), new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();
        }
        else {
            new PromptDialog(getActivity())
                    .setDialogType(PromptDialog.DIALOG_TYPE_WRONG)
                    .setAnimationEnable(true)
                    .setTitleText("INPUT USER")
                    .setContentText("Please input User name and Password before login to Server !")
                    .setPositiveListener(getActivity().getString(R.string.ok), new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }


    private void anhXa() {
        zoo = (TextView)view.findViewById(R.id.zoo);
        login = view.findViewById(R.id.login);
        edt_user = (EditText) view.findViewById(R.id.edt_user);
        edt_pass = view.findViewById(R.id.edt_pass);
    }
}

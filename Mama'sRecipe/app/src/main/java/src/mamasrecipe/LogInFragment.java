
package src.mamasrecipe;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import app.App;
import model.po.UserPO;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LogInFragment extends Fragment {

    private EditText accountLogInEditText;
    private EditText pwLogInEditText;
    private Button logInSubmitButton;

    private String userName;
    private String passWord;
    public LogInFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        view.getBackground().setAlpha(100);
        getReference(view);

        logInSubmitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getInputs();
                UserPO upo = new UserPO();
                upo.setUserName(userName);
                upo.setUserPass(passWord);

                App.getRestClient().getUserService().login(upo, new Callback<UserPO>(){
                    @Override
                    public void success(UserPO userPO, Response response) {
                        if(userPO.getUserName().equals("") || userPO.getUserPass().equals("")){
                            Toast.makeText(getActivity().getBaseContext(),"Null UserName or PassWord" + "Please Check Your UserName and PassWord",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        Intent submit = new Intent(getActivity(), MainActivity.class);
                        submit.putExtra("userID", String.valueOf(userPO.getUserID()));
                        submit.putExtra("userName", userName);
                        startActivity(submit);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getActivity().getBaseContext(),"Wrong UserName or PassWord" + "Please Check Your UserName and PassWord",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return view;

    }
    public void getReference(View view){
        accountLogInEditText = (EditText) view.findViewById(R.id.accountLogInEditText);
        pwLogInEditText = (EditText) view.findViewById(R.id.pwLogInEditText);
        logInSubmitButton = (Button) view.findViewById(R.id.logInSubmitButton);
    }
    public void getInputs(){
        userName = accountLogInEditText.getText().toString();
        passWord = pwLogInEditText.getText().toString();
    }
}

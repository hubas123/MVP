package nz.co.udenbrothers.clockwork.activities.common;

import android.os.Bundle;

import nz.co.udenbrothers.clockwork.R;
import nz.co.udenbrothers.clockwork.customWigets.TextInput;
import nz.co.udenbrothers.clockwork.network.Api;
import nz.co.udenbrothers.clockwork.serverObjects.LoginInfo;
import nz.co.udenbrothers.clockwork.serverObjects.SigninInfo;
import nz.co.udenbrothers.clockwork.temps.Profile;
import nz.co.udenbrothers.clockwork.tools.Match;

public class SignInActivity extends MainActivity{

    private TextInput Email, Epass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Email = findViewById(R.id.editMail);
        Epass = findViewById(R.id.editPass);

        clicked(R.id.signInButton,()->{
            String pass = Epass.getTxt();
            String mail = Email.getTxt();

            if (!Match.mail(mail)) {
                Email.error(getString(R.string.error_invalid_email));
                return;
            }

            if (pass.length() < 6){
                Epass.error(getString(R.string.password_too_short));
                return;
            }
            SigninInfo profile = new SigninInfo("N","N",mail,pass);

            waitingDlg.show(R.string.please_wait);
            Api.signin(this, profile, new Api.OnApiResultListener<LoginInfo>() {
                @Override
                public void onFailed(int statusCode, String errorMsg) {
                    waitingDlg.hideDialog();
                    if (statusCode==404){
                        Email.requestFocus();
                        Email.setError(getString(R.string.error_email_not_found));
                    }
                    else if (statusCode==401){
                        Epass.requestFocus();
                        Epass.setError(getString(R.string.error_invalid_password));
                    }
                    else
                        alert(errorMsg);
                }

                @Override
                public void onSuccess(LoginInfo loginInfo) {
                    waitingDlg.hideDialog();
                    if (loginInfo!=null) {
                        Profile.firstName(loginInfo.firstName);
                        Profile.lastName(loginInfo.lastName);
                        Profile.role(loginInfo.userRoleId + 1);
                        Profile.token(loginInfo.apiToken);
                        Profile.userID(loginInfo.userId);

                        toActivity(LoadingActivity.class);
                    }
                    else{
                        onFailed(-1,getString(R.string.error_occurred));
                    }
                }

                @Override
                public void onNoConnection() {
                    waitingDlg.hideDialog();
                    alert(getString(R.string.no_internet_connection));
                }
            });

            Profile.email(mail);
        });

        clicked(R.id.forgotPassTxt,() -> toActivity(ForgotPassActivity.class));
        clicked(R.id.signUpTxt,() -> toActivity(SignUpActivity.class));
    }
}

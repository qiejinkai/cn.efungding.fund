package context;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by qiejinkai on 16/4/3.
 */
public class FundAppliaction extends Application{

    private String phone ;

    private String password;

    private String token;

    private boolean isFirstIn = true;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isFirstIn() {
        return isFirstIn;
    }

    public void setIsFirstIn(boolean isFirstIn) {
        this.isFirstIn = isFirstIn;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences sp = getSharedPreferences("baseinfo", Activity.MODE_PRIVATE);

        this.phone = sp.getString("phone","13051701098");

        this.password = sp.getString("password","123456");

        this.isFirstIn = sp.getBoolean("isFirstIn",true);
    }
}

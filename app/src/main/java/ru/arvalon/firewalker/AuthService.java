package ru.arvalon.firewalker;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static ru.arvalon.firewalker.MainActivity.TAG;

/** Created by arvalon on 07.02.2018 */

public class AuthService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, getClass().getSimpleName()+ " onTokenRefresh: " + refreshedToken);
    }
}

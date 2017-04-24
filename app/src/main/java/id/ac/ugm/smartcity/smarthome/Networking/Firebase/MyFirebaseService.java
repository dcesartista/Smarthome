package id.ac.ugm.smartcity.smarthome.Networking.Firebase;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseService.class.getSimpleName();


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

    }

    private void sendNotification(Map<String,String> messages) {

    }
}

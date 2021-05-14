package mx.unam.unica.pythonnotificaction;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {
    private static final String TAG = "PUSH_Android";

    /*public void onTokenRefresh() {
        // Get updated InstanceID token.
       // String refreshedToken = FirebaseInstanceId.getInstance().getToken();
       // Log.d(TAG, "Refreshed token: " + refreshedToken);
        FirebaseInstallations.getInstance().get

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

    }
*/

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        String deviceToken=s;

        Log.d(TAG,"Token del dispositivo: " +deviceToken);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
               prensentarNotificacion(remoteMessage);
            } else {
                // Handle message within 10 seconds
             //   handleNow();
            }

        }

        // Check if message contains a notification payload.

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void prensentarNotificacion(RemoteMessage remoteMessage) {

        Intent intent= new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Uri  defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        RemoteMessage.Notification notification= remoteMessage.getNotification();

        Notification.Builder notificationBuilder= new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_notifica)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);


        if(notificationManager != null)
        {
                notificationManager.notify(TAG,0,notificationBuilder.build());
        }


/*

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Cuerpo de mensaje de la notificación: " + remoteMessage.getNotification().getBody());
        }
*/

    }


}

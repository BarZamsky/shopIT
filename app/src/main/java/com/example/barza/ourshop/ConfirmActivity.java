package com.example.barza.ourshop;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.barza.ourshop.Classes.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.channels.Channel;
import java.util.Random;

public class ConfirmActivity extends AppCompatActivity {
    private TextView number;
    private Button btnLogout;
    private Order order;
    FirebaseDatabase db;
    DatabaseReference orders;
    private String ans;
    private final String CHANNEL = "1";
    Context context;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        context = this;

        db = FirebaseDatabase.getInstance();
        orders = db.getReference("Orders");

        number = (TextView) findViewById(R.id.number);
        String orderNum = "" + (int) (new Random().nextInt(9999-1000)+1000);
        ans =  ""+ number.getText() + orderNum;
        number.setText(ans);
        order = new Order(orderNum, false);
        orders.child(order.get_orderNumber()).setValue(order);

        createChannel();
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                logout();
            }
        });


    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void logout() {
        addNotification(CHANNEL);

        Intent s = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(s);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel()
    {
        NotificationManager mNotificationManager=getSystemService(NotificationManager.class);
        String id = CHANNEL;
        CharSequence name = "channel 1";
        String description = "New order alerts in channel 1";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        mChannel.setDescription(description);

        mNotificationManager.createNotificationChannel(mChannel);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void addNotification(String channel)
    {
        Notification.Builder notificationBuilder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            notificationBuilder = new Notification.Builder(this, channel);
        } else {
            //noinspection deprecation
            notificationBuilder = new Notification.Builder(this);
        }

        Intent landingIntent = new Intent(this, LoginActivity.class);
        PendingIntent pendingLandingIntent = PendingIntent.getActivity(this, 0, landingIntent,0);

        Notification notification = notificationBuilder
                .setContentTitle("New Order alert!!")
                .setSmallIcon(R.drawable.ic_launcher_round)
                .setContentIntent(pendingLandingIntent)
                .setContentText(ans+ " is waiting for approve").build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify((int) System.currentTimeMillis(), notification);

    }


}

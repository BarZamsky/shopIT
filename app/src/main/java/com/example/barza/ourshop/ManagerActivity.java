package com.example.barza.ourshop;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.barza.ourshop.Classes.Manager;
import com.example.barza.ourshop.Classes.Order;
import com.example.barza.ourshop.Classes.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManagerActivity extends AppCompatActivity {
    private Button btnLogout, btnApproveOrders, btnAddProduct;
    private TextView helloText;
    FirebaseDatabase db;
    DatabaseReference orders;

    User manager ;
    List<Order> waitingOrders;

    private final String CHANNEL = "1";
    private int counter = 0;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        db = FirebaseDatabase.getInstance();
        orders = db.getReference("Orders");

        waitingOrders = new ArrayList<>();
        manager =  (User) getIntent().getSerializableExtra("User");
        createChannel();

        readData(new FirebaseCallback() {
            @Override
            public void onCallback(List<Order> list) {
                for (final Order order : list){
                    if (!order.is_isApproved()) {
                        counter ++;
                    }
                }
            }
        });

        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnApproveOrders = (Button) findViewById(R.id.btnApproveOrders);
        btnAddProduct = (Button) findViewById(R.id.btnAddProduct);


        helloText = (TextView) findViewById(R.id.helloText);
        helloText.setText("Hello "+ manager.getName()+ " "+manager.getLastName());
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        btnApproveOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveOrders();
            }
        });
    }

    public void logout() {
        Intent s = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(s);
    }

    public void addProduct() {
        Intent s = new Intent(getApplicationContext(), ProductActivity.class);
        s.putExtra("User", manager);
        startActivity(s);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void approveOrders() {
        addNotification(CHANNEL);
        Intent s = new Intent(getApplicationContext(), ApproveActivity.class);
        s.putExtra("User", manager);
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

        Notification notification = notificationBuilder
                .setSmallIcon(R.drawable.ic_launcher_round)
                .setContentTitle("New Orders")
                .setContentText("You have " + counter + " new order!").build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify((int) System.currentTimeMillis(), notification);
    }

    private void readData(final ManagerActivity.FirebaseCallback firebaseCallback) {
        orders.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datas: dataSnapshot.getChildren()) {
                    Order order = datas.getValue(Order.class);
                    waitingOrders.add(order);
                }
                firebaseCallback.onCallback(waitingOrders);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private interface FirebaseCallback {
        void onCallback(List<Order> list);
    }
}

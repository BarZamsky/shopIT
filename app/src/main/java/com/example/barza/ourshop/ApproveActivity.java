package com.example.barza.ourshop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barza.ourshop.Classes.Order;
import com.example.barza.ourshop.Classes.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ApproveActivity extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference orders;
    List<Order> waitingOrders;
    Context context;
    User user;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve);

        waitingOrders = new ArrayList<>();
        db = FirebaseDatabase.getInstance();

        user = (User) getIntent().getSerializableExtra("User");
        orders = db.getReference("Orders");
        final LinearLayout layout = (LinearLayout)findViewById(R.id.linearMain);
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        context = this;

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        readData(new FirebaseCallback() {
            @Override
            public void onCallback(List<Order> list) {

                Log.d("List::::", list.toString());
                    for (final Order order : list) {
                        if (!order.is_isApproved()) {
                            String oNumber = order.get_orderNumber();
                            LinearLayout la = new LinearLayout(context);
                            la.setVerticalGravity(LinearLayout.VERTICAL);
                            TextView tv = new TextView(context);
                            tv.setText(" " + oNumber + " ");
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26f);
                            la.addView(tv);
                            tv.setTextColor(Color.BLACK);
                            tv.setPadding(5, 10, 10, 10);
                            int buttonStyle = R.style.Widget_AppCompat_Button_Colored;
                            final Button btn1 = new Button(new ContextThemeWrapper(context, buttonStyle), null, buttonStyle);
                            btn1.setText("Approve");
                            btn1.setPadding(20, 10, 10, 10);
                            btn1.setLayoutParams(params);
                            la.addView(btn1);
                            layout.addView(la);

                            btn1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    approve(order);
                                }
                            });
                        }
                }
            }
        });
    }

    public void goBack() {
        Intent s = new Intent(getApplicationContext(), ManagerActivity.class);
       // User login = (User) getIntent().getSerializableExtra("User");
        s.putExtra("User", user);
        startActivity(s);
    }

    public void approve(Order o) {
        o.set_isApproved(true);
//        orders.child(o.get_orderNumber()).setValue(o);
        orders.child(o.get_orderNumber()).removeValue();
        Toast.makeText(ApproveActivity.this, "Order approved!", Toast.LENGTH_SHORT).show();
        Log.d("Order after:::", o.toString());
        Intent s = new Intent(getApplicationContext(), ApproveActivity.class);
        s.putExtra("User", user);
        startActivity(s);
    }

    private void readData(final FirebaseCallback firebaseCallback) {
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

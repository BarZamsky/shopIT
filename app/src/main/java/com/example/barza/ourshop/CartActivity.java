package com.example.barza.ourshop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.example.barza.ourshop.Classes.Product;
import com.example.barza.ourshop.Classes.User;

import java.io.File;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    Button btnCheckout;
    double _totalPrice = 0;
    String sizeA = "", sizeB= "";
    Context context;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        context = this;
        final LinearLayout layout = (LinearLayout)findViewById(R.id.linearMain);
        user = (User) getIntent().getSerializableExtra("User");
        String sizeAdidas = getIntent().getStringExtra("SizeAdidas");
        String sizeBoots = getIntent().getStringExtra("SizeBoots");
        if(sizeAdidas != null){
            sizeA = sizeAdidas;
            Log.d("Adidas:::", sizeAdidas);
        }
        if(sizeBoots != null) {
            sizeB = sizeBoots;
            Log.d("Adidas:::", sizeBoots);
        }
        Log.d("user:::", user.toString());


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        for (int i=0; i< user.getCart().size(); i++) {
            final int index = i;
            TextView tv2 = new TextView(this);
            String pName = user.getCart().get(i).get_name();
            double pPrice = user.getCart().get(i).get_price();
            LinearLayout la = new LinearLayout(this);
            la.setVerticalGravity(LinearLayout.VERTICAL);
            TextView tv = new TextView(this);
            tv.setText(" "+pName+" ");
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
            la.addView(tv);
            tv.setTextColor(Color.BLACK);
            tv.setPadding(5, 10,10,10);
            if (pName.equals("Heeled_Boots")) {
                tv2.setText(" "+sizeB+" ");
                tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
                la.addView(tv2);
                tv2.setTextColor(Color.BLACK);
                tv2.setPadding(5, 10,10,10);
            }
            if (pName.equals("adidas_gazelle")) {
                tv2.setText(" "+sizeA+" ");
                tv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
                la.addView(tv2);
                tv2.setTextColor(Color.BLACK);
                tv2.setPadding(5, 10,10,10);
            }
            TextView tv1 = new TextView(this);
            tv1.setText(" "+pPrice+"$");
            _totalPrice += pPrice;
            tv1.setTextColor(Color.BLACK);
            tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
            tv1.setPadding(5, 10,10,10);
            la.addView(tv1);

            int buttonStyle = R.style.Widget_AppCompat_Button_Colored;
            final Button btn1 = new Button(new ContextThemeWrapper(context, buttonStyle), null, buttonStyle);
            btn1.setText("Remove");
            btn1.setPadding(30, 10, 30, 10);
            btn1.setLayoutParams(params);
            la.addView(btn1);
            layout.addView(la);
            btn1.setLayoutParams(params);

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete(index);
                }
            });
        }


        TextView total = (TextView) findViewById(R.id.total);
        total.setText("Total: " + _totalPrice);

        btnCheckout = (Button) findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCheckout();
            }
        });
    }

    public void delete(int index) {
        user.getCart().remove(index);
        Intent s = new Intent(getApplicationContext(), CartActivity.class);
        s.putExtra("User", user);
        startActivity(s);
    }

    public void goToCheckout() {
        Intent s = new Intent(getApplicationContext(), CheckoutActivity.class);
        User login = (User) getIntent().getSerializableExtra("User");
        s.putExtra("User", login);
        s.putExtra("Total", _totalPrice);
        startActivity(s);
    }
}

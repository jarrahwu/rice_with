package com.jy.ricewith;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.stkj.xtools.Bind;
import com.stkj.xtools.Binder;
import com.stkj.xtools.ClickHandler;

public class ActivityMain extends AppCompatActivity {

    @Bind(id = R.id.btn)
    private Button btn;

    @Bind(id = R.id.btn1)
    private Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.widget.Toolbar tb = ToolbarBuilder.build(this, R.layout.toolbar);
        tb.findViewById(R.id.right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Binder.inject(this, this);

        Binder.setClickHandler(this, this, new MyClickHandler());
    }


    private class MyClickHandler extends ClickHandler {
        @Override
        protected void onClickImpl(View v, int id) {
           startActivity(new Intent(getBaseContext(), ActivityWaterFall.class));
        }
    }
}

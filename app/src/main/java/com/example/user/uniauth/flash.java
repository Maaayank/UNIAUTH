package com.example.user.uniauth;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class flash extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    booleans bool ;
    String[] condn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        try {
            bool = new booleans();
            condn = bool.getCond(flash.this);
        }catch(Exception e){
            Toast.makeText(flash.this,"Error" + e,Toast.LENGTH_SHORT).show();
        }

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                try{
                    if(condn!= null && condn[0].equals("true"))
                    {
                        if (condn[1].equals("true"))
                        {Intent mainIntent = new Intent(flash.this,Authentication.class);
                        startActivity(mainIntent);}
                        else {
                            Intent mainIntent = new Intent(flash.this,Password_auth.class);
                            startActivity(mainIntent);
                        }
                    }
                    else{
                        Intent mainIntent = new Intent(flash.this,Register.class);
                        startActivity(mainIntent);
                    }
                }catch(Exception e){
                    Toast.makeText(flash.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
                finally {
                    flash.this.finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}

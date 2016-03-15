package com.androidtechies.gates16;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**<p>
 * Created by Angad on 11/03/2016.
 * </p>
 */
public class SplashActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread T1=new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {   Thread.sleep(3000);
                }
                catch (InterruptedException e)
                {   e.printStackTrace();
                }
                finally
                {   startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }
            }
        });
        T1.start();
    }
}

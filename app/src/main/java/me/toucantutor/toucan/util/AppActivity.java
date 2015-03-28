package me.toucantutor.toucan.util;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by osama on 3/27/15.
 */
public class AppActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Globals.appLoad(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Globals.appSave();
    }

    @Override
    public void finish() {
        super.finish();
        Globals.appSave();
    }
}

package id.ac.ugm.smartcity.smarthome;

/**
 * Created by dito on 09/02/17.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

public class BaseActivity  extends AppCompatActivity{
    Deps deps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File cacheFile = new File(getCacheDir(), "responses");
        deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile)).build();

    }

    public Deps getDeps() {
        return deps;
    }
}
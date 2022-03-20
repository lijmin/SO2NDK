package com.lijmin.other;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.lijmin.other.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'other' library on application startup.
    static {
        System.loadLibrary("other");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
        TextView tv = binding.sampleText;
        tv.setText(stringFromJNI()+new LinkOther().add(1,2));
    }

    /**
     * A native method that is implemented by the 'other' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
package by.it.trudnitski.retrofittest.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import by.it.trudnitski.retrofittest.R;
import by.it.trudnitski.retrofittest.util.CustomCircleView;

public class ShowCustomViewActivity extends AppCompatActivity {
    private CustomCircleView customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_custom_view);
        customView = findViewById(R.id.custom_view);
        customView.setClickable(true);
    }
}

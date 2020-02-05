package by.it.trudnitski.retrofittest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import by.it.trudnitski.retrofittest.R;
import by.it.trudnitski.retrofittest.util.CustomCircleView;

public class ShowCustomViewActivity extends AppCompatActivity {

    CustomCircleView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_custom_view);
        view = findViewById(R.id.custom_view);

    }

}

package by.it.trudnitski.retrofittest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import by.it.trudnitski.retrofittest.R;
import retrofit2.http.Url;

public class NewsDetailActivity extends AppCompatActivity {
    private String title;
    private String sourceName;
    private String description;
    private String imageUrl;
    private TextView titleView;
    private TextView sourceNameView;
    private TextView descriptioView;
    private TextView glideText;
    private TextView picassoText;
    private ImageView glideView;
    private ImageView picassoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        titleView = findViewById(R.id.title_detail);
        sourceNameView = findViewById(R.id.source_name_detail);
        descriptioView = findViewById(R.id.description_detail);
        glideText = findViewById(R.id.glide_text);
        picassoText = findViewById(R.id.picasso_text);
        glideView = findViewById(R.id.glide_image);
        picassoView = findViewById(R.id.picasso_image);

        Intent intent = getIntent();
        title = intent.getExtras().getString("title");
        sourceName = intent.getExtras().getString("source:name");
        description = intent.getExtras().getString("description");
        imageUrl = intent.getExtras().getString("imageUrl");

        titleView.setText(title);
        sourceNameView.setText(sourceName);
        descriptioView.setText(description);
        Picasso.get().load(imageUrl).into(picassoView);
        Glide.with(this).load(imageUrl).into(glideView);
    }
}

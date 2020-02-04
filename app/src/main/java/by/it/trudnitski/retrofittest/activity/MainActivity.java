package by.it.trudnitski.retrofittest.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import by.it.trudnitski.retrofittest.R;
import by.it.trudnitski.retrofittest.api.ApiClient;
import by.it.trudnitski.retrofittest.api.ApiInterface;
import by.it.trudnitski.retrofittest.model.Article;
import by.it.trudnitski.retrofittest.model.News;
import by.it.trudnitski.retrofittest.util.NewsAdapter;
import by.it.trudnitski.retrofittest.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NewsAdapter.OnNewsListener {
    private static final String APY_KEY = "cdb1f31d30b140e0a5aecfd9ec58ec47";
    private static final String DIALOG_REFRESH_CONTENT = " Content refreshing now ";
    private static final String DIALOG_ERROR_MESSAGE = " Something wrong!!! ";
    private static final String TITLE = "title";
    private static final String IMAGE_URL = "imageUrl";
    private static final String PUBLISHED_AT = "publishedAt";
    private static final String SOURCE_NAME = "source:name";
    private static final String DESCRIPTION = "description";
    private String spinnerChoose;
    private RecyclerView recyclerView;
    private List<Article> articles;
    private NewsAdapter adapter;
    private Toolbar toolbar;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinner);
        toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(android.R.drawable.ic_popup_sync);
        pushToRefresh();
        spinnerListener();
    }

    public void pushToRefresh(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Connection(spinnerChoose).execute();
            }
        });
    }
    public void spinnerListener(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerChoose = spinner.getSelectedItem().toString();
                toolbar.setTitle(spinnerChoose);
                new Connection(spinnerChoose).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    public class Connection extends AsyncTask<Void, Void, Void>{
        private String source;
        private ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        Connection(String string) {
            source = string;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setTitle(DIALOG_REFRESH_CONTENT);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<News> call = apiInterface.getNews(source, Utils.getDate(), PUBLISHED_AT, APY_KEY);
            call.enqueue(new Callback<News>() {

                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    recyclerView = findViewById(R.id.recycle_view);
                    LinearLayoutManager manger = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(manger);
                    articles = response.body().getArticles();
                    adapter = new NewsAdapter(articles, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    if (response.isSuccessful()) {
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {
                    dialog.setTitle(DIALOG_ERROR_MESSAGE);
                }
            });
            return null;
        }

    }
    @Override
    public void OnNewsClick(int position) {
        Intent intent = new Intent(this, NewsDetailActivity.class);
        intent.putExtra(TITLE, articles.get(position).getTitle());
        intent.putExtra(SOURCE_NAME, articles.get(position).getSource().getName());
        intent.putExtra(DESCRIPTION, articles.get(position).getDescription());
        intent.putExtra(IMAGE_URL, articles.get(position).getUrlToImage());
        startActivity(intent);
    }
}
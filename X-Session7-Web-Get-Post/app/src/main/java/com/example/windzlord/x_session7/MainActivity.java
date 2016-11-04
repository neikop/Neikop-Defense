package com.example.windzlord.x_session7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.example.windzlord.x_session7.models.PostJSONModel;
import com.example.windzlord.x_session7.recycler_view.Post;
import com.example.windzlord.x_session7.recycler_view.PostAdapter;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "System.out.log";
    private String URL = "https://a5-tumblelog.herokuapp.com/";
    String responseBodyString;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private PostAdapter postAdapter = new PostAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        if (true) getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout_main, new LoginFragment())
                .commit();
        else SetupUI();

//        sendPOSTRequest("form", "This is title", "And this is content");
//        sendPOSTRequest("json", "This is title", "And this is content");
//        sendGETRequest();
    }

    private void SetupUI() {
        int span = 3;
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        GridLayoutManager layoutManager = new GridLayoutManager(this, span);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(span,
                StaggeredGridLayoutManager.VERTICAL
        );
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(postAdapter);
        sendGETRequest();
    }

    private void sendPOSTRequest(String type, String title, String content) {

        // by form
        FormBody formBody = new FormBody.Builder()
                .add("title", title)
                .add("content", content)
                .build();

        // by json
        PostJSONModel newPost = new PostJSONModel(title, content);
        String jsonBody = new Gson().toJson(newPost);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonBody);

        Request request = new Request.Builder()
                .url(URL)
                .post("form".equals(type) ? formBody : requestBody)
                .build();

        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseBodyString = response.body().string();
                System.out.println(responseBodyString);
            }
        });
    }

    private void sendGETRequest() {

        Request request = new Request.Builder()
                .url(URL)
                .build();

        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) return;

                responseBodyString = response.body().string();
                System.out.println("GET\n" + responseBodyString);

                // get data
                PostJSONModel[] postJSONModels =
                        new Gson().fromJson(responseBodyString, PostJSONModel[].class);
                Post.LIST.clear();
                for (PostJSONModel postJSONModel : postJSONModels) {
                    Post.LIST.add(new Post(postJSONModel));
                }

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        postAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Subscribe
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}

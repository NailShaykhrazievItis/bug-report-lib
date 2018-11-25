package com.itis.android.githubapp.ui.sample;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.itis.android.githubapp.api.service.RepoService;
import com.itis.android.githubapp.model.Repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestActivity extends AppCompatActivity {

    String API_BASE_URL = "https://api.github.com/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String API_BASE_URL = "https://api.github.com/";

        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RepoService repoService = retrofit.create(RepoService.class);

        repoService.getUserReposCall(1, "", "", "")
                .enqueue(new Callback<List<Repository>>() {
                    @Override
                    public void onResponse(Call<List<Repository>> call,
                                           Response<List<Repository>> response) {

                    }

                    @Override
                    public void onFailure(Call<List<Repository>> call,
                                          Throwable t) {

                    }
                });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            File myIconFile = new File(getExternalFilesDir(null) +
                    File.separator + "My Icon.png");
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(myIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Log.d("TAG", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}

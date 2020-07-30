package com.example.webview;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    WebView myWebView;
    ProgressBar myProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myWebView = findViewById(R.id.myWebView);
        myProgressBar = findViewById(R.id.myProgressBar);

        myWebView.loadUrl("https://google.com");
        myWebView.getSettings().setJavaScriptEnabled(true);


        myWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

                setTitle(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                myProgressBar.setProgress(newProgress);

                super.onProgressChanged(view, newProgress);
            }
        });


        myWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                myProgressBar.setVisibility(View.VISIBLE);
                //myProgressBar.setVisibility(View.GONE); //progress bar space hides after loading

                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                myProgressBar.setVisibility(View.INVISIBLE);

                super.onPageFinished(view, url);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuBack) {
            if (myWebView.canGoBack()) {
                myWebView.goBack();

            } else {

                showExitDialogue();


            }

        } else if (item.getItemId() == R.id.menuForward) {
            if (myWebView.canGoForward()) {
                myWebView.goForward();
            } else {
                Toast.makeText(this, "Can't go further", Toast.LENGTH_SHORT).show();
            }
        } else if (item.getItemId() == R.id.menuHome) {
            myWebView.loadUrl("https://google.com");

        } else if (item.getItemId() == R.id.menuRefresh) {
            myWebView.reload();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack();
        } else {

            showExitDialogue();

        }
    }


    private void showExitDialogue() {

        AlertDialog.Builder myExitDialogue = new AlertDialog.Builder(MainActivity.this);
        myExitDialogue.setTitle("Exiting app");
        myExitDialogue.setMessage("Do you want to Exit?");
        myExitDialogue.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        myExitDialogue.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        myExitDialogue.create();

        myExitDialogue.show();
    }

}
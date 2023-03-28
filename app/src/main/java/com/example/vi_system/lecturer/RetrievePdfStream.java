package com.example.vi_system.lecturer;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RetrievePdfStream extends AsyncTask<String, Void, InputStream> {
    private PDFView pdfView;
    private ProgressDialog dialog;

    public RetrievePdfStream(PDFView pdfView, ProgressDialog dialog) {
        this.pdfView = pdfView;
        this.dialog = dialog;
    }

    @Override
    protected InputStream doInBackground(String... strings) {
        InputStream inputStream = null;
        try {

            // adding url
            URL url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // if url connection response code is 200 means ok the execute
            if (urlConnection.getResponseCode() == 200) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
            }
        }
        // if error return null
        catch (IOException e) {
            return null;
        }
        return inputStream;
    }

    @Override
    // Here load the pdf and dismiss the dialog box
    protected void onPostExecute(InputStream inputStream) {
        pdfView.fromStream(inputStream).load();
        dialog.dismiss();
    }




}

package com.rhgdf.craftlauncher.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rhgdf.craftlauncher.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EulaFragment extends Fragment {

    public interface EulaCallback {
        void onEulaResult(boolean accepted);
    }

    private EulaCallback callback;

    public static EulaFragment newInstance(EulaCallback callback) {
        EulaFragment fragment = new EulaFragment();
        fragment.callback = callback;
        return fragment;
    }

    private TextView eulaTxt;
    private Button btnAgree, btnDecline;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eula, container, false);

        eulaTxt = view.findViewById(R.id.eulaTxt);
        btnAgree = view.findViewById(R.id.btnAgree);
        btnDecline = view.findViewById(R.id.btnDecline);

        loadEulaFromAssets(requireContext());

        btnAgree.setOnClickListener(v -> {
            if (callback != null) callback.onEulaResult(true);
        });

        btnDecline.setOnClickListener(v -> {
            if (callback != null) callback.onEulaResult(false);
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Ambil callback dari Activity jika diimplementasikan, sebagai fallback jika fragment direkreasi
        if (callback == null && context instanceof EulaCallback) {
            callback = (EulaCallback) context;
        }
    }

    private void loadEulaFromAssets(Context context) {
        try (InputStream is = context.getAssets().open("eula.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            
            // Konversi Markdown sederhana ke HTML untuk tampilan yang lebih baik
            String eulaText = builder.toString();
            String htmlText = convertMarkdownToHtml(eulaText);
            
            // Set text dengan HTML formatting
            Spanned spanned = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY);
            eulaTxt.setText(spanned);
            
        } catch (IOException e) {
            eulaTxt.setText("Gagal memuat EULA dari assets.\n\nError: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Konversi Markdown sederhana ke HTML untuk tampilan yang lebih baik
     */
    private String convertMarkdownToHtml(String markdown) {
        String html = markdown;
        
        // Konversi bold (**text** menjadi <b>text</b>)
        html = html.replaceAll("\\*\\*([^*]+)\\*\\*", "<b>$1</b>");
        
        // Konversi heading (### text menjadi <h3>text</h3>)
        html = html.replaceAll("###\\s+(.+)", "<h3>$1</h3>");
        html = html.replaceAll("##\\s+(.+)", "<h2>$1</h2>");
        html = html.replaceAll("#\\s+(.+)", "<h1>$1</h1>");
        
        // Konversi horizontal rule (--- menjadi <hr/>)
        html = html.replaceAll("---", "<hr/>");
        
        // Konversi bullet points (- text menjadi • text)
        html = html.replaceAll("\n-\\s+", "<br/>• ");
        
        // Konversi line breaks
        html = html.replaceAll("\n\n", "<br/><br/>");
        html = html.replaceAll("\n", "<br/>");
        
        return html;
    }
}

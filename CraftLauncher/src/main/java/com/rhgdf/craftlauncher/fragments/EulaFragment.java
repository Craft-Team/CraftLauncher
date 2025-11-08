package com.rhgdf.craftlauncher.fragments;

import android.content.Context;
import android.os.Bundle;
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

    private void loadEulaFromAssets(Context context) {
        try {
            InputStream is = context.getAssets().open("eula.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            reader.close();
            eulaTxt.setText(builder.toString());
        } catch (IOException e) {
            eulaTxt.setText("Gagal memuat EULA dari assets.");
            e.printStackTrace();
        }
    }
}

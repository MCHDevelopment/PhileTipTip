package com.mch.philetiptip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class MeldungDetailFragment extends Fragment {

    private static final String ARG_MELDUNG_ID = "meldung_id";
    private String meldungId;

    public static MeldungDetailFragment newInstance(String meldungId) {
        MeldungDetailFragment fragment = new MeldungDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MELDUNG_ID, meldungId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            meldungId = getArguments().getString(ARG_MELDUNG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meldung_detail, container, false);

        TextView textMeldung = view.findViewById(R.id.text_message);
        TextView textAdresse = view.findViewById(R.id.text_address);
        ImageView imageFoto = view.findViewById(R.id.image_photo);

        // Abgerundete PhileBeige-Hintergründe setzen
        textMeldung.setBackgroundResource(R.drawable.rounded_background);
        textAdresse.setBackgroundResource(R.drawable.rounded_background);
        imageFoto.setBackgroundResource(R.drawable.rounded_background);

        // Hier die Daten der Meldung abrufen und anzeigen (z.B. aus einer Datenbank)
        // textMeldung.setText(...);
        // textAdresse.setText(...);
        // imageFoto.setImageBitmap(...);

        return view;
    }
}

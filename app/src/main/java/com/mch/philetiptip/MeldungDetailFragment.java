package com.mch.philetiptip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.mch.philetiptip.Logic.Meldung;

public class MeldungDetailFragment extends Fragment {

    //private static final String ARG_MELDUNG_ID = "meldung_id";
    private Meldung meldung;

    public static MeldungDetailFragment newInstance(Meldung meldung) {
        MeldungDetailFragment fragment = new MeldungDetailFragment();
        fragment.setMeldung(meldung);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meldung_detail, container, false);

        TextView textMeldung = view.findViewById(R.id.text_message);
        TextView textAdresse = view.findViewById(R.id.text_address);
        ImageView imageFoto = view.findViewById(R.id.image_photo);

        textMeldung.setBackgroundResource(R.drawable.rounded_background);
        textAdresse.setBackgroundResource(R.drawable.rounded_background);
        imageFoto.setBackgroundResource(R.drawable.rounded_background);

        textMeldung.setText(meldung.getMeldungstext());
        textAdresse.setText(meldung.getMeldungAdresse().getAdressString());
        //TODO: Bild anzeigen
        // imageFoto.setImageBitmap(...);

        return view;
    }

    public void setMeldung(Meldung meldung) {
        this.meldung = meldung;
    }
}

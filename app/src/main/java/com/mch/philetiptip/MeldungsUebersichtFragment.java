package com.mch.philetiptip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.mch.philetiptip.Logic.Meldung;

import java.util.List;

public class MeldungsUebersichtFragment extends Fragment {

    private ListView meldungenListView;
    private List<Meldung> meldungenListe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meldungs_uebersicht, container, false);
        meldungenListView = view.findViewById(R.id.meldungen_list);
        // Hier müsstest du eine eigene Adapter-Klasse erstellen, um die Liste zu füllen

        Button hochButton = view.findViewById(R.id.button_up);
        Button runterButton = view.findViewById(R.id.button_down);

        // Hoch-Button scrollt nach oben
        hochButton.setOnClickListener(v -> {
            // Code zum Scrollen nach oben
        });

        // Runter-Button scrollt nach unten
        runterButton.setOnClickListener(v -> {
            // Code zum Scrollen nach unten
        });

        // Bei Klick auf eine Meldung zum DetailFragment wechseln
        meldungenListView.setOnItemClickListener((parent, view1, position, id) -> {
            String meldungId = "" + meldungenListe.get(position).getId();
            ((UebersichtActivity) getActivity()).zeigeDetailFragment(meldungId);
        });

        return view;
    }
}

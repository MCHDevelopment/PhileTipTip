package com.mch.philetiptip;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.mch.philetiptip.Logic.Meldung;
import com.mch.philetiptip.Logic.PhileTipTipMain;
import com.mch.philetiptip.Optic.MeldungenAdapter;

import java.util.List;

public class MeldungsUebersichtFragment extends Fragment {

    private ListView meldungenListView;
    private List<Meldung> meldungenListe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meldungs_uebersicht, container, false);
        meldungenListView = view.findViewById(R.id.meldungen_list);

        meldungenListe = PhileTipTipMain.getInstance().getMeldungsHolder().getMeldungen();

        Log.e("MeldungsUebersichtFragment", "Anzahl der Meldungen: " + meldungenListe.size());
        MeldungenAdapter adapter = new MeldungenAdapter(getContext(), meldungenListe);
        meldungenListView.setAdapter(adapter);

        // Bei Klick auf eine Meldung zum DetailFragment wechseln
        meldungenListView.setOnItemClickListener((parent, view1, position, id) -> {
            ((UebersichtActivity) getActivity()).zeigeDetailFragment(meldungenListe.get(position));
        });

        return view;
    }
}

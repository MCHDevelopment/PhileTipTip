package com.mch.philetiptip.Optic;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mch.philetiptip.Logic.Meldung;
import com.mch.philetiptip.R;

import java.util.List;

public class MeldungenAdapter extends BaseAdapter {

    private Context context;
    private List<Meldung> meldungenListe;
    private LayoutInflater inflater;

    public MeldungenAdapter(Context context, List<Meldung> meldungenListe) {
        this.context = context;
        this.meldungenListe = meldungenListe;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return meldungenListe.size();
    }

    @Override
    public Object getItem(int position) {
        return meldungenListe.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_meldung, parent, false);
        }

        Meldung meldung = meldungenListe.get(position);

        // Setze die Adresse und das Thumbnail
        TextView adresseTextView = convertView.findViewById(R.id.meldung_adresse);
        adresseTextView.setText(meldung.getMeldungAdresse().getAdressString());

        ImageView thumbnailImageView = convertView.findViewById(R.id.meldung_thumbnail);
        thumbnailImageView.setImageURI(meldung.getBildquelle().getLocalUri());

        return convertView;
    }
}

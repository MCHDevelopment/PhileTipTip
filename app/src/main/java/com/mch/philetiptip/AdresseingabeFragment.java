package com.mch.philetiptip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.Priority;
import com.mch.philetiptip.Logic.Data.Adresse;
import com.mch.philetiptip.Logic.Helper;
import com.mch.philetiptip.Logic.Meldung;
import com.mch.philetiptip.Logic.PhileTipTipMain;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Looper;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AdresseingabeFragment extends Fragment {

    private EditText editTextOrt;
    private EditText editTextPostleitzahl;
    private EditText editTextHausnummer;
    private EditText editTextStrasse;

    private FusedLocationProviderClient fusedLocationClient;
    private final ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                Boolean fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                Boolean coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);
                if (fineLocationGranted != null && fineLocationGranted) {
                    // Berechtigung wurde gewährt, Standort abrufen
                    getCurrentLocation();
                } else if (coarseLocationGranted != null && coarseLocationGranted) {
                    // Nur ungefähre Standorterlaubnis wurde gewährt, Standort abrufen
                    getCurrentLocation();
                } else {
                    Toast.makeText(requireContext(), "Standortberechtigung erforderlich", Toast.LENGTH_SHORT).show();
                }
            });
    private Meldung meldung;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate das Fragment-Layout
        View view = inflater.inflate(R.layout.fragment_adresseingabe, container, false);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        // Meldung aus dem Bundle holen
        if (getArguments() != null) {
            meldung = (Meldung) getArguments().getSerializable("meldung");
        }

        configureButtons(view);
        configureTextFields(view);

        return view;
    }

    void configureTextFields(View view) {
        configureStrasse(view);
        configureHausnummer(view);
        configurePostleitzahl(view);
        configureOrt(view);
    }

    private void configureOrt(View view) {
        editTextOrt = view.findViewById(R.id.city_input);
    }

    private void configurePostleitzahl(View view) {
        editTextPostleitzahl = view.findViewById(R.id.postal_code_input);
    }

    private void configureHausnummer(View view) {
        editTextHausnummer = view.findViewById(R.id.house_number_input);
    }

    private void configureStrasse(View view) {
        editTextStrasse = view.findViewById(R.id.street_input);
    }

    private void configureButtons(View view) {
        configureGPSButton(view);
    }

    private void configureGPSButton(View view) {
        ImageButton buttonGPS = view.findViewById(R.id.button_gps);
        buttonGPS.setOnClickListener(v -> requestLocationPermission());
    }

    public void fuelleAdresse(){
        Adresse adresse = new Adresse();
        adresse.setStrasse(editTextStrasse.getText().toString());
        //TODO: Validieren der Eingabe

        Integer tempHausnummer = Helper.parseIntOrNull(editTextHausnummer.getText().toString());
        if(tempHausnummer != null){
            adresse.setHausNummer(tempHausnummer);
        }

        Integer tempPostleitzahl = Helper.parseIntOrNull(editTextPostleitzahl.getText().toString());
        if(tempPostleitzahl != null){
            //TODO: Validieren der Eingabe - fuenfstellig
            adresse.setPostleitzahl(tempPostleitzahl);
        }

        adresse.setOrt(editTextOrt.getText().toString());

        meldung.setMeldungsAdresse(adresse);
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Wenn keine Berechtigung, die Anfrage abbrechen
            return;
        }

        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
                .setMinUpdateIntervalMillis(5000)
                .build();

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            fusedLocationClient.removeLocationUpdates(this);
            if (locationResult.getLocations().size() > 0) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    meldung.setLatitude(latitude);
                    meldung.setLongitude(longitude);

                    getAddressFromLocation(latitude, longitude);
                }
            }
        }
    };

    private void getAddressFromLocation(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String strasse = address.getThoroughfare();
                String hausnummer = address.getSubThoroughfare();
                String ort = address.getLocality();
                String plz = address.getPostalCode();

                editTextStrasse.setText(strasse);
                editTextHausnummer.setText(hausnummer);
                editTextOrt.setText(ort);
                editTextPostleitzahl.setText(plz);

            } else {
                Toast.makeText(requireContext(), "Keine Adresse gefunden", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Fehler bei der Standortbestimmung", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Berechtigung vorhanden
            getCurrentLocation();
        } else {
            // Berechtigung anfordern
            locationPermissionRequest.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
        }
    }
}
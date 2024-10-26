package com.mch.philetiptip;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.mch.philetiptip.Logic.Meldung;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

public class FotoFragment extends Fragment {

    private Meldung meldung;
    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<String> permissionLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialisiere den ActivityResultLauncher
        galleryLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null && data.getData() != null) {
                    Uri selectedImageUri = data.getData();
                    meldung.getBildquelle().setBildquelle(selectedImageUri);
                }
            }
        });

        // Berechtigungs-Launcher initialisieren
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                // Berechtigung wurde erteilt, Galerie öffnen
                openGallery();
            } else {
                // Berechtigung wurde verweigert, hier ggf. eine Nachricht anzeigen
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate das Fragment-Layout
        View view = inflater.inflate(R.layout.fragment_foto, container, false);

        // Meldung aus dem Bundle holen
        if (getArguments() != null) {
            meldung = (Meldung) getArguments().getSerializable("meldung");
        }

        configureButtons(view);

        return view;
    }

    private void configureButtons(View view){
        configureFotoButton(view);
        configureGalleryButton(view);
    }


    private void configureFotoButton(View view){
                /*
                ImageButton buttonFoto = view.findViewById(R.id.button_camera);

        buttonFoto.setOnClickListener(v -> {
                //TODO: Foto machen
        });
         */
    }

    private void configureGalleryButton(View view){
        ImageButton buttonGallerie = view.findViewById(R.id.button_gallery);
        buttonGallerie.setOnClickListener(v -> {
            checkPermissionAndOpenGallery();
        });
    }

    private void checkPermissionAndOpenGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13 (API 33) und höher - READ_MEDIA_IMAGES ist erforderlich
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
            }
        } else {
            // Für ältere Versionen READ_EXTERNAL_STORAGE anfragen
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(galleryIntent);
    }
}
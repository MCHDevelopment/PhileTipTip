package com.mch.philetiptip;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.mch.philetiptip.Logic.Meldung;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FotoFragment extends Fragment {

    private Meldung meldung;
    // Initialisiere den ActivityResultLauncher, final verhindert irrtuemliches ueberschreiben, direktes Initialisieren bei der Deklaration vermeidet NullPointerException
    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
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
    // Berechtigungs-Launcher initialisieren, final verhindert irrtuemliches ueberschreiben, direktes Initialisieren bei der Deklaration vermeidet NullPointerException
    private final ActivityResultLauncher<String> galleryPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            // Berechtigung wurde erteilt, Galerie öffnen
            openGallery();
        } else {
            // Berechtigung wurde verweigert, hier ggf. eine Nachricht anzeigen
        }
    });

    private Uri photoUri;

    // Camera Launcher initialisieren, final verhindert irrtuemliches ueberschreiben, direktes Initialisieren bei der Deklaration vermeidet NullPointerException
    private ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Hier kannst du das aufgenommene Bild weiterverarbeiten
                    meldung.getBildquelle().setBildquelle(photoUri);
                }
            }
    );
    // Berechtigungs-Launcher für Kamera initialisieren, final verhindert irrtuemliches ueberschreiben, direktes Initialisieren bei der Deklaration vermeidet NullPointerException
    private final ActivityResultLauncher<String> cameraPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    openCamera();
                } else {
                    Toast.makeText(requireContext(), "Kamerazugriff verweigert", Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        ImageButton buttonFoto = view.findViewById(R.id.button_camera);
        buttonFoto.setOnClickListener(v -> {
            checkCameraPermissionAndOpen();
        });
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
                galleryPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
            }
        } else {
            // Für ältere Versionen READ_EXTERNAL_STORAGE anfragen
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                galleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(galleryIntent);
    }

    private void checkCameraPermissionAndOpen() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            // Temporäre Datei für das Bild erstellen
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Fortfahren, wenn die Datei erfolgreich erstellt wurde
            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(requireContext(), "com.mch.philetiptip.fileprovider", photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                cameraLauncher.launch(cameraIntent);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Dateiname basierend auf aktuellem Datum und Zeit erstellen
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }
}
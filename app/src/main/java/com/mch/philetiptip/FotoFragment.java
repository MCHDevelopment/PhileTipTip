package com.mch.philetiptip;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.mch.philetiptip.Logic.Meldung;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FotoFragment extends Fragment {

    private Meldung meldung;
    private Uri photoUri;

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {
                        Uri selectedImageUri = data.getData();

                        // Prüfen, ob der URI lesbar ist oder ob wir über MediaStore darauf zugreifen müssen
                        Uri readableUri = getReadableUriFromGallery(selectedImageUri);
                        if (readableUri != null) {
                            meldung.getBildquelle().setBildquelle(readableUri);
                        } else {
                            Toast.makeText(requireContext(), "Bild konnte nicht geladen werden", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

    /**
     * Konvertiert die URI in eine lesbare URI, falls sie von einem Content Provider stammt.
     * Hierzu wird der InputStream genutzt und als MediaStore URI gespeichert.
     */
    private Uri getReadableUriFromGallery(Uri originalUri) {
        try {
            // Einen temporären InputStream aus der Quelle des Bildes erstellen
            InputStream inputStream = requireContext().getContentResolver().openInputStream(originalUri);
            if (inputStream == null) return null;

            // Zeitstempel für eindeutigen Dateinamen
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String imageFileName = "JPEG_" + timeStamp + ".jpg";

            // ContentValues für den MediaStore-Eintrag vorbereiten
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, imageFileName);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/PhileTipTip");

            // Bild über MediaStore in die Galerie schreiben
            Uri readableUri = requireContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (readableUri != null) {
                // InputStream in die neue Datei kopieren
                OutputStream outputStream = requireContext().getContentResolver().openOutputStream(readableUri);
                if (outputStream != null) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                    outputStream.close();
                }
            }
            inputStream.close();
            return readableUri;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private final ActivityResultLauncher<String> galleryPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    openGallery();
                } else {
                    Toast.makeText(requireContext(), "Zugriff auf Galerie verweigert", Toast.LENGTH_SHORT).show();
                }
            });

    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    meldung.getBildquelle().setBildquelle(photoUri);
                }
            });

    private final ActivityResultLauncher<String> cameraPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    openCamera();
                } else {
                    Toast.makeText(requireContext(), "Kamerazugriff verweigert", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foto, container, false);

        if (getArguments() != null) {
            meldung = (Meldung) getArguments().getSerializable("meldung");
        }

        configureButtons(view);

        return view;
    }

    private void configureButtons(View view) {
        ImageButton buttonFoto = view.findViewById(R.id.button_camera);
        buttonFoto.setOnClickListener(v -> checkCameraPermissionAndOpen());

        ImageButton buttonGallery = view.findViewById(R.id.button_gallery);
        buttonGallery.setOnClickListener(v -> checkPermissionAndOpenGallery());
    }

    private void checkPermissionAndOpenGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                galleryPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
            }
        } else {
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
        photoUri = createImageUri();
        if (photoUri != null) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            cameraLauncher.launch(cameraIntent);
        } else {
            Toast.makeText(requireContext(), "Fehler beim Erstellen des Speicherorts", Toast.LENGTH_SHORT).show();
        }
    }

    private Uri createImageUri() {
        ContentValues values = new ContentValues();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "JPEG_" + timeStamp + ".jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/PhileTipTip");

        return requireContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }
}

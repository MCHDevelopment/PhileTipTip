package com.mch.philetiptip.Logic.Data;

import android.net.Uri;

import com.mch.philetiptip.Logic.Helper;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

public class Bildquelle implements Serializable {
    private String localUriString;
    private String remoteUrlString;

    public boolean hasLocalUri() {
        return !localUriString.isEmpty();
    }

    public void setBildquelle(Uri localUri) {
        this.localUriString = localUri != null ? localUri.toString() : null;
    }

    public Uri getLocalUri() {
        return !Helper.isNullOrEmpty(localUriString) ? Uri.parse(localUriString) : null;
    }

    public boolean hasRemoteUrl() {
        return !Helper.isNullOrEmpty(remoteUrlString);
    }

    public void setBildquelle(URL remoteUrl) {
        this.remoteUrlString = remoteUrl != null ? remoteUrl.toString() : null;
    }

    public URL getRemoteUrl() {
        try {
            return remoteUrlString != null ? new URL(remoteUrlString) : null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void resetBildquelle() {
        remoteUrlString = null;
        localUriString = null;
    }
}

package com.mch.philetiptip.Logic.Data;

import android.net.Uri;

import java.io.Serializable;
import java.net.URL;

public class Bildquelle implements Serializable {
    private Uri localUri;
    private URL remoteUrl;

    public void setBildquelle(Uri localUri) {
        this.localUri = localUri;
    }

    public void setBildquelle(URL remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public boolean isLocal() {
        return localUri != null;
    }

    public Uri getLocalUri() {
        return localUri;
    }

    public URL getRemoteUrl() {
        return remoteUrl;
    }

    public void resetBildquelle() {
        localUri = null;
        remoteUrl = null;
    }
}

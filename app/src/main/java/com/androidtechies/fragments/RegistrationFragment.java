package com.androidtechies.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.androidtechies.gates16.R;

/**<p>
 * Created by Angad on 11/03/2016.
 * </p>
 */
public class RegistrationFragment extends Fragment
{   @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View V=inflater.inflate(R.layout.fragment_registration,container,false);
        return V;
    }
}

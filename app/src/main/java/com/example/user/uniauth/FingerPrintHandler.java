package com.example.user.uniauth;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

@RequiresApi(api = Build.VERSION_CODES.M)
class FingerPrintHandler extends  FingerprintManager.AuthenticationCallback {

    private Context fingerPrintCheck;
    public FingerPrintHandler(Authentication fingerPrintCheck) {
        this.fingerPrintCheck = fingerPrintCheck;
    }

    public void startAuthentication(FingerprintManager fingerprintManager , FingerprintManager.CryptoObject cryptoObject){
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(fingerPrintCheck, Manifest.permission.USE_FINGERPRINT)!= PackageManager.PERMISSION_GRANTED)
            return;
        fingerprintManager.authenticate(cryptoObject,cancellationSignal,0,this,null);
    }

    @Override
    public void onAuthenticationFailed(){
        super.onAuthenticationFailed();
        Toast.makeText(fingerPrintCheck,"Authentication failed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result){
        super.onAuthenticationSucceeded(result);
        fingerPrintCheck.startActivity(new Intent(fingerPrintCheck,MainActivity.class));
    }

}

package it.unirc.pwm.eureca.util;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import it.unirc.pwm.eureca.R;

public class PermissionUtil {

    private static void showExplanation(final Activity aThis, String title, String message, final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(aThis);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(aThis,permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private static void requestPermission(Activity athis,String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(athis,new String[]{permissionName}, permissionRequestCode);
    }

    public static boolean checkPermissionCamera(final Activity aThis) {
        int permissionCheck = ContextCompat.checkSelfPermission(aThis, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(aThis,Manifest.permission.CAMERA)) {
                showExplanation(aThis, aThis.getString(R.string.richiesta_permesso), aThis.getString(R.string.permesso_camera), Manifest.permission.CAMERA, Costanti.MY_PERMISSIONS_REQUEST_CAMERA);
            } else {
                requestPermission(aThis, Manifest.permission.CAMERA, Costanti.MY_PERMISSIONS_REQUEST_CAMERA);
            }
        } else {
            Toast.makeText(aThis, aThis.getString(R.string.permesso_ottenuto), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}

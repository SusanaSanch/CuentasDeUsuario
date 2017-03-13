package femxa.cam.edu.cuentasdeusuario;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private void obtenerCuentas()
    {
        AccountManager accountManager = (AccountManager) getSystemService(ACCOUNT_SERVICE);

        Account[] array_cuentas = accountManager.getAccounts();

        for (Account cuenta : array_cuentas)
        {
            Log.d("MENSAJE", "Tipo " + cuenta.type);
            Log.d("MENSAJE", "Cuenta " + cuenta.name);
        }
    }

    private void pedirPermisos()
    {
        int permiso = ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS);

        if(permiso == PackageManager.PERMISSION_GRANTED)
        {
            Log.d("MENSAJE", "Permiso YA concedido");
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.GET_ACCOUNTS}, 150);
    }

    @Override
    public void onRequestPermissionsResult
            (int requestCode,
             @NonNull String[] permissions,
             @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            Log.d("MENSAJE", "El usuario me da permiso");
            obtenerCuentas();
        }
        else
        {
            Log.d("MENSAJE", "El usuario NO me da permiso");
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Log.d("MENSAJE", "El usuario le ha dado al botón atrás");
        //finish();

        //Me voy a crear un mensaje de alerta, una vista que tiene dos opciones (sí y no)
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        //Lo tengo que personalizar
        alertDialog.setTitle("SALIR");
        alertDialog.setMessage("¿De verdad te quieres ir?");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("MENSAJE", "usuario confirma la salida");
                finish();
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("MENSAJE", "usuario NO confirma salida");
                dialog.cancel();
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "no sé", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("MENSAJE", "usuario no sabe");
                dialog.cancel();
            }
        });


        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pedirPermisos();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, 1, "SALIR");
        menu.add(Menu.NONE, 2, 2, "A2");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("MENSAJE", "ITEM " + item.getItemId());

        /*
        if (item.getItemId() == 1)
        {
            Log.d("MENSAJE", "El usuario quiere salir");
            finish();
        }
        else
        {
            Log.d("MENSAJE", "El usuario va a la actividad 2");
            Intent intent = new Intent(this, Main2Activity);
            startActivity(intent);
        }
        */

        switch (item.getItemId())
        {
            case R.id.boton_salir://salir
                Log.d("MENSAJE",  "Está saliendo del programa" );
                this.finish();
                break;
            case R.id.boton_web://enlace internet
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://femxa-femxa-ebtm.rhcloud.com"));
                startActivity(i);

                default: //Error
        }
        //return super.onOptionsItemSelected(item);
        return true;

    }
}

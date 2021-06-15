package com.example.skynetwork;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private TextView band, mcc, mnc, lac, systemType, rnc, cid, psc, frequencyNumber, rsp, rsrq, snr, cqi, rssi, dataType, ulSpeed, dlSpeed, carrier;
    private SignalStrength signalStrength;
    private TelephonyManager telephonyManager;
    private final static String LTE_TAG = "LTE_Tag";
    private final static String LTE_SIGNAL_STRENGTH = "getLteSignalStrength";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        band = findViewById(R.id.band);
        mcc = findViewById(R.id.mcc);
        mnc = findViewById(R.id.mnc);
        lac = findViewById(R.id.lac);
        systemType = findViewById(R.id.systemType);
        rnc = findViewById(R.id.rnc);
        cid = findViewById(R.id.cid);
        psc = findViewById(R.id.psc);
        frequencyNumber = findViewById(R.id.frequencyNumber);
        rsp = findViewById(R.id.rsp);
        rsrq = findViewById(R.id.rsrq);
        snr = findViewById(R.id.snr);
        cqi = findViewById(R.id.cqi);
        rssi = findViewById(R.id.rssi);
        dataType = findViewById(R.id.dataType);
        ulSpeed = findViewById(R.id.ulSpeed);
        dlSpeed = findViewById(R.id.dlSpeed);
        carrier = findViewById(R.id.carrier);

        getSimInfo();
    }

    private void getSimInfo() {
        /**
         * <uses-permission android:name="android.permission.READ_PHONE_STATE"
         * /> <uses-permission
         * android:name="android.permission.ACCESS_NETWORK_STATE"/>
         */

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperator = telephonyManager.getNetworkOperator();
        int mccc = 0, mncc = 0;
        if (networkOperator != null) {
            mccc = Integer.parseInt(networkOperator.substring(0, 3));
            mncc = Integer.parseInt(networkOperator.substring(3));
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        String SimNumber = telephonyManager.getLine1Number();

//        String SimSerialNumber = telephonyManager.getSimSerialNumber();
        String countryISO = telephonyManager.getSimCountryIso();
        String operatorName = telephonyManager.getSimOperatorName();
        String operator = telephonyManager.getSimOperator();
        int simState = telephonyManager.getSimState();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        String voicemailNumer = telephonyManager.getVoiceMailNumber();
        String voicemailAlphaTag = telephonyManager.getVoiceMailAlphaTag();

        // Getting connected network iso country code
        String networkCountry = telephonyManager.getNetworkCountryIso();
        // Getting the connected network operator ID
        String networkOperatorId = telephonyManager.getNetworkOperator();
        // Getting the connected network operator name
        String networkName = telephonyManager.getNetworkOperatorName();

        int networkType = telephonyManager.getNetworkType();

        String network = "";
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            if (cm.getActiveNetworkInfo().getTypeName().equals("MOBILE"))
                network = "Cell Network/3G";
            else if (cm.getActiveNetworkInfo().getTypeName().equals("WIFI"))
                network = "WiFi";
            else
                network = "N/A";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        dataType.setText(network);
        mcc.setText(Integer.toString(mccc));
        mnc.setText(Integer.toString(mncc));
        GsmCellLocation gc = (GsmCellLocation) telephonyManager.getCellLocation();
        cid.setText(Integer.toString(gc.getCid()));
        lac.setText(Integer.toString(gc.getLac()));
        psc.setText(Integer.toString(gc.getPsc()));

        String ssignal = String.valueOf(signalStrength);
        String[] parts = ssignal.split(" ");
        System.out.println("dbm = " + ssignal);
        int dbm = 0;

//        if (telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_LTE) {
//            // For Lte SignalStrength: dbm = ASU - 140.
//            dbm = Integer.parseInt(parts[8]) - 140;
//        } else {
//            // For GSM Signal Strength: dbm =  (2*ASU)-113.
//            if (signalStrength.getGsmSignalStrength() != 99) {
//                int intdbm = -113 + 2
//                        * signalStrength.getGsmSignalStrength();
//                dbm = intdbm;
//            }
//        }
//        System.out.println("dbm = " + dbm);


//        String n = "network :" + network +
//                "\n" + "countryISO : " + countryISO + "\n" + "operatorName : "
//                + operatorName + "\n" + "operator :      " + operator + "\n"
//                + "simState :" + simState + "\n" + "Sim Serial Number : "
//                + SimSerialNumber + "\n" + "Sim Number : " + SimNumber + "\n"
//                + "Voice Mail Numer" + voicemailNumer + "\n"
//                + "Voice Mail Alpha Tag" + voicemailAlphaTag + "\n"
//                + "Sim State" + simState + "\n" + "Mobile Country Code MCC : "
//                + mccc + "\n" + "Mobile Network Code MNC : " + mncc + "\n"
//                + "Network Country : " + networkCountry + "\n"
//                + "Network OperatorId : " + networkOperatorId + "\n"
//                + "Network Name : " + networkName + "\n" + "Network Type : "
//                + networkType;
    }

    private void getLTEsignalStrength() {
        try {
            Method[] methods = android.telephony.SignalStrength.class.getMethods();
            for (Method mthd : methods) {
                if (mthd.getName().equals(LTE_SIGNAL_STRENGTH)) {
                    int LTEsignalStrength = (Integer) mthd.invoke(signalStrength, new Object[]{});
                    Log.i(LTE_TAG, "signalStrength = " + LTEsignalStrength);
                    return;
                }
            }
        } catch (Exception e) {
            Log.e(LTE_TAG, "Exception: " + e.toString());
        }
    }
}
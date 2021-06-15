package com.example.skynetwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LinearLayout layout;
    TextView textView7;

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            // TODO Auto-generated method stub
            int level = intent.getIntExtra("level", 0);
            textView7.setText(String.valueOf(level) + "%");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = this;
        layout = (LinearLayout) findViewById(R.id.MyLinearLayout);

//        TextView textView = new TextView(context);
//        textView.setText("\n\t\tInfo\n");
//        layout.addView(textView);
//        getIMEIAndIMSI(context);
//
//        TextView textView2 = new TextView(context);
//        textView2.setText("\n\t\tPhone Info\n");
//        layout.addView(textView2);
//        getPhoneInfo(context);
//
//        TextView textView3 = new TextView(context);
//
//        textView3.setText("\n\t\tSim Info\n");
//        layout.addView(textView3);
//        getSimInfo(context);
//
//        TextView textView4 = new TextView(context);
//        textView4.setText("\n\t\tDual Sim phone Info\n");
//        layout.addView(textView4);
//        getDualSimInfo(context);
//
//        TextView textView5 = new TextView(context);
//        textView5.setText("\n\t\tDisplay Info\n");
//        layout.addView(textView5);
//        getDisplayInfo(context);
//
//        TextView textView6 = new TextView(context);
//        textView6.setText("\n\t\tBattery Status Info\n");
//        layout.addView(textView6);

        TextView textView9 = new TextView(context);
        textView9.setText("\n\t\tTest\n");
        layout.addView(textView9);
        getNWInfo(context);

//        textView7 = new TextView(context);
//        layout.addView(textView7);
//        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(
//                Intent.ACTION_BATTERY_CHANGED));

    }

    private void getDisplayInfo(Context context) {

        // Best way for new devices
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        String str_ScreenSize = "Screen Size : " + displayMetrics.widthPixels
                + " x " + displayMetrics.heightPixels;

        String strScreenDIP = "";
        strScreenDIP += "Density DPI : " + displayMetrics.densityDpi + " Hight"
                + "\n";
        strScreenDIP += "Logical Density : " + displayMetrics.density + "\n";
        strScreenDIP += "Font Scalling Factor : "
                + displayMetrics.scaledDensity + "\n";
        strScreenDIP += "Absolute Height(pix) : " + displayMetrics.heightPixels
                + "\n";
        strScreenDIP += "Absolute Width(pix) : " + displayMetrics.widthPixels
                + "\n";
        strScreenDIP += "Pixels per inch X : " + displayMetrics.xdpi + "\n";
        strScreenDIP += "Pixels per inch Y : " + displayMetrics.ydpi + "\n";

        TextView textView = new TextView(context);
        textView.setText(str_ScreenSize + "\n" + strScreenDIP);
        layout.addView(textView);

    }

    private void getSimInfo(Context context) {
        /**
         * <uses-permission android:name="android.permission.READ_PHONE_STATE"
         * /> <uses-permission
         * android:name="android.permission.ACCESS_NETWORK_STATE"/>
         */

        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperator = telephonyManager.getNetworkOperator();
        int mcc = 0, mnc = 0;
        if (networkOperator != null) {
            mcc = Integer.parseInt(networkOperator.substring(0, 3));
            mnc = Integer.parseInt(networkOperator.substring(3));
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
        String SimNumber = telephonyManager.getLine1Number();

        String SimSerialNumber = telephonyManager.getSimSerialNumber();
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
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
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

        TextView textView = new TextView(context);
        textView.setText("network :" + network +

                "\n" + "countryISO : " + countryISO + "\n" + "operatorName : "
                + operatorName + "\n" + "operator :      " + operator + "\n"
                + "simState :" + simState + "\n" + "Sim Serial Number : "
                + SimSerialNumber + "\n" + "Sim Number : " + SimNumber + "\n"
                + "Voice Mail Numer" + voicemailNumer + "\n"
                + "Voice Mail Alpha Tag" + voicemailAlphaTag + "\n"
                + "Sim State" + simState + "\n" + "Mobile Country Code MCC : "
                + mcc + "\n" + "Mobile Network Code MNC : " + mnc + "\n"
                + "Network Country : " + networkCountry + "\n"
                + "Network OperatorId : " + networkOperatorId + "\n"
                + "Network Name : " + networkName + "\n" + "Network Type : "
                + networkType);

        layout.addView(textView);

    }

    private void getPhoneInfo(Context context) {
        /**
         * <uses-permission android:name="android.permission.READ_PHONE_STATE"
         * />
         */
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(TELEPHONY_SERVICE);

        String os_version = System.getProperty("os.version");
        String device = android.os.Build.DEVICE;
        String model = android.os.Build.MODEL;
        String product = android.os.Build.PRODUCT;
        String brand = android.os.Build.BRAND;
        String display = android.os.Build.DISPLAY;
        String CPU_abi = android.os.Build.CPU_ABI;
        String CPU_abi2 = android.os.Build.CPU_ABI2;
        String Unkown = android.os.Build.UNKNOWN;
        String hardware = android.os.Build.HARDWARE;
        String ID = android.os.Build.ID;
        String manufacturer = android.os.Build.MANUFACTURER;
        String serial = android.os.Build.SERIAL;
        String user = android.os.Build.USER;
        String host = android.os.Build.HOST;

        String type = android.os.Build.TYPE;
        String bootloader = android.os.Build.BOOTLOADER;
        String fingerprint = android.os.Build.FINGERPRINT;
        String radio = android.os.Build.RADIO;
        String tags = android.os.Build.TAGS;

        // Build.VERSION constant
        String increamental_Ver = android.os.Build.VERSION.INCREMENTAL;
        String codeName = android.os.Build.VERSION.CODENAME;
        String ver_release = android.os.Build.VERSION.RELEASE;
        int sdk_int = android.os.Build.VERSION.SDK_INT;

        int phoneType = telephonyManager.getPhoneType();
        // Getting software version( not sdk version )
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
        String softwareVersion = telephonyManager.getDeviceSoftwareVersion();
        String phoneTypeName = null;
        switch (phoneType) {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                phoneTypeName = "PHONE_TYPE_CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                phoneTypeName = "PHONE_TYPE_GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                phoneTypeName = "PHONE_TYPE_NONE";
                break;
            default:
                break;
        }

        TextView textView = new TextView(context);
        textView.setText(" os_version :   " + os_version + "\n"
                + " ver_release : " + ver_release + "\n" + " DEVICE : "
                + device + "\n" + " MODEL : " + model + "\n" + " PRODUCT : "
                + product + "\n" + " BRAND : " + brand + "\n" + " DISPLAY : "
                + display + "\n" + " CPU_ABI : " + CPU_abi + "\n"
                + " CPU_ABI2 : " + CPU_abi2 + "\n" + " UNKNOWN : " + Unkown
                + "\n" + " HARDWARE : " + hardware + "\n" + " ID : " + ID
                + "\n" + " MANUFACTURER :  " + manufacturer + "\n"
                + " SERIAL : " + serial + "\n" + " USER : " + user + "\n"
                + " Software Version : " + softwareVersion + "\n"
                + " Phone Type : " + phoneType + "\n" + " phoneTypeName : "
                + phoneTypeName + "\n" + " Host : " + host + "\n" + " type : "
                + type + "\n" + " bootloader : " + bootloader + "\n"
                + " fingerprint : " + fingerprint + "\n" + " radio : " + radio
                + "\n" + " tags : " + tags + "\n" + " increamental_Ver : "
                + increamental_Ver +

                "\n" + " codeName : " + codeName + "\n" + " ver_release : "
                + ver_release + "\n" + " sdk_int : " + sdk_int

        );
        layout.addView(textView);
    }

    private void getIMEIAndIMSI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
//        String imei = telephonyManager.getDeviceId();
//        String IMSI = telephonyManager.getSubscriberId();

        TextView textView = new TextView(context);
        textView.setText("IMEI     " + "imei" + "\n" + "IMSI    " + "IMSI");
        layout.addView(textView);
    }

    private void getDualSimInfo(Context context) {
//        TelephonyInfo telephonyInfo = TelephonyInfo.getInstance(context);
//        String imeiSIM1 = telephonyInfo.getImeiSIM1();
//        String imeiSIM2 = telephonyInfo.getImeiSIM2();
//        boolean isSIM1Ready = telephonyInfo.isSIM1Ready();
//        boolean isSIM2Ready = telephonyInfo.isSIM2Ready();
//        boolean isDualSIM = telephonyInfo.isDualSIM();
//        TextView textView = new TextView(context);
//        textView.setText(" IME1 :         " + imeiSIM1 + "\n" + " IME2 :           "
//                + imeiSIM2 + "\n" + " IS DUAL SIM :      " + isDualSIM + "\n"
//                + " IS SIM1 READY : " + isSIM1Ready + "\n"
//                + " IS SIM2 READY : " + isSIM2Ready + "\n");
//        layout.addView(textView);
    }

    private void getNWInfo(Context context) {
        /**
         * <uses-permission android:name="android.permission.READ_PHONE_STATE"
         * /> <uses-permission
         * android:name="android.permission.ACCESS_NETWORK_STATE"/>
         */

        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperator = telephonyManager.getNetworkOperator();
        System.out.println("networkOperator =" + networkOperator);
        int mcc = 0, mnc = 0;
        if (networkOperator != null) {
            mcc = Integer.parseInt(networkOperator.substring(0, 3));
            mnc = Integer.parseInt(networkOperator.substring(3));
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
        String SimNumber = telephonyManager.getLine1Number();

        String SimSerialNumber = telephonyManager.getSimSerialNumber();
        String countryISO = telephonyManager.getSimCountryIso();
        String operatorName = telephonyManager.getSimOperatorName();
        String operator = telephonyManager.getSimOperator();
        int simState = telephonyManager.getSimState();

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
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
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
        TextView textView = new TextView(context);
        textView.setText("network :" + network +
                "\n" + "countryISO : " + countryISO + "\n" + "operatorName : "
                + operatorName + "\n" + "operator :      " + operator + "\n"
                + "simState :" + simState + "\n" + "Sim Serial Number : "
                + SimSerialNumber + "\n" + "Sim Number : " + SimNumber + "\n"
                + "Voice Mail Numer" + voicemailNumer + "\n"
                + "Voice Mail Alpha Tag" + voicemailAlphaTag + "\n"
                + "Sim State" + simState + "\n" + "Mobile Country Code MCC : "
                + mcc + "\n" + "Mobile Network Code MNC : " + mnc + "\n"
                + "Network Country : " + networkCountry + "\n"
                + "Network OperatorId : " + networkOperatorId + "\n"
                + "Network Name : " + networkName + "\n" + "Network Type : "
                + networkType);
        layout.addView(textView);

    }
}
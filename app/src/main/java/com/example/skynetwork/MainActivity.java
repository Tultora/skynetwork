package com.example.skynetwork;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SettingsSlicesContract;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static android.telephony.TelephonyManager.NETWORK_TYPE_1xRTT;
import static android.telephony.TelephonyManager.NETWORK_TYPE_CDMA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EDGE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_0;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_A;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_B;
import static android.telephony.TelephonyManager.NETWORK_TYPE_GPRS;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSDPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSPAP;
import static android.telephony.TelephonyManager.NETWORK_TYPE_IDEN;
import static android.telephony.TelephonyManager.NETWORK_TYPE_LTE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_NR;
import static android.telephony.TelephonyManager.NETWORK_TYPE_UMTS;

public class MainActivity extends AppCompatActivity {

    private TextView band, mcc, mnc, lac, systemType, rnc, cid, psc, frequencyNumber, rscp, rsrq, snr, cqi, rssi, dataType, ulSpeed, dlSpeed, carrier;
    private TextView bandWidth, tac, enb, pci, frequency4g, ta, rsrp, ecno, txPwr;
    private SignalStrength signalStrength;
    private TelephonyManager telephonyManager;
    private final static String LTE_TAG = "LTE_Tag";
    private final static String LTE_SIGNAL_STRENGTH = "getLteSignalStrength";
    private int typeSystem = 0;
    private LinearLayout bandContainer, bandWidthContainer, mccContainer, mncContainer, lacContainer, systemtypeContainer, tacContainer;
    private LinearLayout rncContainer, enbContainer, cidContainer, pcsContainer, pciContainer, fre3gContainer, fre4gContainer, taContainer;
    private LinearLayout rscpContainer, rsrpContainer, rsrqContainer, snrContainer, cqiContainer, rssiContainer, ecnoContainer, txpwrContainer;
    private View bandView, bandWidthView, mccView, mncView, lacView, systemtypeView, tacView, rncView, enbView, cidView, pscView, pciView;
    private View fre3gView, fre4gView, taView, rscpView, rsrpView, rsrqView, snrView, cqiView, rssiView, ecnoView, txpwrView;
    private int mccc = 0, mncc = 0;
    Handler handler = new Handler();
    Runnable refresh;

    @RequiresApi(api = Build.VERSION_CODES.R)
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
        rscp = findViewById(R.id.rscp);
        rsrq = findViewById(R.id.rsrq);
        snr = findViewById(R.id.snr);
        cqi = findViewById(R.id.cqi);
        rssi = findViewById(R.id.rssi);
        dataType = findViewById(R.id.dataType);
        ulSpeed = findViewById(R.id.ulSpeed);
        dlSpeed = findViewById(R.id.dlSpeed);
        carrier = findViewById(R.id.carrier);
        bandWidth = findViewById(R.id.bandWidth);
        tac = findViewById(R.id.tac);
        enb = findViewById(R.id.enb);
        pci = findViewById(R.id.pci);
        frequency4g = findViewById(R.id.frequency4g);
        ta = findViewById(R.id.ta);
        rsrp = findViewById(R.id.rsrp);
        ecno = findViewById(R.id.ecno);
        txPwr = findViewById(R.id.txPwr);
        bandContainer = findViewById(R.id.bandContainer);
        bandWidthContainer = findViewById(R.id.bandWidthContainer);
        mccContainer = findViewById(R.id.mccContainer);
        mncContainer = findViewById(R.id.mncContainer);
        mncContainer = findViewById(R.id.mncContainer);
        lacContainer = findViewById(R.id.lacContainer);
        systemtypeContainer = findViewById(R.id.systemtypeContainer);
        tacContainer = findViewById(R.id.tacContainer);
        rncContainer = findViewById(R.id.rncContainer);
        enbContainer = findViewById(R.id.enbContainer);
        cidContainer = findViewById(R.id.cidContainer);
        pcsContainer = findViewById(R.id.pcsContainer);
        pciContainer = findViewById(R.id.pciContainer);
        fre3gContainer = findViewById(R.id.fre3gContainer);
        fre4gContainer = findViewById(R.id.fre4gContainer);
        taContainer = findViewById(R.id.taContainer);
        rscpContainer = findViewById(R.id.rscpContainer);
        rsrpContainer = findViewById(R.id.rsrpContainer);
        rsrqContainer = findViewById(R.id.rsrqContainer);
        snrContainer = findViewById(R.id.snrContainer);
        cqiContainer = findViewById(R.id.cqiContainer);
        rssiContainer = findViewById(R.id.rssiContainer);
        ecnoContainer = findViewById(R.id.ecnoContainer);
        txpwrContainer = findViewById(R.id.txpwrContainer);
        bandView = findViewById(R.id.bandView);
        bandWidthView = findViewById(R.id.bandWidthView);
        mccView = findViewById(R.id.mccView);
        lacView = findViewById(R.id.lacView);
        mncView = findViewById(R.id.mncView);
        systemtypeView = findViewById(R.id.systemtypeView);
        tacView = findViewById(R.id.tacView);
        rncView = findViewById(R.id.rncView);
        enbView = findViewById(R.id.enbView);
        cidView = findViewById(R.id.cidView);
        pscView = findViewById(R.id.pscView);
        pciView = findViewById(R.id.pciView);
        fre3gView = findViewById(R.id.fre3gView);
        fre4gView = findViewById(R.id.fre4gView);
        taView = findViewById(R.id.taView);
        rscpView = findViewById(R.id.rscpView);
        rsrpView = findViewById(R.id.rsrpView);
        rsrqView = findViewById(R.id.rsrqView);
        snrView = findViewById(R.id.snrView);
        cqiView = findViewById(R.id.cqiView);
        rssiView = findViewById(R.id.rssiView);
        ecnoView = findViewById(R.id.ecnoView);
        txpwrView = findViewById(R.id.txpwrView);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PackageManager.PERMISSION_GRANTED);
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String networkOperator = telephonyManager.getNetworkOperator();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        getSystemType();

        if (networkOperator != null) {
            mccc = Integer.parseInt(networkOperator.substring(0, 3));
            mncc = Integer.parseInt(networkOperator.substring(3));
        }
        mcc.setText(mccc + "");
        mnc.setText(mncc + "");

        refresh = new Runnable() {
            public void run() {
                getSystemType();
                handler.postDelayed(refresh, 2000);
            }
        };
        handler.post(refresh);
    }

    private void chooseNetwrok(int systemTypes) {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        if (netInfo != null) {
            NetworkCapabilities nc = cm.getNetworkCapabilities(cm.getActiveNetwork());
            int downSpeed = nc.getLinkDownstreamBandwidthKbps();
            int upSpeed = nc.getLinkUpstreamBandwidthKbps();

            dlSpeed.setText(downSpeed + " DownstreamBandwidthKbps");
            ulSpeed.setText(upSpeed + " UpstreamBandwidthKbps");
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        List<CellInfo> cellInfos = telephonyManager.getAllCellInfo();   //This will give info of all sims present inside your mobile
        if (cellInfos != null) {
            for (int i = 0; i < cellInfos.size(); i++) {
                if (cellInfos.get(i).isRegistered()) {
                    if (cellInfos.get(i) instanceof CellInfoWcdma && systemTypes == 2) {
                        CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) telephonyManager.getAllCellInfo().get(i);
                        CellSignalStrengthWcdma cellSignalStrengthWcdma = cellInfoWcdma.getCellSignalStrength();
                        CellIdentityWcdma cellIdentityWcdma = cellInfoWcdma.getCellIdentity();

                        lac.setText(cellIdentityWcdma.getLac() + "");
                        cid.setText(cellIdentityWcdma.getCid() + "");
                        ecno.setText(cellSignalStrengthWcdma.getEcNo() + "");
                        frequencyNumber.setText(cellIdentityWcdma.getUarfcn() + "");
                        psc.setText(cellIdentityWcdma.getPsc() + "");
                        dataType.setText("WCDMA");
//                        strength = String.valueOf(cellSignalStrengthWcdma.getDbm());
                    } else if (cellInfos.get(i) instanceof CellInfoLte && systemTypes == 3) {
                        CellInfoLte cellInfoLte = (CellInfoLte) telephonyManager.getAllCellInfo().get(0);
                        CellSignalStrengthLte cellSignalStrengthLte = cellInfoLte.getCellSignalStrength();
                        CellIdentityLte cellIdentityLte = cellInfoLte.getCellIdentity();
                        System.out.println("cellInfoLte === " + cellInfoLte);
                        System.out.println("cellSignalStrengthLte === " + cellSignalStrengthLte);
                        System.out.println("cellIdentityLte === " + cellIdentityLte);

                        tac.setText(cellIdentityLte.getTac() + "");
                        pci.setText(cellIdentityLte.getPci() + "");
                        frequency4g.setText(cellIdentityLte.getEarfcn() + "");
                        rssi.setText(cellSignalStrengthLte.getRssi() + "");
                        bandWidth.setText(cellIdentityLte.getBandwidth() + "");
                        cqi.setText(cellSignalStrengthLte.getCqi() + "");
                        rsrp.setText(cellSignalStrengthLte.getRsrp() + "");
                        rsrq.setText(cellSignalStrengthLte.getRsrq() + "");
                        dataType.setText("LTE");
//                        strength = String.valueOf(cellSignalStrengthLte.getDbm());
                    }
                }
            }
        }
    }

    private void getSystemType() {

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
        switch (telephonyManager.getDataNetworkType()) {
            case NETWORK_TYPE_EDGE:
            case NETWORK_TYPE_GPRS:
            case NETWORK_TYPE_CDMA:
            case NETWORK_TYPE_IDEN:
            case NETWORK_TYPE_1xRTT: {
                typeSystem = 1;
                systemType.setText("2G");
                break;
            }
            case NETWORK_TYPE_UMTS:
            case NETWORK_TYPE_HSDPA:
            case NETWORK_TYPE_HSPA:
            case NETWORK_TYPE_HSPAP:
            case NETWORK_TYPE_EVDO_0:
            case NETWORK_TYPE_EVDO_A:
            case NETWORK_TYPE_EVDO_B: {
                typeSystem = 2;
                systemType.setText("3G");
                chooseNetwrok(typeSystem);
                get3gInfo();
                break;
            }
            case NETWORK_TYPE_LTE: {
                typeSystem = 3;
                systemType.setText("4G");
                chooseNetwrok(typeSystem);
                get4gInfo();
                break;
            }
            case NETWORK_TYPE_NR: {
                typeSystem = 4;
                systemType.setText("5G");
                break;
            }
            default: {
                typeSystem = 5;
                systemType.setText("Unknown");
            }
        }
    }

    private void get3gInfo() {
        bandWidthContainer.setVisibility(View.GONE);
        bandWidthView.setVisibility(View.GONE);
        taContainer.setVisibility(View.GONE);
        taView.setVisibility(View.GONE);
        cqiContainer.setVisibility(View.GONE);
        cqiView.setVisibility(View.GONE);
        rssiContainer.setVisibility(View.GONE);
        rssiView.setVisibility(View.GONE);
        fre4gContainer.setVisibility(View.GONE);
        fre4gView.setVisibility(View.GONE);
        fre3gContainer.setVisibility(View.VISIBLE);
        fre3gView.setVisibility(View.VISIBLE);
        tacContainer.setVisibility(View.GONE);
        tacView.setVisibility(View.GONE);
        lacContainer.setVisibility(View.VISIBLE);
        lacView.setVisibility(View.VISIBLE);
        rncContainer.setVisibility(View.VISIBLE);
        rncView.setVisibility(View.VISIBLE);
        enbContainer.setVisibility(View.GONE);
        enbView.setVisibility(View.GONE);
        pcsContainer.setVisibility(View.VISIBLE);
        pscView.setVisibility(View.VISIBLE);
        pciContainer.setVisibility(View.GONE);
        pciView.setVisibility(View.GONE);
        rscpContainer.setVisibility(View.VISIBLE);
        rscpView.setVisibility(View.VISIBLE);
        rsrpContainer.setVisibility(View.GONE);
        rsrpView.setVisibility(View.GONE);
        ecnoContainer.setVisibility(View.VISIBLE);
        ecnoView.setVisibility(View.VISIBLE);
        rsrqContainer.setVisibility(View.GONE);
        rsrqView.setVisibility(View.GONE);
    }

    private void get4gInfo() {
        bandWidthContainer.setVisibility(View.VISIBLE);
        bandWidthView.setVisibility(View.VISIBLE);
        taContainer.setVisibility(View.VISIBLE);
        taView.setVisibility(View.VISIBLE);
        cqiContainer.setVisibility(View.VISIBLE);
        cqiView.setVisibility(View.VISIBLE);
        rssiContainer.setVisibility(View.VISIBLE);
        rssiView.setVisibility(View.VISIBLE);
        fre4gContainer.setVisibility(View.VISIBLE);
        fre4gView.setVisibility(View.VISIBLE);
        fre3gContainer.setVisibility(View.GONE);
        fre3gView.setVisibility(View.GONE);
        tacContainer.setVisibility(View.VISIBLE);
        tacView.setVisibility(View.VISIBLE);
        lacContainer.setVisibility(View.GONE);
        lacView.setVisibility(View.GONE);
        rncContainer.setVisibility(View.GONE);
        rncView.setVisibility(View.GONE);
        enbContainer.setVisibility(View.VISIBLE);
        enbView.setVisibility(View.VISIBLE);
        pcsContainer.setVisibility(View.GONE);
        pscView.setVisibility(View.GONE);
        pciContainer.setVisibility(View.VISIBLE);
        pciView.setVisibility(View.VISIBLE);
        rscpContainer.setVisibility(View.GONE);
        rscpView.setVisibility(View.GONE);
        rsrpContainer.setVisibility(View.VISIBLE);
        rsrpView.setVisibility(View.VISIBLE);
        ecnoContainer.setVisibility(View.GONE);
        ecnoView.setVisibility(View.GONE);
        rsrqContainer.setVisibility(View.VISIBLE);
        rsrqView.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
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
                network = "Cell Network/3G/LTE";
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

//        String ssignal = String.valueOf(signalStrength);
//        String[] parts = ssignal.split(" ");
//        int dbm = 0;
        int ssignal = 0;
        try {
            final TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            for (final CellInfo info : tm.getAllCellInfo()) {
                if (info instanceof CellInfoGsm) {
                    final CellSignalStrengthGsm gsm = ((CellInfoGsm) info).getCellSignalStrength();
                    // do what you need
//                    CellInfoGsm cellInfoGsm = (CellInfoGsm) telephonyManager.getAllCellInfo().get(0);
//        CellSignalStrengthGsm cellSignalStrengthGsm = cellInfoGsm.getCellSignalStrength();
//                    ssignal = gsm.getDbm();
                    int getrssi = gsm.getRssi();
                    rssi.setText(String.valueOf(getrssi));

                    System.out.println("ssignal gsm = " + ssignal);

                } else if (info instanceof CellInfoCdma) {
                    final CellSignalStrengthCdma cdma = ((CellInfoCdma) info).getCellSignalStrength();
                    // do what you need
                    ssignal = cdma.getDbm();
                    System.out.println("ssignal cdma= " + ssignal);
                } else if (info instanceof CellInfoLte) {
                    CellInfoLte cellInfoLte = (CellInfoLte) telephonyManager.getAllCellInfo().get(0);
                    int getrsrp = cellInfoLte.getCellSignalStrength().getRsrp();
                    int getrsrq = cellInfoLte.getCellSignalStrength().getRsrq();
                    int getrssnr = cellInfoLte.getCellSignalStrength().getRssnr();
                    rscp.setText(String.valueOf(getrsrp));
                    rsrq.setText(String.valueOf(getrsrq));
                    snr.setText(String.valueOf(getrssnr));
                    int getCqi = cellInfoLte.getCellSignalStrength().getCqi();
                    int getRssi = cellInfoLte.getCellSignalStrength().getRssi();
                    cqi.setText(String.valueOf(getCqi));
                    rssi.setText(String.valueOf(getRssi));
                } else {
                    throw new Exception("Unknown type of cell signal!");
                }
            }
        } catch (Exception e) {
//            Log.e(TAg, "Unable to obtain cell signal information", e);
        }

        ConnectivityManager cam = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cam.getActiveNetworkInfo();

        //should check null because in airplane mode it will be null
//        NetworkCapabilities nc = cam.getNetworkCapabilities(cm.getActiveNetwork());
//        int downSpeed = nc.getLinkDownstreamBandwidthKbps();
//        int upSpeed = nc.getLinkUpstreamBandwidthKbps();
//        ulSpeed.setText(String.valueOf(upSpeed));
//        dlSpeed.setText(String.valueOf(downSpeed));
//        CellInfoGsm cellInfoGsm = (CellInfoGsm) telephonyManager.getAllCellInfo().get(0);
//        CellSignalStrengthGsm cellSignalStrengthGsm = cellInfoGsm.getCellSignalStrength();
//        int ssignal = cellSignalStrengthGsm.getDbm();

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
}
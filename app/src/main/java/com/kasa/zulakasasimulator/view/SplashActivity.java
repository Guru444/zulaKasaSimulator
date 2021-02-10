package com.kasa.zulakasasimulator.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.kasa.zulakasasimulator.R;
import com.kasa.zulakasasimulator.model.zulaSettings.ZulaSettings;
import com.kasa.zulakasasimulator.notification.MyBroadcastReceiver;
import com.kasa.zulakasasimulator.service.userGamerService.ZulaGameDataPostInterface;
import com.kasa.zulakasasimulator.view.aboutUS.ActivityAboutUs;
import com.kasa.zulakasasimulator.view.loginView.loginRegisterKontrol;
import com.onesignal.OneSignal;

import java.util.Calendar;
import java.util.List;

import eu.dkaratzas.android.inapp.update.Constants;
import eu.dkaratzas.android.inapp.update.InAppUpdateManager;
import eu.dkaratzas.android.inapp.update.InAppUpdateStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity implements InAppUpdateManager.InAppUpdateHandler {
    Animation anim;
    ImageView imageView;
    AlarmManager alarmMgr;
    PendingIntent pendingIntent;
    InAppUpdateManager inAppUpdateManager;
    private ProgressDialog progressDialog;
    public static final String BASE_URL = "https://oyunpuanla.com/zulaKasaSimulator/public/index.php/";


    public boolean internetVarmi(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();




        inAppUpdateManager = InAppUpdateManager.Builder(this,101)
                .resumeUpdates(true)
                .mode(Constants.UpdateMode.IMMEDIATE)
                .snackBarAction("Güncelleme bulunmaktadır.")
                .snackBarAction("Restart")
                .handler(this);

        inAppUpdateManager.checkForAppUpdate();



        if(internetVarmi(this)) {
            //startAlarmManager();
            TextView TextView = (TextView) findViewById(R.id.tv_title); // Declare an imageView to show the animation.
            anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade); // Create the animation.
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ZulaGameDataPostInterface zulaGameDataPostInterface = retrofit().create(ZulaGameDataPostInterface.class);
                    Call<List<ZulaSettings>> call = zulaGameDataPostInterface.getSettings("game_status");
                    call.enqueue(new Callback<List<ZulaSettings>>() {
                        @Override
                        public void onResponse(Call<List<ZulaSettings>> call, Response<List<ZulaSettings>> response) {
                            List<ZulaSettings> zulaSettings = response.body();
                            if (zulaSettings != null) {
                                if (Integer.parseInt(zulaSettings.get(0).getOption_value()) == 1) {
                                    try {
                                        PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
                                        progressDialog = new ProgressDialog(SplashActivity.this);
                                        progressDialog.setMessage("Version Kontrol ediliyor...");
                                        progressDialog.show();

                                        ZulaGameDataPostInterface zulaGameDataPostInterface = retrofit().create(ZulaGameDataPostInterface.class);
                                        Call<List<ZulaSettings>> callVersion = zulaGameDataPostInterface.getSettings("version");
                                        callVersion.enqueue(new Callback<List<ZulaSettings>>() {
                                            @Override
                                            public void onResponse(Call<List<ZulaSettings>> callVersion, Response<List<ZulaSettings>> response) {
                                                List<ZulaSettings> zulaSettingss = response.body();
                                                if (zulaSettingss != null) {
                                                    String version = pInfo.versionName;
                                                    int verCode = pInfo.versionCode;
                                                    if(zulaSettingss.get(0).getOption_value().equals(version)){
                                                        startActivity(new Intent(SplashActivity.this, loginRegisterKontrol.class));
                                                    }
                                                    else{
                                                        openDialoVersion(zulaSettingss.get(0).getOption_message());
                                                    }
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Versiyon kontrol Hatası", Toast.LENGTH_LONG).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<List<ZulaSettings>> call, Throwable t) {

                                            }
                                        });
                                    } catch (PackageManager.NameNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else{
                                    openDialogBakım(zulaSettings.get(0).getOption_message());
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "Oyun Bakım hatası", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<ZulaSettings>> call, Throwable t) {

                        }
                    });
                    // HomeActivity.class is the activity to go after showing the splash screen.
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            TextView.startAnimation(anim);
        }
        else
            openDialog();

    }
    public Retrofit retrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }
    public void startAlarmManager() {
        Intent dialogIntent = new Intent(getBaseContext(), MyBroadcastReceiver.class);
        alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(this, 0, dialogIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, 16);
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), calendar.getTimeInMillis(), pendingIntent);

    }
    private void openDialog() {
        View view = getLayoutInflater().inflate(R.layout.fragment_bottom_shet, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        TextView messsage = (TextView) view.findViewById(R.id.messsage);
        messsage.setText("Lütfen internet bağlantınızı kontrol ediniz");
        // TextView gallery_sel = (TextView) view.findViewById(R.id.gallery);
        bottomSheetDialog.show();
    }
    private void openDialogBakım(String message) {
        View view = getLayoutInflater().inflate(R.layout.game_maintenance_dialog, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        TextView messsage = (TextView) view.findViewById(R.id.messsage);
        messsage.setText(message);
        // TextView gallery_sel = (TextView) view.findViewById(R.id.gallery);
        bottomSheetDialog.show();
    }
    private void openDialoVersion(String message) {
        View view = getLayoutInflater().inflate(R.layout.update_version_controll, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        Button messsage = (Button) view.findViewById(R.id.updateApp);
        messsage.setText(message);
        messsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://play.google.com/store/apps/details?id=com.kasa.zulakasasimulator"));
                startActivity(viewIntent);
            }
        });

        bottomSheetDialog.show();
    }

    public void stopAlarmManager() {
        if (alarmMgr != null)
            alarmMgr.cancel(pendingIntent);
    }

    @Override
    public void onInAppUpdateError(int code, Throwable error) {

    }

    @Override
    public void onInAppUpdateStatus(InAppUpdateStatus status) {
        if(status.isDownloaded()){
            View view = getWindow().getDecorView().findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar.make(view,
                    "Update bulunmaktadır.",Snackbar.LENGTH_INDEFINITE);

            snackbar.setAction("", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inAppUpdateManager.completeUpdate();
                }
            });

            snackbar.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

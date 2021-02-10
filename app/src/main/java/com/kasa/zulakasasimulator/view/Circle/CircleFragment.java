package com.kasa.zulakasasimulator.view.Circle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kasa.zulakasasimulator.R;
import com.kasa.zulakasasimulator.model.zulaSettings.ZulaSettings;
import com.kasa.zulakasasimulator.service.userGamerService.ZulaGameDataPostInterface;
import com.kasa.zulakasasimulator.view.aboutUS.ActivityAboutUs;
import com.kasa.zulakasasimulator.view.kasaOpenView.kasaAcilimiMagaza;
import com.kasa.zulakasasimulator.view.zulaGameUserList.kasaOyunuZula;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CircleFragment extends Fragment {
    Animation animation;
    private String typeString;
    private ProgressDialog progressDialog;
    public static final String BASE_URL = "https://oyunpuanla.com/zulaKasaSimulator/public/index.php/";
    public CircleFragment() {
    }

    public static CircleFragment newInstance(String type) {
        CircleFragment fragment = new CircleFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            typeString = getArguments().getString("type","1");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circle, container, false);
        animation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotation);

        TextView text = (TextView) view.findViewById(R.id.text);
        FrameLayout bg = (FrameLayout) view.findViewById(R.id.bg);
        switch (typeString) {
            case "DÜELLO":
                bg.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.background_quarter));
                text.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.swords,0,R.drawable.sketchedleftarrow);
                text.setCompoundDrawablePadding(5);
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog("ÇOK YAKINDA KASA DÜELLOSU SİZLERLE..");
                    }
                });
                break;
            case "KASA MAĞAZA":
                bg.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.background_quarter));
                text.setCompoundDrawablesWithIntrinsicBounds(0,R.mipmap.ic_launcher_foreground,0,R.drawable.sketchedleftarrow);
                text.setCompoundDrawablePadding(5);
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog = new ProgressDialog(requireContext());
                        progressDialog.setMessage("Kasa Simülasyonu başlatılıyor..");
                        progressDialog.show();
                        Intent intent = new Intent(requireContext(), kasaAcilimiMagaza.class);
                        startActivity(intent);
                        progressDialog.dismiss();
                    }
                });
                break;
            case "KASA OYUNU":
                bg.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.background_quarter));
                text.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.gameconsole,0,R.drawable.sketchedleftarrow);
                text.setCompoundDrawablePadding(5);
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog = new ProgressDialog(requireContext());
                        progressDialog.setMessage("Oyun yükleniyor. Lütfen bekleyiniz..");
                        progressDialog.show();

                        ZulaGameDataPostInterface zulaGameDataPostInterface = retrofit().create(ZulaGameDataPostInterface.class);
                        Call<List<ZulaSettings>> call = zulaGameDataPostInterface.getSettings("gaming");
                        call.enqueue(new Callback<List<ZulaSettings>>() {
                            @Override
                            public void onResponse(Call<List<ZulaSettings>> call, Response<List<ZulaSettings>> response) {
                                List<ZulaSettings> zulaSettings = response.body();
                                if (zulaSettings != null) {
                                    if (Integer.parseInt(zulaSettings.get(0).getOption_value()) == 1) {

                                        Intent intent = new Intent(requireContext(), kasaOyunuZula.class);
                                        startActivity(intent);
                                        progressDialog.dismiss();
                                    } else {
                                        openDialog(zulaSettings.get(0).getOption_message());
                                    }
                                } else {
                                    Toast.makeText(requireContext(), "Oyun Bakım hatası", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<ZulaSettings>> call, Throwable t) {

                            }
                        });
                    }
                });
                break;
            case "HAKKIMIZDA":
                bg.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.background_quarter));
                text.setCompoundDrawablePadding(5);
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(requireContext(), ActivityAboutUs.class);
                        startActivity(intent);
                    }
                });
                break;
        }
        text.setText(typeString);
        return view;
    }

    private void openDialog(String message) {
        View view = getLayoutInflater().inflate(R.layout.fragment_bottom_shet, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(view);
        TextView messsage = (TextView) view.findViewById(R.id.messsage);
        messsage.setText(message);
        // TextView gallery_sel = (TextView) view.findViewById(R.id.gallery);
        bottomSheetDialog.show();
    }
    public Retrofit retrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

}
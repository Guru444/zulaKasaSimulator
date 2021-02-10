package com.kasa.zulakasasimulator.view.loginView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kasa.zulakasasimulator.R;
import com.kasa.zulakasasimulator.model.zulaSettings.ZulaSettings;
import com.kasa.zulakasasimulator.service.userGamerService.ZulaGameDataPostInterface;
import com.kasa.zulakasasimulator.view.MainActivity;
import com.kasa.zulakasasimulator.view.aboutUS.ActivityAboutUs;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginFragment extends Fragment implements View.OnClickListener {
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    Button btn_login;
    Button btn_register;
    EditText kullaniciMail;
    EditText kullaniciSifre;
    CheckBox isCheckedPassword;
    private ProgressDialog progressDialog;
    TextView sifremiUnuttum;
    public static final String BASE_URL = "https://oyunpuanla.com/zulaKasaSimulator/public/index.php/";

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentHandle = inflater.inflate(R.layout.fragment_login, container, false);
        btn_login = (Button) fragmentHandle.findViewById(R.id.btn_login);
        kullaniciMail = (EditText) fragmentHandle.findViewById(R.id.kullaniciMail);
        kullaniciSifre = (EditText) fragmentHandle.findViewById(R.id.kullaniciSifre);
        isCheckedPassword = (CheckBox) fragmentHandle.findViewById(R.id.checkBox);
        sifremiUnuttum = (TextView) fragmentHandle.findViewById(R.id.sifremiUnuttum);
        sifremiUnuttum.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(kullaniciMail.getText())){
                    kullaniciMail.setHint("Şifresi unutulan mail adresi");
                    kullaniciMail.setTextColor(R.color.temaColor);
                    Toast.makeText(requireContext(),"Lütfen şifresini unuttuğunuz mail adresini giriniz",Toast.LENGTH_LONG).show();
                }
                else{
                    resetUserPassword(kullaniciMail.getText().toString());
                }

            }
        });

        SharedPreferences preferencesCheck = this.getActivity().getSharedPreferences("isCheck",0);
        Boolean getisCheck = preferencesCheck.getBoolean("checked",false);
        if(!getisCheck){
            openWelcomeGame();
        }


        SharedPreferences preferences = this.getActivity().getSharedPreferences("pref",0);
        String kullaniciMailAdress = preferences.getString("kullaniciMail","");
        String savedPassword = preferences.getString("kullaniciSifre","");
        Boolean savedChecked = preferences.getBoolean("isChecked",false);
        kullaniciMail.setText(kullaniciMailAdress);
        kullaniciSifre.setText(savedPassword);
        isCheckedPassword.isChecked();
        btn_login.setOnClickListener(this);

        // Inflate the layout for this fragment
        return fragmentHandle;


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            progressDialog = new ProgressDialog(requireContext());
            progressDialog.setMessage("Giriş kontrol ediliyor..");
            progressDialog.show();

            final String kMail =kullaniciMail.getText().toString();
            //"y.i.t.30.10.2007@gmail.com";
            final String kPass = kullaniciSifre.getText().toString();
            //"123456";
            boolean isChecked = isCheckedPassword.isChecked();


            if (TextUtils.isEmpty(kMail)) {
                Toast.makeText(getContext(), "Lütfen Mail adresinizi girin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(kPass)) {
                Toast.makeText(getContext(), "Lütfen şifrenizi girin", Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseAuth.signInWithEmailAndPassword(kMail, kPass)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                // there was an error
                                if (kPass.length() < 6) {
                                    kullaniciSifre.setError(getString(R.string.passuyari));
                                    progressDialog.dismiss();
                                } else {
                                    Toast.makeText(getContext(), "Mail adresi veya Şifre Hatalı", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                    openDestekHatti();
                                }
                            } else {
                                if(isChecked){
                                   SharedAdd(kullaniciMail.getText().toString(),kullaniciSifre.getText().toString(),isCheckedPassword.isChecked());
                                }
                                progressDialog.dismiss();
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
        }
    }

    public void SharedAdd(String kullaniciEmail, String password, Boolean check){
        SharedPreferences preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("kullaniciMail",kullaniciEmail); //int değer ekleniyor
        editor.putString("kullaniciSifre",password); //string değer ekleniyor
        editor.putBoolean("isChecked",check);
        editor.commit();
    }
    public void SharedWelcome(Boolean isCheck){
        SharedPreferences preferences = this.getActivity().getSharedPreferences("isCheck", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("checked",isCheck);
        editor.commit();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void openDestekHatti() {
        View view = getLayoutInflater().inflate(R.layout.destek_hatti_layout, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(view);
        TextView discord = (TextView) view.findViewById(R.id.discord);
        TextView instagram = (TextView) view.findViewById(R.id.instagram);

        discord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Destek hattı açılıyor...");
                progressDialog.show();

                ZulaGameDataPostInterface zulaGameDataPostInterface = retrofit().create(ZulaGameDataPostInterface.class);
                Call<List<ZulaSettings>> call = zulaGameDataPostInterface.getSettings("discord");
                call.enqueue(new Callback<List<ZulaSettings>>() {
                    @Override
                    public void onResponse(Call<List<ZulaSettings>> call, Response<List<ZulaSettings>> response) {
                        List<ZulaSettings> zulaSettings = response.body();
                        if (zulaSettings != null) {
                            Intent viewIntent =
                                    new Intent("android.intent.action.VIEW",
                                            Uri.parse(zulaSettings.get(0).getOption_value()));
                            startActivity(viewIntent);
                        } else {
                            Toast.makeText(getContext(), "Sosyal Medya Sunucu Hatası", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ZulaSettings>> call, Throwable t) {

                    }
                });
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://www.instagram.com/guru.ajans/"));
                startActivity(viewIntent);
            }
        });

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
    public void resetUserPassword(String email){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Doğrulanıyor..");
        progressDialog.show();

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(requireContext(), "Şifre sıfırlama talimatları e-postanıza gönderildi",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(requireContext(),
                                    "E-posta mevcut değil", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(requireContext(), "Lütfen kayıt olduğunuz mail adresini giriniz..", Toast.LENGTH_SHORT).show();
                openDestekHatti();
            }
        });
    }

    private void openWelcomeGame() {
        View view = getLayoutInflater().inflate(R.layout.guru_ajans_welcome, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(view);
        CheckBox tekrarGosterme = (CheckBox) view.findViewById(R.id.tekrarGosterme);
        tekrarGosterme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedWelcome(isChecked);
            }
        });

        bottomSheetDialog.show();
    }


}

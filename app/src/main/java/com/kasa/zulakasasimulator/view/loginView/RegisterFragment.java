package com.kasa.zulakasasimulator.view.loginView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kasa.zulakasasimulator.R;
import com.kasa.zulakasasimulator.model.zulaRegister.ZulaGameUsersInfo;
import com.kasa.zulakasasimulator.model.zulaRegister.ZulaUserResponse;
import com.kasa.zulakasasimulator.model.kullanicilar;
import com.kasa.zulakasasimulator.model.zulaSettings.ZulaSettings;
import com.kasa.zulakasasimulator.service.registerService.ZulaUserRegisterInterface;
import com.kasa.zulakasasimulator.service.userGamerService.ZulaGameDataPostInterface;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    public static final String BASE_URL = "https://oyunpuanla.com/zulaKasaSimulator/public/index.php/";
    private ProgressDialog progressDialog;
    EditText kullaniciAdi;
    EditText kullaniciSifre;
    EditText kullaniciMail;
    Button btn_register;
    TextView title;
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListener;

    FirebaseDatabase database;
    DatabaseReference myRef;

    private FirebaseAuth mAuth;


    private OnFragmentInteractionListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentHandle = inflater.inflate(R.layout.fragment_register, container, false);
        title = (TextView) fragmentHandle.findViewById(R.id.tv_title2);
        btn_register = (Button) fragmentHandle.findViewById(R.id.btn_register);
        kullaniciAdi = (EditText) fragmentHandle.findViewById(R.id.kullaniciAdi);
        kullaniciSifre = (EditText) fragmentHandle.findViewById(R.id.kullaniciSifre);
        kullaniciMail = (EditText) fragmentHandle.findViewById(R.id.kullaniciMail);

        btn_register.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        return fragmentHandle;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_register) {
            //Log.i("Tıklama", "tıklandı");
            mAuth = FirebaseAuth.getInstance();

            final String kMail = kullaniciMail.getText().toString();
            final String kAdi = kullaniciAdi.getText().toString();
            final String kPass = kullaniciSifre.getText().toString();
            ZulaGameUsersInfo zulaGameUsers = new ZulaGameUsersInfo(kAdi, kMail, md5(kPass));
            //API yolladık isteği
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            ZulaUserRegisterInterface zulaUserRegisterJava = retrofit.create(ZulaUserRegisterInterface.class);
            Call<ZulaUserResponse> call = zulaUserRegisterJava.zulaUserRegisterMethod(zulaGameUsers);
            // Log.i("hata", call.request().toString());
            if(isEmailValid(kMail)) {
                if (kPass.length() > 6) {
                    call.enqueue(new Callback<ZulaUserResponse>() {
                        @Override
                        public void onResponse(Call<ZulaUserResponse> call, Response<ZulaUserResponse> response) {
                            ZulaUserResponse zulaUserResponse = response.body();
                            if (response.body() != null && zulaUserResponse.getStatus()) {
                                Toast.makeText(requireContext(), zulaUserResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog = new ProgressDialog(requireContext());
                                progressDialog.setMessage("Lütfen Bekleyiniz");
                                progressDialog.show();
                                firebaseAuth.createUserWithEmailAndPassword(kMail, kPass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            //Kullanici Insert
                                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                            builder.setTitle("BAŞARILI");
                                            builder.setMessage("Oyuncu Kimliğiniz oluşturuldu. Lütfen giriş yapınız..");
                                            builder.setPositiveButton("TEŞEKKÜRLER", new DialogInterface.OnClickListener() {
                                                @RequiresApi(api = Build.VERSION_CODES.O)
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    progressDialog.dismiss();

                                                }
                                            });
                                            builder.setCancelable(false);
                                            builder.show();
                                            //insertData(kAdi, kMail, md5(kPass));

                                        } else {

                                            task.getException().printStackTrace();
                                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                            //Toast.makeText(getContext(), "" + task.getException(), Toast.LENGTH_LONG).show();
                                            AlertDialog alert = new AlertDialog.Builder(getContext()).create();
                                            alert.setTitle("Uyarı");

                                            if (errorCode.equals("ERROR_EMAIL_ALREADY_IN_USE")) {
                                                alert.setMessage("Girdiğiniz mail adresi kullanılmaktadır.");
                                                openDestekHatti();
                                            }
                                            alert.setIcon(R.drawable.kasa512x512);
                                            alert.setButton(DialogInterface.BUTTON_NEUTRAL, "Tamam", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                            alert.show();
                                        }
                                    }
                                });
                            } else if (response.body() != null) {
                                if (!zulaUserResponse.getStatus()) {
                                    Toast.makeText(requireContext(), zulaUserResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    openDestekHatti();
                                } else{
                                    Toast.makeText(requireContext(), "Response boş geliyor", Toast.LENGTH_SHORT).show();
                                    openDestekHatti();
                                }

                            } else
                                Toast.makeText(requireContext(), "Kayıt işleminde hata var", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ZulaUserResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(requireContext(), "Sunucu Hatası bulunmaktadır.", Toast.LENGTH_SHORT).show();
                            openDestekHatti();
                        }
                    });
                }
                else {
                    Toast.makeText(requireContext(), "Lütfen 6 karakterden uzun bir şifre giriniz.", Toast.LENGTH_LONG).show();
                    openDestekHatti();
                }
            }
            else
            {
                Toast.makeText(requireContext(),"Girdiğiniz mail adresi doğru formatta değildir.",Toast.LENGTH_LONG).show();
                openDestekHatti();
            }




            Log.i("bilgi", kMail + " " + kPass);
        }
    }

    private void insertData(String kullaniciAdi, String kullaniciMail, String kullaniciSifre) {

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Lütfen Bekleyiniz");
        progressDialog.show();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String format = simpleDateFormat.format(new Date());
        final String _kullaniciAdi = kullaniciAdi;
        final String _kullaniciMail = kullaniciMail;
        final String _kullaniciSifre = kullaniciSifre;
        final String katilimTarihi = format;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("zulaOyunKullanici");
        String key = database.getReference("zulaOyunKullanici").push().getKey();

        kullanicilar zulaOyunKullanici = new kullanicilar(_kullaniciAdi, _kullaniciMail, _kullaniciSifre, key, katilimTarihi, Long.valueOf("100"), Long.valueOf("0"), Long.valueOf("0"));

        ZulaGameUsersInfo zulaGameUsers = new ZulaGameUsersInfo(_kullaniciAdi, _kullaniciMail, _kullaniciSifre);
        myRef.child(key).setValue(zulaOyunKullanici).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("BAŞARILI");
                builder.setMessage("Oyuncu Kimliğiniz oluşturuldu. Lütfen giriş yapınız..");
                builder.setPositiveButton("TEŞEKKÜRLER", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.dismiss();

                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.ShowAlertDialogTheme);
                builder.setTitle("BAŞARILI");
                builder.setMessage("Kimlik Oluşturma Hatası");
                builder.show();
            }
        });




    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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


}


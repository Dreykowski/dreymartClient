package com.dreytech.clientdreymart;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dreytech.clientdreymart.Model.CheckUserResponse;
import com.dreytech.clientdreymart.Model.User;
import com.dreytech.clientdreymart.Retrofit.IDreyMarketAPI;
import com.dreytech.clientdreymart.Utils.Common;
import com.dreytech.clientdreymart.Utils.Tools;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1000;
    private static final int REQUEST_PERMISSION = 1001;
//    Button btnGetStarted;

    IDreyMarketAPI mService;

    //Wizard
    private static final int MAX_STEP = 4;

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private String about_title_array[] = {
            "Banyak pilihan untuk Kebutuhan Anda",
            "Pilih Barang Sesukamu",
            "Pengiriman Cepat dan Aman",
            "Nikmati Kemudahan Berbelanja"
    };
    private String about_description_array[] = {
            "Belanja Sesuka Kamu, Kami Menyediakan Banyak Pilihan.",
            "Pilih yang Kamu Suka, Kami Berikan Harga yang Terbaik!",
            "Pengiriman Aman dan Terjamin, dan Sampai di Rumah Kamu.",
            "Nikmati Kemudahan Berbelanja di Dreymart!",
    };
    private int about_images_array[] = {
            R.drawable.img_wizard_1,
            R.drawable.img_wizard_2,
            R.drawable.img_wizard_3,
            R.drawable.img_wizard_4
    };



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_PERMISSION:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
                break;
                default:
                    break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        // adding bottom dots
        bottomProgressDots(0);

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        Tools.setSystemBarColor(this, R.color.overlay_light_80);
        Tools.setSystemBarLight(this);


        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, REQUEST_PERMISSION);
//        btnGetStarted = findViewById(R.id.btnGetStarted);

        mService = Common.getAPI();

//        btnGetStarted.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startLoginPage(LoginType.PHONE);
//            }
//        });

        //check session
        if (AccountKit.getCurrentAccessToken() !=null)
        {
            final AlertDialog alertDialog = new SpotsDialog(MainActivity.this);
            alertDialog.show();
            alertDialog.setMessage("Mohon Tunggu...");
            //auto login
            AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                @Override
                public void onSuccess(final Account account) {

                    mService.checkUserExists(account.getPhoneNumber().toString())
                            .enqueue(new Callback<CheckUserResponse>() {
                                @Override
                                public void onResponse(Call<CheckUserResponse> call, Response<CheckUserResponse> response) {
                                    CheckUserResponse userResponse = response.body();
                                    if (userResponse.isExists())
                                    {
                                        //Mendapatkan informasi
                                        mService.getUserInformation(account.getPhoneNumber().toString())
                                                .enqueue(new Callback<User>() {
                                                    @Override
                                                    public void onResponse(Call<User> call, Response<User> response) {

                                                        alertDialog.dismiss();
                                                        Common.currentUser = response.body();

                                                        //UpdateToken
                                                        updateTokenToServer();

                                                        startActivity(new Intent(MainActivity.this,HomeActivity.class));
                                                        finish();

                                                    }

                                                    @Override
                                                    public void onFailure(Call<User> call, Throwable t) {

                                                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });


                                    }
                                    else
                                    {
                                        alertDialog.dismiss();

                                        showRegisterDialog(account.getPhoneNumber().toString());
                                    }
                                }

                                @Override
                                public void onFailure(Call<CheckUserResponse> call, Throwable t) {

                                }
                            });

                }

                @Override
                public void onError(AccountKitError accountKitError) {

                    Log.d("ERROR",accountKitError.getErrorType().getMessage());

                }
            });
        }


    }

    private void startLoginPage(LoginType loginType) {

        Intent intent = new Intent(this,AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder builder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(loginType,
                        AccountKitActivity.ResponseType.TOKEN);
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,builder.build());
        startActivityForResult(intent,REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE)
        {
            AccountKitLoginResult result = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (result.getError() != null)
            {
                Toast.makeText(this, ""+result.getError().getErrorType().getMessage(), Toast.LENGTH_SHORT).show();
            }
            else if (result.wasCancelled())
            {
                Toast.makeText(this, "Batal", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if (result.getAccessToken() !=null)
                {
                    final AlertDialog alertDialog = new SpotsDialog(MainActivity.this);
                    alertDialog.show();
                    alertDialog.setMessage("Mohon Tunggu...");

                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(final Account account) {

                            mService.checkUserExists(account.getPhoneNumber().toString())
                                    .enqueue(new Callback<CheckUserResponse>() {
                                        @Override
                                        public void onResponse(Call<CheckUserResponse> call, Response<CheckUserResponse> response) {
                                            CheckUserResponse userResponse = response.body();
                                            if (userResponse.isExists())
                                            {
                                                //Mendapatkan informasi
                                                mService.getUserInformation(account.getPhoneNumber().toString())
                                                        .enqueue(new Callback<User>() {
                                                            @Override
                                                            public void onResponse(Call<User> call, Response<User> response) {

                                                                alertDialog.dismiss();

                                                                Common.currentUser = response.body();
                                                                updateTokenToServer();
                                                                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                                                                finish();

                                                            }

                                                            @Override
                                                            public void onFailure(Call<User> call, Throwable t) {

                                                                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });


                                            }
                                            else
                                            {
                                                alertDialog.dismiss();

                                                showRegisterDialog(account.getPhoneNumber().toString());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<CheckUserResponse> call, Throwable t) {

                                        }
                                    });

                        }

                        @Override
                        public void onError(AccountKitError accountKitError) {

                            Log.d("ERROR",accountKitError.getErrorType().getMessage());

                        }
                    });
                }
            }

        }
    }

    private void showRegisterDialog(final String phone) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater inflater = this.getLayoutInflater();
        View register_layout = inflater.inflate(R.layout.register_layout,null);

        final EditText edt_name = (EditText)register_layout.findViewById(R.id.edt_name);
        final EditText edt_address = (EditText)register_layout.findViewById(R.id.edt_address);
        final EditText edt_birthdate = (EditText)register_layout.findViewById(R.id.edt_birthdate);

        Button btn_register = (Button)register_layout.findViewById(R.id.btn_register);

        builder.setView(register_layout);
        final AlertDialog dialog = builder.create();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();


                if (TextUtils.isEmpty(edt_address.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "Field tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edt_birthdate.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "Field tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edt_name.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "Field tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                    return;
                }


                final AlertDialog waitingDialog = new SpotsDialog(MainActivity.this);
                waitingDialog.show();
                waitingDialog.setMessage("Mohon Tunggu...");


                mService.registerNewUser(phone,
                        edt_name.getText().toString(),
                        edt_address.getText().toString(),
                        edt_birthdate.getText().toString())
                        .enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {

                                waitingDialog.dismiss();
                                User user = response.body();
                                if (TextUtils.isEmpty(user.getError_msg()))
                                {
                                    Toast.makeText(MainActivity.this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                                    Common.currentUser = response.body();
                                    //updateToken
                                    updateTokenToServer();
                                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                                    finish();
                                }

                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {

                                waitingDialog.dismiss();

                            }
                        });

            }


        });



        dialog.show();


    }

    private void printKeyHash() {
        try{
            PackageInfo info = getPackageManager().getPackageInfo("com.dreytech.clientdreymart",
                    PackageManager.GET_SIGNATURES);

            for (Signature signature:info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KEYHASH", Base64.encodeToString(md.digest(),Base64.DEFAULT));

            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    boolean isBackButtonClicked = false;

    @Override
    public void onBackPressed() {
        if (isBackButtonClicked)
        {
            super.onBackPressed();
            return;
        }
        this.isBackButtonClicked = true;
        Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        isBackButtonClicked = false;
        super.onResume();
    }

    private void updateTokenToServer() {
        FirebaseInstanceId.getInstance()
                .getInstanceId()
                .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        IDreyMarketAPI mService = Common.getAPI();
                        mService.updateToken(Common.currentUser.getPhone(),
                                instanceIdResult.getToken()
                                ,"0")
                                .enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        Log.d("DEBUG", response.body());
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Log.d("DEBUG", t.getMessage());
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void bottomProgressDots(int current_index) {
        LinearLayout dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        ImageView[] dots = new ImageView[MAX_STEP];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle);
            dots[i].setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current_index].setImageResource(R.drawable.shape_circle);
            dots[current_index].setColorFilter(getResources().getColor(R.color.light_green_600), PorterDuff.Mode.SRC_IN);
        }
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            bottomProgressDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        private Button btnNext;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.item_card_wizard_light, container, false);
            ((TextView) view.findViewById(R.id.title)).setText(about_title_array[position]);
            ((TextView) view.findViewById(R.id.description)).setText(about_description_array[position]);
            ((ImageView) view.findViewById(R.id.image)).setImageResource(about_images_array[position]);

            btnNext = (Button) view.findViewById(R.id.btn_next);

            if (position == about_title_array.length - 1) {
                btnNext.setText("Get Started");
            } else {
                btnNext.setText("Next");
            }


            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int current = viewPager.getCurrentItem() + 1;
                    if (current < MAX_STEP) {
                        // move to next screen
                        viewPager.setCurrentItem(current);
                    } else {
                        startLoginPage(LoginType.PHONE);
                    }
                }
            });

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return about_title_array.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

}

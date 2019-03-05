package com.dreytech.clientdreymart;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.dreytech.clientdreymart.Adapter.CartAdapter;
import com.dreytech.clientdreymart.Adapter.FavoriteAdapter;
import com.dreytech.clientdreymart.Database.ModelDB.Cart;
import com.dreytech.clientdreymart.Database.ModelDB.Favorite;
import com.dreytech.clientdreymart.Model.DataMessage;
import com.dreytech.clientdreymart.Model.MyResponse;
import com.dreytech.clientdreymart.Model.OrderResult;
import com.dreytech.clientdreymart.Model.Token;
import com.dreytech.clientdreymart.Retrofit.IDreyMarketAPI;
import com.dreytech.clientdreymart.Retrofit.IFCMService;
import com.dreytech.clientdreymart.Utils.Common;
import com.dreytech.clientdreymart.Utils.RecyclerItemTouchHelper;
import com.dreytech.clientdreymart.Utils.RecyclerItemTouchHelperListener;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements RecyclerItemTouchHelperListener {

    private static final int PAYMENT_REQUEST_CODE = 7777;
    RecyclerView recycler_cart;
    Button btn_place_order;

    RelativeLayout rootLayout;


    CompositeDisposable  compositeDisposable;

    CartAdapter cartAdapter;
    List<Cart> cartList = new ArrayList<>();

    IDreyMarketAPI mService;
    IDreyMarketAPI mServiceScalars;

    //Global String
    String token, amount, orderAddress, orderComment;
    HashMap<String, String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        compositeDisposable = new CompositeDisposable();

        mService = Common.getAPI();
        mServiceScalars = Common.getScalarsAPI();

        recycler_cart = (RecyclerView)findViewById(R.id.recycler_cart);
        recycler_cart.setLayoutManager(new LinearLayoutManager(this));
        recycler_cart.setHasFixedSize(true);

        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recycler_cart);


        btn_place_order = (Button)findViewById(R.id.btn_place_order);
        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });

        rootLayout = (RelativeLayout)findViewById(R.id.rootLayout);

        loadCartItems();

        loadToken();

    }

    private void loadToken() {

        final android.app.AlertDialog waitingDialog = new SpotsDialog(CartActivity.this);
        waitingDialog.show();
        waitingDialog.setMessage("Mohon Tunggu...");

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Common.API_TOKEN_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                waitingDialog.dismiss();
                btn_place_order.setEnabled(false);
                Toast.makeText(CartActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                waitingDialog.dismiss();

                token = responseString;
                btn_place_order.setEnabled(true);
            }
        });
    }

    private void placeOrder() {

        if (Common.currentUser !=null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Submit Order");

            View submit_order_layout = LayoutInflater.from(this).inflate(R.layout.submit_order_layout, null);

            final EditText edt_comment = (EditText) submit_order_layout.findViewById(R.id.edt_comment);
            final EditText edt_other_address = (EditText) submit_order_layout.findViewById(R.id.edt_other_address);

            final RadioButton rdi_user_button = (RadioButton) submit_order_layout.findViewById(R.id.rdi_user_address);
            final RadioButton rdi_other_address = (RadioButton) submit_order_layout.findViewById(R.id.rdi_other_address);

            final RadioButton rdi_credit_card = (RadioButton)submit_order_layout.findViewById(R.id.rdi_credit_card);
            final RadioButton rdi_cod = (RadioButton)submit_order_layout.findViewById(R.id.rdi_cod);



            //Event
            rdi_user_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        edt_other_address.setEnabled(false);
                }
            });

            rdi_other_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        edt_other_address.setEnabled(true);
                }
            });

            builder.setView(submit_order_layout);

            builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

//                    if (rdi_credit_card.isChecked()) {
//                        orderComment = edt_comment.getText().toString();
//
//                        if (rdi_user_button.isChecked())
//                            orderAddress = Common.currentUser.getAddress();
//                        else if (rdi_other_address.isChecked())
//                            orderAddress = edt_other_address.getText().toString();
//                        else
//                            orderAddress = "";
//
//                        //Pembayaran via Braintree
//                        DropInRequest dropInRequest = new DropInRequest().clientToken(token);
//
//                        startActivityForResult(dropInRequest.getIntent(CartActivity.this), PAYMENT_REQUEST_CODE);
//                    }
                    if (rdi_cod.isChecked())
                    {
                        orderComment = edt_comment.getText().toString();

                        if (rdi_user_button.isChecked())
                            orderAddress = Common.currentUser.getAddress();
                        else if (rdi_other_address.isChecked())
                            orderAddress = edt_other_address.getText().toString();
                        else
                            orderAddress = "";

                        //Submit order
                        compositeDisposable.add(
                                Common.cartRepository.getCartItems()
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribeOn(Schedulers.io())
                                        .subscribe(new Consumer<List<Cart>>() {
                                            @Override
                                            public void accept(List<Cart> carts) throws Exception {
                                                if (!TextUtils.isEmpty(orderAddress))
                                                    sendOrderToServer(Common.cartRepository.sumPrice(),
                                                            carts,
                                                            orderComment,orderAddress,
                                                            "COD");
                                                else
                                                    Toast.makeText(CartActivity.this, "Alamat tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                        );
                    }
                }
            });

            builder.show();
        }
        else {
//            //Diperlukan login

            AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
            builder.setTitle("Belum Login ?");
            builder.setMessage("Tolong login atau registrasi akun untuk melanjutkan");
            builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    startActivity(new Intent(CartActivity.this,MainActivity.class));
                    finish();
                }
            }).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYMENT_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String strNonce = nonce.getNonce();

                if (Common.cartRepository.sumPrice() < 100000)
                {
                    Toast.makeText(this, "Total Minimal belanja Rp. 100.000", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    amount = String.valueOf(Common.cartRepository.sumPrice());
                    params = new HashMap<>();

                    params.put("amount", amount);
                    params.put("nonce", strNonce);

                    sendPayment();
                }
            }
            else if (resultCode == RESULT_CANCELED)
                Toast.makeText(this, "Transaksi Dibatalkan", Toast.LENGTH_SHORT).show();
            else
            {
                Exception error = (Exception)data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.e("DREY_ERROR",error.getMessage());
            }
        }
    }

    private void sendPayment() {
        mServiceScalars.payment(params.get("nonce"),params.get("amount"))
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().toString().contains("Successful"))
                        {
                            Toast.makeText(CartActivity.this, "Transaction Successful", Toast.LENGTH_SHORT).show();
                            //Submit order
                            compositeDisposable.add(
                                    Common.cartRepository.getCartItems()
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribeOn(Schedulers.io())
                                            .subscribe(new Consumer<List<Cart>>() {
                                                @Override
                                                public void accept(List<Cart> carts) throws Exception {
                                                    if (!TextUtils.isEmpty(orderAddress))
                                                        sendOrderToServer(Common.cartRepository.sumPrice(),
                                                                carts,
                                                                orderComment,orderAddress,
                                                                "COD");
                                                    else
                                                        Toast.makeText(CartActivity.this, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                            );
                        }
                        else
                        {
                            Toast.makeText(CartActivity.this, "Transaction failed", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("DREY_INFO", response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("DREY_INFO", t.getMessage());
                    }
                });
    }


    private void sendOrderToServer(float sumPrice, List<Cart> carts, String orderComment, String orderAddress, String paymentMethod) {
        if (carts.size() > 0)
        {
            String orderDetail = new Gson().toJson(carts);

            mService.submitOrder(sumPrice, orderDetail, orderComment, orderAddress, Common.currentUser.getPhone(),paymentMethod)
                    .enqueue(new Callback<OrderResult>() {
                        @Override
                        public void onResponse(Call<OrderResult> call, Response<OrderResult> response) {
                            sendNotificationToServer(response.body());
                        }

                        @Override
                        public void onFailure(Call<OrderResult> call, Throwable t) {

                        }
                    });
        }
    }

    private void sendNotificationToServer(final OrderResult orderResult) {
        //GEt Server Token
        mService.getToken("server_app_01", "1")
                .enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        //Mengirim notifikasi dengan token
                        Map<String,String> contentSend = new HashMap<>();
                        contentSend.put("title","Dreymart");
                        contentSend.put("message", "Kamu mempunyai pesanan baru "+orderResult.getOrderId());
                        DataMessage dataMessage = new DataMessage();
                        if (response.body().getToken() != null)
                            dataMessage.setTo(response.body().getToken());
                        dataMessage.setData(contentSend);

                        IFCMService ifcmService = Common.getGetFCMService();
                        ifcmService.sendNotification(dataMessage)
                                .enqueue(new Callback<MyResponse>() {
                                    @Override
                                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                        if (response.code() == 200)
                                        {
                                            if (response.body().success == 1)
                                            {
                                                Toast.makeText(CartActivity.this, "Terima Kasih Telah Memesan", Toast.LENGTH_SHORT).show();
                                                //Clear Cart
                                                Common.cartRepository.emptyCart();
                                                finish();
                                            }
                                            else
                                            {
                                                Toast.makeText(CartActivity.this, "Gagal mengirim notifikasi", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<MyResponse> call, Throwable t) {
                                        Toast.makeText(CartActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Toast.makeText(CartActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void loadCartItems() {
        compositeDisposable.add(
                Common.cartRepository.getCartItems()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer<List<Cart>>() {
                        @Override
                        public void accept(List<Cart> carts) throws Exception {
                            displayCartItem(carts);
                        }
                    })
        );
    }

    private void displayCartItem(List<Cart> carts) {
        cartList = carts;
        cartAdapter = new CartAdapter(this,carts);
        recycler_cart.setAdapter(cartAdapter);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCartItems();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CartAdapter.CartViewHolder)
        {
            String name = cartList.get(viewHolder.getAdapterPosition()).name;

            final Cart deleteItem = cartList.get(viewHolder.getAdapterPosition());
            final int deleteIndex = viewHolder.getAdapterPosition();

            //delete item from adapter
            cartAdapter.removeItem(deleteIndex);
            //DELETE item from database room
            Common.cartRepository.deleteCartItem(deleteItem);
            Snackbar snackbar = Snackbar.make(rootLayout, new StringBuilder(name).append(" removed from Favorites list").toString(),
                    Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartAdapter.restoreItem(deleteItem,deleteIndex);
                    Common.cartRepository.insertToCart(deleteItem);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }



}

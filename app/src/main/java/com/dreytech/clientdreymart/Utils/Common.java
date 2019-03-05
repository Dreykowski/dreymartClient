package com.dreytech.clientdreymart.Utils;

import com.dreytech.clientdreymart.Database.DataSource.CartRepository;
import com.dreytech.clientdreymart.Database.DataSource.FavoriteRepository;
import com.dreytech.clientdreymart.Database.Local.DREYRoomDatabase;
import com.dreytech.clientdreymart.Model.Barang;
import com.dreytech.clientdreymart.Model.Category;
import com.dreytech.clientdreymart.Model.Order;
import com.dreytech.clientdreymart.Model.User;
import com.dreytech.clientdreymart.Retrofit.FCMClient;
import com.dreytech.clientdreymart.Retrofit.IDreyMarketAPI;
import com.dreytech.clientdreymart.Retrofit.IFCMService;
import com.dreytech.clientdreymart.Retrofit.RetrofitClient;
import com.dreytech.clientdreymart.Retrofit.RetrofitScalarsClient;

import java.util.ArrayList;
import java.util.List;

public class Common {
    public static final String BASE_URL = "http://10.10.1.223/dreymarket/";
    public static final String API_TOKEN_URL = "http://10.10.1.223/dreymarket/braintree/main.php";

    public static final String TOPPING_MENU_ID ="7";

    public static User currentUser = null;
    public static Category currentCategory = null;
    public static Order currentOrder=null;

    public static List<Barang> toppingList = new ArrayList<>();

    public static double toppingPrice = 0.0;
    public static List<String> toppingAdded = new ArrayList<>();

    //Hold
    public static int sizeOfCup = -1; // -1 : tidak ada pilihan (error), 0 : M, 1:L
    public static int sugar = -1; // -1 : tidak ada pilihan
    public static int ice = -1; //

    //Datavase
    public static DREYRoomDatabase dreyRoomDatabase;
    public static CartRepository cartRepository;
    public static FavoriteRepository favoriteRepository;


    private static final String FCM_API = "https://fcm.googleapis.com/";

    public static IFCMService getGetFCMService()
    {
        return FCMClient.getClient(FCM_API).create(IFCMService.class);
    }

    public static IDreyMarketAPI getAPI()
    {
        return RetrofitClient.getClient(BASE_URL).create(IDreyMarketAPI.class);
    }

    public static IDreyMarketAPI getScalarsAPI()
    {
        return RetrofitScalarsClient.getScalarsClient(BASE_URL).create(IDreyMarketAPI.class);
    }

    public static String convertCodeToStatus(int orderStatus) {
        switch (orderStatus)
        {
            case 0:
                return "Placed";
            case 1:
                return "Sedang diproses";
            case 2:
                return "Dalam perjalanan";
            case 3:
                return "Sampai ditujuan";
            case -1:
                return "Dibatalkan";
                default:
                    return "Pemesanan gagal";
        }
    }
}

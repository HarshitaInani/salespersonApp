package com.example.user.mp_salesperson;

/**
 * Created by Krishna on 12/24/2016.
 */

public class Constant {

    public static String Tag = "ShopKrinaRetailer";

    // Live
    //      public static String BASE_URL = "http://ec2-54-214-137-77.us-west-2.compute.amazonaws.com/api/";
    //      public static String BASE_URL_Images = "http://ec2-54-214-137-77.us-west-2.compute.amazonaws.com/";
    // Live 2 latest
    //   public static String BASE_URL = "http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/";
    //   public static String BASE_URL_Images = "http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/";
    // http://shopkiranamarketplace.moreyeahs.net/api/Peoples?mob=8871899084&password=123456
    // http://shopkiranamarketplace.moreyeahs.net
    //   public static String BASE_URL = "http://shopkiranamarketplace.moreyeahs.net/api/";
    //   public static String BASE_URL_Images = "http://shopkiranamarketplace.moreyeahs.net/";


    // Testing

//
// public static String BASE_URL_Images = "http://SKDeliveryApp.moreyeahs.net/";
// public static String BASE_URL = "http://SKDeliveryApp.moreyeahs.net/api/";
//


// Testing api 2


//   public static String BASE_URL = "http://shopkiranatesting.moreyeahs.net/api/";
//   public static String BASE_URL_Images = "http://shopkiranatesting.moreyeahs.net/";
    //http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com:808/api/

    public static String BASE_URL_Slider_API = "http://137.59.52.130/api/Slider";
    public static String BASE_URL_Images = "http://137.59.52.130/";
    public static String BASE_URL = "http://137.59.52.130/api/";//live url
    // public static String BASE_URL = "http://137.59.52.130:8080/api/";//test url
    public static String BASE_URL_Images1 = "https://s3.ap-south-1.amazonaws.com/fsaapi/imageupload/";
    // folder location   http://137.59.52.130/UploadedImages/
    public static final String UPLOAD_URL = "http://137.59.52.130/api/imageupload";
    public static final String SIGN_UP_IMAGE_URL = "http://137.59.52.130/api/imageupload";
    public static final String CITY_URL = "http://137.59.52.130/api/City";
    public static final String AREA_URL = "http://137.59.52.130/api/area/all";
    //http://skdeliveryapp.moreyeahs.net/api/
    //api/Peoples
    public static final String BASE_SLIDER_IMAGE = "http://137.59.52.130/Sliderimages/";
    public static String BASE_URL_SLIDER = BASE_URL + "Slider";
    public static String BASE_URL_APP_VERSION = BASE_URL + "appVersion";
    public static String BASE_URL_LOGIN = BASE_URL + "signup";
    public static String BASE_URL_REGISTRATION = BASE_URL + "Customers";
    public static String BASE_URL_SIGNUP_UPDATE = BASE_URL + "signup";
    public static String BASE_URL_SALES_LOGIN = BASE_URL + "Peoples";
    // public static String BASE_URL_LOGIN = BASE_URL + "Peoples";
    public static String BASE_URL_REQUEST_BRAND = BASE_URL + "request";
    public static String BASE_URL_FEEDBACK = BASE_URL + "feedback";
    public static String BASE_URL_SIGNUP = BASE_URL + "signup?type=ids";
    // public static String BASE_URL_Active_Retialer =BASE_URL+"NotVisit/Active_Retailer";
    public static String BASE_URL_Active_Retialer = "http://137.59.52.130/api/NotVisit/Active_Retailer";
    public static String ShopBY_PREF = "shopbycompany";
    public static String ShopBY_PREF_OBJ = "shopbycompanyobj";
    public static String ALL_BRANDS_PREF = "AllBrandPref";
    public static String ALL_BRANDS_PREFOBJ = "AllBrandPrefobj";
    public static String BASE_URL_MULTIMOQ = BASE_URL + "itemMaster/MultiMoqItem";
//   public static String BASE_URL_ITEM_MASTER = BASE_URL + "itemMaster";
    public static String BASE_URL_ITEM_MASTER = BASE_URL + "itemMaster/category";
    public static String BASE_URL_ITEM_SEARCH = BASE_URL + "itemMaster";
    public static String BASE_URL_BRAND_PROMOTION = BASE_URL + "BrandPramotions";
    public static String BASE_URL_BRAND_WISE_PROMOTION = BASE_URL + "Brandwisepramotion/GetBrandWiseData";
    public static String BASE_URL_OFFER_ITEM = BASE_URL + "offer/GetAllOffers";
    public static String BASE_URL_ALL_TOP_ADDED_ITEM = BASE_URL + "AppPromotion/AllTopAddedItem";
    public static String BASE_URL_APP_PROMOTION = BASE_URL + "AppPromotion";
    public static String BASE_URL_TODAY_DHAMAKA = BASE_URL + "AppPromotion/GetTodayDhamaka";
    public static String BASE_URL_NEWLY_ADDED_BRANDS = BASE_URL + "AppPromotion/NewlyAddedBrands";
    public static String BASE_URL_MOST_SELLED_ITEM = BASE_URL + "AppPromotion/MostSelledItem";
    public static String BASE_URL_EMPTY_STOCK = BASE_URL + "CurrentStock/GetEmptyStockItem";
    public static String BASE_URL_BULK_ITEM = BASE_URL + "AppPromotion/GetBulkItem";
    public static String BASE_URL_FIND_ITEM_HIGH_DP = BASE_URL + "itemMaster/FindItemHighDP";
    public static String BASE_URL_EXECLUSIVE_BRAND = BASE_URL + "BrandPramotions/Exclusivebrand";
    public static String BASE_URL_NEWLY_ADDED_BRAND_ITEM = BASE_URL + "ssitem";
    public static String BASE_URL_ITEM_LIST = BASE_URL + "ssitem";
    public static String BASE_URL_REPORT = BASE_URL + "Comparison1/SalesManApp?";
    public static String BASE_URL_PLACE_ORDER = BASE_URL + "OrderMastersAPI";
    public static String BASE_URL_MY_ORDERS = BASE_URL + "Orders";
    public static String RETAILER_BEAN_PREF = "retailerBeanPref";
    public static String RETAILER_BEAN_PREF_OBJ = "retailerBeanPrefObj";
    public static String BASECAT_CAT_SUBCAT_PREF = "BaseCatSubCatPref";
    public static String BASECAT_CAT_SUBCAT_PREFOBJ = "BaseCatSubCatPrefObj";
    public static String POPULAR_BRANDS_PREF = "PopularBrandsPref";
    public static String POPULAR_BRANDS_PREF1 = "PopularBrandsPref1";
    public static String POPULAR_BRANDS_PREF2 = "PopularBrandsPref2";
    public static String POPULAR_BRANDS_PREFOBJ = "PopularBrandsPrefObj";
    public static String POPULAR_BRANDS_PREFOBJ1 = "PopularBrandsPrefObj1";
    public static String POPULAR_BRANDS_PREFOBJ2 = "PopularBrandsPrefObj2";
    public static String SUB_SUB_CAT_ITEM_PREF = "SubSubCatItemPref";
    public static String APP_PROMOTION_PREFOBJ = "AppPromotionPrefObj";
    public static String TODAY_DHAMAKA_PREFOBJ = "TodayDhamakaPrefObj";
    public static String NEWLY_ADDED_BRANDS_PREFOBJ = "NewlyAddedBrandsPrefObj";
    public static String ALL_TOP_ADDED_PREFOBJ = "AllTOPAddedPrefObj";
    public static String BULK_ITEM_PREFOBJ = "BulkItemPrefObj";
    public static String MOST_SELLED_ITEM_PREFOBJ = "MostSelledItemPrefObj";
    public static String EMPTY_STOCK_PREFOBJ = "EmptyStockPrefObj";
    public static String FIND_HIGH_DP_PREFOBJ = "FindHighDpPrefObj";
    public static String EXECLUSIVE_BRANDS_PREFOBJ = "ExecutiveBrandsPrefObj";
    public static String HOMECARE_ITEM_PREFOBJ = "homecareitemprefobj";
    public static String HOMECARE_ITEM_PREF = "homecareitempref";
    public static String PERSONAL_ITEM_PREFOBJ = "personalitemprefobj";
    public static String PERSONAL_ITEM_PREF = "personalitem";
    public static String GROCERY_ITEM_PREFOBJ = "groceryitemprefobj";
    public static String GROCERY_ITEM_PREF = "groceryitempref";
    public static String APP_PROMOTION_PREF = "AppPromotionPref";
    public static String TODAY_DHAMAKA_PREF = "TodayDhamakaPref";
    public static String NEWLY_ADDED_BRANDS_PREF = "NewlyAddedBrandsPref";
    public static String ALL_TOP_ADDED_PREF = "AllTOPAddedPref";
    public static String BULK_ITEM_PREF = "BulkItemPref";
    public static String MOST_SELLED_ITEM_PREF = "MostSelledItemPref";
    public static String EMPTY_STOCK_PREF = "EmptyStockPref";
    public static String FIND_HIGH_DP_PREF = "FindHighDpPref";
    public static String EXECLUSIVE_BRANDS_PREF = "ExeclusiveBrandsPref";
    public static String OFFER_SELLED_ITEM_PREF = "OfferSelledItemPref";
    public static String OFFER_SELLED_ITEM_PREFOBJ = "OfferSelledItemPrefObj";
    public static String OFFER_ALL_ITEM_PREF = "OfferAllItemPref";
    public static String OFFER_ALL_ITEM_PREFOBJ = "OfferAllItemPrefObj";
    public static String CART_ITEM_ARRAYLIST_PREF = "CartItemArraylistPref";
    public static String CART_ITEM_ARRAYLIST_PREF_OBJ = "CartItemArraylistPrefObj";
    public static String MY_ORDER_PREF = "myOrderPref";
    public static String MY_ORDER_PREF_OBJ = "myOrderPrefObj";
    public static String APP_VERSION_PREF = "appVersionPref";
    public static String APP_VERSION_PREF_OBJ = "APP_VERSIONPrefObj";
    public static String BASE_URL_SKCODE = BASE_URL + "SalePersonRetailer";
    public static String BASE_URL_MYBID = BASE_URL + "AsignDay";
    public static String BASE_URL_ORDER_NO_PLACED = BASE_URL + "AsignDay/addBeat";
    public static String BASE_URL_REPORT_30_DAYS = BASE_URL + "target/Report?day=30day&skcode=";
    public static String BASE_URL_REPORT_3_MONTHS = BASE_URL + "target/Report?day=3month&skcode=";
    public static String BASE_URL_MYSALES_DATA = BASE_URL + "target/Report";
    public static String BASE_URL_MY_WALLET = BASE_URL + "wallet";
    public static String BASE_URL_REWARD_ITEMS = BASE_URL + "RewardItem";
    public static String BASE_URL_BUT_REWARD_ITEMS = BASE_URL + "OrderMastersAPI/dreamitem";
    public static String BASE_URL_MILESTONE = BASE_URL + "pointConversion/milestone";
    public static String BASE_URL_DELIVERY_CHARGES = BASE_URL + "DeliveryCharge/dc";
    public static String BASE_URL_ACTION_TASK = BASE_URL + "ActionTask/appActionTask";
    public static String BASE_URL_ACTION_TASK_MULTI = BASE_URL + "ActionTask/Search";
    public static String BASE_URL_ACTION_TASK_POST = BASE_URL + "ActionTask";
    public static String BASE_URL_CHEQUE_BOUNCE = BASE_URL + "SalesSettlement/SearchSkcode";
    public static String BASE_URL_SUB_SLIDER = BASE_URL + "CategoryImage/";
    public static String BASE_URL_CITY = BASE_URL + "City";
    public static String BASE_URL_AREA = BASE_URL + "area/all";
    public static String DELIVERY_CHARGE_JSON = "[\n" +
            "  {\n" +
            "    \"id\": 1,\n" +
            "    \"CompanyId\": 3,\n" +
            "    \"min_Amount\": 1000,\n" +
            "    \"max_Amount\": 20000,\n" +
            "    \"del_Charge\": 100,\n" +
            "    \"IsActive\": true,\n" +
            "    \"isDeleted\": true,\n" +
            "    \"desc\": null,\n" +
            "    \"CreatedDate\": \"2017-03-27T19:36:09.11-07:00\",\n" +
            "    \"UpdatedDate\": \"2017-03-30T19:44:52.083-07:00\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 2,\n" +
            "    \"CompanyId\": 3,\n" +
            "    \"min_Amount\": 500,\n" +
            "    \"max_Amount\": 1000,\n" +
            "    \"del_Charge\": 10,\n" +
            "    \"IsActive\": true,\n" +
            "    \"isDeleted\": true,\n" +
            "    \"desc\": null,\n" +
            "    \"CreatedDate\": \"2017-03-27T19:36:54.733-07:00\",\n" +
            "    \"UpdatedDate\": \"2017-03-30T19:44:45.317-07:00\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 3,\n" +
            "    \"CompanyId\": 3,\n" +
            "    \"min_Amount\": 20002,\n" +
            "    \"max_Amount\": 10001,\n" +
            "    \"del_Charge\": 1000,\n" +
            "    \"IsActive\": true,\n" +
            "    \"isDeleted\": true,\n" +
            "    \"desc\": null,\n" +
            "    \"CreatedDate\": \"2017-03-27T19:39:02.813-07:00\",\n" +
            "    \"UpdatedDate\": \"2017-03-30T19:44:49.193-07:00\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 4,\n" +
            "    \"CompanyId\": 3,\n" +
            "    \"min_Amount\": 1000,\n" +
            "    \"max_Amount\": 10000,\n" +
            "    \"del_Charge\": 10,\n" +
            "    \"IsActive\": true,\n" +
            "    \"isDeleted\": false,\n" +
            "    \"desc\": \"Desc firsttt\",\n" +
            "    \"CreatedDate\": \"2017-03-31T12:06:57.893-07:00\",\n" +
            "    \"UpdatedDate\": \"2017-03-31T12:06:57.893-07:00\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 5,\n" +
            "    \"CompanyId\": 3,\n" +
            "    \"min_Amount\": 452,\n" +
            "    \"max_Amount\": 32525,\n" +
            "    \"del_Charge\": 23523,\n" +
            "    \"IsActive\": true,\n" +
            "    \"isDeleted\": true,\n" +
            "    \"desc\": null,\n" +
            "    \"CreatedDate\": \"2017-03-30T19:49:41.227-07:00\",\n" +
            "    \"UpdatedDate\": \"2017-03-31T10:47:28.567-07:00\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 6,\n" +
            "    \"CompanyId\": 3,\n" +
            "    \"min_Amount\": 500,\n" +
            "    \"max_Amount\": 999,\n" +
            "    \"del_Charge\": 5,\n" +
            "    \"IsActive\": true,\n" +
            "    \"isDeleted\": false,\n" +
            "    \"desc\": \"Desc\",\n" +
            "    \"CreatedDate\": \"2017-03-31T12:05:29.923-07:00\",\n" +
            "    \"UpdatedDate\": \"2017-03-31T12:05:29.923-07:00\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 7,\n" +
            "    \"CompanyId\": 3,\n" +
            "    \"min_Amount\": 10001,\n" +
            "    \"max_Amount\": 20000,\n" +
            "    \"del_Charge\": 200,\n" +
            "    \"IsActive\": true,\n" +
            "    \"isDeleted\": false,\n" +
            "    \"desc\": \"Desc Third\",\n" +
            "    \"CreatedDate\": \"2017-03-31T12:06:14.987-07:00\",\n" +
            "    \"UpdatedDate\": \"2017-03-31T12:06:14.987-07:00\"\n" +
            "  }\n" +
            "]";

}

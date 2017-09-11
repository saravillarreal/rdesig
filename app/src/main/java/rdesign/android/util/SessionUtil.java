package rdesign.android.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import rdesign.android.R;
import rdesign.android.bean.Hit;

/**
 * Created by saravillarreal on 9/5/17.
 */

public class SessionUtil {
    public final static String TAG = "SessionUtil";
    public  static ArrayList<Hit> arrayListItems = null;


    public static ArrayList<Hit> getArrayListItems() {
        return arrayListItems;
    }

    public static void setArrayListItems(ArrayList<Hit> arrayListItems) {
        SessionUtil.arrayListItems = arrayListItems;
    }

    public static void savePreferencesItemFlickr(Context act, ArrayList<Hit> items) {
        SharedPreferences prefs = act.getSharedPreferences(act.getString(R.string.items_arraylist), act.MODE_PRIVATE);
        Gson gson = new Gson();
        String longString = gson.toJson(items);
        prefs.edit().putString(act.getString(R.string.items_arraylist), longString).commit();

    }

    public static ArrayList<Hit> getPreferencesItems(Context mContext, String patron) {

        ArrayList<Hit> mItem = new ArrayList<>();
        Gson gson = new Gson();
        SharedPreferences prefs = mContext.getSharedPreferences(patron, mContext.MODE_PRIVATE);
        //get from shared prefs
        String storedAdvertisementString = prefs.getString(patron, "error");
        java.lang.reflect.Type type = new TypeToken<ArrayList<Hit>>() {
        }.getType();
        if (!storedAdvertisementString.equalsIgnoreCase("error")) {
            mItem = gson.fromJson(storedAdvertisementString, type);
        }
        return mItem;

    }
}

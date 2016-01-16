package helpers;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miss.temp.R;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    private static final int TOAST_TEXT_SIZE = 20;

    public static int getDrawableId(String drawableName) {
        try {
            Class res = R.drawable.class;
            Field field = res.getField(drawableName);
            return field.getInt(null);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static Long persistDate(Date date) {
        if (date != null) {
            return date.getTime();
        }
        return null;
    }

    public static Date loadDate(Cursor cursor, int index) {
        if (cursor.isNull(index)) {
            return null;
        }

        return new Date(cursor.getLong(index));
    }

    public static  String getDateAsString(Date date){
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String dateAsString = formatter.format(date);

        return dateAsString;
    }

    public static void showNotification(String notification, Context context){
        Toast toast = Toast.makeText(context, notification, Toast.LENGTH_SHORT);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTextSize(TOAST_TEXT_SIZE);
        toastTV.setTextColor(Color.WHITE);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}

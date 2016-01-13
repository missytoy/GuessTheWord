package helpers;


import android.database.Cursor;
import android.widget.Button;

import com.example.miss.temp.R;

import java.lang.reflect.Field;
import java.util.Date;

public class Utils {

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
}

package helpers;


import com.example.miss.temp.R;

import java.lang.reflect.Field;

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
}

package layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miss.temp.R;

import java.util.ArrayList;
import java.util.List;

import models.Category;

public class CategoryListAdapter extends ArrayAdapter<Category> {
    private final Context context;
    private final List<Category> categories;

    public CategoryListAdapter(Context context, List<Category> values) {
        super(context, R.layout.category_listview_item, values);
        this.context = context;
        this.categories = new ArrayList<Category>(values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.category_listview_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.cat_name);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.cat_image);
        textView.setText(categories.get(position).getName());

        imageView.setImageResource(categories.get(position).getImageResourceId());

        return rowView;
    }
}

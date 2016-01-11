package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miss.temp.R;

import java.util.ArrayList;
import java.util.List;

import models.Category;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {
//    private DataAccess data;

    private List<Category> categories;

    public CategoriesFragment() {
        // Required empty public constructor
    }

//    public void setDb(DataAccess database){
//        this.data = database;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        Bundle args = this.getArguments();
        categories = new ArrayList<Category>((List<Category>) args.getSerializable("categories_array"));

        return view;
    }

}

package layout;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.miss.temp.R;

import java.util.ArrayList;
import java.util.List;

import models.Category;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment implements AdapterView.OnItemClickListener {

    OnListViewItemSelected onCategorySelected;

    private List<Category> categories;
    private ListView categoryListView;

    public CategoriesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        Bundle args = this.getArguments();
        categories = new ArrayList<Category>((List<Category>) args.getSerializable("categories_array"));

        categoryListView = (ListView) view.findViewById(R.id.categories_list_view);
        categoryListView.setOnItemClickListener(this);


        CategoryListAdapter adapter = new CategoryListAdapter(getContext(), categories);
        categoryListView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Category selectedCategory = (Category) categoryListView.getItemAtPosition(position);
        int selectedCategoryId = selectedCategory.getId();

        this.onCategorySelected.onCategoryItemClicked(selectedCategoryId);
    }

    public  interface  OnListViewItemSelected{
        public void onCategoryItemClicked(int categoryId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity  =(Activity) context;

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            onCategorySelected = (OnListViewItemSelected) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnListViewItemSelected");
        }
    }

}

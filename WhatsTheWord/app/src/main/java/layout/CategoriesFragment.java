package layout;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miss.temp.R;

import java.util.List;

import data.DataAccess;
import models.Category;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {
    private DataAccess data;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    public void setDb(DataAccess database){
        this.data = database;
    }

    private class GetDatabaseTask extends AsyncTask<DataAccess, Void, List<Category>> {
        @Override
        protected List<Category> doInBackground(DataAccess... params) {
            List<Category> categories = params[0].getAllCategories();

            return categories;
        }

        @Override
        protected void onPostExecute(List<Category> categories) {
//            Integer numberOfPeople = categories.size();
//
//            TextView numberView = (TextView) getView().findViewById(R.id.number_content);
//            numberView.setText(numberOfPeople.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

}

package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.miss.temp.R;

import java.util.ArrayList;
import java.util.List;

import models.Category;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewWord extends Fragment {

    private String[] categoriesNames;
    private List<Category> categories;
    private ArrayAdapter<String> spinnerAdapter;
    public int pos;
    Spinner categoryName;

    public AddNewWord() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_word, container, false);

        // this is only for reference how to get the id of a drawable resource by name;
//        Button objectBtn = (Button) view.findViewById(R.id.object_btn);
//        int backgroundReId = Utils.getResId("animal_category");
//        objectBtn.setBackgroundResource(backgroundReId);
        Bundle args = this.getArguments();
        categories = new ArrayList<Category>((List<Category>) args.getSerializable("categories_array"));

        categoryName = (Spinner) view.findViewById(R.id.category_spinner);
        categoryName.setSelection(pos);

        categoriesNames = new String[categories.size() + 1];

        categoriesNames[0] = "Choose category";

        for (int i = 0; i < categories.size(); i++) {
            categoriesNames[i + 1] = categories.get(i).getName();
        }

        spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, categoriesNames);
        categoryName.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        categoryName.setAdapter(spinnerAdapter);

        return view;
    }




    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected (AdapterView<?> main, View view, int position,
                                    long Id) {

            if(position > 0){
//                Toast.makeText(getContext(), position,
//                        Toast.LENGTH_SHORT).show();
            }else{
//                Toast.makeText(getContext(), "Select category",
//                        Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
//            Toast.makeText(getContext(), "Select category (on nothing selected)",
//                    Toast.LENGTH_SHORT).show();
        }

    }
}

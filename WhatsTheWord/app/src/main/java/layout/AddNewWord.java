package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.miss.temp.R;

import java.util.ArrayList;
import java.util.List;

import models.Player;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewWord extends Fragment {

    private String[] categoriesNames;
    private ArrayAdapter<String> spinnerAdapter;
    public int pos;
    Spinner categoryName; //AutoCompleteTextView
  //  List<String> categoriesList;

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

        categoryName = (Spinner) view.findViewById(R.id.category_spinner);
        categoryName.setSelection(pos);

        categoriesNames = new String[11];

        categoriesNames[0] = "Select category";
        categoriesNames[1] = "Objects";
        categoriesNames[2] = "Music";
        categoriesNames[3] = "Songs";
        categoriesNames[4] = "Animals";
        categoriesNames[5] = "Bg stars";
        categoriesNames[6] = "Cars";
        categoriesNames[7] = "Food";
        categoriesNames[8] = "Movies";
        categoriesNames[9] = "Bg Songs";
        categoriesNames[10] = "Stars";

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

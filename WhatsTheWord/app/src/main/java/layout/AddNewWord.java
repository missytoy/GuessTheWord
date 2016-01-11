package layout;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.miss.temp.R;

import java.util.ArrayList;
import java.util.List;

import data.DataAccess;
import models.Category;
import models.Word;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewWord extends Fragment implements View.OnClickListener {

    private String[] categoriesNames;
    private List<Category> categories;
    private List<String> wordsList;
    private ArrayAdapter<String> spinnerAdapter;
    public int pos;
    Spinner categoryName;
    Button addNewWordBtn;
    EditText addNewWordEditText;
    Word wordModelToAdd;

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

        new GetWordsTask().execute((DataAccess) args.getSerializable("data"));

        categoryName = (Spinner) view.findViewById(R.id.category_spinner);
        categoryName.setSelection(pos);

        categoriesNames = new String[categories.size() + 1];

        categoriesNames[0] = "Choose category";

        for (int i = 0; i < categories.size(); i++) {
            categoriesNames[i + 1] = categories.get(i).getName();
        }

        spinnerAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, categoriesNames);
        //categoryName.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        categoryName.setAdapter(spinnerAdapter);

        addNewWordBtn = (Button) view.findViewById(R.id.add_new_word_btn);
        addNewWordBtn.setOnClickListener(this);

        addNewWordEditText = (EditText) view.findViewById(R.id.word_to_add);

        return view;
    }

    @Override
    public void onClick(View v) {
        String wordToAdd = addNewWordEditText.getText().toString().trim();
        String chosenCategoryName = String.valueOf(categoryName.getSelectedItem());
        if (wordToAdd.length() == 0){
            Toast.makeText(getContext(), "Word to add cannot be empty.", Toast.LENGTH_SHORT)
                 .show();
            return;
        } else if(wordsList.contains(wordToAdd)){
            Toast.makeText(getContext(), "Word already exists.", Toast.LENGTH_SHORT)
                 .show();
            return;
        } else if(chosenCategoryName == "Choose category"){
            Toast.makeText(getContext(), "You must chose category.", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        int indexOfChosenCategory = 0;
        for (int i = 0; i < categoriesNames.length; i++) {
            if (categoriesNames[i] == chosenCategoryName){
                indexOfChosenCategory = i;
                break;
            }
        }
        Category chosenCategory = categories.get(indexOfChosenCategory);
        wordsList.add(wordToAdd);
        wordModelToAdd = new Word();
        wordModelToAdd.setContent(wordToAdd);
        wordModelToAdd.setCategoryId(chosenCategory.getId());

        new AddWordInDatabaseTask().execute((DataAccess) getArguments().getSerializable("data"));
    }

    private class GetWordsTask extends AsyncTask<DataAccess, Void, List<String>> {
        @Override
        protected List<String> doInBackground(DataAccess... params) {
            List<String> words = params[0].getAllWordsAsContent();

            return words;
        }

        @Override
        protected void onPostExecute(List<String> words) {
            wordsList = new ArrayList<String>(words);
        }
    }

    private class AddWordInDatabaseTask extends AsyncTask<DataAccess, Void, Long> {
        @Override
        protected Long doInBackground(DataAccess... params) {

            Long word_id = params[0].createWord(wordModelToAdd);

            return word_id;
        }

        @Override
        protected void onPostExecute(Long word_id) {
            Toast.makeText(getContext(), "You successfully added new word.", Toast.LENGTH_SHORT)
                 .show();
        }
    }
//
//    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
//
//        @Override
//        public void onItemSelected (AdapterView<?> main, View view, int position,
//                                    long Id) {
//
//            if(position > 0){
////                Toast.makeText(getContext(), position,
////                        Toast.LENGTH_SHORT).show();
//            }else{
////                Toast.makeText(getContext(), "Select category",
////                        Toast.LENGTH_SHORT).show();
//            }
//
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> parent) {
////            Toast.makeText(getContext(), "Select category (on nothing selected)",
////                    Toast.LENGTH_SHORT).show();
//        }
//
//    }
}

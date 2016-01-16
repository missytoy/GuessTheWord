package layout;


import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miss.temp.R;

import java.util.ArrayList;
import java.util.List;

import data.DataAccess;
import helpers.MySoundManager;
import models.Category;
import models.Word;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewWord extends Fragment implements View.OnClickListener {

    private final int   TOAST_TEXT_SIZE = 20;

    private String[] categoriesNames;
    private List<Category> categories;
    private List<String> wordsList;
    private ArrayAdapter<String> spinnerAdapter;
    private DataAccess data;

    private Spinner categoryName;
    private Button addNewWordBtn;
    private EditText addNewWordEditText;
    private Word wordModelToAdd;
    int pos;

    public AddNewWord() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_word, container, false);

        wordsList = new ArrayList<String>();

        Bundle args = this.getArguments();
        categories = new ArrayList<Category>((List<Category>) args.getSerializable("categories_array"));

        new GetWordsTask().execute(data);

        categoryName = (Spinner) view.findViewById(R.id.category_spinner);
        categoryName.setSelection(pos);

        categoriesNames = new String[categories.size() + 1];

        categoriesNames[0] = "Choose category";

        for (int i = 0; i < categories.size(); i++) {
            categoriesNames[i + 1] = categories.get(i).getName();
        }

        spinnerAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, categoriesNames);
        categoryName.setAdapter(spinnerAdapter);

        addNewWordBtn = (Button) view.findViewById(R.id.add_new_word_btn);
        addNewWordBtn.setOnClickListener(this);

        addNewWordEditText = (EditText) view.findViewById(R.id.word_to_add);

        return view;
    }

    @Override
    public void onClick(View v) {

        MySoundManager.playButtonSound(getContext());
        String wordToAdd = addNewWordEditText.getText().toString()
                                                       .trim();
        String chosenCategoryName = String.valueOf(categoryName.getSelectedItem());
        if (wordToAdd.length() == 0){
            Toast toast = Toast.makeText(getContext(), "Word to add cannot be empty.", Toast.LENGTH_SHORT);
            LinearLayout toastLayout = (LinearLayout) toast.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTextSize(TOAST_TEXT_SIZE);
 
            toastTV.setTextColor(Color.WHITE);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            return;
        } else if(wordsList.contains(wordToAdd.toLowerCase())){
            Toast toast = Toast.makeText(getContext(), "Word already exists.", Toast.LENGTH_SHORT);
            LinearLayout toastLayout = (LinearLayout) toast.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTextSize(TOAST_TEXT_SIZE);
            toastTV.setTextColor(Color.WHITE);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            return;
        } else if(chosenCategoryName == "Choose category"){
            
            Toast toast = Toast.makeText(getContext(), "You must choose category", Toast.LENGTH_SHORT);
            LinearLayout toastLayout = (LinearLayout) toast.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTextSize(TOAST_TEXT_SIZE);
            toastTV.setTextColor(Color.WHITE);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            return;
        }

        int indexOfChosenCategory = 0;
        for (int i = 0; i < categoriesNames.length; i++) {
            if (categoriesNames[i] == chosenCategoryName){
          
                indexOfChosenCategory = i - 1;
                break;
            }
        }
        Category chosenCategory = categories.get(indexOfChosenCategory);
        wordsList.add(wordToAdd.toLowerCase());
        wordModelToAdd = new Word();
        wordModelToAdd.setContent(wordToAdd);
        wordModelToAdd.setCategoryId(chosenCategory.getId());

        new AddWordInDatabaseTask().execute(data);
    }

    private class GetWordsTask extends AsyncTask<DataAccess, Void, List<String>> {
        @Override
        protected List<String> doInBackground(DataAccess... params) {
            params[0].open();
            List<String> words = params[0].getAllWordsAsContent();

            return words;
        }

        @Override
        protected void onPostExecute(List<String> words) {
            for (String word : words) {
                wordsList.add(word.toLowerCase());
            }
        }
    }

    private class AddWordInDatabaseTask extends AsyncTask<DataAccess, Void, Long> {
        @Override
        protected Long doInBackground(DataAccess... params) {

            Long word_id = params[0].createWord(wordModelToAdd);
            params[0].close();

            return word_id;
        }

        @Override
        protected void onPostExecute(Long word_id) {
            Toast toast = Toast.makeText(getContext(), "You successfully added the word \"" + wordModelToAdd.getContent() + "\"", Toast.LENGTH_SHORT);
            LinearLayout toastLayout = (LinearLayout) toast.getView();
            TextView toastTV = (TextView) toastLayout.getChildAt(0);
            toastTV.setTextSize(TOAST_TEXT_SIZE);
            toastTV.setTextColor(Color.WHITE);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

}

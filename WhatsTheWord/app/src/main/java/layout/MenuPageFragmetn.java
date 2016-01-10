package layout;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.miss.temp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuPageFragmetn extends Fragment implements View.OnClickListener {

    OnButtonsClick onButtonsPressed;
    Button startGameBtn;
    Button history;
    Button addNewWord;

    public MenuPageFragmetn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_page_fragmetn, container, false);

        startGameBtn = (Button) view.findViewById(R.id.start_game);
        history = (Button) view.findViewById(R.id.see_history);
        addNewWord = (Button) view.findViewById(R.id.add_word);

        startGameBtn.setOnClickListener(this);
        history.setOnClickListener(this);
        addNewWord.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    public  interface  OnButtonsClick{
        public void onStartButtonClicked();
        public void onHistoryButtonClicked();
        public void onAddWordButtonClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity  =(Activity) context;

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            onButtonsPressed = (OnButtonsClick) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnButtonsClick");
        }
    }

    public void onClick(View v) {
        if (v.getId() == startGameBtn.getId()) {
             //Toast.makeText(getContext(), "Start Game", Toast.LENGTH_SHORT).show();
            onButtonsPressed.onStartButtonClicked();
        } else if (v.getId() == addNewWord.getId()) {
          //   Toast.makeText(getContext(), "Add word", Toast.LENGTH_SHORT).show();
            onButtonsPressed.onAddWordButtonClicked();
        } else if (v.getId() == history.getId()) {
           //   Toast.makeText(getContext(), "History", Toast.LENGTH_SHORT).show();
            onButtonsPressed.onHistoryButtonClicked();
        }
    }
}

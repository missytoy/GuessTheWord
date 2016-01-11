package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miss.temp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewWord extends Fragment {


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

        return view;
    }
}

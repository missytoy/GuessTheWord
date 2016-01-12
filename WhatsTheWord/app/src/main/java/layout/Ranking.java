package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.miss.temp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ranking extends Fragment {


    private ArrayAdapter<String> adapter;
    private String[] playersRanking = {
            "1. Konstantina (10 points)",
            "2. Konstantina (10 points)",
            "3. Konstantina (10 points)"
            , "4. Konstantina (10 points)"
            , "5. Konstantina (10 points)"
            , "6. Konstantina (10 points)"
            , "7. Konstantina (10 points)"
            , "8. Konstantina (10 points)",
            "9. Konstantina (10 points)",
            "10. Konstantina (10 points)",
            "11. Konstantina (10 points)"};
    private ListView rankingListView;

    public Ranking() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);

        rankingListView = (ListView) view.findViewById(R.id.ranking_listview);
        adapter = new ArrayAdapter<String>(getContext(), R.layout.ranking_listview_item,playersRanking);
        rankingListView.setAdapter(adapter);

        return view;
    }

}

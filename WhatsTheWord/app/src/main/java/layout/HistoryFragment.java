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
public class HistoryFragment extends Fragment {

    private ArrayAdapter<String> adapter;
    private String[] playersHistory  = {"1. Konstantina (25.10.2015)","2. Pesho (25.10.2015)","2. Pesho (25.10.2015)"};
    private ListView historyListView;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_history, container, false);

       // Bundle args = this.getArguments();
       // history = (String[]) args.getSerializable("players_scores");
        historyListView = (ListView) view.findViewById(R.id.history_listview);
        adapter = new ArrayAdapter<String>(getContext(), R.layout.history_listview_item,playersHistory);
        historyListView.setAdapter(adapter);

        return view;
    }

}

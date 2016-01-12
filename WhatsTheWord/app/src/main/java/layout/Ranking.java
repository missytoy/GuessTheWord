package layout;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.miss.temp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ranking extends Fragment  implements View.OnClickListener  {


    private ArrayAdapter<String> adapter;
    private String[] playersRanking;
    private ListView rankingListView;
    private Button goToMainPage;

    public Ranking() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);

        Bundle args = this.getArguments();
        playersRanking = (String[]) args.getSerializable("players_scores");
        rankingListView = (ListView) view.findViewById(R.id.ranking_listview);
        adapter = new ArrayAdapter<String>(getContext(), R.layout.ranking_listview_item,playersRanking);
        rankingListView.setAdapter(adapter);

        goToMainPage =  (Button) view.findViewById(R.id.go_to_main_page_from_ranking);
        goToMainPage.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        MenuPageFragmetn firstFragment = new MenuPageFragmetn();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_placeholder, firstFragment);
        transaction.commit();
    }


}

package layout;


import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.miss.temp.R;

import helpers.MySoundManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ranking extends Fragment implements View.OnClickListener {


    private IGoToMainPagePressed goToMainPagePressedFromRanking;
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
        MySoundManager.playClapping(getContext());
        Bundle args = this.getArguments();
        playersRanking = (String[]) args.getSerializable("players_scores");
        rankingListView = (ListView) view.findViewById(R.id.ranking_listview);
        adapter = new ArrayAdapter<String>(getContext(), R.layout.ranking_listview_item, playersRanking);
        rankingListView.setAdapter(adapter);

        goToMainPage = (Button) view.findViewById(R.id.go_to_main_page_from_ranking);
        goToMainPage.setOnClickListener(this);

        return view;
    }


    public interface IGoToMainPagePressed {
        void goToMainPageFromRanking();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            goToMainPagePressedFromRanking = (IGoToMainPagePressed) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement IGoToMainPagePressed");
        }
    }


    @Override
    public void onClick(View v) {

        MySoundManager.playButtonSound(getContext());
        this.goToMainPagePressedFromRanking.goToMainPageFromRanking();
    }
}

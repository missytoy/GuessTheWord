package layout;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.miss.temp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import data.DataAccess;
import models.Game;
import models.Player;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements View.OnClickListener {

    private IGoToMainPagePressedFromHistory goToMainPagePressedFromHistory;
    private GamesListAdapter adapter;
    private ListView historyListView;
    private List<Game> lastGames;
    private Button goToMainPage;


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_history, container, false);
        goToMainPage = (Button) view.findViewById(R.id.go_to_main_page);
        goToMainPage.setOnClickListener(this);
        Bundle args = this.getArguments();
        new GetLastGamesHistoryTask().execute((DataAccess) args.getSerializable("data"));

        return view;
    }

    public interface IGoToMainPagePressedFromHistory {
        void goToMainPageFromHistory();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            goToMainPagePressedFromHistory = (IGoToMainPagePressedFromHistory) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement IGoToMainPagePressedFromHistory");
        }
    }

    private class GetLastGamesHistoryTask extends AsyncTask<DataAccess, Void, List<Game>> {
        @Override
        protected List<Game> doInBackground(DataAccess... params) {
            List<Game> games = params[0].getAllGames(10);

            int numberInView = 1;
            for (Game game : games) {
                int gameId = game.getId();

                List<Player> players = params[0].getAllPlayersByGame(gameId);
                Collections.sort(players);
                game.setNumberInView(numberInView);
                game.setWinner(players.get(0));
                for (int j = 0; j < players.size(); j++) {
                    game.addPlayer(players.get(j));
                }

                numberInView++;
            }

            return games;
        }

        @Override
        protected void onPostExecute(List<Game> games) {
            lastGames = new ArrayList<Game>(games);
            historyListView = (ListView) getView().findViewById(R.id.history_listview);
            adapter = new GamesListAdapter(getContext(), lastGames);
            historyListView.setAdapter(adapter);
        }
    }

    public void onClick(View v) {
        this.goToMainPagePressedFromHistory.goToMainPageFromHistory();
    }

}

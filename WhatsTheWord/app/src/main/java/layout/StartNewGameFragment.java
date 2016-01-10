package layout;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.miss.temp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import models.Player;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartNewGameFragment extends Fragment implements View.OnClickListener {

    OnChooseCategoryBtnClicked onChooseCategoryPressed;

    Button addPlayerButton;
    Button chooseCategoryBtn;
    TextView viewPlayers;
    EditText playerName;
    List<Player> playersList;

    public StartNewGameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_new_game, container, false);
        addPlayerButton = (Button) view.findViewById(R.id.add_player);
        addPlayerButton.setOnClickListener(this);
        chooseCategoryBtn = (Button) view.findViewById(R.id.chose_category);
        chooseCategoryBtn.setOnClickListener(this);

        viewPlayers = (TextView) view.findViewById(R.id.view_players);
        playerName = (EditText) view.findViewById(R.id.editTextPlayerName);

        playersList = new ArrayList<Player>();

        return view;
    }

    public  interface  OnChooseCategoryBtnClicked{
        public void onChooseCategoryButtonClicked(Serializable players);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity  =(Activity) context;

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            onChooseCategoryPressed = (OnChooseCategoryBtnClicked) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnChooseCategoryBtnClicked");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == addPlayerButton.getId()) {
            String playerNameText = playerName.getText().toString();
            Player newPlayer = new Player();
            newPlayer.setName(playerNameText);
            newPlayer.setScore(0);
            this.playersList.add(newPlayer);

            String allPlayers = viewPlayers.getText().toString();
            allPlayers = allPlayers + "\n" + playerNameText;
            viewPlayers.setText(allPlayers);
            playerName.setText("");
        } else if(v.getId() == chooseCategoryBtn.getId()) {
            onChooseCategoryPressed.onChooseCategoryButtonClicked((Serializable)playersList);
        }
    }
}

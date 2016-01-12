package layout;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miss.temp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import models.Player;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartNewGameFragment extends Fragment implements View.OnClickListener {
    // Will be filled from the database so that we don't have to type our full name if we have already played.
    // For now it is hardcoded in onCreateView method.
    private String[] playerNames;
    private ArrayAdapter<String> autoCompleteAdapter;

    OnChooseCategoryBtnClicked onChooseCategoryPressed;

    Button addPlayerButton;
    Button chooseCategoryBtn;
    TextView viewPlayers;
    AutoCompleteTextView playerName;
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
        playerName = (AutoCompleteTextView) view.findViewById(R.id.editTextPlayerName);

        playersList = new ArrayList<Player>();

        playerNames = new String[5];
        playerNames[0] = "titi";
        playerNames[1] = "ceco";
        playerNames[2] = "pesho";
        playerNames[3] = "gosho";
        playerNames[4] = "tosho";

        autoCompleteAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, playerNames);

        playerName.setAdapter(autoCompleteAdapter);
        // specify the minimum type of characters before drop-down list is shown
        playerName.setThreshold(1);

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
            if (playerNameText.length() == 0){
                Toast.makeText(getContext(), "Player name cannot be empty", Toast.LENGTH_SHORT)
                     .show();
                return;
            }

            Player newPlayer = new Player();
            newPlayer.setName(playerNameText);
            newPlayer.setScore(0);
            this.playersList.add(newPlayer);

            String allPlayers = viewPlayers.getText().toString();
            allPlayers = allPlayers + "\n" + playerNameText;
            viewPlayers.setText(allPlayers);
            playerName.setText("");
        } else if(v.getId() == chooseCategoryBtn.getId()) {
            if(this.playersList.size() == 0){
                Toast toast = Toast.makeText(getContext(), "Add player to start a game.", Toast.LENGTH_SHORT);
                LinearLayout toastLayout = (LinearLayout) toast.getView();
                TextView toastTV = (TextView) toastLayout.getChildAt(0);
                toastTV.setTextSize(20);
                toastTV.setTextColor(Color.WHITE);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                return;
            }
            onChooseCategoryPressed.onChooseCategoryButtonClicked((Serializable)playersList);
        }
    }
}

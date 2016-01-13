package layout;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miss.temp.R;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.List;

import data.DataAccess;
import helpers.MySoundManager;
import models.Player;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartNewGameFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    // Will be filled from the database so that we don't have to type our full name if we have already played.
    // For now it is hardcoded in onCreateView method.
    private List<String> allPlayerNames;
    private ArrayAdapter<String> autoCompleteAdapter;
    private IOnGeolocationChosen onChosingGeolocation;

    OnChooseCategoryBtnClicked onChooseCategoryPressed;

    Button addPlayerButton;
    Button chooseCategoryBtn;
    Switch takePlaceSwitch;
    TextView viewPlayers;
    AutoCompleteTextView playerName;
    List<Player> playersList;

    public StartNewGameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MySoundManager.playLetItBegin(getContext());
        View view = inflater.inflate(R.layout.fragment_start_new_game, container, false);
        addPlayerButton = (Button) view.findViewById(R.id.add_player);
        addPlayerButton.setOnClickListener(this);
        chooseCategoryBtn = (Button) view.findViewById(R.id.chose_category);
        chooseCategoryBtn.setOnClickListener(this);

        takePlaceSwitch = (Switch) view.findViewById(R.id.take_place);
        takePlaceSwitch.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);

        viewPlayers = (TextView) view.findViewById(R.id.view_players);
        playerName = (AutoCompleteTextView) view.findViewById(R.id.editTextPlayerName);

        playersList = new ArrayList<Player>();
        allPlayerNames = new ArrayList<String>();
        new GetPlayerNamesTask().execute((DataAccess) getArguments().getSerializable("data"));

        autoCompleteAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, allPlayerNames);

        playerName.setAdapter(autoCompleteAdapter);
        // specify the minimum type of characters before drop-down list is shown
        playerName.setThreshold(1);

        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){

        }
    }

    public  interface  OnChooseCategoryBtnClicked{
        public void onChooseCategoryButtonClicked(Serializable players);
    }

    public interface IOnGeolocationChosen{
        void getGeolocation();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity  =(Activity) context;

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            onChooseCategoryPressed = (OnChooseCategoryBtnClicked) activity;
            onChosingGeolocation = (IOnGeolocationChosen) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnChooseCategoryBtnClicked");
        }
    }

    @Override
    public void onClick(View v) {

        MySoundManager.playButtonSound(getContext());
        if (v.getId() == addPlayerButton.getId()) {
            String playerNameText = playerName.getText().toString();
            if (playerNameText.length() == 0){
                Toast toast = Toast.makeText(getContext(), "Player name cannot be empty", Toast.LENGTH_SHORT);
                LinearLayout toastLayout = (LinearLayout) toast.getView();
                TextView toastTV = (TextView) toastLayout.getChildAt(0);
                toastTV.setTextSize(20);
                toastTV.setTextColor(Color.WHITE);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                return;
            } else if(playerNameText.length() > 12){
                Toast toast = Toast.makeText(getContext(), "Player's name too long. Must be less than 13 symbols :)", Toast.LENGTH_SHORT);
                LinearLayout toastLayout = (LinearLayout) toast.getView();
                TextView toastTV = (TextView) toastLayout.getChildAt(0);
                toastTV.setTextSize(20);
                toastTV.setTextColor(Color.WHITE);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

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
            if(this.playersList.size() < 2){
                Toast toast = Toast.makeText(getContext(), "Add at least 2 players to start a game.", Toast.LENGTH_SHORT);
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

    private class GetPlayerNamesTask extends AsyncTask<DataAccess, Void, AbstractSet<String>> {
        @Override
        protected AbstractSet<String> doInBackground(DataAccess... params) {
            AbstractSet<String> names = params[0].getAllPlayerNames();

            return names;
        }

        @Override
        protected void onPostExecute(AbstractSet<String> names) {
            for (String name : names) {
                allPlayerNames.add(name);
            }
        }
    }


}

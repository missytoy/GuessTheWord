package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.miss.temp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartNewGameFragment extends Fragment implements View.OnClickListener {

    Button addPlayerButton;
    TextView viewPlayers;
    EditText playerName;

    public StartNewGameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_new_game, container, false);
        addPlayerButton = (Button) view.findViewById(R.id.add_player);
        addPlayerButton.setOnClickListener(this);
        viewPlayers = (TextView) view.findViewById(R.id.view_players);
        playerName = (EditText) view.findViewById(R.id.editTextPlayerName);


        return view;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == addPlayerButton.getId()) {

            String allPlayers = viewPlayers.getText().toString();
            allPlayers = allPlayers + "\n" + playerName.getText().toString();
           viewPlayers.setText(allPlayers);
            playerName.setText("");
        }
    }
}

package layout;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miss.temp.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class GamePage extends Fragment implements View.OnClickListener {

    TextView timerTextView;
    TextView randomWord;
    RelativeLayout randomWordAndTimer;
    RelativeLayout currentUserInfo;
    Button nextPlayerButton;
    public Long timerStep;

    public GamePage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_game_page, container, false);
        timerTextView = (TextView) view.findViewById(R.id.timer_textview_id);
        randomWord = (TextView) view.findViewById(R.id.random_word_textview_id);
        randomWordAndTimer = (RelativeLayout) view.findViewById(R.id.random_word_and_timer_Layout);
        currentUserInfo = (RelativeLayout) view.findViewById(R.id.current_user_info);
        nextPlayerButton = (Button) view.findViewById(R.id.next_player_button);
        nextPlayerButton.setOnClickListener(this);


        playerTimer();
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == nextPlayerButton.getId()) {

            randomWordAndTimer.setVisibility(View.VISIBLE);
            currentUserInfo.setVisibility(View.INVISIBLE);
            playerTimer();

        }
    }

    public void playerTimer() {
        CountDownTimer waitTimer;
        //TODO: set timer to 60000
        waitTimer = new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerStep = millisUntilFinished / 1000;
                timerTextView.setText(timerStep.toString());
            }

            public void onFinish() {
                timerTextView.setText("0");

                randomWordAndTimer.setVisibility(View.INVISIBLE);
                currentUserInfo.setVisibility(View.VISIBLE);
            }
        }.start();
    }
}

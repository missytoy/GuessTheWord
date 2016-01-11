package layout;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miss.temp.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class GamePage extends Fragment {

    TextView timerTextView;
    TextView randomWord;
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

        CountDownTimer waitTimer;
        waitTimer = new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerStep = millisUntilFinished / 1000;
                timerTextView.setText(timerStep.toString());
            }

            public void onFinish() {
                timerTextView.setText("0");
                Toast.makeText(getContext(), "йей", Toast.LENGTH_SHORT).show();
                randomWord.setVisibility(View.INVISIBLE);
            }
        }.start();

        return view;
    }
}

package layout;


import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.miss.temp.R;

import helpers.MySoundManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuPageFragmetn extends Fragment implements View.OnClickListener {

    OnButtonsClick onButtonsPressed;
    Button startGameBtn;
    Button history;
    Button addNewWord;

  // SensorEventListener listener;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;

    public MenuPageFragmetn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_page_fragmetn, container, false);
        startGameBtn = (Button) view.findViewById(R.id.start_game);
        history = (Button) view.findViewById(R.id.see_history);
        addNewWord = (Button) view.findViewById(R.id.add_word);

        startGameBtn.setOnClickListener(this);
        history.setOnClickListener(this);
        addNewWord.setOnClickListener(this);
        // Inflate the layout for this fragment

//        //TODO: delete
//        senSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
//        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        senSensorManager.registerListener(listener, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        return view;
    }

    public  interface  OnButtonsClick{
        public void onStartButtonClicked();
        public void onHistoryButtonClicked();
        public void onAddWordButtonClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity  =(Activity) context;

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            onButtonsPressed = (OnButtonsClick) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnButtonsClick");
        }
    }

    public void onClick(View v) {

        MySoundManager.playButtonSound(getContext());
        if (v.getId() == startGameBtn.getId()) {
            onButtonsPressed.onStartButtonClicked();
        } else if (v.getId() == addNewWord.getId()) {
            onButtonsPressed.onAddWordButtonClicked();
        } else if (v.getId() == history.getId()) {
            onButtonsPressed.onHistoryButtonClicked();
        }
    }

}

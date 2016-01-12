package layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.miss.temp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Game;

public class GamesListAdapter extends ArrayAdapter<Game> {
    private final Context context;
    private final List<Game> games;

    public GamesListAdapter(Context context, List<Game> values) {
        super(context, R.layout.history_listview_item, values);
        this.context = context;
        this.games = new ArrayList<Game>(values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.history_listview_item, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.game_list_textview);

        String text = games.get(position).getNumberInView().toString() + ". ";
        text += games.get(position).getWinner().getName() + " (";
        Date playedOn = games.get(position).getPlayedOn();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String date = formatter.format(playedOn);
        text += date + ")";

        textView.setText(text);

        return rowView;
    }
}


package helpers;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.miss.temp.R;

import java.util.Random;

/**
 * Created by Miss on 13.01.2016 г..
 */
public class MySoundManager {


   private static int randNum;

    public static void playButtonSound(final Context context) {

        Thread t = new Thread() {
            public void run() {
                MediaPlayer player = null;
                player = MediaPlayer.create(context, R.raw.btn);
                player.start();
                try {

                    Thread.sleep(player.getDuration());
                    player.release();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
            }
        };

        t.start();

    }

    public static  void playCorrectTone(final Context context) {


        Thread t = new Thread() {
            public void run() {
                MediaPlayer player = null;
                player = MediaPlayer.create(context, R.raw.yohoo);
                player.start();
                try {

                    Thread.sleep(player.getDuration());
                    player.release();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
            }
        };

        t.start();

    }

    public static void playNextWordTone(final Context context) {

        Random rand = new Random();
        randNum = rand.nextInt(2);

        Thread t = new Thread() {
            public void run() {
                MediaPlayer player = null;
                if (randNum == 1) {

                    player = MediaPlayer.create(context, R.raw.hahasound);
                } else {
                    player = MediaPlayer.create(context, R.raw.flip);
                }
                player.start();
                try {

                    Thread.sleep(player.getDuration());
                    player.release();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        };

        t.start();

    }

    public static void playClapping(final Context context) {


        Thread t = new Thread() {
            public void run() {
                MediaPlayer player = null;
                player = MediaPlayer.create(context, R.raw.claps);
                player.start();
                try {

                    Thread.sleep(player.getDuration());
                    player.release();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
            }
        };

        t.start();

    }

    public static void playLetItBegin(final Context context) {


        Thread t = new Thread() {
            public void run() {
                MediaPlayer player = null;
                player = MediaPlayer.create(context, R.raw.letitbegin2);
                player.start();
                try {

                    Thread.sleep(player.getDuration());
                    player.release();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
            }
        };

        t.start();

    }
}

package ru.egslava.flag.ui.training;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;
import java.util.logging.Handler;

import ru.egslava.flag.Prefs_;
import ru.egslava.flag.R;
import ru.egslava.flag.ui.*;
import ru.egslava.flag.ui.views.FitGridLayout;
import ru.egslava.flag.utils.Images;
import ru.egslava.flag.utils.UniqueRandom;
import ru.egslava.flag.utils.Utils;

import static ru.egslava.flag.utils.Utils.after;

@EActivity(R.layout.activity_training_end)
public class TrainingEndActivity extends ActionBarActivity {
    @Pref Prefs_ prefs;
    @ViewById   FitGridLayout flagsSGV;
    @Extra int[] oldFlags;
    @Extra int round;
    @Extra ArrayList<Integer> identified;
    @Extra String userName;

    int[] flags;

    @AfterViews void init() {
        loadFlags();
        flagsSGV.init(prefs.t2m().get(), prefs.t2n().get(), flags);

        after( prefs.secs2().get() * 1000, () -> {
            for(Integer element : flagsSGV.getSelectedIds()){
                if(checkSelectedElement(element)){
                    identified.add(element);
                }
            }
            if(identified.size() > prefs.t2m().get() * prefs.t2n().get() * /* TODO numRounds */ prefs.list5Imgs().get()){//
                TrainingResultActivity_.intent(this).identified(identified).userName(userName).start();
            } else {
                TrainingActivity_.intent(this).identified(identified).userName(userName).start();
            }
        });
    }

    private void loadFlags(){
        int size = prefs.t2m().get()*prefs.t2n().get();
        ArrayList<Integer> list = new ArrayList();
        for(int i = 0; i< oldFlags.length; i++){
            list.add(oldFlags[i]);
        }
        UniqueRandom random = new UniqueRandom(0, Images.imgs[prefs.imgFolder().get()].length);
        for(int i = oldFlags.length; i< size; i++){
            //list.add(Images.imgs[random.next()][prefs.imgFolder().get()]);
            list.add(Images.imgs[prefs.imgFolder().get()][random.next()]);
        }
        Collections.shuffle(list);

        flags = new int[size];
        for(int i =0; i< size; i++){
            flags[i] = list.get(i);
        }
    }

    private boolean checkSelectedElement(Integer element){
        for(int i =0; i < oldFlags.length; i++){
            if(oldFlags[i] == element) return true;
        }
        return false;
    }

    @Override public void onBackPressed() {}
}

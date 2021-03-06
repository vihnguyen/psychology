package ru.egslava.flag.ui;

import android.accounts.AccountManager;
import android.app.ListActivity;
import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.res.StringArrayRes;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;

import ru.egslava.flag.Prefs;
import ru.egslava.flag.Prefs_;
import ru.egslava.flag.ui.PrefsActivity_;
import ru.egslava.flag.ui.training.TrainingResultActivity_;
import ru.egslava.flag.ui.userlist.UserListActivity_;
import ru.egslava.flag.utils.Images;
import ru.egslava.flag.utils.UniqueRandom;

@EActivity
public class MenuActivity extends ListActivity {

    @StringArrayRes             String[]                menu;
    public                      ArrayAdapter<String>    adapter;

    @SystemService  AccountManager  accountManager;
    @Pref           Prefs_          prefs;

    @AfterInject void init() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, menu);
        setListAdapter(adapter);
    }

    @ItemClick void list(int position) {
        switch (position) {
            case 0:
                UserListActivity_.intent(this).mode(1).start();
                break;
            case 1:
                UserListActivity_.intent(this).mode(0).start();
                break;
            default:
                PrefsActivity_.intent(this).start();
                break;
        }
    }
}

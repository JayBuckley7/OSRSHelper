package com.infonuascape.osrshelper.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.infonuascape.osrshelper.R;
import com.infonuascape.osrshelper.fetchers.hiscore.HiscoreFetcher;
import com.infonuascape.osrshelper.listeners.HiscoresFetcherListener;
import com.infonuascape.osrshelper.models.Account;
import com.infonuascape.osrshelper.models.players.PlayerSkills;
import com.infonuascape.osrshelper.utils.exceptions.PlayerNotFoundException;

import java.lang.ref.WeakReference;

/**
 * Created by marc_ on 2018-01-14.
 */

public class HiscoresFetcherTask extends AsyncTask<Void, Void, Void> {
    private WeakReference<Context> context;
    private HiscoresFetcherListener listener;
    private Account account;
    private PlayerSkills playerSkills;

    public HiscoresFetcherTask(final Context context, final HiscoresFetcherListener listener, final Account account) {
        this.context = new WeakReference<>(context);
        this.listener = listener;
        this.account = account;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            playerSkills = new HiscoreFetcher(context.get(), account.username, account.type).getPlayerSkills();
        } catch (PlayerNotFoundException e) {
            if(listener != null) {
                listener.onHiscoresError(context.get().getString(R.string.not_existing_player));
            }

        } catch (Exception uhe) {
            uhe.printStackTrace();
            if(listener != null) {
                listener.onHiscoresError(context.get().getString(R.string.internal_error));
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if(listener != null) {
            listener.onHiscoresFetched(playerSkills);
        }
    }
}

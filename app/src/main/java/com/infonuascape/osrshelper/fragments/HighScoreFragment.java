package com.infonuascape.osrshelper.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.infonuascape.osrshelper.R;
import com.infonuascape.osrshelper.db.PreferencesController;
import com.infonuascape.osrshelper.listeners.HiscoresFetcherListener;
import com.infonuascape.osrshelper.listeners.RecyclerItemClickListener;
import com.infonuascape.osrshelper.models.Account;
import com.infonuascape.osrshelper.models.Skill;
import com.infonuascape.osrshelper.models.players.PlayerSkills;
import com.infonuascape.osrshelper.tasks.HiscoresFetcherTask;
import com.infonuascape.osrshelper.utils.ShareImageUtils;
import com.infonuascape.osrshelper.utils.Utils;
import com.infonuascape.osrshelper.views.RSView;
import com.infonuascape.osrshelper.views.RSViewDialog;

public class HighScoreFragment extends OSRSFragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, RecyclerItemClickListener, HiscoresFetcherListener {
	private final static String EXTRA_ACCOUNT = "EXTRA_ACCOUNT";
	private static final int NUM_PAGES = 2;
	private static final int WRITE_PERMISSION_REQUEST_CODE = 9001;
	private Account account;
	private TextView combatText;
	private PlayerSkills playerSkills;
	private RSView rsView;

	private CheckBox virtualLevelsCB;
	private ProfileHeaderFragment profileHeaderFragment;

	public static HighScoreFragment newInstance(final Account account) {
    	HighScoreFragment fragment = new HighScoreFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(EXTRA_ACCOUNT, account);
		fragment.setArguments(bundle);
		return fragment;
    }

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View view = inflater.inflate(R.layout.hiscores, null);

		account = (Account) getArguments().getSerializable(EXTRA_ACCOUNT);
		if(account == null) {
			return view;
		}

		profileHeaderFragment = (ProfileHeaderFragment) getChildFragmentManager().findFragmentById(R.id.profile_header);
		profileHeaderFragment.refreshProfile(account);
		profileHeaderFragment.setTitle(R.string.highscores);

		combatText = (TextView) view.findViewById(R.id.combat);

		virtualLevelsCB = (CheckBox) view.findViewById(R.id.cb_virtual_levels);
		virtualLevelsCB.setOnCheckedChangeListener(this);
		virtualLevelsCB.setChecked(PreferencesController.getBooleanPreference(getContext(), PreferencesController.USER_PREF_SHOW_VIRTUAL_LEVELS, false));
		virtualLevelsCB.setVisibility(View.GONE);

		rsView = (RSView) view.findViewById(R.id.rs_view);

		view.findViewById(R.id.share_btn).setVisibility(View.GONE);
		view.findViewById(R.id.share_btn).setOnClickListener(this);

		new HiscoresFetcherTask(getContext(), this, account).execute();

		return view;
	}


	private void changeHeaderText(final String text) {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				getView().findViewById(R.id.progressbar).setVisibility(View.GONE);
				combatText.setVisibility(View.VISIBLE);
				combatText.setText(text);
			}
		});
	}
	
	private void changeCombatText(){
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				combatText.setVisibility(View.VISIBLE);
				combatText.setText(getString(R.string.combat_lvl, Utils.getCombatLvl(playerSkills)));
			}
		});
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		PreferencesController.setPreference(getContext(), PreferencesController.USER_PREF_SHOW_VIRTUAL_LEVELS, isChecked);
		if(playerSkills != null) {
			populateTable(playerSkills);
		}
	}

	@Override
	public void onClick(View view) {
		if(view.getId() == R.id.share_btn) {
			if(playerSkills != null) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
					requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_REQUEST_CODE);
				} else {
					ShareImageUtils.shareHiscores(getContext(), account.username, playerSkills);
				}
			}
		}
	}

	@Override
	public void onItemClicked(int position) {
		Skill skill = rsView.getItem(position);
		RSViewDialog.showDialog(getContext(), skill);
	}

	@Override
	public void onItemLongClicked(int position) {

	}

	private void populateTable(PlayerSkills playerSkills) {
		changeCombatText();

		getView().findViewById(R.id.share_btn).setVisibility(View.VISIBLE);
        virtualLevelsCB.setVisibility(playerSkills.hasOneAbove99 ? View.VISIBLE : View.GONE);

		rsView.populateView(playerSkills, this);
	}

	@Override
	public void onHiscoresFetched(PlayerSkills playerSkills) {
		this.playerSkills = playerSkills;
		if (playerSkills != null) {
			populateTable(playerSkills);
		}
	}

	@Override
	public void onHiscoresError(String errorMessage) {
		changeHeaderText(errorMessage);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if(requestCode == WRITE_PERMISSION_REQUEST_CODE) {
			for(int i=0 ; i < permissions.length; i++) {
				if(TextUtils.equals(permissions[i], Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
					if(grantResults[i] == PackageManager.PERMISSION_GRANTED) {
						ShareImageUtils.shareHiscores(getContext(), account.username, playerSkills);
					}
				}
			}
		}
	}
}
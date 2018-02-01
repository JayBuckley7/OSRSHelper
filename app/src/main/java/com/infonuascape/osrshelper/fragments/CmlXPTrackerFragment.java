package com.infonuascape.osrshelper.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infonuascape.osrshelper.R;
import com.infonuascape.osrshelper.adapters.CmlXpTrackerFragmentAdapter;
import com.infonuascape.osrshelper.enums.TrackerTime;
import com.infonuascape.osrshelper.listeners.TrackerFetcherListener;
import com.infonuascape.osrshelper.listeners.TrackerUpdateListener;
import com.infonuascape.osrshelper.models.Account;
import com.infonuascape.osrshelper.models.players.PlayerSkills;
import com.infonuascape.osrshelper.tasks.CmlTrackerFetcherTask;
import com.infonuascape.osrshelper.tasks.CmlTrackerUpdateTask;

public class CmlXPTrackerFragment extends OSRSFragment implements OnClickListener, TrackerUpdateListener, ViewPager.OnPageChangeListener {
	private final static String EXTRA_ACCOUNT = "EXTRA_ACCOUNT";
	private Account account;
	private PlayerSkills playerSkills;
	private TextView header;
	private CmlXpTrackerFragmentAdapter adapter;
	private ViewPager viewPager;

	public static CmlXPTrackerFragment newInstance(final Account account) {
		CmlXPTrackerFragment fragment = new CmlXPTrackerFragment();
		Bundle b = new Bundle();
		b.putSerializable(EXTRA_ACCOUNT, account);
		fragment.setArguments(b);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View view = inflater.inflate(R.layout.xptracker, null);

		account = (Account) getArguments().getSerializable(EXTRA_ACCOUNT);

		ProfileHeaderFragment profileHeaderFragment = (ProfileHeaderFragment) getChildFragmentManager().findFragmentById(R.id.profile_header);
		profileHeaderFragment.refreshProfile(account);
		profileHeaderFragment.setTitle(R.string.cml_xptracker);

		header = view.findViewById(R.id.track_metadata);

		viewPager = view.findViewById(R.id.viewpager);
		adapter = new CmlXpTrackerFragmentAdapter(getChildFragmentManager(), getContext(), account);
		viewPager.setAdapter(adapter);
		viewPager.addOnPageChangeListener(this);
		viewPager.setOffscreenPageLimit(5);
		TabLayout tabLayout = view.findViewById(R.id.sliding_tabs);
		tabLayout.setupWithViewPager(viewPager);

		view.findViewById(R.id.update).setOnClickListener(this);

		createAsyncTaskToPopulate(false);

		return view;
	}

	private void createAsyncTaskToPopulate(boolean isUpdating) {
		TrackerTime time = TrackerTime.Hour;

		if (time != null) {
			header.setText(R.string.loading_tracking);
			asyncTask = new CmlTrackerUpdateTask(getContext(), this, account);
			asyncTask.execute();
		}
	}

	@Override
	public void refreshDataOnPreferencesChanged() {
		super.refreshDataOnPreferencesChanged();
		for(int i=0; i < adapter.getCount(); i++) {
			adapter.getItem(i).reloadData();
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.update) {
			createAsyncTaskToPopulate(true);
		}
	}

	public void onTrackingFetched(final PlayerSkills playerSkills) {
		this.playerSkills = playerSkills;

		if (playerSkills != null) {
			if (getView() != null) {
				header.setVisibility(View.VISIBLE);
				if (playerSkills.sinceWhen != null) {
					header.setText(getString(R.string.tracking_since,
							playerSkills.sinceWhen));
				} else if (playerSkills.lastUpdate != null) {
					header.setText(getString(R.string.last_update,
							playerSkills.lastUpdate));
				} else {
					header.setText(getString(R.string.tracking_starting));
				}
			}
		}
	}

	public void onTrackingError(final String errorMessage) {
		if(getActivity() != null) {
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if(getView() != null) {
						getView().findViewById(R.id.progressbar).setVisibility(View.VISIBLE);
						header.setText(errorMessage);
					}
				}
			});
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		adapter.getItem(position).onPageVisible();
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onUpdatingDone(boolean isSuccess) {
		for(int i=0; i < adapter.getCount(); i++) {
			adapter.getItem(i).onForceRepopulate();
		}
		adapter.getItem(viewPager.getCurrentItem()).onPageVisible();
	}
}
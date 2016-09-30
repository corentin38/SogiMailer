package com.sogilis.sogimailer.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sogilis.sogimailer.R;
import com.sogilis.sogimailer.dude.ProfileDude;
import com.sogilis.sogimailer.mail.Profile;

import java.util.List;

import javax.inject.Inject;

public class HomeFragment extends Fragment implements ProfileDude.MultipleListener {

	private static final String TAG = "SOGIMAILER_HOMEFRAG";
	private static final String PROFILE_ARRAY_BUNDLE_KEY = "profile_array_bundle_key";

	@Inject ProfileDude profileDude;

	public interface Listener {
		void onEditButtonClicked(Profile profile);
	}

	public static HomeFragment newInstance() {
		return new HomeFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		Listener listener = (Listener) getContext();

		RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
		ContentAdapter adapter = new ContentAdapter(new Profile[0], listener);
		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		return recyclerView;
	}

	@Override
	public void onResume() {
		super.onResume();
		profileDude.getAll(this);
	}

	@Override
	public void onProfilesUpdate(List<Profile> profiles) {
		Profile[] profilesArray = profiles.toArray(new Profile[profiles.size()]);

		RecyclerView view = (RecyclerView) getView();
		ContentAdapter adapter = (ContentAdapter) view.getAdapter();

		adapter.updateProfiles(profilesArray);

	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView senderTV;
		public TextView hostTV;
		public TextView passwordTV;
		public Button editButton;

		public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
			super(inflater.inflate(R.layout.fragment_home, parent, false));
			senderTV = (TextView) itemView.findViewById(R.id.home_default_user);
			hostTV = (TextView) itemView.findViewById(R.id.home_default_host);
			passwordTV = (TextView) itemView.findViewById(R.id.home_default_mdp);
			editButton = (Button) itemView.findViewById(R.id.edit_button);
		}
	}

	public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
		private Profile[] mProfiles;
		private Listener mListener;

		public ContentAdapter(Profile[] profiles, Listener listener) {
			this.mProfiles = profiles;
			this.mListener = listener;
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			final Profile profile = mProfiles[position];
			holder.senderTV.setText(profile.sender());
			holder.hostTV.setText(profile.host());
			holder.passwordTV.setText(profile.senderPassword());
			holder.editButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mListener.onEditButtonClicked(profile);
				}
			});
		}

		@Override
		public int getItemCount() {
			return mProfiles.length;
		}

		public void updateProfiles(Profile[] profiles) {
			this.mProfiles = profiles;
			notifyDataSetChanged();
		}
	}

}

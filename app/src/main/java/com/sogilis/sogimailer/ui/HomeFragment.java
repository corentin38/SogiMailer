package com.sogilis.sogimailer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sogilis.sogimailer.R;
import com.sogilis.sogimailer.SogiMailerApplication;
import com.sogilis.sogimailer.dude.ProfileDude;
import com.sogilis.sogimailer.mail.Profile;

import java.util.List;

import javax.inject.Inject;

public class HomeFragment extends Fragment implements ProfileDude.MultipleListener, ProfileDude.SingleListener {

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
		((SogiMailerApplication) getActivity().getApplication()).getObjectGraph().inject(this);

		View homeFragmentView = inflater.inflate(R.layout.fragment_home, container, false);

		FloatingActionButton button = (FloatingActionButton) homeFragmentView.findViewById(R.id.home_add_button);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addProfile();
			}
		});

		RecyclerView recyclerView = (RecyclerView) homeFragmentView.findViewById(R.id.home_recycler_view);
		ContentAdapter adapter = new ContentAdapter(new Profile[0], listener);
		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		return homeFragmentView;
	}

	@Override
	public void onResume() {
		super.onResume();
		profileDude.getAll(this);
	}

	// getAll listener
	@Override
	public void onProfilesUpdate(List<Profile> profiles) {
		Profile[] profilesArray = profiles.toArray(new Profile[profiles.size()]);

		RecyclerView view = (RecyclerView) getView().findViewById(R.id.home_recycler_view);
		ContentAdapter adapter = (ContentAdapter) view.getAdapter();

		adapter.updateProfiles(profilesArray);
		profileDude.getDefaultProfile(this);
	}

	// getDefaultProfileListener
	@Override
	public void onProfileUpdate(Profile profile) {
		RecyclerView view = (RecyclerView) getView().findViewById(R.id.home_recycler_view);
		ContentAdapter adapter = (ContentAdapter) view.getAdapter();
		adapter.updateDefaultProfile(profile.id());
	}

	@Override public void notFound() {}
	@Override public void tooMany() {}

	public void addProfile() {
		Intent itt = new Intent(getActivity(), AddActivity.class);
		startActivity(itt);
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView senderTV;
		public TextView hostTV;
		public TextView passwordTV;
		public Button editButton;
		public ImageView isDefault;

		public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
			super(inflater.inflate(R.layout.item_profile_card, parent, false));
			senderTV = (TextView) itemView.findViewById(R.id.home_default_user);
			hostTV = (TextView) itemView.findViewById(R.id.home_default_host);
			passwordTV = (TextView) itemView.findViewById(R.id.home_default_mdp);
			editButton = (Button) itemView.findViewById(R.id.edit_button);
			isDefault = (ImageView) itemView.findViewById(R.id.is_default);
		}
	}

	public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
		private Profile[] mProfiles;
		private Listener mListener;
		private long mDefaultProfileId;

		public ContentAdapter(Profile[] profiles, Listener listener) {
			this.mProfiles = profiles;
			this.mListener = listener;
			this.mDefaultProfileId = -1;
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
			if (profile.id() == mDefaultProfileId) {
				holder.isDefault.setVisibility(View.VISIBLE);
			} else {
				holder.isDefault.setVisibility(View.GONE);
			}
		}

		@Override
		public int getItemCount() {
			return mProfiles.length;
		}

		public void updateProfiles(Profile[] profiles) {
			this.mProfiles = profiles;
			notifyDataSetChanged();
		}

		public void updateDefaultProfile(long id) {
			this.mDefaultProfileId = id;
			notifyDataSetChanged();
		}
	}

}

package it.miriade.corsi.android.mvp.ui.fragment;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import it.miriade.corsi.android.mvp.AppConst;
import it.miriade.corsi.android.mvp.R;
import it.miriade.corsi.android.mvp.model.entity.User;
import it.miriade.corsi.android.mvp.ui.activity.UserDetailActivity;
import it.miriade.corsi.android.mvp.ui.activity.UserListActivity;

/**
 * A fragment representing a single User detail screen.
 * This fragment is either contained in a {@link UserListActivity}
 * in two-pane mode (on tablets) or a {@link UserDetailActivity}
 * on handsets.
 */
public class UserDetailFragment extends Fragment {

    /**
     * The content this fragment is presenting.
     */
    private User mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UserDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(AppConst.USER_INTENT_EXTRA)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = (User) getArguments().getSerializable(AppConst.USER_INTENT_EXTRA);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getFirstName() + " " + mItem.getLastName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.user_detail_email)).setText(mItem.getEmail());
            ((TextView) rootView.findViewById(R.id.user_detail_first)).setText(mItem.getFirstName());
            ((TextView) rootView.findViewById(R.id.user_detail_last)).setText(mItem.getLastName());
            ((TextView) rootView.findViewById(R.id.user_detail_birthday)).setText(new SimpleDateFormat(AppConst.DATE_FORMAT).format(mItem.getBirthday()));
        }

        return rootView;
    }

}

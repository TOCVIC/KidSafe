package com.mansourappdevelopment.androidapp.kidsafe.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mansourappdevelopment.androidapp.kidsafe.R;
import com.mansourappdevelopment.androidapp.kidsafe.adapters.ActivityLogFragmentPagerAdapter;
import com.mansourappdevelopment.androidapp.kidsafe.utils.Call;
import com.mansourappdevelopment.androidapp.kidsafe.utils.Message;

import java.util.HashMap;

import static com.mansourappdevelopment.androidapp.kidsafe.activities.ParentSignedInActivity.CHILD_CALLS_EXTRA;
import static com.mansourappdevelopment.androidapp.kidsafe.activities.ParentSignedInActivity.CHILD_MESSAGES_EXTRA;

public class ActivityLogFragment extends Fragment {
    public static final String TAG = "ActivityLogTAG";
    private HashMap<String, Message> messages;
    private HashMap<String, Call> calls;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_log, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //getData();

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.activityLogViewPager);
        viewPager.setAdapter(setupActivityLogFragmentPagerAdapter());

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.activityLogTabLayout);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void getData() {
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            messages = (HashMap<String, Message>) bundle.getSerializable(CHILD_MESSAGES_EXTRA);
            calls = (HashMap<String, Call>) bundle.getSerializable(CHILD_CALLS_EXTRA);
        }

        for (String key : messages.keySet()) {
            Log.i(TAG, "getData: messageBody: " + messages.get(key).getMessageBody());
            Log.i(TAG, "getData: senderPhoneNumber: " + messages.get(key).getSenderPhoneNumber());
            Log.i(TAG, "getData: timeReceived: " + messages.get(key).getTimeReceived());
        }
    }

    private PagerAdapter setupActivityLogFragmentPagerAdapter() {
        ActivityLogFragmentPagerAdapter pagerAdapter = new ActivityLogFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        pagerAdapter.addFragment(new CallsFragment(), "Calls");
        pagerAdapter.addFragment(new MessagesFragment(), "Messages");
        //TODO:: add one for the history

        return pagerAdapter;
    }
}
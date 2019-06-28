package com.mansourappdevelopment.androidapp.kidsafe.dialogfragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mansourappdevelopment.androidapp.kidsafe.R;
import com.mansourappdevelopment.androidapp.kidsafe.interfaces.OnGeoFenceSettingListener;
import com.mansourappdevelopment.androidapp.kidsafe.utils.Validators;

import static com.mansourappdevelopment.androidapp.kidsafe.activities.ParentSignedInActivity.CHILD_NAME_EXTRA;

public class GeoFenceSettingDialogFragment extends DialogFragment {
    private Spinner spinnerGeoFenceEntries;
    private EditText txtGeoFenceDiameter;
    private TextView txtGeoFenceHeader;
    private TextView txtGeoFenceBody;
    private Button btnSetGeoFence;
    private Button btnCancelSetGeoFence;
    private OnGeoFenceSettingListener onGeoFenceSettingListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_geo_fence, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        onGeoFenceSettingListener = (OnGeoFenceSettingListener) getTargetFragment();

        spinnerGeoFenceEntries = (Spinner) view.findViewById(R.id.spinnerGeoFenceEntries);
        txtGeoFenceDiameter = (EditText) view.findViewById(R.id.txtGeoFenceDiameter);

        txtGeoFenceHeader = (TextView) view.findViewById(R.id.txtGeoFenceHeader);
        txtGeoFenceBody = (TextView) view.findViewById(R.id.txtGeoFenceBody);

        String header = getResources().getString(R.string.setup_a_geofence) + " " + getString(R.string.on) + " " + getChildName();
        txtGeoFenceHeader.setText(header);
        String body = getResources().getString(R.string.if_) + " " + getChildName() + " " + getResources().getString(R.string.if_he_exceeded_it_you_will_be_alerted);
        txtGeoFenceBody.setText(body);

        btnSetGeoFence = (Button) view.findViewById(R.id.btnSetGeoFence);
        btnSetGeoFence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedEntry = spinnerGeoFenceEntries.getSelectedItem().toString();
                String geoFenceDiameter = txtGeoFenceDiameter.getText().toString();

                if (Validators.isValidGeoFenceDiameter(geoFenceDiameter)) {
                    txtGeoFenceDiameter.setError(getResources().getString(R.string.please_enter_a_valid_diameter));
                    txtGeoFenceDiameter.requestFocus();
                } else {
                    onGeoFenceSettingListener.onGeoFenceSet(selectedEntry, Double.parseDouble(geoFenceDiameter));
                    dismiss();
                }

            }
        });

        btnCancelSetGeoFence = (Button) view.findViewById(R.id.btnCancelSetGeoFence);
        btnCancelSetGeoFence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGeoFenceSettingListener.onCancelFence();
                dismiss();
            }
        });


    }

    private String getChildName() {
        Bundle bundle = getActivity().getIntent().getExtras();
        String childName = null;
        if (bundle != null) {
            childName = bundle.getString(CHILD_NAME_EXTRA);
        }
        return childName;
    }
}
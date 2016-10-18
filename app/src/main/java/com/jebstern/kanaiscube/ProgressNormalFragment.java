package com.jebstern.kanaiscube;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import at.grabner.circleprogress.AnimationState;
import at.grabner.circleprogress.AnimationStateChangedListener;
import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.TextMode;


public class ProgressNormalFragment extends Fragment {

    private DBHelper db;
    CircleProgressView cv_armor, cv_weapons, cv_jewelry;
    Boolean mShowUnit = true;
    TextView tv_armor_collected, tv_weapons_collected, tv_jewelry_collected;

    public ProgressNormalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_progress_normal, container, false);

        int cubedArmor = db.getItemAmount("'armor'", "");
        int cubedWeapons = db.getItemAmount("'weapon'", "");
        int cubedJewelry = db.getItemAmount("'jewelry'", "");

        float amount_armor = 100 * (float) cubedArmor / (float) 107;
        float amount_weapons = 100 * (float) cubedWeapons / (float) 142;
        float amount_jewelry = 100 * (float) cubedJewelry / (float) 36;

        tv_armor_collected = (TextView) rootView.findViewById(R.id.tv_armor_collected);
        tv_weapons_collected = (TextView) rootView.findViewById(R.id.tv_weapons_collected);
        tv_jewelry_collected = (TextView) rootView.findViewById(R.id.tv_jewelry_collected);

        tv_armor_collected.setText(cubedArmor + "/107 cubed");
        tv_weapons_collected.setText(cubedWeapons + "/142 cubed");
        tv_jewelry_collected.setText(cubedJewelry + "/36 cubed");

        cv_armor = (CircleProgressView) rootView.findViewById(R.id.cv_armor);
        cv_weapons = (CircleProgressView) rootView.findViewById(R.id.cv_weapons);
        cv_jewelry = (CircleProgressView) rootView.findViewById(R.id.cv_jewelry);


        cv_armor.setMaxValue(100);
        cv_armor.setValue(0);
        cv_armor.setValueAnimated(amount_armor);
        cv_armor.setUnit("%");
        cv_armor.setShowUnit(mShowUnit);
        cv_armor.setAutoTextSize(true); // enable auto text size, previous values are overwritten
        cv_armor.setBarColor(getResources().getColor(R.color.primary_color), getResources().getColor(R.color.secondary_color));
        cv_armor.setAutoTextColor(true); //previous set values are ignored
        cv_armor.setTextMode(TextMode.PERCENT); // Shows current percent of the current value from the max value
        cv_armor.spin(); // start spinning
        cv_armor.setValueAnimated(amount_armor); // stops spinning. Spinner spins until on top. Then fills to set value.
        cv_armor.setShowTextWhileSpinning(true); // Show/hide text in spinning mode
        cv_armor.setText("Loading...");
        cv_armor.setTextMode(TextMode.PERCENT);
        cv_armor.setAnimationStateChangedListener(
                new AnimationStateChangedListener() {
                    @Override
                    public void onAnimationStateChanged(AnimationState _animationState) {
                        switch (_animationState) {
                            case IDLE:
                            case ANIMATING:
                            case START_ANIMATING_AFTER_SPINNING:
                                cv_armor.setTextMode(TextMode.PERCENT); // show percent if not spinning
                                cv_armor.setShowUnit(mShowUnit);
                                break;
                            case SPINNING:
                                cv_armor.setTextMode(TextMode.TEXT); // show text while spinning
                                cv_armor.setShowUnit(false);
                            case END_SPINNING:
                                break;
                            case END_SPINNING_START_ANIMATING:
                                break;
                        }
                    }
                }
        );


        cv_weapons.setMaxValue(100);
        cv_weapons.setValue(0);
        cv_weapons.setValueAnimated(amount_weapons);
        cv_weapons.setUnit("%");
        cv_weapons.setShowUnit(mShowUnit);
        cv_weapons.setAutoTextSize(true); // enable auto text size, previous values are overwritten
        cv_weapons.setBarColor(getResources().getColor(R.color.primary_color), getResources().getColor(R.color.secondary_color));
        cv_weapons.setAutoTextColor(true); //previous set values are ignored
        cv_weapons.setTextMode(TextMode.PERCENT); // Shows current percent of the current value from the max value
        cv_weapons.spin(); // start spinning
        cv_weapons.setValueAnimated(amount_weapons); // stops spinning. Spinner spins until on top. Then fills to set value.
        cv_weapons.setShowTextWhileSpinning(true); // Show/hide text in spinning mode
        cv_weapons.setText("Loading...");
        cv_weapons.setTextMode(TextMode.PERCENT);
        cv_weapons.setAnimationStateChangedListener(
                new AnimationStateChangedListener() {
                    @Override
                    public void onAnimationStateChanged(AnimationState _animationState) {
                        switch (_animationState) {
                            case IDLE:
                            case ANIMATING:
                            case START_ANIMATING_AFTER_SPINNING:
                                cv_weapons.setTextMode(TextMode.PERCENT); // show percent if not spinning
                                cv_weapons.setShowUnit(mShowUnit);
                                break;
                            case SPINNING:
                                cv_weapons.setTextMode(TextMode.TEXT); // show text while spinning
                                cv_weapons.setShowUnit(false);
                            case END_SPINNING:
                                break;
                            case END_SPINNING_START_ANIMATING:
                                break;
                        }
                    }
                }
        );


        cv_jewelry.setMaxValue(100);
        cv_jewelry.setValue(0);
        cv_jewelry.setValueAnimated(amount_jewelry);
        cv_jewelry.setUnit("%");
        cv_jewelry.setShowUnit(mShowUnit);
        cv_jewelry.setAutoTextSize(true); // enable auto text size, previous values are overwritten
        cv_jewelry.setBarColor(getResources().getColor(R.color.primary_color), getResources().getColor(R.color.secondary_color));
        cv_jewelry.setAutoTextColor(true); //previous set values are ignored
        cv_jewelry.setTextMode(TextMode.PERCENT); // Shows current percent of the current value from the max value
        cv_jewelry.spin(); // start spinning
        cv_jewelry.setValueAnimated(amount_jewelry); // stops spinning. Spinner spins until on top. Then fills to set value.
        cv_jewelry.setShowTextWhileSpinning(true); // Show/hide text in spinning mode
        cv_jewelry.setText("Loading...");
        cv_jewelry.setTextMode(TextMode.PERCENT);
        cv_jewelry.setAnimationStateChangedListener(
                new AnimationStateChangedListener() {
                    @Override
                    public void onAnimationStateChanged(AnimationState _animationState) {
                        switch (_animationState) {
                            case IDLE:
                            case ANIMATING:
                            case START_ANIMATING_AFTER_SPINNING:
                                cv_jewelry.setTextMode(TextMode.PERCENT); // show percent if not spinning
                                cv_jewelry.setShowUnit(mShowUnit);
                                break;
                            case SPINNING:
                                cv_jewelry.setTextMode(TextMode.TEXT); // show text while spinning
                                cv_jewelry.setShowUnit(false);
                            case END_SPINNING:
                                break;
                            case END_SPINNING_START_ANIMATING:
                                break;
                        }
                    }
                }
        );


        getActivity().setTitle("Progress - Normal");
        return rootView;
    }


}
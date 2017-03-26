package com.hfad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hfad.workout.R;
import com.hfad.workout.WorkoutDetailFragment;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_WORKOUT_ID = "id"; //для интента и связи MainA и DetailA

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        WorkoutDetailFragment workoutDetailFragment =
                (WorkoutDetailFragment) getFragmentManager().findFragmentById(R.id.detail_frag);
        int workoutId = (int) getIntent().getExtras().get(EXTRA_WORKOUT_ID);
        workoutDetailFragment.setWorkout(workoutId);
    }
}

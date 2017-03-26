package com.hfad.workout;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
//расширяем Fragment
public class WorkoutDetailFragment extends Fragment {
    private long workoutId; //идентефикатор комплекса выбранного упражнения

    public WorkoutDetailFragment() {
        // Required empty public constructor
    }

    //вызывается каждый раз, когда Android потребует макет фрагмента
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //задаём значение workoutId
        if (savedInstanceState != null) {
            workoutId = savedInstanceState.getLong("workoutId");
        }
        //возвращаем объект типа View для прорисовки пользовательского интерфейса
        return inflater.inflate(R.layout.fragment_workout_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView(); //получаем корневой объект View фрагмента
        if (view != null) {
            TextView title = (TextView) view.findViewById(R.id.textTitle);
            Workout workout = Workout.workouts[(int) workoutId];
            title.setText(workout.getName());
            TextView description = (TextView) view.findViewById(R.id.textDescription);
            description.setText(workout.getDescription());
        }
    }

    //сохраняем значение workoutId в объектте savedInstanceState типа Bundle перед уничтожением фрагмента
    //потом она читается в onCreateView()
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong("workoutId", workoutId);
    }

    public void setWorkout (long id) {
        workoutId = id;
    }
}

package com.hfad.workout;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment; //баганутое говно, проще импортировать android.app.Fragment
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.app.ListFragment;
import android.widget.ArrayAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutListFragment extends ListFragment { //расширяется, тк есть фрагмент

    static interface WorkoutListListener {
        void itemClicked(long id);
    }
    //добавляем слушателя к фрагменту ^ и V (это тип стрелочки, лол)
    private WorkoutListListener listener;

    public WorkoutListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] names = new String [Workout.workouts.length];
        //создаём массив строк с названиями комплексов упражнений
        for(int i = 0; i < names.length; i++)
            names[i] = Workout.workouts[i].getName();

        //создаём адаптер массива
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1, names);
        setListAdapter(adapter); //связываем адаптер массива со строковым представлением
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //вызывается при присоединении фрагмента к активности
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (WorkoutListListener) activity;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null)
            listener.itemClicked(id); //сообщаем слушателю, что на одном из вариантов
        //ListView был сделан щелчок
    }
}

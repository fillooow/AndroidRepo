package com.fillooow.clipboardproject;

import android.app.Activity;
import android.app.ListFragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ClipboardTextListFragment extends ListFragment{
    ClipboardHelper helper = new ClipboardHelper();
    String text;
    /*static interface ClipboardListListener {
        void itemClicked(long id);
    }*/
    //добавляем слушателя к фрагменту ^ и V (это тип стрелочки, лол)
    //private ClipboardListListener listener;

    public ClipboardTextListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0); // Получаем первый айтем из буфера обмена
        text = item.getText().toString(); // Получаем текст из буфера
        helper.setClipboardTextArrayList(text);
        text = "sosi";
        helper.setClipboardTextArrayList(text);

        //создаём адаптер массива
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1, helper.clipboardTextArrayList);
        setListAdapter(adapter); //связываем адаптер массива со строковым представлением
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //вызывается при присоединении фрагмента к активности
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //listener = (ClipboardListListener) activity;
    }

    /*@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null)
            listener.itemClicked(id); //сообщаем слушателю, что на одном из вариантов
        //ListView был сделан щелчок
    }*/
}

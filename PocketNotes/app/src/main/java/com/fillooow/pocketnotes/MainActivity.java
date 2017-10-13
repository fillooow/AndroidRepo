package com.fillooow.pocketnotes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Создаём адаптер, который возвращает фрагмент для каждого из трёх
        // основных секций в активити
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(new Intent(MainActivity.this, AddActivity.class));
                intent.putExtra("key", "new");
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        // Ну, вся магия тут, пока что тут (магия)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int counter = 0;
            View rootView = inflater.inflate(R.layout.fragment_tabbed, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            NotesDatabaseAdapter adapter;
            RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_tabb);

            // Получаем фрагмент
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    textView.setVisibility(View.GONE);
                    rv.setVisibility(View.VISIBLE);

                    counter = loadCounter(counter);

                    adapter = new NotesDatabaseAdapter(getActivity(), counter/*,
                            new NotesDatabaseAdapter.NoteDeleteItemListener() {
                        @Override
                        public void onChangeClick(int id) {
                            // TODO: вот тут
                            int noteId = id + 1;
                            SQLiteOpenHelper notesHelper =
                                    new NotesDatabaseHelper(getActivity());
                            try {
                                SQLiteDatabase db = notesHelper.getReadableDatabase();
                                db.delete("NOTES", "_id = " + noteId, null);
                                db.close();
                            } catch (SQLiteException e) {
                                Toast.makeText(getActivity(), "Запрос на запись не обработался",
                                        Toast.LENGTH_SHORT).show();
                            } catch (NullPointerException e) {
                            }
                            Intent intent = new Intent(getActivity(), AddActivity.class);
                            intent.putExtra("key", "new");
                            intent.putExtra("term", "[eq");
                            startActivity(intent);
                        }
                    }*/);

                    rv.setAdapter(adapter); // Ставим адаптер
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    rv.setLayoutManager(layoutManager); // Ставим разметку
                    break;
                case 2:
                    textView.setVisibility(View.VISIBLE);
                    rv.setVisibility(View.GONE);
                    // Забиваем туда текст через связку текст + номер фрагмента
                    textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                    break;
                case 3:
                    textView.setVisibility(View.VISIBLE);
                    rv.setVisibility(View.GONE);
                    // Забиваем туда текст через связку текст + номер фрагмента
                    textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            }

            return rootView;
        }

        private int loadCounter(int count) {

            String dbName = "NOTES";
            SQLiteOpenHelper notesHelper =
                    new NotesDatabaseHelper(getActivity());
            SQLiteDatabase db = notesHelper.getReadableDatabase();
            try {
                Cursor countCursor = db.query(dbName, // Создаём курсор
                        new String[]{"COUNT (_id) AS count"},
                        null, null, null, null, null);
            if (countCursor.moveToFirst()) {
                count = countCursor.getInt(0);
            }
                countCursor.close();
            } catch (SQLiteException e) {
                Toast.makeText(getActivity(), "Счётчик " + /*факапнулся*/ "не хочет работать",
                        Toast.LENGTH_SHORT).show();
            }
            return count;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem() вызывается для установки фрагмента на данной странице
            // Вощвращает PlaceholderFragment (внутренний статик класс ниже)

            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1); // Кидает номер для текста
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        // Ставит название таба
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Заметки";
                case 1:
                    return "Избранное";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }


    /*

                            int noteId = id + 1;
                            SQLiteOpenHelper notesHelper =
                                    new NotesDatabaseHelper(getActivity());
                            try {
                                SQLiteDatabase db = notesHelper.getWritableDatabase();
                                db.delete("NOTES", "_id = " + noteId, null);
                                db.close();
                            } catch (SQLiteException e) {
                                Toast.makeText(getActivity(), "Запрос на запись не обработался",
                                        Toast.LENGTH_SHORT).show();
                            } catch (NullPointerException e) {
                            }
     */
}

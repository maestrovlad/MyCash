package com.example.vb.mycash;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalendarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    Bundle args;
    DayFragment dayFragment;
    String daySelectedDate;
    String weekSelectedDate;
    String mounthSelectedDate;
    String yearSelectedDate;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        CalendarView calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                int WeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;
                int mWeek = WeekOfYear;
                daySelectedDate = new StringBuilder()
                        .append(mDay)
                        .append("-").append(mWeek)
                        .append("-").append(mMonth + 1)
                        .append("-").append(mYear)
                        .append(" ").toString();
                weekSelectedDate = new StringBuilder()
                        .append(mWeek)
                        .append("-").append(mMonth + 1)
                        .append("-").append(mYear)
                        .append(" ").toString();
                mounthSelectedDate = new StringBuilder()
                        .append(mMonth + 1)
                        .append("-").append(mYear)
                        .append(" ").toString();
                yearSelectedDate = new StringBuilder()
                        .append(mYear)
                        .append(" ").toString();
                Log.d("qrrr3444", daySelectedDate);
                CalendarDay();
               // args.putString("dayCalendar",selectedDate);
               // dayFragment.setArguments(args);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_main_frame, new DayFragment());
                ft.commit();

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String calendarDay, String calendarWeek, String calendarMounth, String calendarYear) {
        if (mListener != null) {
            mListener.onFragmentInteraction(calendarDay,calendarWeek,calendarMounth,calendarYear);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void CalendarDay() {
        // Посылаем данные Activity
        mListener.onFragmentInteraction(daySelectedDate,weekSelectedDate,mounthSelectedDate,yearSelectedDate);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String dayCalendar, String weekCalendar, String mounthCalendar, String yearCalendar);
    }
}

package com.example.vb.mycash;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link YearFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link YearFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YearFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    public static String oneYearDate="0";
    TextView balance;
    TextView expensesCash;
    TextView incomeCash;

    public YearFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YearFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YearFragment newInstance(String param1, String param2) {
        YearFragment fragment = new YearFragment();
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
        if (oneYearDate.equals("0")) {
            oneYearDate = getDateNowYear();
        }
    }
    DataBaseHelper dataBaseHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_year, container, false);
        Button plusButton = (Button) view.findViewById(R.id.plus_cash);
        Button minusButton = (Button) view.findViewById(R.id.minus_cash);

        view.setOnTouchListener(new OnSwipeTouchListener(getActivity())
        {
            public void onSwipeRight() {
                try {
                    dateBeforeDay();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SetDate();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(YearFragment.this).attach(YearFragment.this).commit();
                Log.d("qrrr3444swype", oneYearDate);
            }
            public void onSwipeLeft() {
                try {
                    dateAfterDay();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SetDate();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(YearFragment.this).attach(YearFragment.this).commit();
                Log.d("qrrr3444swype", oneYearDate);
            }
        });
        Log.d("qrrr3444", oneYearDate + " dayfrg");

        balance = (TextView) view.findViewById(R.id.balance_of_year_cash);
        expensesCash = (TextView) view.findViewById(R.id.expenses_of_cash_year);
        incomeCash = (TextView) view.findViewById(R.id.income_of_cash_year);

        dataBaseHelper = new DataBaseHelper(getActivity());

        dataBaseHelper.getIncome(oneYearDate);
        dataBaseHelper.getExpenses(oneYearDate);
        SetDate();

        expensesCash.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListFromDB.class);
                intent.putExtra("putListFromDB", "YearLisExpensesFromDB");
                startActivity(intent);
            }
        });

        incomeCash.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListFromDB.class);
                intent.putExtra("putListFromDB", "YearListIncomeFromDB");
                startActivity(intent);
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddCash.class);
                startActivity(intent);
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RemoveCash.class);
                startActivity(intent);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        void onFragmentInteraction(Uri uri);
    }

    public String getDateNowYear()
    {
        String date;
        Date date1 = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("yyyy");
        date = dt.format(date1);
        return date;
    }

    public void dateBeforeDay() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(oneYearDate));
        c.add(Calendar.YEAR, -1);  // number of days to add
        oneYearDate = format.format(c.getTime());
    }

    public void dateAfterDay() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(oneYearDate));
        c.add(Calendar.YEAR, 1);  // number of days to add
        oneYearDate = format.format(c.getTime());
    }

    public void SetDate()
    {
        Cash cash = new Cash();
        incomeCash.setText(String.valueOf(cash.AllCashIncome()));
        expensesCash.setText(String.valueOf(cash.AllCashExpenses()));
        balance.setText(String.valueOf(cash.AllCashIncome()-cash.AllCashExpenses()));
    }
}

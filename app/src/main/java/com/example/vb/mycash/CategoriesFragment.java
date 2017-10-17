package com.example.vb.mycash;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoriesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    DialogFragment dialog;
    Bundle args;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriesFragment newInstance(String param1, String param2) {
        CategoriesFragment fragment = new CategoriesFragment();
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
        args = new Bundle();
        dialog = new DialogDeleteCategorie();
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        final MainActivity mainActivity  = new MainActivity();
         // Create the adapter to convert the array to views
        final ListCategoriesAdapter adapter_incomes = new ListCategoriesAdapter(getActivity(), mainActivity.categoriesIncomes);
        final ListCategoriesAdapter adapter_expenses = new ListCategoriesAdapter(getActivity(), mainActivity.categoriesExpenses);

        // Attach the adapter to a ListView
        final ListView listViewIncomes = (ListView) view.findViewById(R.id.list_all_categories_income);
        ListView listViewExpenses = (ListView) view.findViewById(R.id.list_all_categories_expense);
        listViewIncomes.setAdapter(adapter_incomes);
        listViewExpenses.setAdapter(adapter_expenses);
        listViewIncomes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), EditCategorie.class);
                intent.putExtra("Id_and_Categorie", mainActivity.categoriesIncomes.get(position).ID + " " + mainActivity.categoriesIncomes.get(position).categorie);
                startActivity(intent);
            }
        });
        listViewExpenses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), EditCategorie.class);
                intent.putExtra("Id_and_Categorie", mainActivity.categoriesExpenses.get(position).ID + " " + mainActivity.categoriesExpenses.get(position).categorie);
                startActivity(intent);
            }
        });
        listViewExpenses.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                args.putString("delete_categories", adapter_expenses.getItem(position).ID + "--" + "CategoriesExpense"
                        + "--" + "CategoryExpense");
                dialog.setArguments(args);
                dialog.show(getActivity().getFragmentManager(), "TAG");
                return true;
            }
        });
        listViewIncomes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                args.putString("delete_categories", adapter_incomes.getItem(position).ID + "--" + "CategoriesIncome"
                        + "--" + "CategoryIncome");
                dialog.setArguments(args);
                dialog.show(getActivity().getFragmentManager(), "TAG");
                return true;
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
}

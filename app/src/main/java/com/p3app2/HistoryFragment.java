package com.p3app2;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class HistoryFragment extends Fragment {
    View _view;

    private OnFragmentInteractionListener mListener;



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Log.d("HistoryFragment", "reaching history onViewCreated");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _view =  inflater.inflate(R.layout.fragment_history, container, false);


        return _view;
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

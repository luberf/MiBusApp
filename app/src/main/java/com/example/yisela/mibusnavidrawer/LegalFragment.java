package com.example.yisela.mibusnavidrawer;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LegalFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view;
        view= inflater.inflate(R.layout.fragment_legal, container, false);
        //((MainActivity) getActivity()).onClick(view);//Se usa para llamar del fragment a un Activity
        return view;
    }

    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

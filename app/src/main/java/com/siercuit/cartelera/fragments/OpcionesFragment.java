package com.siercuit.cartelera.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.siercuit.cartelera.R;
import com.siercuit.cartelera.interfaces.animationInterface;
import com.siercuit.cartelera.utilities.ProgressFragment;

public class OpcionesFragment extends ProgressFragment
{
    public OpcionesFragment() { }

    public static Fragment newInstance(Integer color, String title)
    {
        Fragment fragment = new OpcionesFragment();
        setFragmentArguments(color, title, fragment);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setContentView(R.layout.opciones);
    }

    @Override
    public void viewBuilder() { }

    @Override
    public void dataFetcher()
    {
        setData(new empty(), Class.class);
    }

    public class empty { }

    @Override
    public void inAnimator()
    {

    }

    @Override
    public void outAnimator(animationInterface callback)
    {

    }

}
package com.siercuit.cartelera.utilities;

import android.app.ActivityManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.siercuit.cartelera.App;
import io.vextil.billboard.R;
import com.siercuit.cartelera.interfaces.animationInterface;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class ProgressFragment extends Fragment {

    private View mContentContainer;
    private View mContentView;
    private View mEmptyView;
    private boolean mContentShown;
    private boolean mIsContentEmpty;

    private Integer color;
    private String title;
    private boolean toggleIsArrow;
    private boolean paused = false;

    private String data;
    private String dataArrayId;
    private Map<String, String> dataArray;
    private Class POJO;
    private Map<String, Class> POJOArray;

    private Spinner toolbarSpinner;
    private boolean usesToolbarSpinner = false;
    private Integer toolbarSpinnerPosition;
    private Boolean spinnerPositionIsRestored = false;
    private Integer toolbarSpinnerResourceArray;

    private boolean shouldShowData = true;

    private AdView adView;

    public ProgressFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ensureContent();
    }

    @Override
    public void onDestroyView() {
        mContentShown = false;
        mIsContentEmpty = false;
        mContentContainer = mContentView = mEmptyView = null;
        super.onDestroyView();
    }

    public View getContentView() {
        return mContentView;
    }

    public void setContentView(int layoutResId) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View contentView = layoutInflater.inflate(layoutResId, null);
        setContentView(contentView);
    }

    public void setContentView(View view) {
        ensureContent();
        shouldShowData = true;
        if (view == null) {
            throw new IllegalArgumentException("Content view can't be null");
        }
        if (mContentContainer instanceof ViewGroup) {
            ViewGroup contentContainer = (ViewGroup) mContentContainer;
            if (mContentView == null) {
                contentContainer.addView(view);
            } else {
                int index = contentContainer.indexOfChild(mContentView);
                contentContainer.removeView(mContentView);
                contentContainer.addView(view, index);
            }
            mContentView = view;
        } else {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
    }

    public void setEmptyText(int resId) {
        setEmptyText(getString(resId));
    }

    public void setEmptyText(CharSequence text) {
        ensureContent();
        if (mEmptyView != null && mEmptyView instanceof TextView) {
            ((TextView) mEmptyView).setText(text);
        } else {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
    }

    public void setContentShown(boolean shown) {
        setContentShown(shown, true);
    }

    public void setContentShownNoAnimation(boolean shown) {
        setContentShown(shown, false);
    }

    private void setContentShown(boolean shown, boolean animate) {
        ensureContent();
        if (mContentShown == shown) {
            return;
        }
        mContentShown = shown;
        if (shown) {
            if (animate) {
                inAnimator();
            }
        } else {
            if (animate) {
                animationInterface callback = new animationInterface() {
                    @Override
                    public void onFinished() {

                    }
                };
                outAnimator(callback);
            }
        }
    }

    public boolean isContentEmpty() {
        return mIsContentEmpty;
    }

    public void setContentEmpty(boolean isEmpty) {
        ensureContent();
        if (mContentView == null) {
            throw new IllegalStateException("Content view must be initialized before");
        }
        if (isEmpty) {
            mEmptyView.setVisibility(View.VISIBLE);
            mContentView.setVisibility(View.INVISIBLE);
        } else {
            mEmptyView.setVisibility(View.INVISIBLE);
            mContentView.setVisibility(View.VISIBLE);
        }
        mIsContentEmpty = isEmpty;
    }

    private void ensureContent() {
        if (mContentContainer != null) {
            return;
        }
        View root = getView();
        if (root == null) {
            throw new IllegalStateException("Content view not yet created");
        }
        mContentContainer = root.findViewById(R.id.content_container);
        if (mContentContainer == null) {
            throw new RuntimeException("Your content must have a ViewGroup whose id attribute is 'R.id.content_container'");
        }
        mEmptyView = root.findViewById(android.R.id.empty);
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.INVISIBLE);
        }
        mContentShown = true;
        if (mContentView == null) {
            setContentShown(false, false);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        color = bundle.getInt("color", 0);
        title = bundle.getString("title");
        toggleIsArrow = bundle.getBoolean("toggleIsArrow");
        if (savedInstanceState != null) {
            usesToolbarSpinner = savedInstanceState.getBoolean("usesToolbarSpinner");
            if (usesToolbarSpinner) {
                dataArrayId = savedInstanceState.getString("dataArrayId");
                dataArray = (HashMap<String, String>) savedInstanceState.getSerializable("dataArray");
                POJOArray = (HashMap<String, Class>) savedInstanceState.getSerializable("POJOArray");
                setToolbarSpinnerPosition(savedInstanceState.getInt("toolbarSpinnerPosition"), true);
            } else {
                data = savedInstanceState.getString("data");
                POJO = (Class) savedInstanceState.getSerializable("POJO");
            }
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        paused = false;
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(title);
        App.setColorScheme(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            App.getActivity().setTaskDescription(new ActivityManager.TaskDescription(
                    title,
                    ((BitmapDrawable) App.getActivity().getResources().getDrawable(R.drawable.ic_launcher)).getBitmap(),
                    color
            ));
        }
        if (toggleIsArrow) {
            App.animateBurgerToArrow();
        } else {
            App.animateArrowToBurger();
        }

        if (usesToolbarSpinner) {
            if (dataArray == null) {
                dataArray = new HashMap<String, String>();
                POJOArray = new HashMap<String, Class>();
            }

            toolbarSpinner = (Spinner) getActivity().findViewById(R.id.toolbar_spinner);
            toolbarSpinner.setVisibility(View.VISIBLE);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    getActivity(),
                    toolbarSpinnerResourceArray,
                    R.layout.actionbar_spinner_item
            );

            adapter.setDropDownViewResource(R.layout.actionbar_spinner_dropdown_item);
            toolbarSpinner.setAdapter(adapter);
            toolbarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    setDataArrayId(App.getCinemaSlugs()[position]);
                    setToolbarSpinnerPosition(position);
                    notifyDataArrayIdChanged();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });
            if (spinnerPositionIsRestored) {
                toolbarSpinner.setSelection(toolbarSpinnerPosition);
            }
        } else if (getData() == null) {
            dataFetcher();
        } else if (shouldShowData) {
            showData();
        }
    }


    @Override
    public void onPause()
    {
        if (adView != null) {
            adView.destroy();
        }
        super.onPause();
        paused = true;
        color = App.getColor();
        setToolbarSpinnerPosition(toolbarSpinnerPosition, true);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (usesToolbarSpinner) {
            toolbarSpinner.setVisibility(View.GONE);
        }
    }

    public boolean isPaused()
    {
        return paused;
    }

    public static void setFragmentArguments(Integer color, String title, Fragment fragment)
    {
        Bundle args = new Bundle();
        args.putInt("color", color);
        args.putString("title", title);
        args.putBoolean("toggleIsArrow", false);
        fragment.setArguments(args);
    }

    public static void setFragmentArguments(Integer color, String title, String id, Fragment fragment, boolean isToggleArrow)
    {
        Bundle args = new Bundle();
        args.putInt("color", color);
        args.putString("title", title);
        args.putString("id", id);
        args.putBoolean("toggleIsArrow", isToggleArrow);
        fragment.setArguments(args);
    }

    public static void setFragmentArguments(String title, String id, Fragment fragment, boolean isToggleArrow)
    {
        Bundle args = new Bundle();
        args.putInt("color", App.getActivity().getResources().getColor(R.color.material_blue_grey_800));
        args.putString("title", title);
        args.putString("id", id);
        args.putBoolean("toggleIsArrow", isToggleArrow);
        fragment.setArguments(args);
    }

    public void showData()
    {
        shouldShowData = false;
        viewBuilder();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentShown(true);

            }
        }, 0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adView != null) {
                    adView.loadAd(new AdRequest.Builder().build());
                }
            }
        }, 2000);
    }

    public void setData(Object objectData, Class classPOJO)
    {
        data = App.getGson().toJson(objectData);
        POJO = classPOJO;
        shouldShowData = true;
        if (!isPaused()) {
            showData();
        }
    }

    public Object getData()
    {
        if (data != null) {
            return App.getGson().fromJson(data, POJO);
        }
        return null;
    }

    public void setDataArrayId(String id)
    {
        dataArrayId = id;
    }

    public String getDataArrayId()
    {
        return dataArrayId;
    }

    public void setDataOnArrayId(Object objectData, Class classPOJO)
    {
        dataArray.put(dataArrayId, App.getGson().toJson(objectData));
        POJOArray.put(dataArrayId, classPOJO);
        shouldShowData = true;
        if (!isPaused()) {
            showData();
        }
    }

    public Object getDataFromArray()
    {
        if (dataArray.containsKey(getDataArrayId())) {
            return App.getGson().fromJson(dataArray.get(getDataArrayId()), POJOArray.get(getDataArrayId()));
        }
        return null;
    }

    public void notifyDataArrayIdChanged()
    {
        setContentShown(false);
        if (dataArray.containsKey(getDataArrayId())) {
            showData();
        } else {
            dataFetcher();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle)
    {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("toggleIsArrow", toggleIsArrow);
        bundle.putBoolean("usesToolbarSpinner", usesToolbarSpinner);
        if (usesToolbarSpinner) {
            bundle.putString("dataArrayId", dataArrayId);
            bundle.putSerializable("dataArray", (Serializable) dataArray);
            bundle.putSerializable("POJOArray", (Serializable) POJOArray);
            bundle.putInt("toolbarSpinnerPosition", toolbarSpinnerPosition);
        } else if (data != null) {
            bundle.putString("data", data);
            bundle.putSerializable("POJO", POJO);
        }
    }

    public void usesToolbarSpinner(Boolean does)
    {
        usesToolbarSpinner = does;
    }

    public void setToolbarSpinnerPosition (Integer position, Boolean restored)
    {
        toolbarSpinnerPosition = position;
        spinnerPositionIsRestored = restored;
    }

    public void setToolbarSpinnerPosition (Integer position)
    {
        setToolbarSpinnerPosition(position, false);
    }

    public void setToolbarSpinnerResourceArray(Integer resource)
    {
        toolbarSpinnerResourceArray = resource;
    }

    public void setAdView(AdView view)
    {
        adView = view;
    }

    public abstract void viewBuilder();

    public abstract void inAnimator();

    public abstract void outAnimator(animationInterface callback);

    public abstract void dataFetcher();

}
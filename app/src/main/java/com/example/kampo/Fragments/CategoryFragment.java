package com.example.kampo.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.kampo.R;
import com.example.kampo.databinding.FragmentCategoryBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {
    FragmentCategoryBinding binding;
    Animation leftanim, rightanim,topanim,bottomanim;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
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
        binding=FragmentCategoryBinding.inflate(inflater,container,false);

//        Animation
        leftanim= AnimationUtils.loadAnimation(getContext(),R.anim.left_animation);
        rightanim= AnimationUtils.loadAnimation(getContext(),R.anim.right_animation);
        topanim= AnimationUtils.loadAnimation(getContext(),R.anim.top_animation);
        bottomanim= AnimationUtils.loadAnimation(getContext(),R.anim.bottom_animation);
        binding.hairColor.setAnimation(topanim);
        binding.hairCut.setAnimation(topanim);
        binding.hairStyling.setAnimation(leftanim);
        binding.massage.setAnimation(leftanim);
        binding.hairTreatment.setAnimation(rightanim);
        binding.categoryShaving.setAnimation(rightanim);
        binding.facials.setAnimation(bottomanim);
        binding.trimming.setAnimation(bottomanim);
        return binding.getRoot();

    }
}
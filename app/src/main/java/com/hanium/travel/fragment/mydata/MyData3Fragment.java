package com.hanium.travel.fragment.mydata;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.hanium.travel.R;
import com.hanium.travel.project.PreferenceManager;
import com.hanium.travel.validclass.ValidationCard;

public class MyData3Fragment extends Fragment implements ValidationCard {

    private MaterialCardView food_card;
    private MaterialCardView picture_card;
    private MaterialCardView tour_card;
    private MaterialCardView healing_card;

    private boolean[] isCheckedArray;

    public static MyData3Fragment newInstance() {
        MyData3Fragment myData3Fragment = new MyData3Fragment();
        return myData3Fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_mydata3, container, false);

        ImageView food_image = view.findViewById(R.id.food_image);
        ImageView picture_image = view.findViewById(R.id.picture_image);
        ImageView tour_image = view.findViewById(R.id.tour_image);
        ImageView healing_image = view.findViewById(R.id.healing_image);

        food_card = view.findViewById(R.id.food_card);
        picture_card = view.findViewById(R.id.picture_card);
        tour_card = view.findViewById(R.id.tour_card);
        healing_card = view.findViewById(R.id.healing_card);

        Glide.with(getActivity()).load(R.drawable.sample).placeholder(R.drawable.noimage).into(food_image);
        Glide.with(getActivity()).load(R.drawable.sample).placeholder(R.drawable.noimage).into(picture_image);
        Glide.with(getActivity()).load(R.drawable.sample).placeholder(R.drawable.noimage).into(tour_image);
        Glide.with(getActivity()).load(R.drawable.sample).placeholder(R.drawable.noimage).into(healing_image);

        food_card.setOnClickListener(onClickListener);
        picture_card.setOnClickListener(onClickListener);
        tour_card.setOnClickListener(onClickListener);
        healing_card.setOnClickListener(onClickListener);

        if(PreferenceManager.getBoolean(requireActivity(), "isFirst3"))
            for(int i = 0; i < 4; i++)
                switch (i) {
                    case 0 :
                        food_card.setChecked(PreferenceManager.getBoolean(requireActivity(), "mydata3-" + i));
                        break;
                    case 1 :
                        picture_card.setChecked(PreferenceManager.getBoolean(requireActivity(), "mydata3-" + i));
                        break;
                    case 2 :
                        tour_card.setChecked(PreferenceManager.getBoolean(requireActivity(), "mydata3-" + i));
                        break;
                    case 3 :
                        healing_card.setChecked(PreferenceManager.getBoolean(requireActivity(), "mydata3-" + i));
                        break;
                }

        View nextBtnView = requireActivity().findViewById(R.id.next_btn);
        nextBtnView.setOnClickListener(btnView -> {

            boolean isValid = isSelectCard(food_card, picture_card, tour_card, healing_card);

            if(!isValid) {
                setDialog(requireActivity(), "선택해 주세요!", "취향에 맞는 여행지 추천을 위해 최소 한 개 이상 선택해 주세요.");
                return;
            }

            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            MyData4Fragment myData4Fragment = new MyData4Fragment();

            fragmentTransaction.setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
            );

            for(int i = 0; i < isCheckedArray.length; i++)
                PreferenceManager.setBoolean(requireActivity(), "mydata3-" + i, isCheckedArray[i]);
            PreferenceManager.setBoolean(requireActivity(), "isFirst3", true);

            Bundle bundle = new Bundle();
            bundle.putBooleanArray("mydata1", getArguments().getBooleanArray("mydata1"));
            bundle.putBooleanArray("mydata2", getArguments().getBooleanArray("mydata2"));
            bundle.putBooleanArray("mydata3", isCheckedArray);
            myData4Fragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.mydata_frame, myData4Fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commitAllowingStateLoss();
        });

        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.food_card:
                    food_card.setChecked(!food_card.isChecked());
                    break;
                case R.id.picture_card:
                    picture_card.setChecked(!picture_card.isChecked());
                    break;
                case R.id.tour_card:
                    tour_card.setChecked(!tour_card.isChecked());
                    break;
                case R.id.healing_card:
                    healing_card.setChecked(!healing_card.isChecked());
                    break;
            }
        }
    };

    @Override
    public boolean isSelectCard(MaterialCardView cardView1, MaterialCardView cardView2, MaterialCardView cardView3) {
        return false;
    }

    @Override
    public boolean isSelectCard(MaterialCardView cardView1, MaterialCardView cardView2, MaterialCardView cardView3, MaterialCardView cardView4) {

        isCheckedArray = new boolean[4];
        isCheckedArray[0] = cardView1.isChecked();
        isCheckedArray[1] = cardView2.isChecked();
        isCheckedArray[2] = cardView3.isChecked();
        isCheckedArray[3] = cardView4.isChecked();

        return cardView1.isChecked() || cardView2.isChecked() || cardView3.isChecked() || cardView4.isChecked();
    }
}

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
import com.hanium.travel.validclass.ValidationCard;
import com.hanium.travel.activity.CollectMyDataActivity;
import com.hanium.travel.R;
import com.hanium.travel.project.PreferenceManager;

public class MyData1Fragment extends Fragment implements ValidationCard {

    private MaterialCardView car_card;
    private MaterialCardView public_card;
    private MaterialCardView bike_card;
    private MaterialCardView walk_card;

    private boolean[] isCheckedArray;

    public static MyData1Fragment newInstance() {
        MyData1Fragment myData1Fragment = new MyData1Fragment();
        return myData1Fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_mydata1, container, false);

        ((CollectMyDataActivity) getActivity()).setVisibility();

        ImageView car_image = view.findViewById(R.id.car_image);
        ImageView public_image = view.findViewById(R.id.public_image);
        ImageView bike_image = view.findViewById(R.id.bike_image);
        ImageView walk_image = view.findViewById(R.id.walk_image);

        car_card = view.findViewById(R.id.car_card);
        bike_card = view.findViewById(R.id.bike_card);
        public_card = view.findViewById(R.id.public_card);
        walk_card = view.findViewById(R.id.walk_card);

        Glide.with(getActivity()).load(R.drawable.sample).placeholder(R.drawable.noimage).into(car_image);
        Glide.with(getActivity()).load(R.drawable.sample).placeholder(R.drawable.noimage).into(public_image);
        Glide.with(getActivity()).load(R.drawable.sample).placeholder(R.drawable.noimage).into(bike_image);
        Glide.with(getActivity()).load(R.drawable.sample).placeholder(R.drawable.noimage).into(walk_image);

        car_card.setOnClickListener(onClickListener);
        bike_card.setOnClickListener(onClickListener);
        public_card.setOnClickListener(onClickListener);
        walk_card.setOnClickListener(onClickListener);

        if(PreferenceManager.getBoolean(requireActivity(), "isFirst1"))
            for(int i = 0; i < 4; i++)
                switch (i) {
                    case 0 :
                        walk_card.setChecked(PreferenceManager.getBoolean(requireActivity(), "mydata1-" + i));
                        break;
                    case 1 :
                        bike_card.setChecked(PreferenceManager.getBoolean(requireActivity(), "mydata1-" + i));
                        break;
                    case 2 :
                        public_card.setChecked(PreferenceManager.getBoolean(requireActivity(), "mydata1-" + i));
                        break;
                    case 3 :
                        car_card.setChecked(PreferenceManager.getBoolean(requireActivity(), "mydata1-" + i));
                        break;
                }

        View nextBtnView = requireActivity().findViewById(R.id.next_btn);
        nextBtnView.setOnClickListener(btnView -> {

            boolean isValid = isSelectCard(walk_card, bike_card, public_card, car_card);

            if(!isValid) {
                setDialog(requireActivity(), "선택해 주세요!", "취향에 맞는 여행지 추천을 위해 최소 한 개 이상 선택해 주세요.");
                return;
            }

            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            MyData2Fragment myData2Fragment = new MyData2Fragment();

            fragmentTransaction.setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
            );

            for(int i = 0; i < isCheckedArray.length; i++)
                PreferenceManager.setBoolean(requireActivity(), "mydata1-" + i, isCheckedArray[i]);
            PreferenceManager.setBoolean(requireActivity(), "isFirst1", true);

            Bundle bundle = new Bundle();
            bundle.putBooleanArray("mydata1", isCheckedArray);
            myData2Fragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.mydata_frame, myData2Fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commitAllowingStateLoss();
        });

        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.car_card:
                    car_card.setChecked(!car_card.isChecked());
                    break;
                case R.id.bike_card:
                    bike_card.setChecked(!bike_card.isChecked());
                    break;
                case R.id.walk_card:
                    walk_card.setChecked(!walk_card.isChecked());
                    break;
                case R.id.public_card:
                    public_card.setChecked(!public_card.isChecked());
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

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

public class MyData2Fragment extends Fragment implements ValidationCard {

    private MaterialCardView sea_card;
    private MaterialCardView mountain_card;
    private MaterialCardView city_card;

    private boolean[] isCheckedArray;

    public static MyData2Fragment newInstance() {
        MyData2Fragment myData2Fragment = new MyData2Fragment();
        return myData2Fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_mydata2, container, false);

        ImageView sea_image = view.findViewById(R.id.sea_image);
        ImageView mountain_image = view.findViewById(R.id.mountain_image);
        ImageView city_image = view.findViewById(R.id.city_image);

        sea_card = view.findViewById(R.id.sea_card);
        mountain_card = view.findViewById(R.id.mountain_card);
        city_card = view.findViewById(R.id.city_card);

        Glide.with(getActivity()).load(R.drawable.sample).placeholder(R.drawable.noimage).into(sea_image);
        Glide.with(getActivity()).load(R.drawable.sample).placeholder(R.drawable.noimage).into(mountain_image);
        Glide.with(getActivity()).load(R.drawable.sample).placeholder(R.drawable.noimage).into(city_image);

        sea_card.setOnClickListener(onClickListener);
        mountain_card.setOnClickListener(onClickListener);
        city_card.setOnClickListener(onClickListener);

        if(PreferenceManager.getBoolean(requireActivity(), "isFirst2"))
            for(int i = 0; i < 3; i++)
                switch (i) {
                    case 0 :
                        sea_card.setChecked(PreferenceManager.getBoolean(requireActivity(), "mydata2-" + i));
                        break;
                    case 1 :
                        mountain_card.setChecked(PreferenceManager.getBoolean(requireActivity(), "mydata2-" + i));
                        break;
                    case 2 :
                        city_card.setChecked(PreferenceManager.getBoolean(requireActivity(), "mydata2-" + i));
                        break;
                }

        View nextBtnView = requireActivity().findViewById(R.id.next_btn);
        nextBtnView.setOnClickListener(btnView -> {

            boolean isValid = isSelectCard(sea_card, mountain_card, city_card);

            if(!isValid) {
                setDialog(requireActivity(), "선택해 주세요!", "취향에 맞는 여행지 추천을 위해 최소 한 개 이상 선택해 주세요.");
                return;
            }

            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            MyData3Fragment myData3Fragment = new MyData3Fragment();

            fragmentTransaction.setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
            );

            for(int i = 0; i < isCheckedArray.length; i++)
                PreferenceManager.setBoolean(requireActivity(), "mydata2-" + i, isCheckedArray[i]);
            PreferenceManager.setBoolean(requireActivity(), "isFirst2", true);

            Bundle bundle = new Bundle();
            bundle.putBooleanArray("mydata1", getArguments().getBooleanArray("mydata1"));
            bundle.putBooleanArray("mydata2", isCheckedArray);
            myData3Fragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.mydata_frame, myData3Fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commitAllowingStateLoss();
        });

        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.sea_card:
                    sea_card.setChecked(!sea_card.isChecked());
                    break;
                case R.id.mountain_card:
                    mountain_card.setChecked(!mountain_card.isChecked());
                    break;
                case R.id.city_card:
                    city_card.setChecked(!city_card.isChecked());
                    break;
            }
        }
    };

    @Override
    public boolean isSelectCard(MaterialCardView cardView1, MaterialCardView cardView2, MaterialCardView cardView3) {

        isCheckedArray = new boolean[3];
        isCheckedArray[0] = cardView1.isChecked();
        isCheckedArray[1] = cardView2.isChecked();
        isCheckedArray[2] = cardView3.isChecked();

        return cardView1.isChecked() || cardView2.isChecked() || cardView3.isChecked();
    }

    @Override
    public boolean isSelectCard(MaterialCardView cardView1, MaterialCardView cardView2, MaterialCardView cardView3, MaterialCardView cardView4) {
        return false;
    }
}

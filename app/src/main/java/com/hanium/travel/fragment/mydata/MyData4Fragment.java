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

public class MyData4Fragment extends Fragment implements ValidationCard {

    private MaterialCardView lover_card;
    private MaterialCardView friend_card;
    private MaterialCardView family_card;
    private MaterialCardView solo_card;

    private boolean[] isCheckedArray;

    public static MyData4Fragment newInstance() {
        MyData4Fragment myData4Fragment = new MyData4Fragment();
        return myData4Fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_mydata4, container, false);

        ImageView lover_image = view.findViewById(R.id.lover_image);
        ImageView friend_image = view.findViewById(R.id.friend_image);
        ImageView family_image = view.findViewById(R.id.family_image);
        ImageView solo_image = view.findViewById(R.id.solo_image);

        lover_card = view.findViewById(R.id.lover_card);
        friend_card = view.findViewById(R.id.friend_card);
        family_card = view.findViewById(R.id.family_card);
        solo_card = view.findViewById(R.id.solo_card);

        Glide.with(getActivity()).load(R.drawable.sample).placeholder(R.drawable.noimage).into(lover_image);
        Glide.with(getActivity()).load(R.drawable.sample).placeholder(R.drawable.noimage).into(friend_image);
        Glide.with(getActivity()).load(R.drawable.sample).placeholder(R.drawable.noimage).into(family_image);
        Glide.with(getActivity()).load(R.drawable.sample).placeholder(R.drawable.noimage).into(solo_image);

        lover_card.setOnClickListener(onClickListener);
        friend_card.setOnClickListener(onClickListener);
        family_card.setOnClickListener(onClickListener);
        solo_card.setOnClickListener(onClickListener);

        if(PreferenceManager.getBoolean(requireActivity(), "isFirst4"))
            for(int i = 0; i < 4; i++)
                switch (i) {
                    case 0 :
                        lover_card.setChecked(PreferenceManager.getBoolean(requireActivity(), "mydata4-" + i));
                        break;
                    case 1 :
                        friend_card.setChecked(PreferenceManager.getBoolean(requireActivity(), "mydata4-" + i));
                        break;
                    case 2 :
                        family_card.setChecked(PreferenceManager.getBoolean(requireActivity(), "mydata4-" + i));
                        break;
                    case 3 :
                        solo_card.setChecked(PreferenceManager.getBoolean(requireActivity(), "mydata4-" + i));
                        break;
                }

        View nextBtnView = requireActivity().findViewById(R.id.next_btn);
        nextBtnView.setOnClickListener(btnView -> {

            boolean isValid = isSelectCard(lover_card, friend_card, family_card, solo_card);

            if(!isValid) {
                setDialog(requireActivity(), "선택해 주세요!", "취향에 맞는 여행지 추천을 위해 최소 한 개 이상 선택해 주세요.");
                return;
            }

            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            MyData5Fragment myData5Fragment = new MyData5Fragment();

            fragmentTransaction.setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
            );

            for(int i = 0; i < isCheckedArray.length; i++)
                PreferenceManager.setBoolean(requireActivity(), "mydata4-" + i, isCheckedArray[i]);
            PreferenceManager.setBoolean(requireActivity(), "isFirst4", true);

            Bundle bundle = new Bundle();
            bundle.putBooleanArray("mydata1", getArguments().getBooleanArray("mydata1"));
            bundle.putBooleanArray("mydata2", getArguments().getBooleanArray("mydata2"));
            bundle.putBooleanArray("mydata3", getArguments().getBooleanArray("mydata3"));
            bundle.putBooleanArray("mydata4", isCheckedArray);
            myData5Fragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.mydata_frame, myData5Fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commitAllowingStateLoss();
        });

        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.lover_card:
                    lover_card.setChecked(!lover_card.isChecked());
                    break;
                case R.id.friend_card:
                    friend_card.setChecked(!friend_card.isChecked());
                    break;
                case R.id.family_card:
                    family_card.setChecked(!family_card.isChecked());
                    break;
                case R.id.solo_card:
                    solo_card.setChecked(!solo_card.isChecked());
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

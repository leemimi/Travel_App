package com.hanium.travel.fragment.mydata;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.hanium.travel.R;
import com.hanium.travel.project.PreferenceManager;
import com.hanium.travel.validclass.ValidationEdit;

public class MyData5Fragment extends Fragment implements ValidationEdit {

    private TextInputEditText minMoney;
    private TextInputEditText maxMoney;

    public static MyData5Fragment newInstance() {
        MyData5Fragment myData5Fragment = new MyData5Fragment();
        return myData5Fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_mydata5, container, false);

        minMoney = view.findViewById(R.id.min_money);
        maxMoney = view.findViewById(R.id.max_money);

        if(PreferenceManager.getBoolean(requireActivity(), "isFirst5"))
            for(int i = 0; i < 2; i++)
                switch (i) {
                    case 0 :
                        minMoney.setText(PreferenceManager.getString(requireActivity(), "mydata5-" + i));
                        break;
                    case 1 :
                        maxMoney.setText(PreferenceManager.getString(requireActivity(), "mydata5-" + i));
                        break;
                }

        View nextBtnView = requireActivity().findViewById(R.id.next_btn);
        nextBtnView.setOnClickListener(btnView -> {

            boolean isValid = isEnterAllValue(minMoney, maxMoney);
            if(!isValid) {
                setDialog(requireActivity(), "입력해 주세요!", "취향에 맞는 여행지 추천을 위해 모든 사항을 입력해 주세요.");
                return;
            }

            int isDataValid = isDataValid(minMoney, maxMoney);
            switch (isDataValid) {
                case 1 :
                    setDialog(requireActivity(), "OOPS !", "최대 금액에는 최소 금액보다 큰 금액을 기재해 주셔야 해요!");
                    break;
                case 2 :
                    minMoney.setError("금액은 1만원보다 커야 해요!");
                    break;
                default :
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    MyData6Fragment myData6Fragment = new MyData6Fragment();

                    fragmentTransaction.setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.fade_out,
                            R.anim.fade_in,
                            R.anim.slide_out
                    );

                    String[] valueArray = new String[2];
                    valueArray[0] = minMoney.getText().toString().trim();
                    valueArray[1] = maxMoney.getText().toString().trim();

                    for(int i = 0; i < valueArray.length; i++)
                        PreferenceManager.setString(requireActivity(), "mydata5-" + i, valueArray[i]);
                    PreferenceManager.setBoolean(requireActivity(), "isFirst5", true);

                    Bundle bundle = new Bundle();
                    bundle.putBooleanArray("mydata1", getArguments().getBooleanArray("mydata1"));
                    bundle.putBooleanArray("mydata2", getArguments().getBooleanArray("mydata2"));
                    bundle.putBooleanArray("mydata3", getArguments().getBooleanArray("mydata3"));
                    bundle.putBooleanArray("mydata4", getArguments().getBooleanArray("mydata4"));
                    bundle.putStringArray("mydata5", valueArray);
                    myData6Fragment.setArguments(bundle);

                    fragmentTransaction.replace(R.id.mydata_frame, myData6Fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commitAllowingStateLoss();
                    break;
            }
        });

        return view;
    }

    @Override
    public boolean isEnterAllValue(TextInputEditText editText1, TextInputEditText editText2) {
        return !editText1.getText().toString().trim().isEmpty() && !editText2.getText().toString().trim().isEmpty();
    }

    @Override
    public boolean isEnterAllValue(TextInputEditText editText1, TextInputEditText editText2, TextInputEditText editText3) {
        return false;
    }

    @Override
    public int isDataValid(TextInputEditText editText1, TextInputEditText editText2) {
        int minMoney = Integer.parseInt(editText1.getText().toString().trim());
        int maxMoney = Integer.parseInt(editText2.getText().toString().trim());

        if(minMoney > maxMoney)
            return 1;
        if(minMoney < 10000)
            return 2;
        return 0;
    }

    @Override
    public int isDataValid(TextInputEditText editText1, TextInputEditText editText2, TextInputEditText editText3) {
        return 0;
    }
}

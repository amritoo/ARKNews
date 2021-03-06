package com.example.arknews.utility;


import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import androidx.core.util.Pair;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Date;

/**
 * This class is made to show a {@link MaterialDatePicker} when clicking on the textView.
 */
public class EditTextDateRangePicker implements OnClickListener {

    private EditText mEditText1, mEditText2;
    FragmentManager mFragmentManager;

    MaterialDatePicker<Pair<Long, Long>> dateRangePicker;
    public static Date startDate, endDate;

    public EditTextDateRangePicker(EditText mEditText1, EditText mEditText2, FragmentManager mFragmentManager) {
        this.mEditText1 = mEditText1;
        this.mEditText2 = mEditText2;
        this.mFragmentManager = mFragmentManager;

        mEditText1.setOnClickListener(this);
        mEditText2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select date range")
                .setSelection(
                        Pair.create(
                                MaterialDatePicker.thisMonthInUtcMilliseconds(),
                                MaterialDatePicker.todayInUtcMilliseconds()
                        )
                )
                .build();
        dateRangePicker.addOnPositiveButtonClickListener(selection -> {
            Long startUtc = dateRangePicker.getSelection().first;
            Long endUtc = dateRangePicker.getSelection().second;
            startDate = new Date(startUtc);
            endDate = new Date(endUtc);

            updateDisplay();
        });
        dateRangePicker.show(mFragmentManager, "date-range");
    }

    /**
     * This method updates the date in the EditText.
     */
    private void updateDisplay() {
        mEditText1.setText(Methods.convertDateToShortString(startDate));
        mEditText2.setText(Methods.convertDateToShortString(endDate));
    }

}
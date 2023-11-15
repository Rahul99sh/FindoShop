package com.pro.findoshop.bottomSheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pro.findoshop.R;

public class LoadingSheet extends BottomSheetDialogFragment {
    String text;

    public LoadingSheet(String text) {
        this.text = text;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loading_sheet, container, false);
        TextView textView = view.findViewById(R.id.text);
        textView.setText(text);
        return  view;
    }

}

package com.pro.findoshop.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.pro.findoshop.R;
import com.pro.findoshop.bottomSheets.Comparison;
import com.pro.findoshop.dataClasses.Items;
import com.pro.findoshop.databinding.ActivityStoreAnalyticsBinding;
import com.pro.findoshop.viewModels.StoreViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreAnalytics extends AppCompatActivity {

    ActivityStoreAnalyticsBinding binding;
    private StoreViewModel storeViewModel;
    List<Items> itemList;
    private String selectedCategory;
    private Items selectedProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_store_analytics);
        storeViewModel = new ViewModelProvider(this).get(StoreViewModel.class);
        storeViewModel.getLiveStoreItemsData().observe(this, items -> {
            this.itemList = items;
            setupBarChart();
            setupPieChart();
            setupRadarChart();
        });


        binding.barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // Handle bar chart selection
                int selectedIndex = (int) e.getX();
                selectedProduct = itemList.get(selectedIndex);
                updateComparison(); // Update comparison based on the selected category and product
            }

            @Override
            public void onNothingSelected() {
                // Handle when nothing is selected on the bar chart
                selectedProduct = null;
                updateComparison(); // Update comparison based on the selected category
            }
        });

// Pie chart setup
        binding.pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // Handle pie chart selection
                selectedCategory = ((PieEntry) e).getLabel();
                updateComparison(); // Update comparison based on the selected category and product
            }

            @Override
            public void onNothingSelected() {
                // Handle when nothing is selected on the pie chart
                selectedCategory = null;
                updateComparison(); // Update comparison based on the selected product
            }
        });
    }

    private void setupRadarChart() {
        List<BarEntry> overallPerformanceEntries = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        for (int i = 0; i < itemList.size(); i++) {
            Items item = itemList.get(i);
            float clicks = item.getClicks();
            float addToCart = item.getAddedToCart();
            float overallPerformance = (addToCart / clicks)*100;

            overallPerformanceEntries.add(new BarEntry(i, overallPerformance));
            labels.add(item.getItemName());
        }

        BarDataSet overallPerformanceDataSet = new BarDataSet(overallPerformanceEntries, "Overall Performance");
        overallPerformanceDataSet.setColor(Color.MAGENTA);

        BarData overallPerformanceData = new BarData(overallPerformanceDataSet);
        XAxis overallPerformanceXAxis = binding.hChart.getXAxis();
        overallPerformanceXAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        binding.hChart.setData(overallPerformanceData);
        binding.hChart.invalidate();

    }

    private void setupPieChart() {
        List<PieEntry> pieEntries = new ArrayList<>();

        Map<String, Integer> categoryClicks = new HashMap<>();

        for (Items item : itemList) {
            String category = item.getCategory();
            int clicks = item.getClicks();

            if (categoryClicks.containsKey(category)) {
                categoryClicks.put(category, categoryClicks.get(category) + clicks);
            } else {
                categoryClicks.put(category, clicks);
            }
        }

        for (Map.Entry<String, Integer> entry : categoryClicks.entrySet()) {
            pieEntries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Category-wise Clicks");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData(dataSet);
        binding.pieChart.setData(pieData);
        binding.pieChart.invalidate();

    }

    private void setupBarChart() {
        // Assuming you have a list of data objects called itemList
        List<BarEntry> clicksEntries = new ArrayList<>();
        List<BarEntry> addToCartEntries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (int i = 0; i < itemList.size(); i++) {
            Items item = itemList.get(i);
            clicksEntries.add(new BarEntry(i, item.getClicks()));
            addToCartEntries.add(new BarEntry(i, item.getAddedToCart()));
            labels.add(item.getItemName());
        }

        BarDataSet clicksDataSet = new BarDataSet(clicksEntries, "Clicks");
        clicksDataSet.setColor(Color.BLUE);

        BarDataSet addToCartDataSet = new BarDataSet(addToCartEntries, "Added to Cart");
        addToCartDataSet.setColor(Color.GREEN);

        BarData barData = new BarData(clicksDataSet, addToCartDataSet);
        XAxis xAxis = binding.barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        binding.barChart.setData(barData);
        binding.barChart.invalidate();

    }

    // Update comparison logic
    private void updateComparison() {
        // Check if both category and product are selected
        if (selectedCategory != null && selectedProduct != null) {
            // Filter items by selected category
            List<Items> categoryItems = new ArrayList<>();
            for (Items item : itemList) {
                if (selectedCategory.equals(item.getCategory())) {
                    categoryItems.add(item);
                }
            }

            // Sort items by the ratio of addedToCart to clicks in descending order
            Collections.sort(categoryItems, new Comparator<Items>() {
                @Override
                public int compare(Items item1, Items item2) {
                    float ratio1 = item1.getAddedToCart() / item1.getClicks();
                    float ratio2 = item2.getAddedToCart() / item2.getClicks();
                    // Sort in descending order
                    return Float.compare(ratio2, ratio1);
                }
            });

            // Select top 5 items
            Items topItem = categoryItems.get(0);
            Comparison comparisonFragment = Comparison.newInstance(selectedProduct, topItem);
            comparisonFragment.show(getSupportFragmentManager(), comparisonFragment.getTag());
        } else {
            Log.d("Comparison", "Please select both category and product for comparison");
        }
    }


}
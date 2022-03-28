package com.example.arknews.ui.categories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arknews.R;
import com.example.arknews.dao.ARKDatabase;
import com.example.arknews.model.Category;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Category> categoryList;
    Context context;

    public CategoryAdapter(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryAdapter.CategoryViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Category category = categoryList.get(position);
        ((CategoryAdapter.CategoryViewHolder) holder).bind(category);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        ImageView categoryImageView;
        MaterialTextView categoryName;
        CheckBox categoryCheckbox;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            //Initialize
            categoryImageView = itemView.findViewById(R.id.category_image);
            categoryName = itemView.findViewById(R.id.category_name);
            categoryCheckbox = itemView.findViewById(R.id.category_checkbox);
        }

        void bind(Category category) {
            categoryName.setText(category.getDescription());
            categoryCheckbox.setChecked(category.isSelected());
            loadCatIcon(category.getName());

            categoryCheckbox.setOnClickListener(v -> {
                category.setSelected(categoryCheckbox.isChecked());
                ARKDatabase.getInstance(itemView.getContext()).categoryDao().update(category);
            });
        }
        void loadCatIcon(String categoryName) {
            int id = 0;
            switch (categoryName) {
                case "education":
                    id = R.drawable.education;
                    break;
                case "general":
                    id = R.drawable.general;
                    break;
                case "business":
                    id = R.drawable.business;
                    break;
                case "entertainment":
                    id = R.drawable.entertainment;
                    break;
                case "health":
                    id = R.drawable.health;
                    break;
                case "politics":
                    id = R.drawable.politics;
                    break;
                case "sports":
                    id = R.drawable.sports;
                    break;
                case "science":
                    id = R.drawable.science;
                    break;
                case "technology":
                    id = R.drawable.technology;
                    break;
            }
            categoryImageView.setImageResource(id);
        }


    }

}

package com.example.arknews.ui.help;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arknews.R;
import com.example.arknews.model.FAQ;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FAQ> faqList;

    public FaqAdapter(List<FAQ> faqList) {
        this.faqList = faqList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FaqAdapter.FaqViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.faq_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FAQ faq = faqList.get(position);
        ((FaqViewHolder) holder).question.setText(faq.getQuestion());
        ((FaqViewHolder) holder).answer.setText(faq.getAnswer());
    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }

    public class FaqViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView question, answer;

        FaqViewHolder(View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.faq_card_question);
            answer = itemView.findViewById(R.id.faq_card_answer);
        }
    }

}

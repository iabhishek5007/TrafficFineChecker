package com.ontrack.trafficfine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

class FaqAdapater extends RecyclerView.Adapter<FaqAdapater.FaqViewholder> {

    private static final String TAG = "FaqAdapater";
    List<FaqQuestionModel> faqQuestionModelsList;

    public FaqAdapater(List<FaqQuestionModel> faqQuestionModels) {
        this.faqQuestionModelsList = faqQuestionModels;
    }

    @NonNull
    @Override
    public FaqViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new FaqViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqViewholder holder, int position) {

        FaqQuestionModel faqQuestionModel = faqQuestionModelsList.get(position);

        holder.tvFaqQuestion.setText("RegistrationNo:  "+faqQuestionModel.getFaqQuestions());
        holder.tvFaqAnswer.setText("Fine Amount:  "+faqQuestionModel.getFaqAnswers());

        boolean isAnswerVisible = faqQuestionModelsList.get(position).isFaqExapand();
        holder.clFaqExpandableAnswers.setVisibility(isAnswerVisible ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return faqQuestionModelsList.size();
    }

    class FaqViewholder extends RecyclerView.ViewHolder {

        ConstraintLayout clFaqExpandableAnswers;

        TextView tvFaqQuestion, tvFaqAnswer;

        public FaqViewholder(@NonNull View itemView) {
            super(itemView);

            clFaqExpandableAnswers = itemView.findViewById(R.id.activity_faq_answer_holder);
            tvFaqQuestion = itemView.findViewById(R.id.activity_faq_question_tv);
            tvFaqAnswer = itemView.findViewById(R.id.activity_faq_answer_tv);

//            tvFaqQuestion.setOnClickListener(v -> {
//                FaqQuestionModel faqQuestionModel = faqQuestionModelsList.get(getAdapterPosition());
//                faqQuestionModel.setFaqExapand(!faqQuestionModel.isFaqExapand());
//                notifyItemChanged(getAdapterPosition());
//            });

        }
    }
}


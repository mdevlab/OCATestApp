package io.mdevlab.ocatraining.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.model.Question;
import io.mdevlab.ocatraining.modelManager.QuestionManager;
import io.mdevlab.ocatraining.util.UtilUI;

/**
 * Created by husaynhakeem on 4/28/17.
 */

public class FavoriteQuestionsAdapter extends RecyclerView.Adapter<FavoriteQuestionsAdapter.FavoriteQuestionViewHolder> {

    Context context;
    ArrayList<Question> questions;

    public FavoriteQuestionsAdapter(Context context, ArrayList<Question> questions) {
        this.context = context;
        this.questions = questions;
    }

    @Override
    public FavoriteQuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_favorite_question, parent, false);
        return new FavoriteQuestionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FavoriteQuestionViewHolder holder, int position) {
        Question currentQuestion = questions.get(position);

        UtilUI.setViewDrawableBackgroundColor(holder.chapter, QuestionManager.getChapterColor(currentQuestion));
        holder.question.setText(currentQuestion.getStatement());
    }

    @Override
    public int getItemCount() {
        if (questions == null)
            return 0;
        return questions.size();
    }

    class FavoriteQuestionViewHolder extends RecyclerView.ViewHolder {

        TextView chapter;
        TextView question;

        FavoriteQuestionViewHolder(View itemView) {
            super(itemView);
            chapter = (TextView) itemView.findViewById(R.id.favorite_question_chapter);
            question = (TextView) itemView.findViewById(R.id.favorite_question_text);
        }
    }
}

package com.soulfly.englishcards.general;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.soulfly.englishcards.R;
import com.soulfly.englishcards.model.Card;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Elena on 18.03.2017.
 */
public class RepositoryHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.list_item__word_text_view)
    TextView word;

    public RepositoryHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull Card card) {
        word.setText(card.getWord());
    }
}

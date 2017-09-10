package com.soulfly.englishcards.general;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.soulfly.englishcards.R;
import com.soulfly.englishcards.model.Card;

import java.util.List;

/**
 * Created by Elena on 18.03.2017.
 */
public class CardsAdapter extends BaseAdapter<RepositoryHolder, Card> {

    public CardsAdapter(@NonNull List<Card> items) {
        super(items);
    }

    @Override
    public RepositoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepositoryHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RepositoryHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Card card = getItem(position);
        holder.bind(card);
    }
}

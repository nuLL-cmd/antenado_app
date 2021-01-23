package com.automatodev.antenado.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.automatodev.antenado.R;
import com.automatodev.antenado.databinding.LayoutItensMainBinding;
import com.automatodev.antenado.models.TvMostPopular;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {
    private List<TvMostPopular> tvMostPopularList;
    private LayoutInflater layoutInflater;

    public FavouritesAdapter(List<TvMostPopular> tvMostPopularList) {
        this.tvMostPopularList = tvMostPopularList;

    }

    @NonNull
    @Override
    public FavouritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        LayoutItensMainBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_itens_main,parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesAdapter.ViewHolder holder, int position) {
        holder.binding(tvMostPopularList.get(position));
    }

    @Override
    public int getItemCount() {
        return tvMostPopularList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutItensMainBinding itensBinding;

        public ViewHolder(@NonNull LayoutItensMainBinding itensBinding) {
            super(itensBinding.getRoot());
            this.itensBinding = itensBinding;
        }

        public void binding(TvMostPopular tvMostPopular){
            itensBinding.setTvShow(tvMostPopular);
            itensBinding.executePendingBindings();
        }
    }
}

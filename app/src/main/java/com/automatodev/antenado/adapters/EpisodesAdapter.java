package com.automatodev.antenado.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.automatodev.antenado.R;
import com.automatodev.antenado.databinding.LayoutEpisodesBinding;
import com.automatodev.antenado.models.EpisodesEntity;

import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.ViewHolder>{

    private List<EpisodesEntity> episodesEntities;
    private LayoutInflater layoutInflater;

    public EpisodesAdapter(List<EpisodesEntity> episodesEntities){
        this.episodesEntities = episodesEntities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      if (layoutInflater == null){
          layoutInflater = LayoutInflater.from(parent.getContext());
      }
      LayoutEpisodesBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_episodes,parent, false);

      return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding(episodesEntities.get(position));
    }

    @Override
    public int getItemCount() {
        return episodesEntities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutEpisodesBinding episodesBinding;
        public ViewHolder(@NonNull LayoutEpisodesBinding episodesBinding) {
            super(episodesBinding.getRoot());
            this.episodesBinding = episodesBinding;
        }

        public void binding(EpisodesEntity episodesEntity){
            episodesBinding.setEpisodesEntity(episodesEntity);
            episodesBinding.executePendingBindings();
        }
    }
}

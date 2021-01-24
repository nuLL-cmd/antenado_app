package com.automatodev.antenado.listener;

import com.automatodev.antenado.models.TvMostPopular;

public interface TvDataListener {

    void tvShowClicked(TvMostPopular tvMostPopular);
    void tvShowDelete(TvMostPopular tvMOstPopular, int position);

}

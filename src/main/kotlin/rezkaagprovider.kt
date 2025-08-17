package com.example.cloudstreamaddon

import com.lagradost.cloudstream3.MainAPI
import com.lagradost.cloudstream3.TvType
import com.lagradost.cloudstream3.SearchResponse

class HdrezkaProvider : MainAPI() {
    override val mainUrl = "https://rezka.ag"
    override val name = "RezkaAG"
    override val lang = "ru"
    override val tvTypes = listOf(TvType.Movie, TvType.TvSeries, TvType.Anime)

    override suspend fun search(query: String): List<SearchResponse> {
        return emptyList()
    }
}

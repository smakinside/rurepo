package com.example.cloudstreamaddon

import com.lagradost.cloudstream3.MainAPI
import com.lagradost.cloudstream3.SearchResponse
import com.lagradost.cloudstream3.TvType
import com.lagradost.cloudstream3.MovieSearchResponse
import org.jsoup.Jsoup

class HdrezkaProvider : MainAPI() {
    override val mainUrl = "https://rezka.ag"
    override val name = "RezkaAG"
    override val lang = "ru"
    override val tvTypes = listOf(TvType.Movie, TvType.TvSeries, TvType.Anime)

    override suspend fun search(query: String): List<SearchResponse> {
        // 1. Формируем URL для поиска
        val url = "$mainUrl/search/?q=$query"

        // 2. Делаем HTTP-запрос и получаем HTML страницы
        val document = Jsoup.connect(url).get()

        // 3. Находим блоки с результатами поиска
        val results = document.select(".b-content__inline_item")

        // 4. Создаём список для результатов
        val searchResults = mutableListOf<SearchResponse>()

        for (result in results) {
            val title = result.selectFirst(".b-content__inline_item-link")?.text() ?: continue
            val poster = result.selectFirst("img")?.attr("src") ?: ""
            val href = result.selectFirst("a")?.attr("href") ?: continue
            val urlFull = if (href.startsWith("http")) href else "$mainUrl$href"

            // Добавляем результат в список
            searchResults.add(
                MovieSearchResponse(
                    title = title,
                    url = urlFull,
                    posterUrl = poster,
                    year = null,
                    rating = null,
                    tvType = TvType.Movie
                )
            )
        }

        // 5. Возвращаем результаты
        return searchResults
    }
}

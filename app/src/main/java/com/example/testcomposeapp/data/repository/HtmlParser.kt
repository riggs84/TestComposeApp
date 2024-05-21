package com.example.testcomposeapp.data.repository

import com.example.testcomposeapp.data.Forecast
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class HtmlParser {

    private val url = "https://world-weather.ru/pogoda/russia/saint_petersburg/biometeorology/"

    fun getTodayForecast(): ForecastData {
        val nodes = getDocument().getElementsByClass("biometric")
        val result = ForecastData()

        nodes.forEachIndexed { index, element ->
            if (index == 0) {
                result.currentDay = getForecast(element)
            } else {
                result.nextDay = getForecast(element)
            }
        }
        return result
    }

    private fun getDocument(): Document {
        return Jsoup.connect(url).get()
    }

    private fun getForecast(node: Element): List<Forecast> {
        val resultList = mutableListOf<Forecast>()
        for (i in 1..3) {
            val row = node.getElementsByClass("bio col-${i}")

            val rowData = mutableListOf<String>()
            for (j in 1..3) {
                val rowResult = row.first()?.getElementsByClass("bio-li-${j}")?.first()?.text()
                rowData.add(rowResult ?: "")
            }
            resultList.add(Forecast(rowData[0], rowData[1].toInt(), rowData[2]))
        }

        return resultList
    }
}

data class ForecastData(
    var currentDay: List<Forecast> = emptyList(),
    var nextDay: List<Forecast> = emptyList()
)
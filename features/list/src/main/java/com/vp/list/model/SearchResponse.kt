package com.vp.list.model

import com.google.gson.annotations.SerializedName

import java.util.Collections.emptyList

class SearchResponse private constructor(
        @field:SerializedName("Response")
        private val response: String)
{
    companion object {
        private val POSITIVE_RESPONSE = "True"
    }

    @SerializedName("Search")
    private val search: List<ListItem>? = null

    val totalResults: Int = 0

    fun getSearch(): List<ListItem> {
        return search ?: emptyList()
    }

    fun hasResponse(): Boolean {
        return POSITIVE_RESPONSE == response
    }


}

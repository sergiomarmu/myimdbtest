package com.vp.list.model

import java.util.Collections

class SearchResult private constructor(val items: List<ListItem>,
                                       private val hasResponse: Boolean,
                                       val totalResult: Int) {

    companion object {

        fun error(): SearchResult {
            return SearchResult(emptyList(), false, 0)
        }

        fun success(items: List<ListItem>, totalResult: Int): SearchResult {
            return SearchResult(items, true, totalResult)
        }
    }

    fun hasResponse(): Boolean {
        return hasResponse
    }


}

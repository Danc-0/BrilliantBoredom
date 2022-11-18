package com.danc.brilliantboredom.domain.usecases

import com.danc.brilliantboredom.data.local.entity.BoredomActivityEntity
import com.danc.brilliantboredom.domain.models.Bookmarked
import com.danc.brilliantboredom.domain.models.BoredActivity
import com.danc.brilliantboredom.domain.repositories.BoredomActivityRepository

class AddActivityToBookmarks(private val repository: BoredomActivityRepository) {

    operator fun invoke(bookmarked: Bookmarked) {
        val response = repository.bookmarkActivity(bookmarked)
        if (response.isNotBlank()){
            repository.deleteBookmarkedActivity(bookmarked.key)
        }
    }

}
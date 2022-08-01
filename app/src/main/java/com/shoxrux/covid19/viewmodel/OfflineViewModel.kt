package com.shoxrux.covid19.viewmodel

import androidx.lifecycle.*

import com.shoxrux.covid19.db.SavedEntity
import com.shoxrux.covid19.di.room.DBRepository
import com.shoxrux.covid19.utils.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.xml.transform.Transformer

@HiltViewModel
class OfflineViewModel @Inject constructor(private val dbRepository: DBRepository) : ViewModel() {


    private val liveBookmarkData = MutableLiveData<Resource<List<SavedEntity>>>()



    fun insertArticle(savedEntity: SavedEntity) {
        viewModelScope.launch {

            dbRepository.insertSaved(savedEntity)
        }
    }


    fun getBookmark(): LiveData<Resource<List<SavedEntity>>> {

        viewModelScope.launch {
            liveBookmarkData.postValue(Resource.loading(null))
            try {
                coroutineScope {

//                    repository.addBookmark(bookmarkEntity = BookmarkEntity(1,rasm,title,description,url))

                    val async1 = async { dbRepository.getAllValue() }

                    val await1 = async1.await()
                    liveBookmarkData.postValue(Resource.success(await1))

                }
            }catch (e:Exception){
                liveBookmarkData.postValue(Resource.error(e.message ?: "Error",null))
            }
        }
        return liveBookmarkData
    }



}
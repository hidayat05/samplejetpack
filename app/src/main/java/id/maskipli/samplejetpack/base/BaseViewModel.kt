package id.maskipli.samplejetpack.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author hidayat @on 12/07/19
 **/
open class BaseViewModel : ViewModel(), LifecycleObserver {

    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

}
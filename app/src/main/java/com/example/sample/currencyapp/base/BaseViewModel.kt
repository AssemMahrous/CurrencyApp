package com.example.sample.currencyapp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sample.currencyapp.R
import com.example.sample.currencyapp.utils.RetrofitException
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

abstract class BaseViewModel<Repository : BaseRepository> : ViewModel() {

    @Inject
    lateinit var repository: Repository

    private val compositeDisposable = CompositeDisposable()

    val Status = MutableLiveData<ScreenStatus>()

    private fun handleError(exception: Throwable) {
        if (exception is RetrofitException) {
            when (exception.getKind()) {
                RetrofitException.Kind.NETWORK ->
                    Status.value = ScreenStatus.Error(errorResId = R.string.no_internet_connection)
                RetrofitException.Kind.HTTP ->
                    Status.value = ScreenStatus.Error(errorString = exception.message ?: "")
                RetrofitException.Kind.UNEXPECTED ->
                    Status.value = ScreenStatus.Error(errorString = exception.message ?: "")
            }
        } else {
            // todo
        }
    }

    fun <T> subscribe(
        observable: Observable<T>,
        success: Consumer<T>,
        error: Consumer<Throwable>,
        subscribeScheduler: Scheduler = Schedulers.io(),
        observeOnMainThread: Boolean = true
    ) {

        val observerScheduler =
            if (observeOnMainThread) AndroidSchedulers.mainThread()
            else subscribeScheduler

        compositeDisposable.add(
            observable
                .subscribeOn(subscribeScheduler)
                .observeOn(observerScheduler)
                .subscribe(success, error)
        )
    }

    fun <T> subscribe(
        observable: Single<T>,
        success: Consumer<T>,
        error: Consumer<Throwable> = Consumer { },
        subscribeScheduler: Scheduler = Schedulers.io(),
        observeOnMainThread: Boolean = true,
        showLoading: Boolean = true,
        checkConnectivity: Boolean = true
    ) {

        val observerScheduler =
            if (observeOnMainThread) AndroidSchedulers.mainThread()
            else subscribeScheduler

        compositeDisposable.add(observable
            .subscribeOn(subscribeScheduler)
            .observeOn(observerScheduler)
            .compose { single ->
                composeSingle<T>(single, showLoading, checkConnectivity)
            }
            .subscribe(success, Consumer {
                Timber.e(it)
                handleError(it)
                error.accept(it)
            })
        )
    }

    private fun <T> composeSingle(
        single: Single<T>,
        showLoading: Boolean = true,
        checkConnectivity: Boolean = true
    ): Single<T> {
        return single
            .doOnSubscribe {
                if (checkConnectivity && repository.connectivityUtils.isNetworkConnected().not()) {
                    throw RetrofitException.networkError(IOException())
                } else if (showLoading) Status.postValue(ScreenStatus.Loading())
            }
            .doAfterTerminate {
                if (showLoading) Status.postValue(ScreenStatus.Loaded())
            }
    }

    fun clearSubscription() {
        if (compositeDisposable.isDisposed.not()) compositeDisposable.clear()
    }

    override fun onCleared() {
        clearSubscription()
        super.onCleared()
    }
}
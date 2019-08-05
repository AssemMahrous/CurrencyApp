package com.example.sample.currencyapp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sample.currencyapp.CurrencyApp
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
import javax.inject.Inject

abstract class BaseViewModel<Repository : BaseRepository> : ViewModel() {

    @Inject
    lateinit var repository: Repository

    private val compositeDisposable = CompositeDisposable()
    val error = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    private fun handleError(exception: Throwable) {
        if (exception is RetrofitException) {
            when (exception.getKind()) {
                RetrofitException.Kind.NETWORK ->
                    error.value = /*exception.message ?:*/ CurrencyApp.getContext().resources.getString(R.string.no_internet_connection)
                // todo
                RetrofitException.Kind.HTTP ->
                    error.value = exception.message ?: ""
                // todo
                RetrofitException.Kind.UNEXPECTED ->
                    error.value = exception.message ?: ""
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
            observeOnMainThread: Boolean = true) {

        val observerScheduler =
                if (observeOnMainThread) AndroidSchedulers.mainThread()
                else subscribeScheduler

        compositeDisposable.add(observable
                .subscribeOn(subscribeScheduler)
                .observeOn(observerScheduler)
                .subscribe(success, error))
    }

    fun <T> subscribe(
            observable: Single<T>,
            success: Consumer<T>,
            error: Consumer<Throwable> = Consumer { },
            subscribeScheduler: Scheduler = Schedulers.io(),
            observeOnMainThread: Boolean = true,
            showLoading: Boolean = true) {

        val observerScheduler =
                if (observeOnMainThread) AndroidSchedulers.mainThread()
                else subscribeScheduler

        compositeDisposable.add(observable
                .subscribeOn(subscribeScheduler)
                .observeOn(observerScheduler)
                .compose { single ->
                    composeSingle<T>(single, showLoading)
                }
                .subscribe(success, error))
    }

    private fun <T> composeSingle(single: Single<T>, showLoading: Boolean = true): Single<T> {
        return single
                .flatMap { item ->
                    Single.just(item)

                }
                .doOnError {
                    Timber.e(it)
                    handleError(it)
                }
                .doOnSubscribe {
                    loading.postValue(showLoading)
                }
                .doAfterTerminate {
                    loading.postValue(false)
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
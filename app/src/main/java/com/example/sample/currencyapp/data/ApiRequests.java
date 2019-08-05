package com.example.sample.currencyapp.data;

import com.example.sample.currencyapp.data.model.Currencies;
import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by Mostafa on 08/20/2017.
 */

public interface ApiRequests {

    @GET("currencies.json")
    Single<Currencies> getCurrencies();
}

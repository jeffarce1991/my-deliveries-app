package com.jeff.deliveries.webservices.api;

import dagger.Binds;
import dagger.Module;

@Module
public interface ApiModule {

    @Binds
    @SuppressWarnings("unused")
    ApiFactory bindApiFactory(DefaultApiFactory apiFactory);
}




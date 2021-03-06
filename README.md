# Octopus
 [ ![Download](https://api.bintray.com/packages/volkdown/octopusmaven/coreoctopus/images/download.svg) ](https://bintray.com/volkdown/octopusmaven/coreoctopus/_latestVersion)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Octopus is a lightweight library on Android that helps you organize communication between different features (modules).

## Why "Octopus"?

We think this is the most suitable name since a feature API connects the features between each other just like an octopus tentacles.

## What Does This Library Do?

- provides abstraction to simplify interaction between features
- guarantees event delivery between features
- delivers events via an internal API only to the feature owner
- emits events to all the owners using the current feature

## How Does This Work?

The general idea is that one feature should have one API. A feature is some isolated functionality, for example:
	- A separate screen
	- A group of screens that are part of a single workflow
	- Some isolated logic

The coreoctopus module contains the BaseFeatureApi class which implements two interfaces - FeatureApi and InnerFeatureApi. This class connects the inner and the outer APIs of our feature.

![GitHub Logo](https://github.com/volkdown/octopus/blob/develop/media/feature_api_diagram.png?raw=true)

Each feature gets a unique featureId upon creation, so that the same feature could be used multiple times in the same project. Consequently all the events are sent to this featureId. FeatureApi and InnerFeatureApi have methods to emit events. FeatureApi sends events to the feature, InnerFeatureApi - to the feature's owner. In order to subscribe to an event you need to receive a subscriber from the feature API.

## How to Add Octopus to a Project?

1) Add all the required dependencies:
- com.github.volkdown:coreoctopus:XXX
- If you need rx support: com.github.volkdown:octopusrx:XXX
- If you need cicerone support: com.github.volkdown:octopuscicerone:XXX

2) Create a feature API with all the interfaces. The feature API object has to be a singleton.

```kotlin

object SampleFeatureApiImpl: BaseFeatureApi(), SampleFeatureApi, SampleInnerFeatureApi {

    fun getScreen(featureId: String): Screen {
        return object: FeatureScreen(featureId){

            override fun getFeatureFragment(): Fragment? {
                return SampleFeatureFragment.newInstance()
            }
        }
    }
}

interface SampleFeatureApi : FeatureApi

interface SampleInnerFeatureApi : InnerFeatureApi

object AppImpl : Application() {

    fun getSampleFeatureApi(): SampleFeatureApi = SampleFeatureApiImpl

    fun getSampleInnerFeatureApi(): InnerFeatureApi = SampleFeatureApiImpl
}

```

3) Choose a feature owner. A Presenter or a View Model (depending on your architecture) are the best for this role. The feature owner has to implement the FeatureOwner interface.

4) Register the feature owner in the feature API using the registerOwner method.

5) After you're done working with the feature you must remove its owner (unregisterOwner) and all the subsribers (unregisterSubscriber)

```kotlin

//MainPresenter it this owner for sample feature

class MainPresenter constructor(featureIdentifier: FeatureIdentifier,
                                private val sampleFeatureApi: SampleFeatureApi): BasePresenter<MainView>(), FeatureOwner {

    private val compositeDisposable = CompositeDisposable()

    private val sampleFeatureSubscriber = sampleFeatureApi.newSubscriber(this)

    fun onCreate(){
        sampleFeatureApi.registerOwner(this)
        val disposable = sampleFeatureSubscriber
            .asRxSubscriber()
            .getEvents()
            .subscribe({
                //Handle events from sample feature
            }, {})
        compositeDisposable.add(disposable)
    }

    fun onDestroy(){
        sampleFeatureApi.unregisterOwner(this)
        sampleFeatureApi.unregisterSubscriber(sampleFeatureSubscriber)
        compositeDisposable.clear()
    }
    
    fun onMainActionButtonClicked(){
        //Send event to sample feature
        sampleFeatureApi.sendEvents(this, UpdateContentEvent())
    }
    
    override val id: String = featureIdentifier.featureId
}


class SampleFeaturePresenter constructor(featureIdentifier: FeatureIdentifier,
                                private val sampleInnerFeatureApi: SampleInnerFeatureApi
): BasePresenter<MainView>(), FeatureOwner {

    private val compositeDisposable = CompositeDisposable()

    private val sampleInnerFeatureApiSubscriber = sampleInnerFeatureApi.newInnerSubscriber(this)

    fun onCreate(){
        val disposable = sampleInnerFeatureApiSubscriber
            .asRxSubscriber()
            .getEvents()
            .subscribe({
                //Handle events from feature owner
            }, {})
        compositeDisposable.add(disposable)
    }

    fun onDestroy(){
        sampleInnerFeatureApi.unregisterSubscriber(sampleInnerFeatureApiSubscriber)
        compositeDisposable.clear()
    }

    fun onSampleFeatureActionButtonClicked(){
        //send event to feature owner (MainPresenter)
        sampleInnerFeatureApi.sendInnerEvents(this, SampleFeatureEvent())
    }

    override val id: String = featureIdentifier.featureId
}

```

You can see how it all works in detail in the sample app.

Sending a pending event in a feature:

![](https://github.com/volkdown/octopus/raw/develop/media/pending_event_sample.gif)

Using one feature instance in the feature owner:

![](https://github.com/volkdown/octopus/raw/develop/media/single_feature_event_sample.gif)

Using multiple instances of feature in the feature owner:

![](https://github.com/volkdown/octopus/raw/develop/media/multi_feature_event.gif)

## License
```
MIT License

Copyright (c) 2020 Konstantin Veretelnikov (@volkdown)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

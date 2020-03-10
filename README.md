# Octopus
 [ ![Download](https://api.bintray.com/packages/volkdown/octopusmaven/coreoctopus/images/download.svg) ](https://bintray.com/volkdown/octopusmaven/coreoctopus/_latestVersion)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

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

3) Choose a feature owner. A Presenter or a View Model (depending on your architecture) are the best for this role. The feature owner has to implement the FeatureOwner interface.

4) Register the feature owner in the feature API using the registerOwner method.

5) After you're done working with the feature you must remove its owner (unregisterOwner) and all the subsribers (unregisterSubscriber)

You can see how it all works in detail in the example.
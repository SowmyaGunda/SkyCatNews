# SkyCatNews
A simple Android application to demonstrate how to represent dynamic list data fetched from an endpoint in UI using Android recycler view. This is application is follows MVVM structural design pattern.

The app has two screens, the main screen lits all the hot news happening in the cats world and next one is a navigated screen by clicking on a news item that takes you to a view where more details can be found about our beloved cats.

## Technologies and Frameworks
* Prgoramming Language
    * Kotlin
* Packages
    * Dagger - For dependency injection
    * Retrofit - A type safe Http-Client. It is an incredible easy to use library that turns your API into a Java (Kotlin) interface.
    * OkHttp - OkHttp is an HTTP client.
* Components
    * Recycler View - RecyclerView makes it easy to efficiently display large sets of data
* Architecture
    * MVVM - MVVM separates your view (i.e. Activitys and Fragments) from your business logic.


## Usage and Configuratoin

### Prerequisites
To build and run the app you need **Android Studio**(or any relavent IDE with android support) with gradle and its related needed packages installed.

### Configuraton

The base URL for the api to fetch data is in retrofit\SkyCatNewsModule.kt. The default value in code is using a postman mock server endpoint with defined endpoints and responses, please change this URL to test with other endpoints of same structure.

```kotlin
companion object {
        const val DEFAULT_API_URL = "<some endpoint url>"
        .
        .
        .
        
    }

```
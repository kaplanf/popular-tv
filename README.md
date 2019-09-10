
# Popular Tv App
A sample app written in Kotlin and Java, which fetches Popular TV shows from MovieDB and updates it periodically.

# App Architecture
This app is built according to MVP pattern.
The UI operations are handled in Activity and Fragment classes, isolated from the logic operations.
Network operations are handled by AppDatamanager, which uses an instance of AppApiHelper.
Logic operations are handled by Presenter classes belonging to each UI fragment/activity and isolated from UI layer.

# Responsibility of Classes
BaseActivity and BaseFragment classes are created for consistency among View classes, also for time saving by eliminating
boilerplate code to be written for every View class.

AppDatamanager uses instances of AppApiHelper and AppPreferencesHelper, for managing network operations.

ActivityModule and ApplicationModule classes are used for providing necessary instances for the specified scope with dependency injection. Dagger2 library used for Dependency Injection.

Every UI element has a Presenter and View interface to define necessary operations in their respective classes, Presenter is responsible of conducting logic operations and conveying required data to its View class.

Room persistence library is used for DB operations, and Picasso is used for image caching.

Every logic operation regarding Lists handled with RxJava2 Observables and Java8 stream api.

Database classes and Main UI/Presenter classes written in Kotlin, remaining classes are written in Java.

Kotlin coroutines are used for periodically recurring background tasks in the MainActivity.

AndroidX library used for View dependencies.

# Used Technologies
Dagger2 - Dependency Injection

Room Persistence Library - Database Operations

Picasso - Image preview and caching

Retrofit- Network Operations

Kotlin Coroutine - Periodic tasks

RxJava2- Background tasks and logic

ButterKnife - View Injections



# Travel Rewards Android Client
Made in collaboration with [Gavin Oiamo] and [Skye Waddell]

A Java-based Android mobile client for managing travel rewards, built with Android SDK and Material Design components. This mobile application provides a user interface for interacting with the Travel Rewards REST API, featuring complete CRUD operations through an intuitive mobile interface.


## Technologies Used
- Android SDK with Java
- OkHttp for API communication
- RecyclerView for dynamic list display
- Material Design components
- Gson for JSON parsing

## Features
- Dynamic rewards list with RecyclerView
- Add/Edit reward functionality
- Delete confirmation
- Real-time updates
- Cross-origin API integration
- Material Design UI components

## Prerequisites
- Android Studio
- Minimum SDK: Android 7.1 (API Level 25)
- Target SDK: Android 12 (API Level 31)
- Travel Rewards REST API running (see [REST API Repository])

## Development Setup
1. Clone the repository
2. Configure API endpoint in `MainActivity.java` and `RewardActivity.java`:
   ```java
   String url = "http://10.0.2.2:8080/TeamOneREST_war_exploded/api/rewards";
   ```
   Note: `10.0.2.2` is the Android emulator's equivalent of `localhost`

3. Run using Android Studio:
    - Open project in Android Studio
    - Configure Android Virtual Device (AVD) or connect physical device
    - Build and run the application

## Project Structure
```java
src/main/
├── java/
│   └── c.example.t1travelexpertsandroid/
│       ├── MainActivity.java
│       ├── Reward.java
│       ├── RewardActivity.java
│       └── RewardAdapter.java
├── res/
│   ├── drawable/
│   ├── layout/
│   ├── mipmap/
│   ├── navigation/
│   ├── values/
│   └── xml/
└── AndroidManifest.xml
```

## Key Components

### MainActivity
- Manages the main RecyclerView display
- Handles API data fetching
- Implements floating action button for adding new rewards

### RewardActivity
- Provides interface for adding/editing rewards
- Implements form validation
- Handles CRUD operations via API calls

### RewardAdapter
- Custom RecyclerView adapter
- Manages reward item display and interaction
- Implements click listeners for item selection

### Reward Model
- Parcelable data class for rewards
- Manages reward data structure
- Implements serialization for intent passing

## Development Notes
- The app requires the REST API to be running and accessible
- Uses OkHttp for network operations
- For production deployment:
    - Update API endpoints to production URLs
    - Implement proper error handling
    - Consider implementing user authentication
    - Add loading indicators for network operations

## Device Compatibility
- Minimum Android 7.1 (API 25)
- Target Android 12 (API 34)
- Tested on Android emulator
- Material Design ensures consistent UI across devices

## Related Projects
- [REST API Repository] - Backend REST API
- [Web Client Repository] - Web frontend application

[REST API Repository]: https://github.com/EsotericRazz/OOSD-REST-API
[Web Client Repository]: https://github.com/EsotericRazz/OOSD-JSP-Webpage
[Gavin Oiamo]: https://github.com/oiamo123
[Skye Waddell]: https://github.com/skyelynwaddell
[circleup_readme.md](https://github.com/user-attachments/files/22009395/circleup_readme.md)
# CircleUp

A modern Android social networking application built with clean architecture principles and Factory design patterns. CircleUp demonstrates advanced Android development practices with a multi-layered architecture that separates concerns and ensures maintainable, testable code.

## 🏗️ Architecture Overview

CircleUp follows a clean architecture pattern with clear separation of concerns across four distinct layers:

```
App Layer (UI) → Service Layer → Repository Layer → Models Layer
```

### Architecture Components

- **App Layer (UI)**: Contains Activities, Fragments, ViewModels, and UI components
  - `appbar/`, `favourites/`, `home/`, `login/`, `profile/`, `registration/`
  - Custom UI components like `BottomNavigationBar`, `CustomSnackbarVisuals`
  - Theme management with `AppUtils`, `Color`, `Dimensions`, `Theme`

- **Service Layer**: Implements business logic and coordinates between UI and data layers
  - Authentication services, Post services, Favourites services
  - Factory pattern implementations for service creation

- **Repository Layer**: Handles data operations, API calls, and local storage
  - Post repositories, Cache repositories, Database operations
  - Runtime profile-based repository selection

- **Models Layer**: Contains data models, entities, and DTOs
  - Posts, Users, Security, Favourites models
  - Database entities and data transfer objects

## 🛠️ Setup & Build Instructions

### Prerequisites
- **Android Studio**: Arctic Fox (2020.3.1) or later
- **JDK**: 11 or higher
- **Android SDK**: API level 21+
- **Kotlin**: Latest stable version
- **Git**: For version control

### Installation Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/Sunaan-Sultan/CircleUp.git
   cd CircleUp
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the cloned CircleUp directory
   - Wait for Gradle sync to complete

3. **Build the Project**
   ```bash
   ./gradlew clean
   ./gradlew build
   ```

4. **Run the Application**
   - Connect an Android device or start an emulator
   - Run the app:
   ```bash
   ./gradlew installDebug
   ```

### Build Variants
- **Debug**: Development build with debugging enabled and test data
- **Release**: Production-ready build with optimizations

## 🏛️ Libraries & Technologies Used

### Core Technologies
- **Kotlin**: Primary programming language
- **Android SDK**: Native Android development
- **MVVM Architecture**: Model-View-ViewModel pattern with ViewModels

### Key Android Components
- **Room Database**: Local data persistence and caching
- **ViewBinding**: Type-safe view references
- **Navigation Component**: Fragment-based navigation
- **Material Design**: Modern UI components and theming

### Networking & Data Management
- **Retrofit**: REST API client (inferred from repository structure)
- **Local Repositories**: Offline-first architecture with caching
- **Runtime Profile Management**: Dynamic repository selection

### UI & User Experience
- **Custom Snackbar**: Enhanced user feedback system
- **Bottom Navigation**: Intuitive app navigation
- **Dynamic Theming**: Customizable app appearance
- **Responsive Design**: Adaptive layouts for different screen sizes

## 🏭 Factory Pattern Implementation

CircleUp implements a sophisticated Factory pattern system with three specialized factories that demonstrate excellent software engineering practices:

### 1. SecurityFactory 
**Location**: `com.project.service.security.SecurityFactory`

```kotlin
object SecurityFactory {
    fun getIdentityService(context: Context, name: String): IdentityService {
        return IdentityServiceImpl(context)
    }
    
    fun getIdentityRepository(context: Context): IdentityRepository {
        return if (RuntimeProfile.getCurrentRuntime() == LIVE_RUNTIME) {
            IdentityRepositoryImpl()
        } else {
            IdentityLocalRepositoryImpl(context)
        }
    }
}
```

**Purpose & Benefits:**
- **Authentication Management**: Creates identity services and repositories
- **Runtime-based Selection**: Dynamically chooses between live and local implementations
- **Security Consistency**: Ensures consistent security configuration across the app
- **Testing Support**: Easy mocking for unit tests

### 2. PostFactory
**Location**: `com.project.service.posts.PostFactory`

```kotlin
object PostFactory {
    fun getPostRepository(context: Context): PostRepository {
        return if (RuntimeProfile.getCurrentRuntime() == LIVE_RUNTIME) {
            PostRepositoryImpl()
        } else {
            context?.let { PostLocalRepositoryImpl(it) }
                ?: throw IllegalArgumentException("Context required for local repository")
        }
    }
    
    fun getPostCacheRepository(context: Context): PostCacheRepository {
        return if (RuntimeProfile.getCurrentRuntime() == LIVE_RUNTIME) {
            PostCacheRepositoryImpl()
        } else {
            PostCacheLocalRepositoryImpl(context)
        }
    }
    
    fun getFavouritesRepository(context: Context): FavouritesRepository {
        return if (RuntimeProfile.getCurrentRuntime() == LIVE_RUNTIME) {
            FavouritesRepositoryImpl()
        } else {
            FavouritesLocalRepositoryImpl(context)
        }
    }
}
```

**Purpose & Benefits:**
- **Post Management**: Handles creation of post-related repositories
- **Cache Strategy**: Manages both live and cached post data
- **Offline Support**: Provides local implementations for offline functionality
- **Favourites Integration**: Unified factory for post and favourites management

### 3. FavouritesFactory
**Location**: `com.project.service.favourites.FavouritesFactory`

```kotlin
object FavouritesFactory {
    fun getFavouritesRepository(context: Context): FavouritesRepository {
        return if (RuntimeProfile.getCurrentRuntime() == LIVE_RUNTIME) {
            FavouritesRepositoryImpl()
        } else {
            FavouritesLocalRepositoryImpl(context)
        }
    }
}
```

**Purpose & Benefits:**
- **Favourites Management**: Dedicated factory for user favourites functionality
- **Runtime Flexibility**: Supports both online and offline favourites
- **Separation of Concerns**: Isolated favourites logic from other features
- **Consistency**: Maintains consistent API across different runtime environments

### Factory Pattern Benefits

#### 🎯 **Runtime Profile Management**
All factories use `RuntimeProfile.getCurrentRuntime()` to determine execution environment:
- **LIVE_RUNTIME**: Production/online mode with server APIs
- **Local Mode**: Offline/development mode with local storage

#### 🧪 **Enhanced Testability**
- Easy mocking of dependencies in unit tests
- Consistent object creation patterns
- Isolated factory logic for focused testing

#### 🔄 **Flexible Architecture**
- Dynamic switching between implementations
- Support for different deployment environments
- Easy addition of new repository types

#### 📦 **Dependency Management**
- Centralized object creation logic
- Consistent initialization patterns
- Proper context management for Android components

## 🔧 Key Features

### Social Networking Core
- **User Authentication**: Secure registration and login system
- **Post Management**: Create, view, edit, and delete posts
- **Favourites System**: Bookmark and manage favourite posts
- **User Profiles**: Personal profile management and viewing

### Technical Features
- **Offline-First Architecture**: Full functionality without internet
- **Smart Caching**: Efficient data caching with `PostCacheRepository`
- **Runtime Flexibility**: Dynamic switching between live and local data
- **Custom UI Components**: Enhanced user experience with custom snackbars and navigation

### Performance Optimizations
- **Local Repository Pattern**: Fast offline data access
- **Efficient Database Operations**: Room database integration
- **Smart Resource Management**: Context-aware repository creation

## 📋 Assumptions & Limitations

### Assumptions

#### Technical Environment
- **Android Version**: Minimum API level 21 (Android 5.0+)
- **Kotlin Runtime**: Application assumes Kotlin runtime environment
- **Storage Access**: Device has sufficient storage for local repositories and cache
- **Memory Management**: Adequate device memory for Room database operations

#### User Behavior
- **Network Connectivity**: Users may switch between online/offline modes
- **Data Persistence**: Users expect data to persist across app sessions
- **Performance Expectations**: Users expect fast loading with local repositories

### Current Limitations

#### Technical Constraints
- **Runtime Switching**: Runtime profile changes require app restart
- **Context Dependencies**: Local repositories require Android Context, limiting pure unit testing
- **Memory Usage**: Local repositories and cache may consume significant device storage
- **Database Migrations**: Room database schema changes require careful migration handling

#### Feature Limitations
- **Real-time Sync**: Limited real-time synchronization between local and live data
- **Conflict Resolution**: Basic conflict resolution when switching between runtime modes
- **Bulk Operations**: Large data operations may impact UI performance
- **Background Sync**: No background synchronization service implemented

#### Architecture Trade-offs
- **Factory Complexity**: Runtime-based factory selection adds complexity
- **Code Duplication**: Separate implementations for live and local repositories
- **Testing Complexity**: Mocking runtime profiles requires additional setup
- **Dependency Chain**: Deep dependency chains in factory pattern may affect performance

### Future Enhancement Opportunities

#### Technical Improvements
- **Background Sync Service**: Implement WorkManager for data synchronization
- **Improved Caching**: Add intelligent cache invalidation strategies
- **Performance Monitoring**: Add analytics for repository performance tracking
- **Memory Optimization**: Implement lazy loading and pagination for large datasets

#### Feature Enhancements
- **Real-time Updates**: WebSocket integration for live updates
- **Advanced Search**: Full-text search across posts and users
- **Data Export**: Allow users to export their data
- **Multi-account Support**: Support for multiple user accounts

#### Architecture Evolution
- **Dependency Injection**: Integrate Dagger Hilt for better dependency management
- **Coroutines Flow**: Enhanced reactive programming with StateFlow/SharedFlow
- **Modular Architecture**: Split into feature modules for better scalability
- **Compose Migration**: Gradual migration to Jetpack Compose for modern UI

## 🧪 Testing Strategy

### Unit Testing
```bash
./gradlew test
```

**Factory Testing Benefits:**
- **Isolated Testing**: Each factory can be tested independently
- **Mock Repositories**: Easy mocking of repository implementations
- **Runtime Simulation**: Test different runtime profiles in isolation

### Integration Testing
```bash
./gradlew connectedAndroidTest
```

**Repository Testing:**
- Test local vs live repository behavior
- Validate cache repository functionality
- Verify factory selection logic

### Testing Architecture
The Factory pattern implementation enables comprehensive testing:
- **Service Layer**: Mock repositories for business logic testing
- **Repository Layer**: Test both local and live implementations
- **Factory Logic**: Validate runtime profile selection

## 📱 Project Structure

```
app/
├── manifests/
├── kotlin+java/
│   └── com.project.example/
│       ├── ui/                 # App Layer
│       │   ├── appbar/
│       │   ├── favourites/
│       │   ├── home/
│       │   ├── login/
│       │   ├── profile/
│       │   └── registration/
│       └── models/             # Models Layer
│           ├── posts/
│           ├── security/
│           └── users/
├── repository/                 # Repository Layer
│   ├── cacheposts/
│   ├── database/
│   └── favourites/
└── service/                   # Service Layer
    ├── favourites/
    ├── posts/
    └── security/
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Follow the existing Factory pattern conventions
4. Add tests for new factories or repository implementations
5. Commit changes (`git commit -m 'Add amazing feature'`)
6. Push to branch (`git push origin feature/amazing-feature`)
7. Open a Pull Request

### Development Guidelines
- Maintain the runtime profile pattern in new factories
- Add both live and local implementations for new repositories
- Follow the existing package structure
- Include comprehensive error handling

## 📞 Contact

**Sunaan Sultan**
- GitHub: [@Sunaan-Sultan](https://github.com/Sunaan-Sultan)
- Project Link: [https://github.com/Sunaan-Sultan/CircleUp](https://github.com/Sunaan-Sultan/CircleUp)

---

*Built with ❤️ using clean architecture principles, Factory patterns, and modern Android development practices.*

[circleup_readme.md](https://github.com/user-attachments/files/22010259/circleup_readme.md)
# CircleUp

A modern Android application built with clean architecture principles and Factory design patterns. CircleUp demonstrates advanced Android development practices with a multi-layered architecture that separates concerns and ensures maintainable, testable code.

## Demo Clip
https://drive.google.com/file/d/1O0rfvzqcQ5wDOtOL7Vgw-DA4IXoxbz52/view?usp=sharing

## Screenshot

<p float="left">
  <img src="https://github.com/user-attachments/assets/078a0c7b-9912-4bad-8009-a9142c05ed5a" width="33%" />
    <img src="https://github.com/user-attachments/assets/a5033dd3-3ad3-48e8-88b1-c17801709cc3" width="33%" />
  <img src="https://github.com/user-attachments/assets/811be196-3eae-495a-a520-040feaf0a5b7" width="33%" />
</p>
![posts]()

## 🏗️ Clean Architecture Implementation

CircleUp is built following **Clean Architecture** principles with clear separation of concerns and dependency inversion. The architecture ensures maintainable, testable, and framework-independent code.

```
App/UI Layer → Service Layer → Repository Layer → Models Layer

```
<img width="604" height="161" alt="Screenshot 2025-08-27 204355" src="https://github.com/user-attachments/assets/5cca85fc-47a1-4dd1-a469-ed242750b189" />

### Clean Architecture Layers

#### **🎨 UI Layer (Frameworks & Drivers)**
**Role**: External interfaces - Activities, Views, ViewModels, and UI components

- **Components**: `appbar/`, `favourites/`, `home/`, `login/`, `profile/`, `registration/`
- **Custom UI**: `BottomNavigationBar`, `CustomSnackbarVisuals`
- **Theme System**: `AppUtils`, `Color`, `Theme`
- **Dependency Flow**: Depends on Service Layer only

#### **⚙️ Service Layer (Use Cases/Application Business Rules)**
**Role**: Contains application-specific business rules and orchestrates data flow

- **Business Logic**: Authentication services, Post services, Favourites services
- **Factory Patterns**: `SecurityFactory`, `PostFactory`, `FavouritesFactory`
- **Use Case Orchestration**: Coordinates between UI and Repository layers
- **Framework Independence**: No dependency on Android-specific components

#### **🗄️ Repository Layer (Interface Adapters)**
**Role**: Converts data between use cases and external systems (databases, APIs)

- **Data Access**: Post repositories, Cache repositories, Database operations
- **Abstraction**: Hides implementation details from Service layer
- **Runtime Switching**: Dynamic selection between live and local implementations
- **Adapter Pattern**: Adapts external data sources to internal business needs

#### **📊 Models Layer (Entities)**
**Role**: Core business entities and enterprise-wide business rules

- **Business Entities**: Posts, Users, Security, Favourites models
- **Pure Business Logic**: Independent of frameworks and external concerns
- **Data Transfer Objects**: Clean data structures for layer communication
- **Enterprise Rules**: Fundamental business rules that don't change

## 🎯 Clean Architecture Principles Demonstrated

### ✅ **1. Dependency Rule**
Dependencies point inward - outer layers depend on inner layers, never the reverse:
```
UI → Service → Repository → Models
```

### ✅ **2. Independence of Frameworks**
The Service layer contains pure business logic independent of Android frameworks:
```kotlin
// Service layer doesn't know about Android Context or Room Database
// Repository layer handles these framework-specific details
```

### ✅ **3. Independence of Database**
Repository abstraction allows switching between different data sources:
```kotlin
return if (RuntimeProfile.getCurrentRuntime() == LIVE_RUNTIME) {
    PostRepositoryImpl()        // Live Network/API implementation
} else {
    PostLocalRepositoryImpl()   // Local Room database implementation
}
```

### ✅ **4. Testability**
Each layer can be tested independently with mocked dependencies:
- **UI Layer**: Test ViewModels with mocked Services
- **Service Layer**: Test business logic with mocked Repositories  
- **Repository Layer**: Test data operations independently
- **Factory Pattern**: Easy mocking for different implementations

### ✅ **5. Independence of UI**
Business logic is completely separated from UI concerns - the same business logic could work with different UI frameworks.

## 🛠️ Setup & Build Instructions

### Prerequisites
- **Android Studio**: (latest stable version recommended)
- **JDK**: 11 or higher
- **Android SDK**: API level 24+
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

## 🏛️ Libraries & Technologies Used

### Core Technologies
- **Kotlin**: Primary programming language
- **Jetpack Compose**: declarative UI framework
- **Android SDK**: Native Android development
- **MVVM Architecture**: Model-View-ViewModel pattern with ViewModels

### Key Android Components
- **Room Database**: Local data persistence and caching
- **Material Design**: Modern UI components and theming

### Networking & Data Management
- **Retrofit**: REST API client (inferred from repository structure)
- **Local Repositories**: Offline-first architecture with caching
- **Runtime Profile Management**: Dynamic repository selection

### UI & User Experience
- **Custom Snackbar**: Enhanced user feedback system
- **Bottom Navigation**: Intuitive app navigation
- **Dynamic Theming**: Customizable app appearance with Dark mode support

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
- **Local Mode**: Offline/development mode with local storage/JSON 

#### 🧪 **Enhanced Testability**
- Easy mocking of dependencies in unit tests
- Consistent object creation patterns
- Isolated factory logic for focused testing

#### 🔄 **Dependency Inversion Principle**
High-level modules (Services) don't depend on low-level modules (specific Repository implementations):
```kotlin
// Service depends on Repository interface, not concrete implementation
class PostService(private val repository: PostRepository) {
    // Business logic here - doesn't care if it's local or remote repository
}
```

#### 📦 **Single Responsibility Principle**  
Each factory has a single, well-defined responsibility:
- `SecurityFactory` → Authentication and identity management
- `PostFactory` → Post creation, caching, and management
- `FavouritesFactory` → User favourites functionality

## 🏆 Advanced Clean Architecture Features

### **🌐 Runtime Environment Abstraction**
Beyond typical Clean Architecture, The project implements environment-agnostic business logic:
```kotlin
RuntimeProfile.getCurrentRuntime() == LIVE_RUNTIME
```
This allows the same business rules to work in different deployment environments without code changes.

## 🎖️ Clean Architecture Benefits Achieved

### **🧪 Enhanced Testability**
- **Isolated Testing**: Each layer can be tested independently
- **Mock Dependencies**: Factory pattern enables easy mocking
- **Pure Business Logic**: Service layer contains testable business rules
- **Framework Independence**: Core logic can be tested without Android dependencies

### **🔧 Maintainability**
- **Clear Boundaries**: Well-defined layer responsibilities
- **Loose Coupling**: Layers communicate through interfaces
- **High Cohesion**: Related functionality grouped together
- **Easy Refactoring**: Changes in one layer don't affect others

### **🚀 Scalability**
- **Feature Addition**: New features follow established patterns
- **Team Development**: Different teams can work on different layers
- **Technology Migration**: Can switch frameworks without affecting business logic (Kotlin Multiplatform)
- **Environment Flexibility**: Same codebase works in multiple environments

#### 📦 **Dependency Management**
- Centralized object creation logic
- Consistent initialization patterns
- Proper context management for Android components


## 🔧 Key Features

### Social Networking Core
- **User Authentication**: Registration validation and login system
- **Post Management**: View, search, and tag posts as favourites
- **Favourites System**: Bookmark and manage favourite posts

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
- **Android Version**: Minimum API level 24 (Android 7.0+)
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
- **Memory Usage**: Local repositories and cache may consume more device storage
- **Database Migrations**: Room database schema changes require careful migration handling
- **Testing Complexity**: Mocking runtime profiles requires additional setup

## 📞 Contact

**Sunaan Sultan**
- GitHub: [@Sunaan-Sultan](https://github.com/Sunaan-Sultan)
- Project Link: [https://github.com/Sunaan-Sultan/CircleUp](https://github.com/Sunaan-Sultan/CircleUp)

---

*Built with ❤️ using clean architecture principles, Factory patterns, and modern Android development practices.*

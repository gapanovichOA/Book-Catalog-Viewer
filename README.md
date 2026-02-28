# Book Catalog Viewer

---

## Architecture Decisions

The app follows **Clean Architecture** principles to ensure scalability, testability, and a clear separation of concerns.

### 1. Layered Separation
* **Domain Layer:** Contains the pure business logic, `Book` model, and `CatalogRepository` interface. It is independent of any framework.
* **Data Layer:** Handles data persistence via **Room**, JSON parsing using **Kotlinx Serialization**, and the `CatalogRepositoryImpl`.
* **Presentation Layer:** Built with **Jetpack Compose**. It utilizes a **Shared ViewModel** pattern to maintain state across screens.

### 2. MVI (Model-View-Intent) Pattern
The presentation layer follows the **MVI** pattern to create a predictable, unidirectional data flow:

* **Intent:** Represents any user action (e.g., `Search`, `ToggleFavorite`, `SelectBook`). These are dispatched to the ViewModel.
* **Model (State):** The ViewModel processes Intents and updates a single `UiState` object. This state is exposed as a `StateFlow`.
* **View:** Compose functions observe the `UiState` and render the UI accordingly. The View never modifies state directly; it only emits Intents.

### 3. Navigation 3 & State-Driven UI
This app implements the modern **Navigation 3** philosophy, treating navigation as a purely state-driven mechanism:
* **Backstack as State:** Navigation is managed as a `mutableStateListOf<NavKey>`. The UI simply reacts to changes in this list.
* **Navigation Manager:** A centralized `@Singleton` class handles the backstack, allowing the **ViewModel** to trigger navigation (e.g., navigating to details upon selecting a book).

### 4. Dependency Injection
* **Hilt:** Standardized dependency injection for all layers.
* **Shared Lifecycle:** The `MainViewModel` is scoped to the `MainActivity` to prevent data re-fetching and maintain search filters during navigation.

---

## 🛠️ Tech Stack
* **Language:** [Kotlin 2.3.10](https://kotlinlang.org/)
* **UI:** [Jetpack Compose (Material 3)](https://developer.android.com/jetpack/compose)
* **DI:** [Hilt 2.57.1](https://dagger.dev/hilt/)
* **Database:** [Room](https://developer.android.com/training/data-storage/room)
* **Serialization:** [Kotlinx Serialization](https://kotlinlang.org/docs/serialization.html)
* **Navigation:** [Navigation 3 (Basics/EntryProvider)](https://developer.android.com/guide/navigation/navigation-3)
* **Concurrency:** Kotlin Coroutines & Flow

---

## 🚀 How to Run the App

### Installation
1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/your-username/book-catalog-viewer.git](https://github.com/your-username/book-catalog-viewer.git)
    ```
2.  **Open in Android Studio:**
    Allow Gradle to sync. If you see a `jvmDefault` error, ensure your Kotlin and AGP versions are aligned as per the `libs.versions.toml`.
3.  **Run:**
    Select your device/emulator and press **Run**.

---

## 🧪 Testing
The project includes unit tests for the repository.

* **MockK:** For mocking dependencies.
* **Turbine:** For testing Kotlin Flows.

Run tests via terminal:
```bash
./gradlew test
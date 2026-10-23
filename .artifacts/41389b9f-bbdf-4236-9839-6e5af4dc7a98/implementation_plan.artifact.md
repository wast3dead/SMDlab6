# Implementation Plan - Kotlin Tasks Dashboard UI in Jetpack Compose

Create an interactive Jetpack Compose dashboard inside `MainActivity.kt` that lists all 15 mini-projects (Tasks 1 to 15), allowing the user to tap on any task to view its description, run sample simulations, and see outputs directly on the Android screen instead of console `readln()`.

## User Review Required

> [!IMPORTANT]
> Since the mini-projects originally used console input (`readln()`), we will adapt each task's core logic into composable UI screens or demonstration runners that execute and display results cleanly within Material 3 cards and buttons in the Android app.

## Open Questions

> [!NOTE]
> None. A centralized task selector dashboard is the ideal way to showcase console-based logic tasks inside an Android application.

## Proposed Changes

### App UI Dashboard
#### [MODIFY] [MainActivity.kt](file:///C:/Users/Lab/AndroidStudioProjects/SMDlab6/app/src/main/java/com/example/smdlab6/MainActivity.kt)
- Create a Material 3 UI with a LazyColumn listing Tasks 1–15.
- Implement interactive demo functions for each task showcasing enums, sealed classes, pattern matching (`when`), guard conditions, generics, and domain models.
- Provide a detail view or simulated execution card for each task when tapped.

## Verification Plan

### Automated Tests
- Run Gradle build to ensure the Compose UI compiles successfully:
  ```bash
  ./gradlew app:assembleDebug
  ```

### Manual Verification
- Run the app on an Android emulator or device to verify task navigation and output display.

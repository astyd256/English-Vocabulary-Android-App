# English Vocabulary App (University Android Course Project)

This is a mobile application for smartphones that helps users improve their English vocabulary.
The app is made as part of a series of lab assignments for the Android programming course in the Tomsk State University and was designed to support Android 8.0 and newer versions.

## Table of Contents

- [English Vocabulary App (University Android Course Project)](#english-vocabulary-app-university-android-course-project)
  - [Table of Contents](#table-of-contents)
  - [Core Features (Lab 1)](#core-features-lab-1)
    - [1. Launch Screen](#1-launch-screen)
    - [2. Onboarding Flow](#2-onboarding-flow)
    - [3. User Registration \& Validation](#3-user-registration--validation)
    - [4. Dictionary Lookup](#4-dictionary-lookup)
    - [5. Video Content Viewer](#5-video-content-viewer)
  - [Enhanced Features (Lab 2)](#enhanced-features-lab-2)
    - [1. Advanced Dictionary Search](#1-advanced-dictionary-search)
    - [2. Word Audio Playback](#2-word-audio-playback)
    - [3. Word Bookmarking (Offline Saving)](#3-word-bookmarking-offline-saving)
    - [4. Offline Mode Support](#4-offline-mode-support)
    - [5. Error Handling](#5-error-handling)
  - [Learning \& Testing Features (Lab 3)](#learning--testing-features-lab-3)
    - [1. Interactive Learning Screen](#1-interactive-learning-screen)
    - [2. Adaptive Learning Algorithm](#2-adaptive-learning-algorithm)
    - [3. Empty State Handling](#3-empty-state-handling)
    - [4. Saved Word Counter](#4-saved-word-counter)
    - [5. Test Session Setup](#5-test-session-setup)
    - [6. Smart Test Selection](#6-smart-test-selection)
  - [Getting Started](#getting-started)
  - [Development](#development)
  - [License](#license)
  - [Acknowledgments](#acknowledgments)
  - [Contacts](#contacts)


## Core Features (Lab 1)

### 1. Launch Screen
- Displays a centered logo upon app startup.

### 2. Onboarding Flow

- Introduces app features via swipeable screens or a "Next" button.
- Includes a "Skip" option to bypass onboarding.

### 3. User Registration & Validation

- Allows users to sign up by filling required fields.
- Validates input and shows error dialogs for incorrect data.
- Supports password visibility toggle.

### 4. Dictionary Lookup

- Enables word searches with real-time API requests.
- Displays results in a RecyclerView.
- Shows a placeholder if no word is found.
- Includes audio playback for pronunciation.

### 5. Video Content Viewer

- Loads the British Council’s "Video Zone" [page](https://learnenglish.britishcouncil.org/general-english/video-zone) in a WebView.
- Restricts navigation to this domain only.

## Enhanced Features (Lab 2)

### 1. Advanced Dictionary Search

- Triggers API requests when users tap the search icon.
- Dynamically updates results in RecyclerView.

### 2. Word Audio Playback

- Plays pronunciation audio when the sound icon is clicked.

### 3. Word Bookmarking (Offline Saving)

- Saves words & definitions to device storage via "Add to Dictionary" button.

### 4. Offline Mode Support

- If offline, searches locally saved words instead of fetching from API.

### 5. Error Handling

- Displays error dialogs for:

  - Failed API requests.
  - No internet connection.

API Used:

    https://api.dictionaryapi.dev/api/v2/entries/en/[word]
    (Example: .../en/cooking fetches "cooking" definition.)

## Learning & Testing Features (Lab 3)

### 1. Interactive Learning Screen

- Tests users on saved dictionary words.

### 2. Adaptive Learning Algorithm

- Tracks learning speed coefficient per word:
  - +1 for correct answers.
  - -1 for incorrect answers.
- Prioritizes words with the lowest coefficients in tests.

### 3. Empty State Handling

- Shows a prompt if no words are saved: "Add words to dictionary first."

### 4. Saved Word Counter

- Displays the total count of bookmarked words.

### 5. Test Session Setup

- Starts a 5-second countdown (+1s for "Go!") upon clicking "Start".
- Animates a color-changing circle synced with the timer.
- After countdown, launches the question screen.

### 6. Smart Test Selection

- Selects up to 10 words with the lowest learning coefficients.
- Adjusts to fewer words if the saved list is small.

## Getting Started

To run the app on your Android device, follow these steps:

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Build and run the app on your Android device or emulator with Android 8.0 or newer.

## Development

The app was developed using Android Studio as part of a series of lab assignments for the Android programming course in the Tomsk State University and follows the Material Design guidelines for a smooth and intuitive user experience.

## License

This project is licensed under the [MIT License](https://en.wikipedia.org/wiki/MIT_License).

## Acknowledgments

I would like to thank the British Council for providing their video resources for language learning. Also, huge thanks to [meetDeveloper](https://github.com/meetDeveloper) for providing [freeDictionaryAPI](https://github.com/meetDeveloper/freeDictionaryAPI)

## Contacts

For any inquiries or issues, please contact me at astyd256@yandex.ru

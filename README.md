# English Vocabulary App (University Android Course Project)

This is a mobile application for smartphones that helps users improve their English vocabulary.
The app is made as part of a series of lab assignments for the Android programming course in the Tomsk State University and was designed to support Android 8.0 and newer versions.

## Table of Contents


- [English Vocabulary App (University Android Course Project)](#english-vocabulary-app-university-android-course-project)
  - [Table of Contents](#table-of-contents)
  - [Features (Lab 1)](#features-lab-1)
  - [Additional Features (Lab 2)](#additional-features-lab-2)
  - [Additional Features (Lab 3)](#additional-features-lab-3)
  - [Getting Started](#getting-started)
  - [Development](#development)
  - [License](#license)
  - [Acknowledgments](#acknowledgments)
  - [Contacts](#contacts)


## Features (Lab 1)

- Launch Screen: The app displays a logo in the center of the screen upon startup.
- Onboarding Screens: The app provides onboarding screens with a gesture swipe or a "Next" button to introduce its features. Users can skip these screens by clicking "Skip".
- Registration Screen: Users can register by filling in the required fields. The app validates the input and displays an error dialog if the fields are not properly filled. Users can toggle password visibility.
- Dictionary Screen: Users can search for words in the dictionary. If the entered word is not found, a placeholder is displayed. The app sends a search request to the server and displays the retrieved results using a RecyclerView. Users can play the audio associated with a word.
- Video Screen: The app displays a WebView that loads the webpage: https://learnenglish.britishcouncil.org/general-english/video-zone. Only navigation within this page and its child pages is allowed.

## Additional Features (Lab 2)

- Dictionary Search: Users can search for words in the dictionary by clicking the magnifying glass icon and sending a request to the server. The app displays the retrieved results on the screen using a RecyclerView.
- Audio Playback: Users can play the audio associated with a word by clicking the sound icon.
- Word Bookmarking: Users can save a word and its information in the device's memory by clicking the "Add to Dictionary" button.
- Offline Mode: If there is no internet connection, the app searches for the word among the saved words in the device's memory.
- Error Handling: If an error occurs during server communication or due to the absence of internet access, the app displays an error dialog.

API: 
- The app uses the API `https://api.dictionaryapi.dev/api/v2/entries/en/` to retrieve word definitions. To retrieve a specific word, add it to the URL, for example: `https://api.dictionaryapi.dev/api/v2/entries/en/cooking`.

## Additional Features (Lab 3)

- Learning Screen: The app includes a learning screen where users can test their knowledge of the dictionary words.
- Test Logic: The app keeps a record of the learning speed for each word, starting with a coefficient of 0 for new words. On correct answers, the coefficient is increased by 1, and on incorrect answers, it is decreased by 1. The test includes words with the lowest learning speed coefficient.
- Empty Memory: If there are no saved words in the device's memory, the app displays a message instructing users to add words to the dictionary.
- Word Count: The app displays the number of words saved in the device's memory.
- Countdown Timer: When users click the "Start" button, a 5-second countdown begins (+1 second for the word "Go!"). The app displays an animation with a changing color circle matching the text color. After the countdown, a question screen is displayed.
- Test Words Selection: The app selects 10 (or fewer if there are no saved words) words with the lowest learning speed coefficient from the device's memory.

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

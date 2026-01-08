# 📱 Connectify

**Connectify** is an Android application built with **Jetpack Compose** that allows users to manage contacts in a simple, modern, and efficient way. The app implements the classic **CRUD** operations (Create, Read, Update, Delete), combining clean architecture principles, responsive design, and solid testing practices.

---

## ✨ Key Features

* ➕ Create contacts
* 👁️ View contact details
* ✏️ Update contact information
* 🗑️ Delete contacts
* 🔍 Contact search
* ⭐ Favorite contacts management
* 👉 Quick actions using **SwipeToDismissBox** (delete / update)

---

## 🛠️ Tech Stack

* **Kotlin**
* **Jetpack Compose**
* **Hilt** – Dependency Injection
* **Room Database** – Local persistence
* **SharedTransitionLayout** – Shared element animations between screens
* **Material 3**
* **Intents**
* **StateFlow / ViewModel**

---

## 🔗 System Intents Integration

* **Phone calls**
* **SMS**
* **Emails**

---

## 🎨 UI / UX

* 🌗 Theme support:

    * Light mode
    * Dark mode
    * Follow system theme
* 📱 **Responsive design**, adaptable to different screen sizes
* 🎞️ Smooth animations and shared transitions between screens

---

## 🧪 Testing

The project includes tests to ensure application quality and stability:

* ✅ **Unit tests**
* ✅ **Instrumented tests (UI tests)**
* Application of best practices to make ViewModels and data layers easy to test

---

## 🧱 Architecture

The application follows an **MVVM** architecture, with clearly separated layers:

* **UI (Compose)** – Rendering and state handling
* **ViewModel** – Presentation logic
* **Repository** – Data source abstraction
* **Data** – Room Database and DAOs

This structure improves maintainability, scalability, and testability.

---

## 🖼️ Screenshots

*Below are some images of the application:*

---
<div style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 2rem; ">
<img src="https://res.cloudinary.com/dtvbans9e/image/upload/v1767884814/projects/android/Jetpack%20compose/Connectify/wauue0j1hdxqobuneurk.png" width="300" alt="image_preview_1"/>
<img src="https://res.cloudinary.com/dtvbans9e/image/upload/v1767886530/projects/android/Jetpack%20compose/Connectify/emddw026dg7qnsiuyecs.png" width="300" alt="image_preview_2"/>
<img src="https://res.cloudinary.com/dtvbans9e/image/upload/v1767886530/projects/android/Jetpack%20compose/Connectify/tjiqgn3vaur3sr0kiqr2.png" width="300" alt="image_preview_3"/>
</div>

<div>
<img src="https://res.cloudinary.com/dtvbans9e/image/upload/v1767886530/projects/android/Jetpack%20compose/Connectify/gagfq3givzbq6fpu9vdm.png" width="700" alt="image_preview_4"/>   
</div>

## 🚀 Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/byron-fran/Connectify.git
   ```
2. Open the project in **Android Studio**
3. Sync Gradle
4. Run the app on an emulator or physical device

---

## 📌 Project Status

✔️ Functional and continuously improving

This project was developed for **educational and demonstrative** purposes, applying modern Android development best practices.

---
⭐ If you find this project useful or interesting, don’t forget to give it a star!

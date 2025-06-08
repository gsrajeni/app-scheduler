# 📅 App Scheduler

An Android app that allows users to **schedule installed apps to launch at a specific time**. Built with modern Android development tools and architecture, with a focus on Jetpack Compose.

---

## ✨ Features

- ⏰ **Schedule Any App**: Schedule any installed app on the device to launch at a specific time.
- ❌ **Cancel Schedule**: Cancel the scheduled launch before it starts.
- 🔁 **Update Schedule**: Modify the time for an already scheduled app.
- 🔄 **Multiple Schedules**: Support for multiple app schedules with **no time conflicts**.
- 📊 **Execution Tracking**: Log and track whether a scheduled app was successfully launched.

---

## 🧱 Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose (**XML-free**)
- **Database**: Room
- **Dependency Injection**: Hilt
- **Navigation**: Jetpack Compose Navigation
- **Image Loading**: Coil
- **Architecture**: MVVM + Coroutines + Flow
- **Services**: Foreground Service

---

## 🧠 App Behavior

1. Users can select any installed app and schedule it to run at a specific time.
2. If the app is in the **foreground**, it will launch the target app as scheduled.
3. If the app is in the **background or killed**, it will:
   - Try to use **Accessibility service** to open the target app.
   - If Accessibility is unavailable:
     - Attempt to run the app directly (if OS permits).
     - Else, display a **notification** prompting the user to manually launch the app.

---

## ⚠️ Limitations

> Running apps from the background is restricted from **Android 10+**.

- Accessibility service can bypass this restriction, but:
  - It is risky for Play Store approval.
  - Some OEMs **block accessibility settings** if the app is not from an official source (e.g. Play Store).

---

## 🛠️ Workarounds

- At launch, the app requests:
  - **Notification permission** (required).
  - **Accessibility permission** (optional but recommended).
- Fallback flow:
  1. Try to open the scheduled app if in the **foreground**.
  2. If in the **background**, attempt via **Accessibility**.
  3. If that fails, check for **OS-level support**.
  4. If unsupported, show a **notification** for the user to tap and launch manually.

---

## 🚀 Future Improvements

- 🔁 Support for **recurring schedules** (e.g. every day at 9 AM).
- 🔗 Trigger app launches via **custom events** (e.g. on boot, on network available).
- ✅ **Unit testing** and test coverage.
- 📥 Export/import schedules as backup.

---

## 🧪 Build & Run

1. Clone the project:
   ```bash
   git clone https://github.com/your-username/app-scheduler.git
   ```
2. Open in **Android Studio Hedgehog+**.
3. Run on a real device (emulators might not support background app launch behavior correctly).
4. Grant **Notification** and (optionally) **Accessibility** permissions.

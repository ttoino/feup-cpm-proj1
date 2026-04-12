# FEUP-CPM-PROJ1

First project for the CPM course unit at FEUP.

TikTek is a mobile theater ticketing application developed for the Mobile Computing course. The app provides an immersive experience for theater customers, allowing them to buy event tickets and order from the theater cafeteria.

## Features

- User authentication and registration
- Event browsing and ticket purchasing
- Cafeteria ordering with QR code validation
- Voucher system (discounts and free items)
- Offline capability with local data caching
- Multiple terminals (ticket and cafeteria validation)
- Dark/Light mode support
- Multi-language support (English and Portuguese)

## Architecture

The app follows the principles suggested in the [Google Guide to App Architecture](https://developer.android.com/topic/architecture), using:

- **Clean Architecture**: Separation of concerns with Screen, ViewModel, UseCase, Repository, and Data Source layers
- **Modularization**: Feature modules for auth, cafeteria, events, profile, and tickets
- **Dependency Injection**: Using Hilt
- **Background Processing**: Work Manager for data synchronization

## Building and Running

This is an Android project built with Gradle. To build and run:

```bash
./gradlew :app:main:installDebug
```

Or open the project in Android Studio and run from there.

## Unit info

- **Name**: Computação Móvel (Mobile Computing)
- **Date**: Year 1, Semester 2, 2023/24
- [**More info**](https://sigarra.up.pt/feup/ucurr_geral.ficha_uc_view?pv_ocorrencia_id=518814)

## Disclaimer

This repository (and all others with the name format `feup-*`) are for archival and educational purposes only.

If you don't understand some part of the code or anything else in this repo, feel free to ask (although I may not understand it myself anymore).

Keep in mind that this repo is public. If you copy any code and use it in your school projects you may be flagged for plagiarism by automated tools.

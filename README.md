# Text Summarizer

A Chrome extension that summarizes webpage content using a Spring Boot backend.

## Structure
- `chrome-extension/`: Chrome extension frontend (HTML, CSS, JS, manifest.json).
- `spring-boot-backend/`: Spring Boot backend for text summarization.

## Setup
### Backend
1. Navigate to `summarize-assistant/`.
2. Run `mvn spring-boot:run` to start the backend server on `http://localhost:8080`.
3. Ensure Java and Maven are installed.

### Frontend
1. Open Chrome and go to `chrome://extensions/`.
2. Enable "Developer mode".
3. Click "Load unpacked" and select the `summarize-assistant-ext/` folder.
4. The extension will appear in the Chrome toolbar.

## Usage
1. Start the Spring Boot backend.
2. Open a webpage in Chrome.
3. Click the extension icon and click "Summarize" to summarize the page content.

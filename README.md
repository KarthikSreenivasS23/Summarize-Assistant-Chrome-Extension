# Text Summarizer

A Chrome extension that summarizes webpage content using a Spring Boot backend integrated with the Gemini API for text summarization.

## Repository Structure
- `summarize-assistant-ext/`: Contains the Chrome extension frontend files (HTML, CSS, JS, `manifest.json`).
- `summarize-assistant/`: Contains the Spring Boot backend for text summarization using the Gemini API.

## Prerequisites
- **Java** and **Maven** for the backend.
- **Google Chrome** for the extension.
- **Gemini API Key**: Obtain from [Google's AI Studio](https://aistudio.google.com/) or the relevant Google API platform.

## Setup Instructions
### Backend Setup
1. **Navigate to the backend directory**:
   ```bash
   cd summarize-assistant
   ```
2. **Set the Gemini API key as an environment variable**:
   - **Linux/Mac**:
     ```bash
     export GEMINI_KEY=your-gemini-api-key
     ```
   - **Windows (Command Prompt)**:
     ```cmd
     set GEMINI_KEY=your-gemini-api-key
     ```
   - **Windows (PowerShell)**:
     ```powershell
     $env:GEMINI_KEY="your-gemini-api-key"
     ```
3. **Run the backend**:
   ```bash
   mvn spring-boot:run
   ```
   The server will start at `http://localhost:8080`.

### Frontend Setup
1. **Open Chrome** and navigate to `chrome://extensions/`.
2. **Enable Developer mode** (toggle in the top right).
3. **Click "Load unpacked"** and select the `chrome-extension/` folder.
4. The extension will appear in the Chrome toolbar.

## Usage
1. **Start the backend server** (see Backend Setup above).
2. **Open a webpage** in Chrome.
3. **Click the extension icon** in the Chrome toolbar and select **"Summarize Page"** to summarize the webpage content using the Gemini API.
4. The summary will appear in the extension popup.


## Troubleshooting
- **API Errors**: Validate the Gemini API key and endpoint in `summarize-assistant/src/main/java/org/summarize/assistant/service/SummarizeService.java`.
- **No Summary**: Check if the webpage has extractable text and the backend is running.
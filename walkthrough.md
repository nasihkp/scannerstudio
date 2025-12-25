# Document Scanner & Editor Walkthrough

## Overview
This Android application provides a complete solution for scanning documents, managing them, and performing advanced editing with OCR capabilities.

## Features Implemented

### 1. Architecture
- **MVVM Pattern**: Separation of UI (Activities/Fragments), Data (Room/Repositories), and Logic (ViewModels).
- **Clean Structure**: Organized into `ui`, `data`, `utils` packages.

### 2. Scanning (CameraX)
- **ScanActivity**: Uses CameraX to preview and capture high-quality images.
- **Image Processing**: Captures images to local storage.

### 3. PDF Creation & Management
- **PDF Generation**: Converts captured images into PDF documents using Android's native `PdfDocument`.
- **Database**: Uses Room to store document metadata (Name, Date, Path).
- **Document List**: Displays saved documents in `MainActivity` with a clean UI.

### 4. Advanced PDF Editor
- **Overlay System**: A custom `DraggableItemView` allows adding editable elements on top of the PDF page.
- **Supported Elements**:
    - **Text**: Editable text boxes.
    - **Box**: Resizable colored rectangles.
    - **Table**: Basic 2x2 tables (expandable in code).
- **Gestures**: Full support for dragging and resizing elements.
- **Saving**: "Flattens" the overlays onto the PDF page, creating a new version of the document.

### 5. OCR (Text Recognition)
- **ML Kit Integration**: Uses Google ML Kit to detect text in the scanned image.
- **Editable View**: Automatically populates the editor with text blocks found in the image, allowing the user to edit the "scanned" text directly.

## Verification Steps

### Manual Verification
1.  **Scan a Document**:
    - Open the app.
    - Click the "+" FAB.
    - Grant Camera permissions.
    - Capture a photo. **Capture multiple photos** if needed.
    - Click "Done" when finished.
    - You will be taken to the `CropActivity`.

2.  **Review & Filter**:
    - In `CropActivity`, you see the first page.
    - Click "Apply Filter" to toggle Grayscale.
    - Click "Next / Save" to move to the next page or Save if it's the last one.
    - The app will save the PDF and return to `MainActivity`.
    - Verify the new document appears in the list.

3.  **Edit Document**:
    - Tap on a document in the list.
    - The `EditorActivity` opens.
    - **Add Text**: Click "Add Text", type something, drag it around.
    - **Add Box**: Click "Box", resize it using the blue handle.
    - **OCR**: Click "OCR". Wait for the toast. Text blocks from the image should appear as editable boxes.

4.  **Save Edits**:
    - Click "Save".
    - A new PDF is created with your edits "burned" in.
    - Toast confirms the save location.

## Future Improvements
- **Cloud Sync**: Integrate Firebase or Google Drive API.
- **Multi-page Scanning**: Allow capturing multiple images before generating the PDF.
- **Advanced Table Editing**: Add UI to add/remove rows/cols dynamically.
- **Vector PDF Saving**: Use `PDFBox` to save text as real text objects instead of flattening to an image.

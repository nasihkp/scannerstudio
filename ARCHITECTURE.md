# App Architecture

## Overview
The app follows the **MVVM (Model-View-ViewModel)** architectural pattern to ensure separation of concerns and testability.

## Modules & Packages

### `ui/` (Presentation Layer)
Contains all UI-related components.
- **`scan/`**: `ScanActivity` or `ScanFragment`. Handles CameraX preview, image capture, and edge detection feedback.
- **`editor/`**: `EditorActivity`. The core editing experience.
    - `PdfRendererView`: Custom view to display PDF pages.
    - `OverlayView`: A `FrameLayout` that sits on top of the PDF page to hold editable elements (Text boxes, Shapes).
    - `DraggableItemView`: A custom view representing an editable object (Text, Box) that handles touch events (drag, resize).
- **`documents/`**: `DocumentsFragment`. Displays the list of saved PDFs using a RecyclerView.
- **`viewmodel/`**: `ScanViewModel`, `EditorViewModel`, `DocumentsViewModel`.

### `data/` (Data Layer)
Handles data storage and retrieval.
- **`model/`**: Data classes like `DocumentEntity` (Room), `Page`, `OverlayData`.
- **`local/`**: Room Database setup (`AppDatabase`, `DocumentDao`).
- **`repository/`**: `DocumentRepository`. Mediates between the ViewModel and data sources (Database + File System).

### `utils/` (Utility Layer)
- **`ImageUtils`**: Bitmap scaling, rotation, cropping.
- **`PdfUtils`**: Helpers for `PdfDocument` creation and `PdfRenderer`.
- **`OcrManager`**: Wrapper around Google ML Kit for text recognition.

## Key Interactions
1.  **Scanning**: `ScanFragment` uses CameraX -> captures image -> `ImageUtils` processes it -> saves temp file.
2.  **PDF Creation**: `PdfUtils` takes list of image paths -> creates PDF -> saves to storage.
3.  **Editing**:
    - `EditorActivity` loads PDF using `PdfRenderer`.
    - User adds a "Text Box". A `DraggableItemView` is added to `OverlayView`.
    - User clicks "Save". `PdfUtils` draws the underlying PDF page + the `OverlayView` content onto a NEW PDF page canvas.
4.  **OCR**: `OcrManager` takes a Bitmap -> returns `Text` object -> mapped to `OverlayData` -> rendered as editable text boxes.

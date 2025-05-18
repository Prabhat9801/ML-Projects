# 🧠 Fashion MNIST Clothing Category Prediction

This project is a deep learning-based clothing classifier using the Fashion MNIST dataset and a custom-built Convolutional Neural Network (CNN) in TensorFlow/Keras. It includes data loading, preprocessing, model training, evaluation, and custom image prediction features—all well documented and beginner-friendly.

---

## 📁 Project Files

- **`"Predict_cloth_category  .ipynb"`**  
  A Jupyter Notebook with **step-by-step markdown explanations** before each code cell, guiding the user through the entire workflow.
  
- **Trained Model File (`trained_fashion_mnist_model.h5`)**  
  Generated during training and used for later predictions.

---

## 🧾 Dataset Description

The Fashion MNIST dataset contains **60,000 grayscale images** of 10 fashion categories such as:

- T-shirt/top  
- Trouser  
- Pullover  
- Dress  
- Coat  
- Sandal  
- Shirt  
- Sneaker  
- Bag  
- Ankle boot

Each image is 28x28 pixels, single-channel (grayscale).

---

## 🚀 Model Architecture

The CNN model used in this project has the following architecture:

1. `Conv2D(32, (3, 3))` → `ReLU`  
2. `MaxPooling2D((2, 2))`  
3. `Conv2D(64, (3, 3))` → `ReLU`  
4. `MaxPooling2D((2, 2))`  
5. `Conv2D(64, (3, 3))` → `ReLU`  
6. `Flatten()`  
7. `Dense(64)` → `ReLU`  
8. `Dense(10)` → *Raw logits for 10 classes*

The model is compiled with:
- **Optimizer**: `Adam`
- **Loss Function**: `SparseCategoricalCrossentropy(from_logits=True)`
- **Metric**: `Accuracy`


---

# 📊 Training & Evaluation

- Trained for **5 epochs** on the Fashion MNIST training set
- Achieved **~89% accuracy** on the test set
- Training and validation accuracy/loss curves are plotted
- Includes detailed data exploration and visualization

---

## 🖼️ Predicting a Custom Image

To predict an image **outside the dataset**, follow the **exact order** of the steps below to ensure compatibility with the model input format.

### ✅ Step 1: Upload, Preprocess, and Download the Image

1. Locate the cell titled:  
   🔹 **"📤🖼️ Upload, Preprocess & Download Image"**
2. This cell opens an upload dialog in Google Colab or Jupyter.
3. Upload your **custom image** (e.g., a picture of a shirt, shoe, etc.).
4. The image will be:
   - Converted to grayscale
   - Resized to 28×28 pixels
   - Normalized to the range [0, 1]
5. The processed image will be saved and shown for preview.
6. **Download the processed image** for the next step.

> ⚠️ You **must** use this processed image for accurate predictions. Raw images won’t work directly.

---

### ✅ Step 2: Load Model and Predict Custom Image

1. Locate the cell titled:  
   🔹 **"🔍🖼️ Load and Predict Custom Image"**
2. This cell will:
   - Load the saved `.h5` trained model
   - Load your **processed** image from step 1
   - Preprocess it again for safety
   - Perform prediction and print the predicted class

---

## 🔍 Key Features Learned

- Loading and preprocessing Fashion MNIST dataset
- Building and training CNN models in Keras
- Data normalization and reshaping
- Model evaluation and visualization
- Understanding CNN feature extraction

---

## 🧑‍💻 How to Use This Notebook

1. Run all cells sequentially from **top to bottom**, **except** the **last two cells** — for them, follow this special order:
   - First, run the **last cell** (`Upload, Preprocess & Download Image`)
   - Then, run the **second last cell** (`Load and Predict Custom Image`)
2. For Google Colab:
   - Use GPU runtime (Runtime > Change Runtime Type > GPU)
   - The notebook includes all necessary imports and setup

---

## 📜 Notes

- The notebook focuses on model building and evaluation
- For custom image prediction, additional preprocessing steps would be needed:
  - Convert to grayscale
  - Resize to 28×28 pixels
  - Normalize pixel values to [0, 1]
  - Reshape to (1, 28, 28, 1) format

---

## 👨‍💻 Developer

**Prabhat Singh**
: prabhatkumarsictc12@gmail.com



---

## 📜 License

This project is open-source and available under the MIT License.


# Customer Review Summarization

## 🔍 Overview
**Customer Review Summarization** is an AI-powered tool that automatically summarizes product reviews to extract meaningful insights from large volumes of user-generated content. It leverages Natural Language Processing (NLP) techniques and transformer-based models to generate concise summaries of reviews, aiding consumers in decision-making and helping businesses understand customer sentiment.

## 📁 Dataset
- **Source**: [Amazon Product Reviews Dataset](https://www.kaggle.com/datasets/arhamrumi/amazon-product-reviews)
- The dataset includes thousands of product reviews from various categories.

## ⚙️ Features
- Automatically fetches and processes review data.
- Cleans and preprocesses text (stopword removal, tokenization, etc.).
- Utilizes state-of-the-art summarization models.
- Evaluation using ROUGE metric.
- Generates extractive and abstractive summaries.

## 🛠 Technologies Used
- Python
- Jupyter Notebook
- Google Colab
- NLP Libraries: `nltk`, `spacy`, `transformers`
- Evaluation: `rouge_score`
- Dataset Management: `kaggle`

## 🧠 Model Description
- Preprocessing includes tokenization, stopword removal, and lemmatization.
- Utilized pre-trained transformer models (like BERT or T5) for summarization.
- Evaluated using ROUGE score for summary precision and recall.
  
## Model link: https://huggingface.co/Prabhat9801/model

## 📊 Results
- Summarization results show strong alignment with user sentiments.
- Effectively condenses multi-paragraph reviews into 1-2 meaningful sentences.

## Drive Link: https://drive.google.com/drive/folders/1dRhuyD9WfpRMfvvyKtWf0xv5Vd7R6EXV?usp=drive_link

## Deployed Link: https://huggingface.co/spaces/Prabhat9801/Customer_Review_Summarizer


## 🚀 Getting Started

1. Clone the repository:
    ```bash
    git clone https://github.com/your-username/customer-review-summarization.git
    cd customer-review-summarization
    ```

2. Install dependencies:
    ```bash
    pip install -r requirements.txt
    ```

3. Upload your Kaggle API token and download the dataset:
    - Upload `kaggle.json` and run the script in the Jupyter notebook.

4. Run the notebook to train and evaluate the summarizer.

## 📈 Future Enhancements
- Add multilingual summarization support.
- Implement real-time review summarization via API.
- Explore fine-tuned models for specific product categories.

## 🧾 License
This project is open-source and available under the MIT License.

## 👩‍💻 Author
Developed by Prabhat Kumar Singh

---

Feel free to contribute, raise issues, or suggest enhancements!

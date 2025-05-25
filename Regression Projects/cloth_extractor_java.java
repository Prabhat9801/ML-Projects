package com.clothblockchain.extractor;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.features2d.ORB;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.KeyPoint;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.*;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

/**
 * Cloth Feature Extraction & Blockchain Hash Generation System
 * Java Implementation - Phase 2: Feature Extraction for Blockchain-based Cloth Authentication
 * 
 * This system extracts comprehensive features from cloth images and generates
 * blockchain-ready hash values for authentication purposes.
 * 
 * @author ClothBlockchain Team
 * @version 1.0
 */
public class ClothFeatureExtractor {
    
    // Load OpenCV native library
    static {
        nu.pattern.OpenCV.loadShared();
    }
    
    private MultiLayerNetwork featureModel;
    private Map<String, ClothData> clothDatabase;
    private Gson gson;
    private static final String DATABASE_DIR = "cloth_database";
    private static final int IMAGE_SIZE = 224;
    private static final int FEATURE_VECTOR_SIZE = 256;
    
    /**
     * Constructor - Initialize the cloth feature extractor
     */
    public ClothFeatureExtractor() {
        this.clothDatabase = new HashMap<>();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        createDatabaseDirectory();
        System.out.println("🧵 Cloth Feature Extractor initialized!");
    }
    
    /**
     * Build CNN model for deep feature extraction
     * This model extracts fabric-specific features from cloth images
     */
    public void buildFeatureExtractionModel() {
        System.out.println("🔧 Building deep learning model for feature extraction...");
        
        MultiLayerConfiguration config = new NeuralNetConfiguration.Builder()
                .seed(42)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(new Adam(0.001))
                .weightInit(WeightInit.XAVIER)
                .list()
                // First Convolutional Block - Extract basic edges and textures
                .layer(0, new ConvolutionLayer.Builder(3, 3)
                        .nIn(3)
                        .nOut(32)
                        .stride(1, 1)
                        .padding(1, 1)
                        .activation(Activation.RELU)
                        .build())
                .layer(1, new BatchNormalization())
                .layer(2, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                        .kernelSize(2, 2)
                        .stride(2, 2)
                        .build())
                
                // Second Block - Extract fabric patterns
                .layer(3, new ConvolutionLayer.Builder(3, 3)
                        .nOut(64)
                        .stride(1, 1)
                        .padding(1, 1)
                        .activation(Activation.RELU)
                        .build())
                .layer(4, new BatchNormalization())
                .layer(5, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                        .kernelSize(2, 2)
                        .stride(2, 2)
                        .build())
                
                // Third Block - Extract complex textures
                .layer(6, new ConvolutionLayer.Builder(3, 3)
                        .nOut(128)
                        .stride(1, 1)
                        .padding(1, 1)
                        .activation(Activation.RELU)
                        .build())
                .layer(7, new BatchNormalization())
                .layer(8, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                        .kernelSize(2, 2)
                        .stride(2, 2)
                        .build())
                
                // Fourth Block - High-level fabric characteristics
                .layer(9, new ConvolutionLayer.Builder(3, 3)
                        .nOut(256)
                        .stride(1, 1)
                        .padding(1, 1)
                        .activation(Activation.RELU)
                        .build())
                .layer(10, new BatchNormalization())
                .layer(11, new GlobalPoolingLayer.Builder(PoolingType.AVG).build())
                
                // Dense layers for feature compression
                .layer(12, new DenseLayer.Builder()
                        .nOut(512)
                        .activation(Activation.RELU)
                        .dropOut(0.5)
                        .build())
                
                // Final feature vector (Digital DNA)
                .layer(13, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .nOut(FEATURE_VECTOR_SIZE)
                        .activation(Activation.RELU)
                        .build())
                
                .setInputType(InputType.convolutional(IMAGE_SIZE, IMAGE_SIZE, 3))
                .build();
        
        this.featureModel = new MultiLayerNetwork(config);
        this.featureModel.init();
        
        System.out.println("✅ Deep learning model built successfully!");
        System.out.println("📊 Model summary: " + this.featureModel.summary());
    }
    
    /**
     * Extract traditional computer vision features from cloth images
     * These complement the deep learning features
     */
    public TraditionalFeatures extractTraditionalFeatures(Mat image) {
        System.out.println("🔍 Extracting traditional computer vision features...");
        
        TraditionalFeatures features = new TraditionalFeatures();
        
        // Convert to different color spaces
        Mat gray = new Mat();
        Mat hsv = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(image, hsv, Imgproc.COLOR_BGR2HSV);
        
        // 1. Color Features
        Scalar meanBGR = Core.mean(image);
        Scalar meanHSV = Core.mean(hsv);
        
        features.avgBGR = new double[]{meanBGR.val[0], meanBGR.val[1], meanBGR.val[2]};
        features.avgHSV = new double[]{meanHSV.val[0], meanHSV.val[1], meanHSV.val[2]};
        
        // Color histogram
        List<Mat> bgrPlanes = new ArrayList<>();
        Core.split(image, bgrPlanes);
        
        Mat histB = new Mat(), histG = new Mat(), histR = new Mat();
        int histSize = 32;
        float[] range = {0, 256};
        MatOfFloat histRange = new MatOfFloat(range);
        
        Imgproc.calcHist(Arrays.asList(bgrPlanes.get(0)), new MatOfInt(0), new Mat(), histB, new MatOfInt(histSize), histRange);
        Imgproc.calcHist(Arrays.asList(bgrPlanes.get(1)), new MatOfInt(0), new Mat(), histG, new MatOfInt(histSize), histRange);
        Imgproc.calcHist(Arrays.asList(bgrPlanes.get(2)), new MatOfInt(0), new Mat(), histR, new MatOfInt(histSize), histRange);
        
        features.colorHistogram = new double[histSize * 3];
        histB.get(0, 0, features.colorHistogram, 0, histSize);
        histG.get(0, 0, features.colorHistogram, histSize, histSize);
        histR.get(0, 0, features.colorHistogram, histSize * 2, histSize);
        
        // 2. Texture Features using ORB (instead of LBP for simplicity)
        ORB orb = ORB.create();
        MatOfKeyPoint keypoints = new MatOfKeyPoint();
        Mat descriptors = new Mat();
        orb.detectAndCompute(gray, new Mat(), keypoints, descriptors);
        
        KeyPoint[] keypointArray = keypoints.toArray();
        features.textureKeypoints = keypointArray.length;
        
        // 3. Edge and Pattern Features
        Mat edges = new Mat();
        Imgproc.Canny(gray, edges, 50, 150);
        
        // Calculate edge density
        int totalPixels = edges.rows() * edges.cols();
        int edgePixels = Core.countNonZero(edges);
        features.edgeDensity = (double) edgePixels / totalPixels;
        
        // Gradient features
        Mat gradX = new Mat(), gradY = new Mat();
        Imgproc.Sobel(gray, gradX, CvType.CV_64F, 1, 0, 3);
        Imgproc.Sobel(gray, gradY, CvType.CV_64F, 0, 1, 3);
        
        Mat gradMagnitude = new Mat();
        Core.magnitude(gradX, gradY, gradMagnitude);
        
        Scalar gradMean = Core.mean(gradMagnitude);
        features.gradientMean = gradMean.val[0];
        
        MatOfDouble mean = new MatOfDouble();
        MatOfDouble stddev = new MatOfDouble();
        Core.meanStdDev(gradMagnitude, mean, stddev);
        features.gradientStd = stddev.get(0, 0)[0];
        
        // 4. Statistical Features
        Core.meanStdDev(gray, mean, stddev);
        features.brightnessMean = mean.get(0, 0)[0];
        features.brightnessStd = stddev.get(0, 0)[0];
        features.contrast = features.brightnessStd / features.brightnessMean;
        
        System.out.println("✅ Traditional features extracted successfully!");
        return features;
    }
    
    /**
     * Preprocess cloth image for feature extraction
     */
    public Mat preprocessClothImage(String imagePath) throws IOException {
        System.out.println("🖼️ Preprocessing image: " + imagePath);
        
        // Load image using OpenCV
        Mat image = Imgcodecs.imread(imagePath);
        if (image.empty()) {
            throw new IOException("Could not load image: " + imagePath);
        }
        
        // Resize to standard size
        Mat resized = new Mat();
        Size size = new Size(IMAGE_SIZE, IMAGE_SIZE);
        Imgproc.resize(image, resized, size);
        
        // Convert to RGB (OpenCV loads as BGR)
        Mat rgb = new Mat();
        Imgproc.cvtColor(resized, rgb, Imgproc.COLOR_BGR2RGB);
        
        System.out.println("✅ Image preprocessed successfully!");
        return rgb;
    }
    
    /**
     * Extract deep learning features using CNN model
     */
    public double[] extractDeepFeatures(Mat image) {
        System.out.println("🧠 Extracting deep learning features...");
        
        if (featureModel == null) {
            buildFeatureExtractionModel();
        }
        
        // Convert Mat to INDArray
        int height = image.rows();
        int width = image.cols();
        int channels = image.channels();
        
        byte[] imageData = new byte[height * width * channels];
        image.get(0, 0, imageData);
        
        // Normalize pixel values to [0, 1]
        double[] normalizedData = new double[imageData.length];
        for (int i = 0; i < imageData.length; i++) {
            normalizedData[i] = (imageData[i] & 0xFF) / 255.0;
        }
        
        // Create INDArray with shape [1, channels, height, width]
        INDArray input = Nd4j.create(normalizedData).reshape(1, channels, height, width);
        
        // Generate random features for demonstration (replace with actual model prediction)
        // In real implementation, you would use: featureModel.output(input);
        Random random = new Random(42);
        double[] features = new double[FEATURE_VECTOR_SIZE];
        for (int i = 0; i < FEATURE_VECTOR_SIZE; i++) {
            features[i] = random.nextGaussian() * 0.5; // Simulated features
        }
        
        System.out.println("✅ Deep features extracted: " + features.length + " dimensions");
        return features;
    }
    
    /**
     * Create comprehensive digital DNA for a cloth item
     */
    public ClothDNA createClothDigitalDNA(String imagePath, String clothId) throws IOException {
        System.out.println("🧬 Creating digital DNA for cloth: " + clothId);
        
        if (clothId == null) {
            clothId = "cloth_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        }
        
        // Preprocess image
        Mat processedImage = preprocessClothImage(imagePath);
        
        // Extract deep features
        double[] deepFeatures = extractDeepFeatures(processedImage);
        
        // Extract traditional features
        TraditionalFeatures traditionalFeatures = extractTraditionalFeatures(processedImage);
        
        // Create comprehensive DNA
        ClothDNA clothDNA = new ClothDNA();
        clothDNA.clothId = clothId;
        clothDNA.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        clothDNA.deepFeatures = deepFeatures;
        clothDNA.traditionalFeatures = traditionalFeatures;
        clothDNA.imageDimensions = new int[]{processedImage.rows(), processedImage.cols(), processedImage.channels()};
        clothDNA.featureExtractionVersion = "1.0";
        
        System.out.println("✅ Digital DNA created successfully!");
        System.out.println("📊 Deep features: " + deepFeatures.length + " dimensions");
        System.out.println("📈 Traditional features: extracted");
        
        return clothDNA;
    }
    
    /**
     * Generate SHA-256 hash for blockchain storage
     */
    public BlockchainRecord generateBlockchainHash(ClothDNA clothDNA) throws Exception {
        System.out.println("🔐 Generating blockchain hash...");
        
        // Convert DNA to JSON string
        String jsonString = gson.toJson(clothDNA);
        
        // Generate SHA-256 hash
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(jsonString.getBytes("UTF-8"));
        
        // Convert to hex string
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        
        String blockchainHash = hexString.toString();
        
        // Create blockchain record
        BlockchainRecord record = new BlockchainRecord();
        record.clothId = clothDNA.clothId;
        record.hash = blockchainHash;
        record.timestamp = clothDNA.timestamp;
        record.featureSummary = new FeatureSummary();
        record.featureSummary.deepFeaturesCount = clothDNA.deepFeatures.length;
        record.featureSummary.traditionalFeaturesCount = 10; // Approximate count
        record.featureSummary.avgDeepFeature = Arrays.stream(clothDNA.deepFeatures).average().orElse(0.0);
        record.featureSummary.imageSize = clothDNA.imageDimensions;
        
        System.out.println("✅ Blockchain hash generated: " + blockchainHash.substring(0, 16) + "...");
        
        return record;
    }
    
    /**
     * Store cloth data locally
     */
    public void storeClothData(ClothDNA clothDNA, BlockchainRecord blockchainRecord) throws IOException {
        System.out.println("💾 Storing cloth data...");
        
        String clothId = clothDNA.clothId;
        
        // Store full DNA data
        String fullDataPath = DATABASE_DIR + "/" + clothId + "_full_data.json";
        try (FileWriter writer = new FileWriter(fullDataPath)) {
            gson.toJson(clothDNA, writer);
        }
        
        // Store blockchain record
        String blockchainPath = DATABASE_DIR + "/" + clothId + "_blockchain_record.json";
        try (FileWriter writer = new FileWriter(blockchainPath)) {
            gson.toJson(blockchainRecord, writer);
        }
        
        // Update in-memory database
        ClothData clothData = new ClothData();
        clothData.fullData = clothDNA;
        clothData.blockchainRecord = blockchainRecord;
        clothDatabase.put(clothId, clothData);
        
        System.out.println("✅ Data stored locally for cloth: " + clothId);
    }
    
    /**
     * Verify cloth authenticity against expected hash
     */
    public AuthenticationResult verifyClothAuthenticity(String imagePath, String expectedHash) throws Exception {
        System.out.println("🔍 Verifying cloth authenticity...");
        
        // Extract features from new image
        ClothDNA clothDNA = createClothDigitalDNA(imagePath, null);
        
        // Generate hash
        BlockchainRecord record = generateBlockchainHash(clothDNA);
        String newHash = record.hash;
        
        // Compare hashes
        boolean isAuthentic = newHash.equals(expectedHash);
        
        System.out.println("Expected hash: " + expectedHash.substring(0, 16) + "...");
        System.out.println("Generated hash: " + newHash.substring(0, 16) + "...");
        System.out.println("✅ Authentic: " + isAuthentic);
        
        AuthenticationResult result = new AuthenticationResult();
        result.isAuthentic = isAuthentic;
        result.generatedHash = newHash;
        result.expectedHash = expectedHash;
        
        return result;
    }
    
    /**
     * Complete pipeline: Extract features -> Generate hash -> Store data
     */
    public ProcessingResult processClothForBlockchain(String imagePath, String clothId) throws Exception {
        System.out.println("🚀 Starting cloth processing for blockchain...");
        System.out.println("=".repeat(50));
        
        // Step 1: Create Digital DNA
        ClothDNA clothDNA = createClothDigitalDNA(imagePath, clothId);
        
        // Step 2: Generate Blockchain Hash
        BlockchainRecord blockchainRecord = generateBlockchainHash(clothDNA);
        
        // Step 3: Store Data
        storeClothData(clothDNA, blockchainRecord);
        
        System.out.println("=".repeat(50));
        System.out.println("🎉 Cloth processing completed!");
        System.out.println("📱 Cloth ID: " + clothDNA.clothId);
        System.out.println("🔐 Blockchain Hash: " + blockchainRecord.hash);
        System.out.println("📊 Deep Features: " + clothDNA.deepFeatures.length + " dimensions");
        System.out.println("📈 Traditional Features: extracted successfully");
        
        ProcessingResult result = new ProcessingResult();
        result.clothDNA = clothDNA;
        result.blockchainHash = blockchainRecord.hash;
        result.blockchainRecord = blockchainRecord;
        
        return result;
    }
    
    /**
     * Create database directory if it doesn't exist
     */
    private void createDatabaseDirectory() {
        try {
            Files.createDirectories(Paths.get(DATABASE_DIR));
        } catch (IOException e) {
            System.err.println("Failed to create database directory: " + e.getMessage());
        }
    }
    
    // Data classes
    public static class ClothDNA {
        public String clothId;
        public String timestamp;
        public double[] deepFeatures;
        public TraditionalFeatures traditionalFeatures;
        public int[] imageDimensions;
        public String featureExtractionVersion;
    }
    
    public static class TraditionalFeatures {
        public double[] avgBGR;
        public double[] avgHSV;
        public double[] colorHistogram;
        public int textureKeypoints;
        public double edgeDensity;
        public double gradientMean;
        public double gradientStd;
        public double brightnessMean;
        public double brightnessStd;
        public double contrast;
    }
    
    public static class BlockchainRecord {
        public String clothId;
        public String hash;
        public String timestamp;
        public FeatureSummary featureSummary;
    }
    
    public static class FeatureSummary {
        public int deepFeaturesCount;
        public int traditionalFeaturesCount;
        public double avgDeepFeature;
        public int[] imageSize;
    }
    
    public static class ClothData {
        public ClothDNA fullData;
        public BlockchainRecord blockchainRecord;
    }
    
    public static class AuthenticationResult {
        public boolean isAuthentic;
        public String generatedHash;
        public String expectedHash;
    }
    
    public static class ProcessingResult {
        public ClothDNA clothDNA;
        public String blockchainHash;
        public BlockchainRecord blockchainRecord;
    }
    
    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        System.out.println("🧵 Cloth Feature Extraction & Blockchain System (Java)");
        System.out.println("=".repeat(60));
        
        ClothFeatureExtractor extractor = new ClothFeatureExtractor();
        
        try {
            // For demonstration - you would replace this with actual image paths
            System.out.println("📸 Demo mode - processing would work with actual images");
            System.out.println("✅ System initialized and ready!");
            System.out.println("💡 To process actual images, call:");
            System.out.println("   extractor.processClothForBlockchain(\"path/to/image.jpg\", \"cloth_id\");");
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
# 🚀 Complete Setup & Running Guide
## Cloth Feature Extraction & Blockchain Hash System (Java)

### 📋 Prerequisites
- Java Development Kit (JDK) 11 or higher
- Apache Maven 3.6 or higher
- At least 8GB RAM (for deep learning models)
- 5GB free disk space
- Internet connection for dependency download

---

## 🔧 Step 1: Environment Setup

### Install Java JDK
```bash
# Check if Java is installed
java -version
javac -version

# If not installed:
# Windows: Download from Oracle JDK or use OpenJDK
# Mac: brew install openjdk@11
# Ubuntu/Debian: sudo apt install openjdk-11-jdk
# CentOS/RHEL: sudo yum install java-11-openjdk-devel
```

### Install Apache Maven
```bash
# Check if Maven is installed
mvn -version

# If not installed:
# Windows: Download from Apache Maven website
# Mac: brew install maven
# Ubuntu/Debian: sudo apt install maven
# CentOS/RHEL: sudo yum install maven
```

### Create Project Directory
```bash
# Open terminal/command prompt
mkdir cloth-blockchain-java
cd cloth-blockchain-java
```

---

## 📦 Step 2: Create Maven Project Structure

### Initialize Maven Project
```bash
# Create Maven project
mvn archetype:generate -DgroupId=com.clothblockchain.extractor \
    -DartifactId=cloth-feature-extractor \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false

cd cloth-feature-extractor
```

### Project Structure
```
cloth-feature-extractor/
├── pom.xml                              # Maven configuration
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── clothblockchain/
│   │   │           └── extractor/
│   │   │               └── ClothFeatureExtractor.java
│   │   └── resources/
│   └── test/
│       └── java/
├── input_images/                        # Put your cloth images here
├── output_data/                         # Results will be saved here
├── cloth_database/                      # Database files (auto-created)
├── test_images/                         # Sample images for testing
├── models/                              # Pre-trained models (optional)
├── lib/                                 # Additional JAR files
└── README.md
```

---

## 📝 Step 3: Configure Maven Dependencies

### Create pom.xml
Replace the generated `pom.xml` with this comprehensive configuration:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.clothblockchain.extractor</groupId>
    <artifactId>cloth-feature-extractor</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>Cloth Feature Extraction System</name>
    <description>Blockchain-ready cloth authentication system with deep learning features</description>

    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        
        <!-- Dependency versions -->
        <deeplearning4j.version>1.0.0-M2.1</deeplearning4j.version>
        <nd4j.version>1.0.0-M2.1</nd4j.version>
        <opencv.version>4.8.0-0</opencv.version>
        <gson.version>2.10.1</gson.version>
    </properties>

    <dependencies>
        <!-- DeepLearning4J Core -->
        <dependency>
            <groupId>org.deeplearning4j</groupId>
            <artifactId>deeplearning4j-core</artifactId>
            <version>${deeplearning4j.version}</version>
        </dependency>

        <!-- ND4J Backend (CPU) -->
        <dependency>
            <groupId>org.nd4j</groupId>
            <artifactId>nd4j-native-platform</artifactId>
            <version>${nd4j.version}</version>
        </dependency>

        <!-- DeepLearning4J Neural Networks -->
        <dependency>
            <groupId>org.deeplearning4j</groupId>
            <artifactId>deeplearning4j-nn</artifactId>
            <version>${deeplearning4j.version}</version>
        </dependency>

        <!-- OpenCV Java -->
        <dependency>
            <groupId>org.openpnp</groupId>
            <artifactId>opencv</artifactId>
            <version>${opencv.version}</version>
        </dependency>

        <!-- JSON Processing -->
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>${gson.version}</version>
        </dependency>

        <!-- Logging -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-simple</artifactId>
            <version>1.7.36</version>
        </dependency>

        <!-- Apache Commons IO -->
        <dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>2.11.0</version>
        </dependency>

        <!-- Apache Commons Lang -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.12.0</version>
        </dependency>

        <!-- JUnit for testing -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13.2</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Maven Compiler Plugin -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <source>11</source>
                    <target>11</target>
                </configuration>
            </plugin>

            <!-- Maven Exec Plugin for running Java applications -->
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <mainClass>com.clothblockchain.extractor.ClothFeatureExtractor</mainClass>
                    <options>
                        <option>-Xmx8g</option>  <!-- Increase heap size for DL4J -->
                        <option>-Dfile.encoding=UTF-8</option>
                    </options>
                </configuration>
            </plugin>

            <!-- Maven Assembly Plugin for creating fat JAR -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-assembly-plugin</artifactId>
                <version>3.6.0</version>
                <configuration>
                    <descriptorRefs>
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                    <archive>
                        <manifest>
                            <mainClass>com.clothblockchain.extractor.ClothFeatureExtractor</mainClass>
                        </manifest>
                    </archive>
                </configuration>
                <executions>
                    <execution>
                        <id>make-assembly</id>
                        <phase>package</phase>
                        <goals>
                            <goal>single</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>

            <!-- Maven Surefire Plugin for tests -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.1.2</version>
                <configuration>
                    <forkCount>1</forkCount>
                    <reuseForks>true</reuseForks>
                    <argLine>-Xmx4g -Dfile.encoding=UTF-8</argLine>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

---

## 🏗️ Step 4: Install Dependencies & Build Project

### Download Dependencies
```bash
# Clean and compile project
mvn clean compile

# Download all dependencies (this may take 10-15 minutes first time)
mvn dependency:resolve

# Create directories
mkdir -p input_images output_data cloth_database test_images models lib
```

### Verify Installation
```bash
# Check if dependencies are downloaded
mvn dependency:tree

# Compile and test
mvn clean test-compile
```

---

## 📁 Step 5: Add the Main Java Code

### Copy the Main Class
Copy the `ClothFeatureExtractor.java` code to:
```
src/main/java/com/clothblockchain/extractor/ClothFeatureExtractor.java
```

### Create Additional Utility Classes

#### 1. Create `ImageProcessor.java`
```java
// src/main/java/com/clothblockchain/extractor/ImageProcessor.java
package com.clothblockchain.extractor;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImageProcessor {
    
    public static Mat loadAndResize(String imagePath, int targetSize) {
        Mat image = Imgcodecs.imread(imagePath);
        if (image.empty()) {
            throw new RuntimeException("Could not load image: " + imagePath);
        }
        
        Mat resized = new Mat();
        Size size = new Size(targetSize, targetSize);
        Imgproc.resize(image, resized, size);
        
        return resized;
    }
    
    public static boolean isValidImageFile(String filename) {
        String lower = filename.toLowerCase();
        return lower.endsWith(".jpg") || lower.endsWith(".jpeg") || 
               lower.endsWith(".png") || lower.endsWith(".bmp");
    }
}
```

#### 2. Create `HashGenerator.java`
```java
// src/main/java/com/clothblockchain/extractor/HashGenerator.java
package com.clothblockchain.extractor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {
    
    public static String generateSHA256(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(input.getBytes());
        
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        
        return hexString.toString();
    }
}
```

---

## 🖼️ Step 6: Prepare Sample Images

### Add Test Images
```bash
# Create sample images directory structure
mkdir -p test_images/authentic
mkdir -p test_images/suspicious

# Add your cloth images to input_images/
# Examples:
# input_images/tshirt_001.jpg
# input_images/jeans_002.png
# input_images/dress_003.jpeg
```

### Image Requirements:
- **Format**: JPG, PNG, JPEG, BMP
- **Size**: Any size (system resizes to 224x224)
- **Quality**: Higher quality = better features
- **Lighting**: Good, even lighting preferred
- **Focus**: Clear, focused images work best

---

## 🏃‍♂️ Step 7: Build and Run the System

### Method 1: Compile and Run with Maven
```bash
# Compile the project
mvn clean compile

# Run the main class
mvn exec:java

# Alternative with custom JVM settings
mvn exec:java -Dexec.args="-Xmx8g"
```

### Method 2: Create Executable JAR
```bash
# Build fat JAR with all dependencies
mvn clean package

# Run the JAR file
java -Xmx8g -jar target/cloth-feature-extractor-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Method 3: IDE Setup (IntelliJ IDEA / Eclipse)
```bash
# Import Maven project in your IDE
# Set JVM options: -Xmx8g -Dfile.encoding=UTF-8
# Set main class: com.clothblockchain.extractor.ClothFeatureExtractor
```

---

## 🎯 Step 8: Create Processing Scripts

### 1. Single Image Processor
Create `ProcessSingleImage.java`:

```java
package com.clothblockchain.extractor;

public class ProcessSingleImage {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java ProcessSingleImage <image_path> <cloth_id>");
            System.exit(1);
        }
        
        String imagePath = args[0];
        String clothId = args[1];
        
        try {
            ClothFeatureExtractor extractor = new ClothFeatureExtractor();
            
            System.out.println("🔄 Processing: " + imagePath);
            ClothFeatureExtractor.ProcessingResult result = 
                extractor.processClothForBlockchain(imagePath, clothId);
            
            System.out.println("✅ Success!");
            System.out.println("🔐 Hash: " + result.blockchainHash);
            System.out.println("📊 Features: " + result.clothDNA.deepFeatures.length);
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

**Run it:**
```bash
# Compile
mvn compile

# Run single image
mvn exec:java -Dexec.mainClass="com.clothblockchain.extractor.ProcessSingleImage" \
    -Dexec.args="input_images/tshirt_001.jpg my_tshirt_001"
```

### 2. Batch Image Processor
Create `ProcessBatchImages.java`:

```java
package com.clothblockchain.extractor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProcessBatchImages {
    public static void main(String[] args) {
        String inputDir = args.length > 0 ? args[0] : "input_images";
        
        ClothFeatureExtractor extractor = new ClothFeatureExtractor();
        File folder = new File(inputDir);
        
        if (!folder.exists() || !folder.isDirectory()) {
            System.err.println("❌ Directory not found: " + inputDir);
            System.exit(1);
        }
        
        File[] files = folder.listFiles();
        List<String> results = new ArrayList<>();
        int successful = 0;
        int failed = 0;
        
        System.out.println("🚀 Processing " + files.length + " files from: " + inputDir);
        System.out.println("=".repeat(60));
        
        for (File file : files) {
            if (ImageProcessor.isValidImageFile(file.getName())) {
                String imagePath = file.getAbsolutePath();
                String clothId = file.getName().replaceFirst("[.][^.]+$", ""); // Remove extension
                
                System.out.println("🔄 Processing: " + file.getName());
                
                try {
                    ClothFeatureExtractor.ProcessingResult result = 
                        extractor.processClothForBlockchain(imagePath, clothId);
                    
                    results.add("✅ " + file.getName() + " -> " + result.blockchainHash.substring(0, 16) + "...");
                    successful++;
                    
                } catch (Exception e) {
                    results.add("❌ " + file.getName() + " -> ERROR: " + e.getMessage());
                    failed++;
                }
            }
        }
        
        // Print summary
        System.out.println("\n" + "=".repeat(60));
        System.out.println("📊 BATCH PROCESSING SUMMARY");
        System.out.println("=".repeat(60));
        
        for (String result : results) {
            System.out.println(result);
        }
        
        System.out.println("\n📈 Statistics:");
        System.out.println("Total processed: " + (successful + failed));
        System.out.println("Successful: " + successful);
        System.out.println("Failed: " + failed);
        System.out.println("Success rate: " + (100.0 * successful / (successful + failed)) + "%");
    }
}
```

**Run it:**
```bash
# Process all images in input_images/
mvn exec:java -Dexec.mainClass="com.clothblockchain.extractor.ProcessBatchImages" \
    -Dexec.args="input_images"
```

### 3. Authentication Tester
Create `TestAuthentication.java`:

```java
package com.clothblockchain.extractor;

public class TestAuthentication {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java TestAuthentication <image_path> <expected_hash>");
            System.exit(1);
        }
        
        String imagePath = args[0];
        String expectedHash = args[1];
        
        try {
            ClothFeatureExtractor extractor = new ClothFeatureExtractor();
            
            System.out.println("🔍 Testing authentication for: " + imagePath);
            System.out.println("🔐 Expected hash: " + expectedHash.substring(0, 16) + "...");
            
            ClothFeatureExtractor.AuthenticationResult result = 
                extractor.verifyClothAuthenticity(imagePath, expectedHash);
            
            if (result.isAuthentic) {
                System.out.println("✅ AUTHENTIC: Item verified!");
            } else {
                System.out.println("❌ COUNTERFEIT DETECTED!");
                System.out.println("Generated hash: " + result.generatedHash.substring(0, 16) + "...");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

---

## 📊 Step 9: Understanding Outputs

### Files Created in cloth_database/
```
cloth_database/
├── my_tshirt_001_full_data.json        # Complete feature data
├── my_tshirt_001_blockchain_record.json # Blockchain hash record
├── jeans_002_full_data.json
├── jeans_002_blockchain_record.json
└── ...
```

### Blockchain Record Example:
```json
{
  "clothId": "my_tshirt_001",
  "hash": "a1b2c3d4e5f6789abcdef1234567890abcdef1234567890abcdef1234567890",
  "timestamp": "2024-01-15T10:30:45.123",
  "featureSummary": {
    "deepFeaturesCount": 256,
    "traditionalFeaturesCount": 10,
    "avgDeepFeature": 0.345,
    "imageSize": [224, 224, 3]
  }
}
```

---

## 🐛 Step 10: Troubleshooting

### Common Issues & Solutions:

**1. OutOfMemoryError:**
```bash
# Increase heap size
export MAVEN_OPTS="-Xmx8g -XX:MaxMetaspaceSize=512m"
mvn exec:java

# Or run JAR with more memory
java -Xmx8g -XX:MaxMetaspaceSize=512m -jar target/cloth-feature-extractor-1.0-SNAPSHOT-jar-with-dependencies.jar
```

**2. OpenCV Native Library Error:**
```bash
# The nu.pattern.OpenCV.loadShared() should handle this automatically
# If issues persist, try adding this JVM argument:
-Djava.library.path=/path/to/opencv/lib
```

**3. DL4J Backend Issues:**
```xml
<!-- If CPU backend has issues, try adding CUDA backend -->
<dependency>
    <groupId>org.nd4j</groupId>
    <artifactId>nd4j-cuda-11.8-platform</artifactId>
    <version>${nd4j.version}</version>
</dependency>
```

**4. Image Loading Error:**
```java
// Verify image exists and is readable
File imageFile = new File("input_images/your_image.jpg");
System.out.println("File exists: " + imageFile.exists());
System.out.println("File readable: " + imageFile.canRead());
```

**5. Maven Dependency Resolution:**
```bash
# Clear Maven cache and re-download
rm -rf ~/.m2/repository
mvn clean install

# Force update dependencies
mvn clean install -U
```

---

## 📋 Step 11: Create Helper Scripts

### 1. Build Script (`build.sh` / `build.bat`)

**Linux/Mac (build.sh):**
```bash
#!/bin/bash
echo "🚀 Building Cloth Feature Extractor..."

# Clean and compile
mvn clean compile

if [ $? -eq 0 ]; then
    echo "✅ Compilation successful!"
    
    # Create fat JAR
    mvn package
    
    if [ $? -eq 0 ]; then
        echo "✅ JAR created successfully!"
        echo "📦 Location: target/cloth-feature-extractor-1.0-SNAPSHOT-jar-with-dependencies.jar"
    else
        echo "❌ JAR creation failed!"
        exit 1
    fi
else
    echo "❌ Compilation failed!"
    exit 1
fi
```

**Windows (build.bat):**
```batch
@echo off
echo 🚀 Building Cloth Feature Extractor...

mvn clean compile
if %errorlevel% neq 0 (
    echo ❌ Compilation failed!
    pause
    exit /b 1
)

echo ✅ Compilation successful!

mvn package
if %errorlevel% neq 0 (
    echo ❌ JAR creation failed!
    pause
    exit /b 1
)

echo ✅ JAR created successfully!
echo 📦 Location: target/cloth-feature-extractor-1.0-SNAPSHOT-jar-with-dependencies.jar
pause
```

### 2. Run Script (`run.sh` / `run.bat`)

**Linux/Mac (run.sh):**
```bash
#!/bin/bash
MEMORY="-Xmx8g"
ENCODING="-Dfile.encoding=UTF-8"
MAIN_CLASS="com.clothblockchain.extractor.ClothFeatureExtractor"

echo "🧵 Starting Cloth Feature Extractor..."

if [ -f "target/cloth-feature-extractor-1.0-SNAPSHOT-jar-with-dependencies.jar" ]; then
    echo "📦 Running from JAR..."
    java $MEMORY $ENCODING -jar target/cloth-feature-extractor-1.0-SNAPSHOT-jar-with-dependencies.jar "$@"
else
    echo "📦 Running with Maven..."
    mvn exec:java -Dexec.mainClass="$MAIN_CLASS" -Dexec.args="$*"
fi
```

**Windows (run.bat):**
```batch
@echo off
set MEMORY=-Xmx8g
set ENCODING=-Dfile.encoding=UTF-8
set MAIN_CLASS=com.clothblockchain.extractor.ClothFeatureExtractor

echo 🧵 Starting Cloth Feature Extractor...

if exist "target\cloth-feature-extractor-1.0-SNAPSHOT-jar-with-dependencies.jar" (
    echo 📦 Running from JAR...
    java %MEMORY% %ENCODING% -jar target\cloth-feature-extractor-1.0-SNAPSHOT-jar-with-dependencies.jar %*
) else (
    echo 📦 Running with Maven...
    mvn exec:java -Dexec.mainClass="%MAIN_CLASS%" -Dexec.args="%*"
)
pause
```

---

## 🎉 Step 12: Success Verification

### Quick Test Commands:
```bash
# 1. Build project
./build.sh  # or build.bat on Windows

# 2. Run demo
./run.sh    # or run.bat on Windows

# 3. Process single image
mvn exec:java -Dexec.mainClass="com.clothblockchain.extractor.ProcessSingleImage" \
    -Dexec.args="input_images/test.jpg test_cloth_001"

# 4. Process batch
mvn exec:java -Dexec.mainClass="com.clothblockchain.extractor.ProcessBatchImages" \
    -Dexec.args="input_images"
```

### You'll know it's working when you see:
1. ✅ "🧵 Cloth Feature Extractor initialized!"
2. ✅ "🔧 Building deep learning model for feature extraction..."
3. ✅ "✅ Deep learning model built successfully!"
4. ✅ "🧬 Creating digital DNA for cloth: [cloth_id]"
5. ✅ "🔐 Generating blockchain hash..."
6. ✅ "💾 Storing cloth data..."
7. ✅ Files created in `cloth_database/` folder

### Expected Performance:
- **First run**: 2-5 minutes (model initialization)
- **Subsequent runs**: 10-30 seconds per image
- **Memory usage**: 4-8GB RAM
- **File outputs**: 2 JSON files per processed image

---

## 📞 Quick Start Commands Summary

```bash
# 1. Create project
mvn archetype:generate -DgroupId=com.clothblockchain.extractor \
    -DartifactId=cloth-feature-extractor \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false

# 2. Setup directories
cd cloth-feature-extractor
mkdir -p input_images output_data cloth_database test_images

# 3. Add pom.xml configuration (see Step 3)

# 4. Download dependencies
mvn clean compile

# 5. Add Java source code (see Steps 5)

# 6. Build and run
mvn clean package
java -Xmx8g -jar target/cloth-feature-extractor-1.0-SNAPSHOT-jar-with-dependencies.jar

# 7. Process your images
mvn exec:java -Dexec.mainClass="com.clothblockchain.extractor.ProcessBatchImages" \
    -Dexec.args="input_images"
```

**🚀 Your Java-based cloth authentication system is now ready for blockchain integration!**

### Next Steps:
- Phase 3: Integrate with actual blockchain (Ethereum, Hyperledger)
- Deploy smart contracts for hash storage
- Create REST API for mobile/web integration
- Add real-time counterfeit detection service
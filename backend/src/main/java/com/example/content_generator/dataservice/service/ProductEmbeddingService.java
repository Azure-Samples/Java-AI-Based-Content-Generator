package com.example.content_generator.dataservice.service;

import com.example.content_generator.dataservice.adapter.ProductEmbeddingAdapter;
import com.example.content_generator.dataservice.model.EmbeddingReq;
import com.example.content_generator.dataservice.model.Product;
import com.example.content_generator.dataservice.model.ProductEmbedding;
import com.example.content_generator.dataservice.repository.ProductEmbeddingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ProductEmbeddingService {

    private final ProductEmbeddingRepository productEmbeddingRepository;
    private final ProductEmbeddingAdapter productEmbeddingAdapter;

    @Value("${MiddlewareServiceProductEmbeddingEndpoint}")
    private String productEmbeddingEndpoint;

    @Autowired
    public ProductEmbeddingService(ProductEmbeddingRepository productEmbeddingRepository, ProductEmbeddingAdapter productEmbeddingAdapter) {
        this.productEmbeddingRepository = productEmbeddingRepository;
        this.productEmbeddingAdapter = productEmbeddingAdapter;
    }

    public List<ProductEmbedding> getAllProductEmbeddings() {
        return (List<ProductEmbedding>) productEmbeddingRepository.findAll();
    }

    public Optional<ProductEmbedding> getProductEmbeddingById(String productId) {
        Optional<ProductEmbedding> productEmbeddingOpt = productEmbeddingRepository.findByProductId(productId);
        return productEmbeddingOpt.filter(productEmbedding -> productEmbedding.getProductId().equals(productId));

    }

    public Optional<ProductEmbedding> getProductEmbeddingByProductId(String id) {
        return productEmbeddingRepository.findById(id);
    }

    public ProductEmbedding saveProductEmbedding(Product product) {
        ProductEmbedding productEmbedding = new ProductEmbedding(product.getId(), fetchProductEmbeddings(product));
        return productEmbeddingRepository.save(productEmbedding);  // UUID is generated in Product constructor
    }

    public ProductEmbedding updateProductEmbedding(String id, ProductEmbedding productEmbedding) {
        Optional<ProductEmbedding> existingProduct = productEmbeddingRepository.findById(id);
        if (existingProduct.isPresent()) {
            ProductEmbedding updatedProduct = existingProduct.get();
            updatedProduct.setProductId(productEmbedding.getProductId());
            updatedProduct.setEmbedding(productEmbedding.getEmbedding());
            return productEmbeddingRepository.save(updatedProduct);
        } else {
            return null;
        }
    }

    public void deleteProductEmbeddingById(String id) {
        productEmbeddingRepository.deleteById(id);
    }

    /**
     * Calculates the cosine similarity between user queries and product data, and filters based on a score threshold.
     *
     * <p>Note:</p>
     * <ul>
     *     <li><b>Cosine Similarity Threshold:</b> A threshold score of 0.85 is used to filter out less relevant results.
     *         Products with a cosine similarity score above this threshold are considered relevant.</li>
     *     <li><b>Handling Product Queries with Additional Words:</b> User queries may contain additional non-relevant words
     *         that can affect the cosine similarity score. For example, in a query like "Best for you organics sale for 20%
     *         in the winter":
     *         <ul>
     *             <li>The segment "Best for you organics" is recognized as the product name and may yield a cosine similarity
     *                 score above 0.85 if it closely matches a product.</li>
     *             <li>The additional details such as "sale for 20% in the winter" may affect the score. If these details dilute
     *                 the relevance of the product details, the final score might fall below 0.85.</li>
     *         </ul>
     *     </li>
     *     <li><b>Result Adjustment:</b> If the cosine similarity score falls below 0.85 due to non-relevant words, the product
     *         may not appear in the result list. The algorithm may need adjustments to better account for the relevance of
     *         product names despite additional query details.</li>
     *     <li><b>Product Not Found:</b> If no products meet the threshold, a "Product not found" message will be returned to the user.</li>
     * </ul>
     *
     * @param queryEmbedding The data for products to compare.
     * @return A list of relevant productIds based on the cosine similarity score.
     */

    public List<String> searchSimilarProductEmbeddings(Float[] queryEmbedding) {

        // Fetch all product embeddings
        List<ProductEmbedding> allEmbeddings = (List<ProductEmbedding>) productEmbeddingRepository.findAll();

        // Calculate cosine similarity and filter based on score > 0.85
        List<ProductEmbedding> similarProductEmbeddings = allEmbeddings.stream()
                .sorted(Comparator.comparingDouble(embedding -> -cosineSimilarity(queryEmbedding, embedding.getEmbedding()))) // Sort by similarity in descending order
                .filter(embedding -> cosineSimilarity(queryEmbedding, embedding.getEmbedding()) > 0.85) // Only keep embeddings with a cosine similarity greater than 0.85, as this indicates strong similarity to the queryEmbedding.
                .limit(5)  // Limit to top 5 results
                .toList();

        return similarProductEmbeddings.stream().map(ProductEmbedding::getProductId).toList();
    }

    private double cosineSimilarity(Float[] vectorA, List<Float> vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB.get(i);
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB.get(i), 2);
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    public List<Float> fetchProductEmbeddings(Product product) {
        return productEmbeddingAdapter.getProductEmbeddings(
                productEmbeddingEndpoint,
                new EmbeddingReq(product)).block();
    }
}
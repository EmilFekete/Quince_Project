package marketPlace.services;

import marketPlace.controller.model.ProductModel;
import marketPlace.controller.model.SellerModel;
import marketPlace.repository.Seller;

import java.util.List;
import java.util.Optional;

public interface SellerService {
    String saveSeller(SellerModel sellerModel);

    String deleteSeller(int sellerId);

    SellerModel getSellerById(int sellerId);

    List<SellerModel> getAllSellers();

    List<ProductModel> getProductsBySeller(int sellerId);
}

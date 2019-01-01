package marketPlace.services;

import marketPlace.controller.model.ProductModel;
import marketPlace.controller.model.SellerModel;
import marketPlace.environment.mapper.Repository_ServicesMapper;
import marketPlace.environment.mapper.Service_ControllerMapper;
import marketPlace.repository.Seller;
import marketPlace.repository.ProductRepository;
import marketPlace.repository.SellerRepository;
import marketPlace.services.domain.SellerDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@ComponentScan(basePackages = "marketPlace.environment")
public class SellerServiceDbImplementation implements SellerService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private Service_ControllerMapper service_controllerMapper;

    @Autowired
    private Repository_ServicesMapper repository_serviceMapper;


    public String saveSeller(SellerModel sellerModel) {
        SellerDomain sellerDomain = service_controllerMapper.sellerModelToSellerDomain(sellerModel);
        sellerRepository.save(repository_serviceMapper.sellerDomainToNewSeller(sellerDomain));
        return "Saved sellerDomain";
    }

    public SellerModel getSellerById(int sellerId) {
        SellerDomain sellerDomain = repository_serviceMapper.sellerToSellerDomain(sellerRepository.findById(sellerId).get());
        return service_controllerMapper.sellerDomainToSellerModel(sellerDomain);
    }

    @Override
    public List<SellerModel> getAllSellers() {
        Iterable<Seller> iterableSellers = sellerRepository.findAll();
        List<SellerDomain> sellerDomains = new ArrayList<>();
        iterableSellers.forEach(seller -> sellerDomains.add(repository_serviceMapper.sellerToSellerDomain(seller)));
        return sellerDomains.stream().map(sellerDomain -> service_controllerMapper.sellerDomainToSellerModel(sellerDomain)).collect(Collectors.toList());
    }

    @Override
    public String deleteSeller(int sellerId) {
        sellerRepository.deleteById(sellerId);
        return "Successful delete";
    }

    @Override
    public List<ProductModel> getProductsBySeller(int sellerId) {
        SellerDomain sellerDomain = repository_serviceMapper.sellerToSellerDomain(sellerRepository.findById(sellerId).get());
        return sellerDomain.getProducts().stream().map(productDomain -> service_controllerMapper.productDomainToProductModel(productDomain)).collect(Collectors.toList());
    }
}

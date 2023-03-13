package com.naumovets.market.core.soap.endpoints;

import com.naumovets.market.api.exceptions.ResourceNotFoundException;
import com.naumovets.market.core.service.products.ProductService;
import com.naumovets.market.core.soap.product.*;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.function.Function;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {
    private final static String NAMESPACE_URI = "http://www.naumovets.com/spring/ws/products";
    private final ProductService productService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) {
        GetProductByIdResponse response = new GetProductByIdResponse();
        Product product = productService.findById(request.getId()).map(productToSOAPProduct).orElseThrow(() -> new ResourceNotFoundException("Продукт не найден"));
        response.setProduct(product);

        return response;
    }

    //маппер из сущности продукта в SOAP продукт
    public Function<com.naumovets.market.core.entities.products.Product, Product> productToSOAPProduct = product -> {
        Product soapProduct = new Product();
        soapProduct.setId(product.getId());
        soapProduct.setTitle(product.getTitle());
        soapProduct.setCost(product.getCost().intValue());
        soapProduct.setGroupTitle(product.getCategory().getTitle());

        return soapProduct;
    };

    /*
        Пример запроса: POST http://localhost:8190/ws/
        Header -> Content-Type: text/xml

        //Получить все продукты
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:f="http://www.naumovets.com/spring/ws/products">
            <soapenv:Header/>
            <soapenv:Body>
                <f:getAllProductsRequest/>
            </soapenv:Body>
        </soapenv:Envelope>

        //Получить конкретный продукт
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:f="http://www.naumovets.com/spring/ws/products">
            <soapenv:Header/>
            <soapenv:Body>
                <f:getProductByIdRequest>
                    <f:id>3</f:id>
                </f:getProductByIdRequest>
            </soapenv:Body>
        </soapenv:Envelope>
     */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllStudents(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        productService.findAll().stream().map(productToSOAPProduct).forEach(response.getProducts()::add);
        return response;
    }
}

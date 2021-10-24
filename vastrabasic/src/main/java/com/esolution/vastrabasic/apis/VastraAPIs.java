package com.esolution.vastrabasic.apis;

import com.esolution.vastrabasic.apis.request.LoginRequest;
import com.esolution.vastrabasic.apis.request.RegisterDesignerRequest;
import com.esolution.vastrabasic.apis.request.RegisterShopperRequest;
import com.esolution.vastrabasic.apis.response.APIResponse;
import com.esolution.vastrabasic.apis.response.LoginResponse;
import com.esolution.vastrabasic.apis.response.RegisterDesignerResponse;
import com.esolution.vastrabasic.apis.response.RegisterUserResponse;
import com.esolution.vastrabasic.models.Catalogue;
import com.esolution.vastrabasic.models.Designer;
import com.esolution.vastrabasic.models.ProductFilter;
import com.esolution.vastrabasic.models.User;
import com.esolution.vastrabasic.models.product.Color;
import com.esolution.vastrabasic.models.product.Material;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.models.product.ProductColor;
import com.esolution.vastrabasic.models.product.ProductInventory;
import com.esolution.vastrabasic.models.product.ProductSize;
import com.esolution.vastrabasic.models.product.ProductType;
import com.google.gson.JsonElement;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface VastraAPIs {

    // ------------- User --------------

    @POST("/user")
    Observable<APIResponse<RegisterUserResponse>> registerShopper(@Body RegisterShopperRequest registerShopperRequest);

    @POST("/fd/user")
    Observable<APIResponse<RegisterDesignerResponse>> registerDesigner(@Body RegisterDesignerRequest registerDesignerRequest);

    @POST("/login")
    Observable<APIResponse<LoginResponse>> login(@Body LoginRequest loginRequest);

    @PUT("/user")
    Observable<APIResponse<JsonElement>> updateShopper(@Body User user);

    @PUT("/fd/user")
    Observable<APIResponse<JsonElement>> updateDesigner(@Body Designer designer);


    // ------------- Catalogue --------------

    @POST("/catalogue")
    Observable<APIResponse<Catalogue>> createCatalogue(@Header("token") String token, @Body Catalogue catalogue);

    @GET("/catalogue/list")
    Observable<APIResponse<List<Catalogue>>> getCatalogues(@Query("designerId") int designerId);


    // ------------- Product --------------

    @POST("/product")
    Observable<APIResponse<Product>> createProduct(@Body Product product);

    @PUT("/product")
    Observable<APIResponse<Product>> updateProduct(@Body Product product);

    // show product list with filter -> fd
    @POST("/product/list")
    Observable<APIResponse<List<Product>>> getProducts(@Body ProductFilter productFilter);

    // delete product
    @DELETE("/product")
    Observable<APIResponse<JsonElement>> deleteProduct(@Body int productId);


    // ------------- Product Color --------------

    // add product color -> while updating inventory -> call API add inventories
    @POST("/product/color")
    Observable<APIResponse<ProductColor>> addProductColor(@Body ProductColor productColor);

    // update product color -> while updating inventory
    @PUT("/product/color")
    Observable<APIResponse<ProductColor>> updateProductColor(@Body ProductColor productColor);

    // delete product color -> while updating inventory -> also delete inventory
    @DELETE("/product/color")
    Observable<APIResponse<JsonElement>> deleteProductColor(@Body int productColorId);

    // get all colors
    @GET("/color/list")
    Observable<APIResponse<List<Color>>> getColors();


    // ------------- Product Size --------------

    // add product size -> while updating inventory -> call API add inventories
    @POST("/product/size")
    Observable<APIResponse<ProductSize>> addProductSize(@Body ProductSize productSize);

    // add product size -> while updating inventory
    @PUT("/product/size")
    Observable<APIResponse<ProductSize>> updateProductSize(@Body ProductSize productSize);

    // add product size -> while updating inventory -> also delete inventory
    @DELETE("/product/size")
    Observable<APIResponse<JsonElement>> deleteProductSize(@Body int productSizeId);

    // get all sizes of a designer having same product type
    @GET("/product/size/list")
    Observable<APIResponse<JsonElement>> getProductSizes(@Query("designerId") int designerId,
                                                         @Query("productTypeId") int productTypeId);


    // ------------- Product Inventory --------------

    // add inventories
    @POST("/product/inventory/list")
    Observable<APIResponse<JsonElement>> addProductInventories(@Body List<ProductInventory> productInventories);

    // update inventories
    @PUT("/product/inventory/list")
    Observable<APIResponse<JsonElement>> updateProductInventories(@Body List<ProductInventory> productInventories);


    // ------------- Product Type --------------

    // get all product types
    @GET("/product/type/list")
    Observable<APIResponse<List<ProductType>>> getProductTypes();


    // ------------- Product Material --------------

    // get all materials
    @GET("/material/list")
    Observable<APIResponse<List<Material>>> getMaterials();

    // add material
    @POST("/material")
    Observable<APIResponse<Material>> addMaterial(@Body String materialName);


    // ------------- Upload Image --------------

    // upload image
    @Multipart
    @POST("/upload")
    Observable<APIResponse<String>> uploadImage(@Part MultipartBody.Part image);

}

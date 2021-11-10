package com.esolution.vastrabasic.apis;

import com.esolution.vastrabasic.apis.request.LoginRequest;
import com.esolution.vastrabasic.apis.request.RegisterDesignerRequest;
import com.esolution.vastrabasic.apis.request.RegisterShopperRequest;
import com.esolution.vastrabasic.apis.response.APIResponse;
import com.esolution.vastrabasic.apis.response.LoginResponse;
import com.esolution.vastrabasic.apis.response.RegisterDesignerResponse;
import com.esolution.vastrabasic.apis.response.RegisterUserResponse;
import com.esolution.vastrabasic.models.BasicCatalogue;
import com.esolution.vastrabasic.models.Catalogue;
import com.esolution.vastrabasic.models.Designer;
import com.esolution.vastrabasic.models.ProductFilter;
import com.esolution.vastrabasic.models.User;
import com.esolution.vastrabasic.models.product.BasicProduct;
import com.esolution.vastrabasic.models.product.Color;
import com.esolution.vastrabasic.models.product.Material;
import com.esolution.vastrabasic.models.product.Product;
import com.esolution.vastrabasic.models.product.ProductColor;
import com.esolution.vastrabasic.models.product.ProductInventory;
import com.esolution.vastrabasic.models.product.ProductSize;
import com.esolution.vastrabasic.models.product.ProductType;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface VastraAPIs {

    // ------------- User --------------

    @POST("user")
    Observable<APIResponse<RegisterUserResponse>> registerShopper(@Body RegisterShopperRequest registerShopperRequest);

    @POST("fd/user")
    Observable<APIResponse<RegisterDesignerResponse>> registerDesigner(@Body RegisterDesignerRequest registerDesignerRequest);

    @POST("login")
    Observable<APIResponse<LoginResponse>> login(@Body LoginRequest loginRequest);

    @PUT("user")
    Observable<APIResponse<JsonElement>> updateShopper(@Header("token") String token, @Body User user);

    @PUT("fd/user")
    Observable<APIResponse<JsonElement>> updateDesigner(@Header("token") String token, @Body Designer designer);

    @GET("fd/user/list")
    Observable<APIResponse<List<Designer>>> getDesigners();


    // ------------- Catalogue --------------

    @POST("catalogue")
    Observable<APIResponse<Catalogue>> createCatalogue(@Header("token") String token, @Body Catalogue catalogue);

    @GET("catalogue/list")
    Observable<APIResponse<List<BasicCatalogue>>> getCatalogues(@Header("token") String token, @Query("designerId") int designerId);


    // ------------- Product --------------

    @POST("product")
    Observable<APIResponse<Product>> createProduct(@Header("token") String token, @Body Product product);

    @PUT("product")
    Observable<APIResponse<Product>> updateProduct(@Header("token") String token, @Body Product product);

    // show product list with filter -> fd
    @POST("product/list")
    Observable<APIResponse<List<Product>>> getProducts(@Header("token") String token, @Body ProductFilter productFilter);

    @GET("product/fd/catalogue/list")
    Observable<APIResponse<List<BasicProduct>>> getCatalogueProducts(@Header("token") String token,
                                                                    @Query("catalogueId") int catalogueId);

    // delete product
    @DELETE("product")
    Observable<APIResponse<JsonElement>> deleteProduct(@Header("token") String token, @Query("productId") int productId);

    @GET("product/basiclist/fd/type")
    Observable<APIResponse<List<BasicProduct>>> getDesignerProductsByTypes(@Header("token") String token,
                                                                           @Query("designerId") int designerId,
                                                                           @Query("productTypeId") int productType);

    @GET("product/exist/fd")
    Observable<APIResponse<Boolean>> isDesignerProductsExist(@Header("token") String token,
                                                                        @Query("designerId") int designerId);

    // ------------- Product Color --------------

    // add product color -> while updating inventory -> call API add inventories
    @POST("product/color")
    Observable<APIResponse<ProductColor>> addProductColor(@Header("token") String token, @Body ProductColor productColor);

    // update product color -> while updating inventory
    @PUT("product/color")
    Observable<APIResponse<ProductColor>> updateProductColor(@Header("token") String token, @Body ProductColor productColor);

    // delete product color -> while updating inventory -> also delete inventory
    @DELETE("product/color")
    Observable<APIResponse<JsonElement>> deleteProductColor(@Header("token") String token, @Body int productColorId);

    // get all colors
    @GET("color/list")
    Observable<APIResponse<ArrayList<Color>>> getColors();


    // ------------- Product Size --------------

    // add product size -> while updating inventory -> call API add inventories
    @POST("product/size")
    Observable<APIResponse<ProductSize>> addProductSize(@Header("token") String token, @Body ProductSize productSize);

    // add product size -> while updating inventory
    @PUT("product/size")
    Observable<APIResponse<ProductSize>> updateProductSize(@Header("token") String token,
                                                           @Body ProductSize productSize);

    // add product size -> while updating inventory -> also delete inventory
    @DELETE("product/size")
    Observable<APIResponse<JsonElement>> deleteProductSize(@Header("token") String token,
                                                           @Body int productSizeId);

    @GET("product/size/list")
    Observable<APIResponse<List<ProductSize>>> getProductSizes(@Header("token") String token,
                                                               @Query("designerId") int designerId,
                                                               @Query("productId") int productId);

    @GET("product/customSize/list")
    Observable<APIResponse<List<String>>> getCustomProductSizes();


    // ------------- Product Inventory --------------

    // add inventories
    @POST("product/inventory/list")
    Observable<APIResponse<JsonElement>> addProductInventories(@Header("token") String token,
                                                               @Body List<ProductInventory> productInventories);

    // update inventories
    @PUT("product/inventory/list")
    Observable<APIResponse<JsonElement>> updateProductInventories(@Header("token") String token,
                                                                  @Body List<ProductInventory> productInventories);


    // ------------- Product Type --------------

    // get all product types
    @GET("product/type/list")
    Observable<APIResponse<List<ProductType>>> getProductTypes();


    // ------------- Product Material --------------

    // get all materials
    @GET("material/list")
    Observable<APIResponse<List<Material>>> getMaterials();


    // add material
    @POST("material")
    Observable<APIResponse<Material>> addMaterial(@Header("token") String token, @Body String materialName);


    // ------------- Upload Image --------------

    // upload image
    @Multipart
    @POST("upload")
    Observable<APIResponse<String>> uploadImage(@Header("token") String token, @Part MultipartBody.Part file);

}

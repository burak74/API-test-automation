package demo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;

import static io.restassured.RestAssured.given;

import java.io.File;

public class ECommerceAPITest {

	public static void main(String[] args) {
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.setContentType(ContentType.JSON).build();
		
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserEmail("burakalkan74@gmail.com");
		loginRequest.setUserPassword("A453eqwqwbrk_");
		
		RequestSpecification reqLogin = given().log().all().spec(req).body(loginRequest);
		LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login").then().extract().response().as(LoginResponse.class);
		
		System.out.println(loginResponse.getToken());
		System.out.println(loginResponse.getUserId());
		
		String token=loginResponse.getToken();
		String userId=loginResponse.getUserId();
		
		
		//Add Product
		
		RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization", token)
				.build();
		RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseReq).param("productName", "Laptop")
		.param("productAddedBy", userId).param("productCategory", "fashion")
		.param("productSubCategory", "shirts").param("productPrice", "11500")
		.param("productDescription", "Lenovo").param("productFor", "men")
		.multiPart("productImage", "/Rest Assured automation script for OAuth end to end API's test/src/demo/f76d0412008ec6ccc2b4e8b4bc2fe923.png");
		
		String addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product")
		.then().log().all().extract().response().asString();
		
		JsonPath js = new JsonPath(addProductResponse);
		
		String productId = js.get("productId");
		
		
		

	}

}

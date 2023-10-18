package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPointsProperties;
import api.payload.User;
import io.restassured.response.Response;

public class UserTestsProperties {
	
	Faker faker;
	User userPayload;
	
	public Logger logger;  // for logs
	
	@BeforeClass
	public void setup() 
	{
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
	
		//logs
		logger= LogManager.getLogger(this.getClass());
		
	}
	
	@Test(priority=1)
	public void testPostUser()
	{
		logger.info("******** Creating User *********");
		Response response = UserEndPointsProperties.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("******** User is created  *********");
	}
	
	@Test(priority=2)
	public void testGetUserByName()
	{
		logger.info("******** Reading user info *********");
		
		Response response = UserEndPointsProperties.readUser(this.userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("******** User info is dispayed *********");
	}
	
	@Test(priority=3)
	public void testUpdateUserByName()
	{
		logger.info("******** Updating User *********");
		
		//Update data- using payload
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		
		Response response = UserEndPointsProperties.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);   
		
		logger.info("******** User Updated *********");
		
		//Checking data after update
		
		Response responseAfterUpdate = UserEndPointsProperties.readUser(this.userPayload.getUsername());
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
				
	}
	
	@Test(priority=4)
	public void testDeleteUserByName()
	
	{
		logger.info("******** Deleting User *********");
		
		Response response = UserEndPointsProperties.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
			
		logger.info("********  User Deleted *********");
	}
	

}
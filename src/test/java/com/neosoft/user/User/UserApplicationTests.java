package com.neosoft.user.User;

import com.neosoft.user.User.entity.User;
import com.neosoft.user.User.enumeration.Gender;
import com.neosoft.user.User.repository.UserRepository;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserApplicationTests {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TestRestTemplate testRestTemplate;
	HttpHeaders headers;
	HttpEntity<String> entity;
	Long id;

	@BeforeEach
	public void setup() {
		headers= new HttpHeaders();
		entity=new HttpEntity<String>(null,headers);
	}

	@AfterEach
	public  void clean() {
		userRepository.deleteAll();
	}

	public void init() throws ParseException {
		Date dob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-03-03");
		Date joining = new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-03");
		User user = new User("ajinkya","pethkar","ajinkya@gmail.com","Nashik","1234567890", Gender.MALE,422005,dob,joining);
		ResponseEntity<User> saveUser=testRestTemplate.postForEntity(getRootUrl(),user,User.class,"");
		id=saveUser.getBody().getUserId();
	}

	@LocalServerPort
	private int port;
	private String getRootUrl(){
		return "http://localhost:"+port+"/v1/api/user";
	}

	@Test
	public void testGetAllUsers() throws JSONException, ParseException {
		init();
		ResponseEntity<String> responseEntity=testRestTemplate.exchange(getRootUrl(), HttpMethod.GET,entity,String.class);
		Assert.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
	}
	@Test
	public void testCreateUser() throws JSONException, ParseException {
		init();
		Date dob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-03-03");
		Date joining = new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-03");
		User user = new User("swaraj","pethkar","swaraj@gmail.com","Nashik","1234567891", Gender.MALE,422005,dob,joining);
		ResponseEntity<User> responseEntity=testRestTemplate.postForEntity(getRootUrl(),user,User.class,"");
		Assert.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
	}

	@Test
	public void testDuplicateEmailUser() throws JSONException, ParseException {
		init();
		Date dob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-03-03");
		Date joining = new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-03");
		User user = new User("swaraj","pethkar","ajinkya@gmail.com","Nashik","1234567891", Gender.MALE,422005,dob,joining);
		ResponseEntity<User> responseEntity=testRestTemplate.postForEntity(getRootUrl(),user,User.class,"");
		Assert.assertNotEquals(HttpStatus.OK,responseEntity.getStatusCode());
	}

	@Test
	public void testDuplicatePhoneUser() throws JSONException, ParseException {
		init();
		Date dob = new SimpleDateFormat("yyyy-MM-dd").parse("2020-03-03");
		Date joining = new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-03");
		User user = new User("swaraj","pethkar","swaraj@gmail.com","Nashik","1234567890", Gender.MALE,422005,dob,joining);
		ResponseEntity<User> responseEntity=testRestTemplate.postForEntity(getRootUrl(),user,User.class,"");
		Assert.assertNotEquals(HttpStatus.OK,responseEntity.getStatusCode());
	}

	@Test
	public void testUpdateUser() throws JSONException, ParseException {
		init();
		User user=testRestTemplate.getForObject(getRootUrl()+"/"+id,User.class);
		user.setUserName("sham");
		user.setLastName("pagar");
		testRestTemplate.put(getRootUrl()+"/"+id,user);
		ResponseEntity<String> responseEntity=testRestTemplate.exchange(getRootUrl()+"/"+id,HttpMethod.GET,entity,String.class);
		String expected="{\"userId\":"+id+",\"userName\":\"sham\",\"lastName\":\"pagar\",\"email\":\"ajinkya@gmail.com\",\"address\":\"Nashik\",\"phone\":\"1234567890\",\"gender\":\"MALE\",\"pincode\":422005,\"dob\":\"2020-03-03T00:00:00.000+00:00\",\"joining\":\"2020-06-03T00:00:00.000+00:00\",\"isDeleted\":false}\n";
		System.out.println(responseEntity.getBody().toString());
		System.out.println(expected);
		Assert.assertEquals(expected.toString().trim(),responseEntity.getBody().toString().trim());
	}

	@Test
	public void testHardDeleteUser() throws JSONException, ParseException {
		init();
		testRestTemplate.exchange(getRootUrl()+"/"+id,HttpMethod.DELETE,entity,String.class);
		ResponseEntity<String> response2=testRestTemplate.exchange(getRootUrl()+"/"+id,HttpMethod.GET,entity,String.class);
		Assert.assertEquals(HttpStatus.NOT_FOUND,response2.getStatusCode());
	}
}

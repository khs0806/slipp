package net.slipp.domain.users;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserTest {
	private static final Logger log = LoggerFactory.getLogger(UserTest.class);
	
	private static Validator validator;
	
	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void userIdWhenIsEmpty() {
		User user = new User("", "password", "name", "asd@asd.com");
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
		assertThat(constraintViolations.size(), is(2));
		
		for (ConstraintViolation<User> constraintViolation : constraintViolations) {
			log.debug("error : {}", constraintViolation.getMessage());
		}
	}
	
	@Test
	public void matchPassword() throws Exception {
		String password = "password";	
		Authenticate authenticate = new Authenticate("userId", password);
		User user = new User("userId", password, "name", "asd@asd.com");
		boolean actual = user.matchPassword(authenticate);
		assertTrue(actual);
		
		String password2 = "password2";
		authenticate = new Authenticate("userId", password2);
		boolean actual2 = user.matchPassword(authenticate);
		assertFalse(actual2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void updateWhenMisMatchUserId() throws Exception{
		User user = new User("asd", "1234", "asd", "asd@naver.com");
		User updateUser = new User("boydsa", "1234", "khs", "boydsa12@naver.com");
		user.update(updateUser);
	}
	
	@Test
	public void update() throws Exception{
		User user = new User("boydsa", "1234", "asd", "asd@naver.com");
		User updateUser = new User("boydsa", "1234", "khs", "boydsa12@naver.com");
		User updatedUser = user.update(updateUser);
		assertThat(updatedUser, is(updateUser));
	}
}

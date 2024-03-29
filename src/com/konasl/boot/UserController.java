package com.konasl.boot;

/**
 * A class to test interactions with the MySQL database using the UserDao class.
 *
 * @author Toufiq Mahmud
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	// ------------------------
	// PRIVATE FIELDS
	// ------------------------

	@Autowired
	private UserDao userDao;

	// ------------------------
	// PUBLIC METHODS
	// ------------------------

	@RequestMapping("/")
	@ResponseBody
	public String index() {
		return "Hellow World";
	}

	/**
	 * /create --> Create a new user and save it in the database.
	 * 
	 * @param email
	 *            User's email
	 * @param name
	 *            User's name
	 * @return A string describing if the user is succesfully created or not.
	 */
	/*
	 * Sample execution command -
	 * http://localhost:8080/create?email=toufiq@gmail.com&name=toufiq
	 */
	@RequestMapping("/create")
	@ResponseBody
	public String create(String email, String name) {
		User user = null;
		try {
			user = new User(email, name);
			userDao.save(user);
		} catch (Exception ex) {
			return "Error creating the user: " + ex.toString();
		}
		return "User succesfully created! (id = " + user.getId() + ")";
	}

	/**
	 * /delete --> Delete the user having the passed id.
	 * 
	 * @param id
	 *            The id of the user to delete
	 * @return A string describing if the user is succesfully deleted or not.
	 */
	/*
	 * Sample execution command - http://localhost:8080/delete?id=2
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(long id) {
		try {
			User user = new User(id);
			userDao.delete(user);
		} catch (Exception ex) {
			return "Error deleting the user: " + ex.toString();
		}
		return "User succesfully deleted!";
	}

	/**
	 * /get-by-email --> Return the id for the user having the passed email.
	 * 
	 * @param email
	 *            The email to search in the database.
	 * @return The user id or a message error if the user is not found.
	 */
	/*
	 * Sample execution command -
	 * http://localhost:8080/get-by-email?email=toufiq@gmail.com
	 */
	@RequestMapping("/get-by-email")
	@ResponseBody
	public String getByEmail(String email) {
		String userId;
		String userName;
		try {
			User user = userDao.findByEmail(email);
			userId = String.valueOf(user.getId());
			userName = user.getName();
		} catch (Exception ex) {
			return "User not found";
		}
		return "The user id is: " + userId + " user name is: " + userName;
	}

	/**
	 * /get-by-name --> Return the id for the user having the passed name.
	 * 
	 * @param name
	 *            The name to search in the database.
	 * @return The user id or a message error if the user is not found.
	 */
	/*
	 * Sample execution command - http://localhost:8080/get-by-name?name=toufiq
	 */
	@RequestMapping("/get-by-name")
	@ResponseBody
	public String getByName(String name, String email) {
		System.out.println("getByName");
		String userId;
		String userEmail;
		try {
			User user = userDao.findByName(name, email);
			userId = String.valueOf(user.getId());
			userEmail = user.getEmail();
			System.out.println("user ID is : " + user.getId());
		} catch (Exception ex) {
			return "User not found";
		}
		return "The user id is: " + userId + " user email is: " + userEmail;
	}

	/**
	 * /update --> Update the email and the name for the user in the database
	 * having the passed id.
	 * 
	 * @param id
	 *            The id for the user to update.
	 * @param email
	 *            The new email.
	 * @param name
	 *            The new name.
	 * @return A string describing if the user is succesfully updated or not.
	 */
	/*
	 * Sample execution command -
	 * http://localhost:8080/update?id=2&email=toufiq@gmail.com&name=toufiq
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String updateUser(long id, String email, String name) {
		try {
			User user = userDao.findOne(id);
			user.setEmail(email);
			user.setName(name);
			userDao.save(user);
		} catch (Exception ex) {
			return "Error updating the user: " + ex.toString();
		}
		return "User succesfully updated!";
	}

} // class UserController
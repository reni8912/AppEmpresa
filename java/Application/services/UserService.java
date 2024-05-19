package Application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Application.models.User;
import Application.repositories.UserRepository;

@Service
public class UserService {

	
	@Autowired
	UserRepository userRepository;
	
	public int registerNewUserSericeMethod(String nombre, String email, String password) {
		return userRepository.registerNewUser(nombre, email, password);
	}
	
	public String checkUserPassword(String email){
		return userRepository.CheckPassword(email);
	}
	
	public User userInfo(int id){
		return userRepository.UserInfo(id);
	}
	
	public User userInfoEmail(String email){
		return userRepository.UserInfoEmail(email);
	}
	
	
	
	
}

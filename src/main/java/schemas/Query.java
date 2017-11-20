package schemas;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

import models.Price;
import models.User;
import repositories.UserRepository;

public class Query implements GraphQLRootResolver {
	private final UserRepository userRepository;
	
	
	public Query(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public List<User> allUsers() {
		return userRepository.getAllUsers();
	}
	
	public User getUserById(String id) {
		return userRepository.findById(id);
	}
	
	public Price getPrice() {
		Price price = userRepository.getPrice();
		return price;
	}
	
}

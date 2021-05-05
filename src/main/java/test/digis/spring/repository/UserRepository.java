package test.digis.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import test.digis.spring.model.User;

public interface UserRepository extends MongoRepository<User, String> {
}

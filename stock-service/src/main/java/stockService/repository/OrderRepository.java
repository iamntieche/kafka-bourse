package stockService.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import stockService.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Order> findById(Long id);
}

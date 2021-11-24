package stockService.logic;



import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stockService.model.Order;
import stockService.repository.OrderRepository;

@Service
public class OrderLogic {

	private OrderRepository repository;
	
	public OrderLogic(OrderRepository repository) {
		this.repository = repository;
	}
	
	public Order add(Order order) {
		return repository.save(order);
	}
	
	@Transactional
	public boolean performUpdate(Long buyOrderId, Long sellOrderId, int amount) {
		Order buyOrder = repository.findById(buyOrderId).orElseThrow( () ->  new RuntimeException("buy order doesn't exit with id "+buyOrderId));
		Order sellOrder = repository.findById(sellOrderId).orElseThrow(() -> new RuntimeException("sell order doesn't exist with id "+sellOrderId));
		int buyAvailableCount = buyOrder.getProductCount() - buyOrder.getRealizedCount();
		int selleAvailableCount = sellOrder.getProductCount() - sellOrder.getRealizedCount();
		
		if(buyAvailableCount < amount && selleAvailableCount < amount) {
			return false;
		}
		
		buyOrder.setRealizedCount(buyOrder.getRealizedCount() + amount);
		sellOrder.setRealizedCount(sellOrder.getRealizedCount() + amount);
		repository.save(buyOrder);
		repository.save(sellOrder);
		return true;
	}
}

package stockService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class OrdersSellBuy {

	private int sellCount;
	private int buyCount;

	public OrdersSellBuy addSell(int sellCount) {
		this.sellCount += sellCount;
		return this;
	}

	public OrdersSellBuy addBuy(int buyCount) {
		this.buyCount += buyCount;
		return this;
	}
}

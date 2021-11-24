package stockService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class TransactionTotalWithProduct {

	private Transaction transaction;
	private Integer productId;
}

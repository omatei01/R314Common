package ro.anzisoft.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MfaStockType {

	String itemCode = "";
	int quantity = 0;
}
